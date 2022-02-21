plugins {
    kotlin("jvm") version "1.6.10"
    id("io.gitlab.arturbosch.detekt").version("1.19.0")
}

group = "me.wowaster"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:1.19.0")
}