rootProject.name = "web-awesome"

pluginManagement {
	val ktVersion = "1.8.22"
	val ktDokkaVersion = "1.8.20"

	plugins {
		kotlin("jvm") version ktVersion
		kotlin("plugin.serialization") version ktVersion
		id("org.jetbrains.dokka") version ktDokkaVersion
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
