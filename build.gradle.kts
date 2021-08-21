val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kotlinx_coroutines: String by project
val exposed_version: String by project
val h2_version: String by project
val hikari_cp_version: String by project
val dokka_version: String by project

plugins {
    application
    kotlin("jvm") version "1.5.10"
    id("org.jetbrains.dokka") version "1.4.30"
}

group = "com.daps.ent"
version = "0.0.1"
application {
    mainClass.set("application.ApplicationKt")
}
/*
 To run with gradle...
    1. cd [ root project directory]
    2. ./gradlew run
    3. to send to background: ./gradlew run &
    4. to check on processes in the background `jobs -p` to get the pid
    https://www.unix.com/man-page/linux/1/jobs/
*/
//mainClassName = 'application.ApplicationKt'
/* In fact Kotlin is building a class to encapsulate your main function,
   named with the same name of your file - with Title Case. */
// experimental Kotlin feature. Turn off for now.
//implementationKotlin {
//    kotlinOptions.useIR = true
//}
// how to update gradle wrapper
// ./gradlew wrapper --gradle-version 4.6 --distribution-type all
sourceSets {
    main {
        java {
            srcDirs("src")
        }
        resources {
            srcDirs("resources")
        }
    }
    test {
        java {
            srcDirs("test")
        }
        resources {
            srcDirs("testresources")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-serialization:$ktor_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines")
    implementation("io.ktor:ktor-jackson:$ktor_version")
    implementation("io.ktor:ktor-websockets:$ktor_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-html-builder:$ktor_version")
    implementation("io.ktor:ktor-locations:$ktor_version")
    implementation("io.ktor:ktor-freemarker:$ktor_version")
    implementation("io.ktor:ktor-webjars:$ktor_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("com.zaxxer:HikariCP:$hikari_cp_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("me.tongfei:progressbar:0.8.1")
    implementation("com.icerockdev.service:email-service:0.1.1")
    implementation("io.ktor:ktor-server-tests:$ktor_version")
    implementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlin_version")
}


//task npm(type: Exec) {
//    workingDir "$projectDir/react-front-end"
////    commandLine 'npm', 'run', 'build'
//    commandLine 'npm', 'run','start'
//}
// To run from command line:
// ./gradlew run -Phost=localhost -Pport=5000
//run() {
////    run.dependsOn npm
//    if (project.hasProperty("host")) {
//        systemProperties["host"] = host
//    }
//    if (project.hasProperty("port")) {
//        systemProperties["port"] = port
//    }
//}

// To run from command line with java execute:
// ./gradlew jar && java -jar ./build/libs/daps-enterprise-mgmt-0.0.1.jar
//jar {
//    manifest {
//        attributes 'Main-Class': 'application.ApplicationKt'
//    }
//    from { configurations.implementation.collect { it.isDirectory() ? it : zipTree(it) } }
//}
// ./gradlew jar && java -jar ./build/libs/daps-enterprise-mgmt-0.0.1.jar /Users/michaelcordero/Documents/DAPS_Migrations/daps-csv
//jar {
//    manifest {
//        attributes 'Main-Class' : 'utilities.H2exCSVKt'
//    }
//    from { configurations.implementation.collect { it.isDirectory() ? it : zipTree(it) } }
//}

/**
 * To run from command line:
 * ./gradlew csv --args=/Users/michaelcordero/Documents/DAPS_Migrations/daps-csv
 * UPDATE: No longer using this because {@link JavaExec} does not support backslash escape characters, which are used
 * in the process method for status updates. :/
 * UPDATE #2: This task now uses progressbar dependency, so i'm not sure if that fixed it or updating to Gradle 6.3.
 */
//task csv(type: JavaExec) {
//    group = "Execution"
//    description = "Run the H2exCSVKt main class"
//    classpath = sourceSets.main.runtimeClasspath
//    main = 'utilities.H2exCSVKt'
//}