plugins {
	`java-library`
	jacoco
	kotlin("jvm")
	id("org.jetbrains.dokka") apply true
}

group = "no.telenor.webawesome"
version = System.getenv().getOrDefault("VERSION", "UNVERSIONED")

allprojects {
	apply(plugin = "java-library")
	apply(plugin = "jacoco")
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "org.jetbrains.dokka")

	group = "no.telenor.webawesome"
	version = System.getenv().getOrDefault("VERSION", "UNVERSIONED")

	kotlin {
		jvmToolchain(17)
	}

	repositories {
		mavenCentral()
	}

	dependencies {
		testImplementation(kotlin("test"))
	}
}
