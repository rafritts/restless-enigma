plugins {
    java
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "io.restless.enigma"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}


dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:3.2.0")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-websocket")
    implementation("org.springframework.cloud:spring-cloud-starter:4.1.0")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    //    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    //    runtimeOnly("org.postgresql:postgresql")
    //    implementation("io.micrometer:micrometer-tracing-bridge-brave")
    //    implementation("io.zipkin.reporter2:zipkin-reporter-brave")
    //    implementation("org.springframework.session:spring-session-core")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
