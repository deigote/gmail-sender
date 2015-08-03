# gmail-sender
A command line tool for sending emails through Gmail SMTP using gradle and groovy

### What

This is a command line tool for sending messages through the normal SMTP Gmail service - in other words, what your email client might be doing if you use SMTP, but from the command line.

### Why

I needed to send emails when certain things happen in some servers I have for personal stuff, and I didn't want to deal with mail services.

### How

gmail-sender is built using groovy and gradle, but neither of them need to be installed. You do need an installed JVM or docker to use it.

#### With Java

Assuming you have a shell with a valid ```JAVA_HOME``` environment variable, enter the project's directory and execute:

```./gradlew sendMail -Pusername=yourGmailEmail -Ppassword=yourGmailPassword -Pto=some@guy.com,some@gal.com -Psubject='The subject' -Pbody='Some <strong>HTML</strong> body to love'```

#### With docker

Run:

```docker run -it --rm -v $HOME/docker-dotgradle:/root/.gradle -v (pwd)/gmail-sender:/app java bash -c 'cd /app && ./gradlew sendMail -Pusername=yourGmailEmail -Ppassword=yourGmailPassword -Pto=some@guy.com,some@gal.com -Psubject="The subject" -Pbody="Some <strong>HTML</strong> body to love"```

This will create a ```$HOME/docker-dotgradle``` directory with the gradle wrapper and the maven dependencies. You can adjust it to whatever you want (even ```$HOME/.gradle``` if you are using gradle in the host machine).
