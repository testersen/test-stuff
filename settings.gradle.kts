rootProject.name = "web-awesome"

pluginManagement {
	val ktVersion: String by settings
	val ktDokkaVersion: String by settings

	plugins {
		kotlin("jvm") version ktVersion
		id("org.jetbrains.dokka") version ktDokkaVersion
	}
}
