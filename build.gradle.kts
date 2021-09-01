import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.5.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    id("com.google.protobuf") version "0.8.17"
}
group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}
repositories {
    mavenCentral()
}
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    compileOnly("jakarta.annotation:jakarta.annotation-api:1.3.5")
    implementation("io.github.lognet:grpc-spring-boot-starter:3.5.1")
    /*grpc관련*/
    implementation("com.google.protobuf:protobuf-java:3.17.3")
    implementation("io.grpc:grpc-protobuf:1.39.0")
    implementation("io.grpc:grpc-protobuf:1.33.1")
    implementation("io.grpc:grpc-stub:1.33.1")
    implementation("io.grpc:grpc-netty:1.33.1")
    implementation("com.google.protobuf:protobuf-java-util:3.13.0")
    implementation("io.grpc:grpc-all:1.39.0")

    implementation("io.grpc:grpc-kotlin-stub:1.1.0")
    implementation("io.grpc:protoc-gen-grpc-kotlin:0.1.5")


    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.1")
    //implementation("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
    api("org.apache.tomcat:annotations-api:6.0.53") // necessary for Java 9+

    //implementation("io.grpc:grpc-core:1.40.1")

    /*implementation("io.grpc:grpc-protobuf:1.33.1")
    implementation("io.grpc:grpc-stub:1.33.1")
    implementation("io.grpc:grpc-netty:1.33.1")
    implementation("com.google.protobuf:protobuf-java-util:3.13.0")
    implementation("io.grpc:grpc-all:1.39.0")
    implementation("io.grpc:grpc-kotlin-stub:0.2.1")
    implementation("io.grpc:protoc-gen-grpc-kotlin:0.1.5")
    implementation("com.google.protobuf:protobuf-gradle-plugin:0.8.13")*/


}
sourceSets {
    getByName("main") {
        java {
            srcDirs(
                "build/generated/source/proto/main/grpckt", //여기에 생성될 것임 grpckt가 protobuf의 네이밍과 동일
                "build/generated/source/proto/main/grpc",
                "build/generated/source/proto/main/java"
            )
        }
    }
}
protobuf {
    protoc {
        // The artifact spec for the Protobuf Compiler
        artifact = "com.google.protobuf:protoc:3.17.3"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.33.1"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:0.1.5"
        }
        /*id("reactor") {
            artifact = "com.salesforce.servicelibs:reactor-grpc:1.2.0"
        }*/

    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpckt") //상단 plugins 의 grpckt를 호출
                id("grpc")
            }
        }
    }
    /*plugins {
        // Optional: an artifact spec for a protoc plugin, with "grpc" as
        // the identifier, which can be referred to in the "plugins"
        // container of the "generateProtoTasks" closure.
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.39.0"
        }
    }
    generateProtoTasks {
        ofSourceSet("main").forEach {
            it.plugins {
                // Apply the "grpc" plugin whose spec is defined above, without options.
                id("grpc")
            }
        }
    }*/
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}
tasks.withType<Test> {
    useJUnitPlatform()
}