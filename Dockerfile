# 환경 설정
FROM openjdk:8-jre-slim
ENV ACTIVE_PROFILE dev

# jar파일 복사
COPY build/libs/*.jar k8sapi.jar

# 타임존 설정
RUN ln -sf /usr/share/zoneinfo/Asia/Seoul /etc/localtime

# 실행 포트 및 실행
EXPOSE 8080/tcp
ENTRYPOINT ["java", "-Dspring.profiles.active=${ACTIVE_PROFILE}", "-jar", "k8sapi.jar"]
