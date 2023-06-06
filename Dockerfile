FROM openjdk:19
EXPOSE 9090
ADD target/spring-boot1.jar spring-boot1.jar
ENTRYPOINT ["java","-jar","/spring-boot1.jar"]