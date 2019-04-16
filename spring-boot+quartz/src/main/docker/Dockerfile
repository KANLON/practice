FROM docker.io/java:8
RUN curl -q -L -C - -b "oraclelicense=accept-securebackup-cookie" -o /tmp/jce_policy-8.zip -O http://download.oracle.com/otn-pub/java/jce/8/jce_policy-8.zip \
    && unzip -oj -d /usr/lib/jvm/java-8-openjdk-amd64/jre/lib/security /tmp/jce_policy-8.zip \*/\*.jar \
    && rm /tmp/jce_policy-8.zip
VOLUME /tmp
ADD spring-boot-quartz*.jar app.jar
RUN bash -c 'touch app.jar'
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
