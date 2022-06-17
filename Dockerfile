FROM openjdk:8-jre-slim
WORKDIR /usr/src/nds
COPY build/libs/*.jar ./k8sapi.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ENV ACTIVE_PROFILE dev

# EXPOSE 80/tcp


ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "/k8sapi.jar"]