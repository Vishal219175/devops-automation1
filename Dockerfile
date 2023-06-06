FROM openjdk:19
EXPOSE 9090
ADD target/oktademo-0.0.1.jar oktademo-0.0.1.jar
ENTRYPOINT ["java","-jar","/oktademo-0.0.1.jar"]