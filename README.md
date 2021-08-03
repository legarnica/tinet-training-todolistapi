# Getting Started.

### Requirements
+ Docker 20.10.7+
+ Docker-compose 1.29.2+

### Execute application.

run docker command.

```shell
docker build -t webapp .
```

After build, you must execute the next command.

```shell
docker-compose up
```

### Sagger ui
After run the application, go to swagger documentation: http://localhost:8080/swagger-ui.html

### Integrations services details

This application, is deploying with a Jenkins local instance and `AWS - Elastic Beanstalk` free service. See this [URL](http://todotinet.us-east-2.elasticbeanstalk.com), 
to try this application.

Docker Local - Jenkins execution:

```shell
docker run -p 8880:8080 -v /Users/luisherrera/projects/VOLUME-JENKINS --name=jenkins-local -d jenkins/jenkins:lts-jdk11
```