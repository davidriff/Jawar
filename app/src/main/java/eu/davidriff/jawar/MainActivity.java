package eu.davidriff.jawar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;

public class MainActivity extends AppCompatActivity {


    public static final int JOB_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scheduleJob();
    }




    private void scheduleJob() {
        ComponentName serviceName = new ComponentName(this, JawarService.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(15000)//execute every 32 seconds (30 seconds of recording)
                .setPersisted(true)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int result=scheduler.schedule(jobInfo);
        if (result == JobScheduler.RESULT_SUCCESS) {
            System.out.println("Schedule created successfuly !");
        }
    }
}
