FROM adoptopenjdk/openjdk11:alpine-jre
COPY target/config-server-1.0.0.jar /usr/src/myapp/app.jar
WORKDIR /usr/src/myapp
EXPOSE 8080
CMD ["java", "-Xms51config-server-1.0.0.jar 2M", "-Xmx768M", "-jar", "app.jar"]