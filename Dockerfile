FROM openjdk:11.0.9-slim-buster

COPY ./target/todolist-0.0.1-SNAPSHOT.jar todolist-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","todolist-0.0.1-SNAPSHOT.jar"]