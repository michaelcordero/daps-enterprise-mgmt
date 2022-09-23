val ktor_tools_version: String by project
val ktor_server_tests_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val kotlinx_coroutines: String by project
val exposed_version: String by project
val h2_version: String by project
val hikari_cp_version: String by project
val dokka_version: String by project
val progress_bar_version: String by project
val email_service_version: String by project
val commons_codec_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
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
    implementation("io.ktor:ktor-serialization:$ktor_tools_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinx_coroutines")
    implementation("io.ktor:ktor-jackson:$ktor_tools_version")
    implementation("io.ktor:ktor-websockets:$ktor_tools_version")
    implementation("io.ktor:ktor-auth-jwt:$ktor_tools_version")
    implementation("io.ktor:ktor-server-netty:$ktor_tools_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-core:$ktor_tools_version")
    implementation("io.ktor:ktor-html-builder:$ktor_tools_version")
    implementation("io.ktor:ktor-locations:$ktor_tools_version")
    implementation("io.ktor:ktor-freemarker:$ktor_tools_version")
    implementation("io.ktor:ktor-webjars:$ktor_tools_version")
    implementation("com.h2database:h2:$h2_version")
    implementation("com.zaxxer:HikariCP:$hikari_cp_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("me.tongfei:progressbar:$progress_bar_version")
//    implementation("com.icerockdev.service:email-service:$email_service_version")
    implementation("commons-codec:commons-codec:$commons_codec_version")
    testImplementation("io.ktor:ktor-server-tests:$ktor_server_tests_version")
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
tasks.jar {
    manifest {
        attributes(Pair("Main-Class", "application.ApplicationKt"))
    }
    from(configurations.compileClasspath.get().map { if (it.isDirectory) it else zipTree(it) },
        configurations.testCompileClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

/**
 * To run from command line:
 * ./gradlew csv --args=/Users/michaelcordero/Documents/DAPS_Migrations/daps-csv
 * UPDATE: No longer using this because {@link JavaExec} does not support backslash escape characters, which are used
 * in the process method for status updates. :/
 * UPDATE #2: This task now uses progressbar dependency, so I'm not sure if that fixed it or updating to Gradle 6.3.
 */
tasks.register("csv", JavaExec::class.java) {
    group = "Execution"
    description = "Run the H2exCSVKt main class"
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass.set("utilities.H2exCSVKt")
}
