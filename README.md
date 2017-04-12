# Jawar

Jawar is a malware for Android. It is supposed to be an app for audio notes, so during installation any user will give it permission to access microphone. Once installed, the app will show a message like "Downloading plugins, come back later :)" (We could program a real functionality for the *legitim* app, but this is a Proof of Concept so we will stay with the message). At this point, the user closed the app and forgets about it for some time.

# What does it do ?

Jawar **always** keeps a service running in background and constantly records using the microphone (remember that user granted permission for it).
After recording, Jawar sends the audio file through TCP to a remote server.
If the user closes the app or reboots the phone, the malicious Jawar service keeps running (thanks to [scheduled services](https://developer.android.com/guide/components/services.html) of Android).  

# More info

- Works for Android 5.0 and above
- The malicious background service only starts if the phone has any kind of connection to the network/Internet.
- This service runs periodically (the period is changeable)
- It is persistent (keeps executing if Jawar app is closed or phone is rebooted)
