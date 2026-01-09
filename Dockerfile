# 1단계: 빌드 스테이지
FROM gradle:8.5-jdk21 AS build
WORKDIR /app

# 의존성 파일만 먼저 복사하여 캐싱 활용 (빌드 속도 향상 및 부하 분산)
COPY build.gradle.kts settings.gradle.kts ./
RUN gradle dependencies --no-daemon || true

# 소스 복사 및 빌드
COPY src ./src
RUN chmod +x ./gradlew

# --no-daemon: 빌드 후 프로세스 종료하여 메모리 확보
# -Dorg.gradle.jvmargs: Gradle 자체의 힙 메모리를 제한하여 시스템 셧다운 방지
RUN ./gradlew clean bootJar -x test --no-daemon -Dorg.gradle.jvmargs="-Xmx512m"

# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "app.jar"]