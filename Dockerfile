FROM openjdk:8-jre-slim
# make nds directory
RUN mkdir -p /usr/src/nds

WORKDIR /usr/src/nds
# COPY build/libs/*.jar ./k8sapi.jar
COPY build/libs/*.jar /usr/src/nds/k8sapi.jar

RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

ENV ACTIVE_PROFILE dev

# EXPOSE 80/tcp
EXPOSE 8080/tcp

# ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "/k8sapi.jar"]
CMD ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "k8sapi.jar"]