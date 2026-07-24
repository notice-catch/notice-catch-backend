# 1단계: 빌드 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /application

# 타임존 데이터 설치 및 한국 시간(Asia/Seoul) 세팅
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

# 보안을 위한 non-root 유저 생성
RUN addgroup --system spring && adduser --system spring -G spring
USER spring:spring

# 같은 경로의 app.jar를 복사하도록 변경
COPY app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]