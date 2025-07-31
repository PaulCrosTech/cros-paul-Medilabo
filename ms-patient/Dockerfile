FROM maven:3.9.11-eclipse-temurin-21
LABEL authors="Paul CROS"

RUN mkdir /opt/app
ADD . /opt/app

WORKDIR /opt/app
RUN mvn clean package -DskipTests --update-snapshots --batch-mode --file ./pom.xml

EXPOSE 9005

ENTRYPOINT ["java", "-jar", "/opt/app/target/ms-patient-0.0.1-SNAPSHOT.jar"]



