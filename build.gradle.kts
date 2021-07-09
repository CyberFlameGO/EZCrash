plugins {
    java
    id("xyz.jpenilla.run-paper") version "1.0.3"
}

group = "me.notom3ga"
version = "1.0"

java {
    targetCompatibility = JavaVersion.toVersion(8)
    sourceCompatibility = JavaVersion.toVersion(8)
}

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.17.1-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.6.0")
}

tasks {
    runServer {
        minecraftVersion("1.17.1")
    }
}
