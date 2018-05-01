FROM airdock/oracle-jdk:1.8
VOLUME /tmp
ADD target/vod-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT [ "sh", "-c", "java -jar app.jar" ]
