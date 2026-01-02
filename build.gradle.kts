plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	id("org.springframework.boot") version "3.5.8"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.moira"
version = "1.0.0"
description = "itda"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
    // aws: s3
    implementation("com.amazonaws:aws-java-sdk:1.12.794")
    // database
    runtimeOnly("com.mysql:mysql-connector-j")
    // jwt
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    // kotlin
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    // mail
    implementation("org.springframework.boot:spring-boot-starter-mail")
    // mybatis
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.5")
    // springboot
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mybatis.spring.boot:mybatis-spring-boot-starter-test:3.0.5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // websocket
    implementation("org.springframework.boot:spring-boot-starter-websocket")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}
