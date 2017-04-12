# Jawar

Jawar is a malware for Android. It is supposed to be an app for audio notes, so during installation any user will give it permission to access microphone. Once installed, the app will show a message like "Downloading plugins, come back later :)", but it's already stealing your data.

# What does it do ?

Jawar **always** keeps a service running in background, and records all the time using the microphone (remember that you granted permission for it).
After recording, Jawar sends the audio file through TCP to a remote server.
If the user closes the app or reboots the phone, the malicious Jawar service keeps running.  
