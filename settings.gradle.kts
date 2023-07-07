rootProject.name = "web-awesome"

pluginManagement {
	val ktVersion = "1.8.22"

	plugins {
		kotlin("jvm") version ktVersion
		kotlin("plugin.serialization") version ktVersion
		id("org.jetbrains.dokka") version "1.8.20"
	}
}

dependencyResolutionManagement {
	versionCatalogs {
		create("libs") {
			library("logback", "ch.qos.logback:logback-classic:1.4.8")
		}
	}
}

include(":util:logback-configurer")
