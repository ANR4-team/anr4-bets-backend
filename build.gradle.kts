import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    kotlin("jvm") version Versions.Kotlin
    id("com.github.johnrengelman.shadow") version Versions.ShadowJar
}

group = "com.github.anr4-team"
version = Versions.App

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {

    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", Versions.Kotlin)
    implementation("org.jetbrains.kotlinx", "kotlinx-coroutines-core", Versions.Coroutines)
    implementation("ch.qos.logback", "logback-classic", Versions.Logback)
    implementation(ktor( "ktor-server-netty"))
    implementation(ktor("ktor-server-core"))
    implementation(ktor("ktor-server-sessions"))
    implementation(ktor("ktor-auth"))
    implementation(ktor( "ktor-auth-jwt"))
    implementation(ktor("ktor-gson"))
    implementation(ktor("ktor-client-core"))
    implementation(ktor("ktor-client-core-jvm"))
    implementation(ktor("ktor-client-cio"))
    implementation(ktor("ktor-client-okhttp"))
    implementation(ktor("ktor-client-auth"))
    implementation(ktor("ktor-client-json"))
    implementation(ktor("ktor-client-gson"))
    implementation(ktor("ktor-client-logging-jvm"))

    implementation("org.postgresql", "postgresql", Versions.Postgres.Driver)
    implementation(exposed("exposed-core"))
    implementation(exposed("exposed-dao"))
    implementation(exposed("exposed-jdbc"))
    implementation(exposed("exposed-java-time"))

    implementation(koin("koin-core"))
    implementation(koin("koin-ktor"))
    implementation(koin("koin-core-ext"))

    testImplementation(platform("org.junit:junit-bom:5.7.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    implementation("com.github.nielsfalk:ktor-swagger:0.7.0")

    implementation("com.google.firebase:firebase-admin:7.3.0")
}

kotlin.sourceSets["main"].kotlin.srcDirs("src/main/kotlin")
kotlin.sourceSets["test"].kotlin.srcDirs("src/test/kotlin")

sourceSets["main"].resources.srcDirs("src/main/resources")
sourceSets["test"].resources.srcDirs("src/test/resources")

val uberJarFileName = "anr4-bets-backend-${Versions.App}.jar"

tasks {
    named<ShadowJar>("shadowJar") {
        archiveFileName.set(uberJarFileName)
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to application.mainClassName))
        }
    }
    named("jar") {
        enabled = false
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.register("stage") {
    dependsOn(tasks.named("clean"), tasks.named("build"))
    mustRunAfter(tasks.named("clean"))
}

tasks.withType(KotlinCompile::class.java).all {
    kotlinOptions.jvmTarget = "1.8"
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=io.ktor.util.InternalAPI"
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.ExperimentalStdlibApi"
    kotlinOptions.freeCompilerArgs += "-Xinline-classes"
}
