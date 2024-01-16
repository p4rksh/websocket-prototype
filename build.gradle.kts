plugins {
    `java-library`
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_11
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client")
    runtimeOnly("software.aws.rds:aws-mysql-jdbc")

    implementation("com.fasterxml.jackson.datatype:jackson-datatype-guava")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    compileOnly("jakarta.validation:jakarta.validation-api")
    compileOnly("com.google.guava:guava:31.1-jre")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("io.netty:netty-resolver-dns-native-macos:4.1.68.Final:osx-aarch_64")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
