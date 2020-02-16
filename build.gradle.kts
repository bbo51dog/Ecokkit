plugins {
    kotlin("jvm") version "1.3.60"
    id("net.minecrell.plugin-yml.nukkit") version "0.3.0"
    id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "net.bbo51dog"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven {
        url "https://repo.nukkitx.com/snapshot/"
    }
}

repositories {
    jcenter()
    maven {
        url = uri("https://repo.nukkitx.com/main/")
    }
}

dependencies {
    shadow(kotlin("stdlib"))
    compileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")
    testCompileOnly("cn.nukkit:nukkit:1.0-SNAPSHOT")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

}

nukkit {
    main = "net.bbo51dog.EcokkitPlugin"
    api = listOf("1.0.0")
    authors = listOf("bbo51dog")
}
