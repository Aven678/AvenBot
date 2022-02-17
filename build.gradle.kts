import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

application {
    mainClassName = "fr.aven.bot.Launcher"
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
    implementation("net.dv8tion:JDA:5.0.0-alpha.5")
    implementation("com.github.minndevelopment:jda-ktx:6527755")

    implementation("com.sedmelluq:lavaplayer:1.3.78")
    implementation("com.sedmelluq:lavaplayer-ext-youtube-rotator:0.2.3")

    implementation("com.sksamuel.hoplite:hoplite-core:1.4.16")
    implementation("com.sksamuel.hoplite:hoplite-yaml:1.4.16")

    implementation("com.google.firebase:firebase-admin:8.1.0")

    implementation("ch.qos.logback", "logback-classic", "1.0.9")
    implementation("ch.qos.logback", "logback-core", "1.0.9")

    @Suppress("GradlePackageUpdate")
    implementation("org.reflections:reflections:0.9.11")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}