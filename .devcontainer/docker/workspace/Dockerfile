FROM openjdk:17-ea-jdk-buster

SHELL ["/bin/bash", "-c"]

ARG PKG="sudo git vim curl zip unzip"
RUN apt-get update \
    && apt-get install -y ${PKG}