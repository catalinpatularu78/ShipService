FROM openjdk:8-jre-alpine3.9
EXPOSE 9091
ADD ship-service/target/ship-service-0.0.1-SNAPSHOT.jar ship-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT [ "java","-jar","ship-service-0.0.1-SNAPSHOT.jar" ]
