# 1단계: 빌드 스테이지
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
# plain jar 생성을 방지하고 배포용 bootJar만 깔끔하게 빌드합니다.
RUN ./gradlew clean bootJar -x test

# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /application

# 타임존 데이터 설치 및 한국 시간(Asia/Seoul) 세팅
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

# 보안을 위한 non-root 유저 생성
RUN addgroup --system spring && adduser --system spring -G spring
USER spring:spring

# 빌드 스테이지에서 생성된 단 하나의 단독 .jar 파일만 안전하게 복사합니다.
COPY --from=builder /builder/build/libs/*-SNAPSHOT.jar app.jar

EXPOSE 8080

# JVM 옵션을 부여하여 바로 실행합니다.
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]