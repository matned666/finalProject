# Training project management kit
<br>
A simple webapp for tasks management and projects creation for training purposes.  <br>
You can create projects and sell them ;) I wish you good luck.  <br>
Maybe it will be usefull some day :)  <br>
<br>
It is deployed at:
<html><a href="https://the-final-project.herokuapp.com/">https://the-final-project.herokuapp.com/</a></html>
<br>
Check it out and dont forget to send a feedback !! ;-)

## Usage

```
Just log in
and try it yourself
You can register a user
add tasks,
create a project and manage tasks for it
You can also add the project to the basket 

While login you can check remember me, 
to stay logged for some time even if the browser is closed

As an admin you can additionally manage users.

Check it out and send me feedback with a message form!!!

```

In order for the program to work, with your own database
you have to add to application.properties file all necessary data:

```
*** Database connection ***
spring.datasource.username
spring.datasource.password
spring.datasource.url
spring.jpa.properties.hibernate.dialect
spring.datasource.driver-class-name

*** Mail data ***
spring.mail.username
spring.mail.password
spring.mail.host
spring.mail.port
spring.mail.properties.mail.transport.protocol
spring.mail.properties.mail.smtp.port
spring.mail.properties.mail.smtp.auth
spring.mail.properties.mail.smtp.starttls.enable
spring.mail.properties.mail.smtp.starttls.required

*** domain url or if locally - localhost:8080 (or with other port) ***
www.domain.url

*** secret key for session tokens ***
secret.key.for.session.token

*** default admin user ***
default.admin.username
default.admin.password
```

It is in development stage, so look forward for future features:

```
Future TODO is -> basket and sales management
```



# License
  Copyright 2020 Mateusz Niedbał
  
  contact:  mat.niedbal6@gmail.com



