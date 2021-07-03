plugins {
    kotlin("jvm")
    kotlin("kapt")
}

repositories {
    mavenLocal()
    mavenCentral()
    jcenter()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.jetbrains.kotlin", "kotlin-stdlib-jdk8", Versions.Kotlin)

    implementation("com.squareup:kotlinpoet:0.7.0")
    implementation("com.google.auto.service:auto-service:1.0")
    implementation("com.google.auto.service:auto-service-annotations:1.0")
    kapt("com.google.auto.service:auto-service:1.0")
}

sourceSets {
    main {
        java {
            srcDir("${buildDir.absolutePath}/generated/source/kaptKotlin")
        }
    }
}
kotlin.sourceSets["main"].kotlin.srcDirs("src/main/kotlin")
