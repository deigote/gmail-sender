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

Assuming you have a shell with ```docker``` in the path, enter the project's directory and execute:

```docker run -it --rm -v $HOME/docker-dotgradle:/root/.gradle -v (pwd):/app java bash -c 'cd /app && ./gradlew sendMail -Pusername=yourGmailEmail -Ppassword=yourGmailPassword -Pto=some@guy.com,some@gal.com -Psubject="The subject" -Pbody="Some <strong>HTML</strong> body to love"```

This will create a ```$HOME/docker-dotgradle``` directory with the gradle wrapper and the maven dependencies. You can adjust it to whatever you want (even ```$HOME/.gradle``` if you are using gradle in the host machine).

#### As a maven dependency in another Java based project

You'll need to add `http://deigote.github.io/maven/` as a maven repo, and `com.deigote:gmail-sender:0.1` as a dependency. Once done, you can use [GmailSender#instance]( https://github.com/deigote/gmail-sender/blob/master/src/main/groovy/com/deigote/gmailSender/GmailSender.groovy) and then [GmailSender#send](https://github.com/deigote/gmail-sender/blob/master/src/main/groovy/com/deigote/gmailSender/GmailSender.groovy#L27) to send an email, building first the [Email](https://github.com/deigote/gmail-sender/blob/master/src/main/groovy/com/deigote/gmailSender/Email.groovy) and the [Credentials](https://github.com/deigote/gmail-sender/blob/master/src/main/groovy/com/deigote/gmailSender/Credentials.groovy).

