# Pull base image.
FROM ubuntu:latest

RUN \
# Update
apt-get update -y && \
# Install Java
apt-get install default-jre -y

ADD ./target/eventgram-1.0.jar eventgram.jar

EXPOSE 8090

CMD java -jar eventgram.jar