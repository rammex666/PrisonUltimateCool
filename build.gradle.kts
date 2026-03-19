plugins {
    java
    id("io.papermc.paperweight.userdev") version "2.0.0-beta.19"
}

group = "fr.rammex"
version = "1.0-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")

    implementation("com.google.code.gson:gson:2.10.1")
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
}