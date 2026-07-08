# 1단계: 빌드 스테이지
FROM eclipse-temurin:21-jdk-alpine AS builder
WORKDIR /builder

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src

RUN chmod +x ./gradlew
RUN ./gradlew bootJar
RUN java -Djarmode=layertools -jar build/libs/*.jar extract


# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jre-alpine
WORKDIR /application

# 타임존 데이터 설치 및 한국 시간(Asia/Seoul) 세팅
RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone

RUN addgroup --system spring && adduser --system spring -G spring
USER spring:spring

# 분할된 레이어 카피
COPY --from=builder /builder/dependencies/ ./
COPY --from=builder /builder/spring-boot-loader/ ./
COPY --from=builder /builder/snapshot-dependencies/ ./
COPY --from=builder /builder/application/ ./

EXPOSE 8080

# 운영 서버의 효율적인 컨테이너 메모리 활용을 위한 JVM 옵션 추가
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "org.springframework.boot.loader.launch.JarLauncher"]