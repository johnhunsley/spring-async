FROM openjdk:8
VOLUME /tmp
ADD target/spring-async-example-*.jar /app.jar
RUN bash -c 'touch /app.jar'
ENV JAVA_OPTS="-Xmx256m"
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]