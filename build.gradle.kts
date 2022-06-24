import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

application {
    mainClass.set("fr.aven.bot.Launcher")
}

group = "fr.aven"
version = "3.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io/")
    maven("https://m2.dv8tion.net/releases")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("net.dv8tion:JDA:5.0.0-alpha.12")
    implementation("com.github.minndevelopment:jda-ktx:bf7cd96")

    implementation("com.github.walkyst:lavaplayer-fork:custom-SNAPSHOT")
    implementation("com.github.walkyst.lavaplayer-fork:lavaplayer-ext-youtube-rotator:df43206b26")

    implementation("com.github.yvasyliev:deezer-api:1.0.3")

    implementation("com.sksamuel.hoplite:hoplite-core:2.1.5")
    implementation("com.sksamuel.hoplite:hoplite-yaml:2.1.5")

    implementation("ch.qos.logback", "logback-classic", "1.0.9")
    implementation("ch.qos.logback", "logback-core", "1.0.9")

    // https://mvnrepository.com/artifact/org.reflections/reflections
    implementation("org.reflections:reflections:0.10.2")

    implementation("org.jetbrains.exposed", "exposed-core", "0.37.3")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.37.3")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.37.3")
    implementation("mysql:mysql-connector-java:8.0.29")

    implementation("io.github.reactivecircus.cache4k:cache4k:0.6.0")
    implementation("com.google.code.gson:gson:2.9.0")

    // https://mvnrepository.com/artifact/org.json/json
    implementation("org.json:json:20220320")

}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
    kotlinOptions.freeCompilerArgs = listOf(
        "-Xjvm-default=all",  // use default methods in interfaces
        "-Xlambdas=indy"      // use invokedynamic lambdas instead of synthetic classes
    )
}