# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle ./gradle

RUN chmod +x ./gradlew
# 의존성 먼저 다운로드 (캐싱 활용)
RUN ./gradlew dependencies --no-daemon || true

# 소스 복사 및 빌드
COPY src ./src

# --no-daemon 및 메모리 제한 옵션으로 빌드 안정성 확보
RUN ./gradlew clean bootJar -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx512m"

# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-Xmx384m", "-jar", "app.jar"]