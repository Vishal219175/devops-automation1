FROM openjdk:19
EXPOSE 9090
ADD target/OktaDemo-0.0.1-SNAPSHOT.jar OktaDemo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/OktaDemo-0.0.1-SNAPSHOT.jar"]