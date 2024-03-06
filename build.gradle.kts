plugins {
    `java-library`
    id("io.papermc.paperweight.userdev") version "1.5.11"
    id("xyz.jpenilla.run-paper") version "2.2.2"
}

group = "net.faceless.aurelius"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
}

dependencies {
    paperweight.paperDevBundle("1.20.4-R0.1-SNAPSHOT")
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
}

tasks.withType(JavaCompile::class.java) {
    options.encoding = "UTF-8"
}

tasks{
    val paths = arrayOf(
        "C:\\Users\\Faceless\\Desktop\\Servers\\Paper 1.20.4\\plugins",
        "C:\\Users\\Faceless\\Desktop\\Servers\\Purpur 1.20.4\\plugins"
    )
    val name = "\\${project.name}-${project.version}.jar"

    assemble{
        dependsOn(reobfJar)
    }

    reobfJar {
        paths.forEach { path ->
            if (File(path).exists()) outputJar.set(layout.buildDirectory.file(path + name))
        }
    }
}
