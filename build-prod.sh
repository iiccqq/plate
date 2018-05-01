docker run -it --rm --name my-maven-project -v "$(pwd)":/usr/src/mymaven -v maven-repo:/usr/share/maven/ref  -w /usr/src/mymaven maven:3.3-jdk-8 mvn clean install -P prod
docker build -t vod-server .
