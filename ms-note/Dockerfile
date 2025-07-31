FROM maven:3.9.11-eclipse-temurin-21
LABEL authors="Paul CROS"

RUN mkdir /opt/app-source && \
    mkdir /opt/app

ADD . /opt/app-source

WORKDIR /opt/app-source

RUN mvn clean compile --update-snapshots --batch-mode --file ./pom.xml \
    && mvn package -DskipTests --batch-mode --file ./pom.xml \
    && cp target/*.jar /opt/app
EXPOSE 9006

ENTRYPOINT ["java", "-jar", "/opt/app/ms-note-0.0.1-SNAPSHOT.jar"]


