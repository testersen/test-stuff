package no.telenor.webawesome.util.logbackconfigurer

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.Configurator
import ch.qos.logback.core.spi.ContextAwareBase
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.stream.Collectors

class HijackLogbackConfiguration : ContextAwareBase(), Configurator {
	override fun configure(loggerContext: LoggerContext?): Configurator.ExecutionStatus {
		loggerContext ?: throw Exception("Did not receive a logger context!")

		val ctx = LCtx(loggerContext)

		for (klass in getAllClasses()) {
			for (method in klass.methods) {
				for (annotation in method.annotations) {
					if (annotation !is LogbackConfigurer) continue

					val strRepresentation =
						"${klass.name}#${method.name}(${
							method.parameters.joinToString(",") {
								"${it.name}: ${it.parameterizedType}"
							}
						}): ${method.genericReturnType}"

					if (method.parameters.size != 1) {
						throw Exception("invalid parameter count! ($strRepresentation)")
					}

					if (method.parameters[0].type !== LCtx::class.java) {
						throw Exception("invalid parameter type! ($strRepresentation)")
					}

					if (!method.returnType.isPrimitive || method.returnType.name !== "void") {
						throw Exception("invalid return type! ($strRepresentation)")
					}

					method.invoke(null, ctx)
					if (ctx.installed) break
				}
			}
		}

		if (!ctx.installed) ctx.install()

		return Configurator.ExecutionStatus.DO_NOT_INVOKE_NEXT_IF_ANY
	}

	@Suppress("UNCHECKED_CAST")
	private fun getAllClasses(): Set<Class<*>> =
		ClassLoader.getSystemClassLoader()
			.definedPackages
			.map { pkg ->
				val pkgName = pkg.name
				try {
					BufferedReader(
						InputStreamReader(
							ClassLoader.getSystemClassLoader().getResourceAsStream(pkgName.replace(".", "/"))
								?: throw Exception("Unable to get stream for '$pkgName'")
						)
					)
						.lines()
						.filter { it.endsWith(".class") }
						.map {
							try {
								Class.forName("$pkgName.${it.substring(0, it.lastIndexOf("."))}")
							} catch (ex: Throwable) {
								null
							}
						}
						.filter { it !== null }
						.collect(Collectors.toSet())
				} catch (ex: Throwable) {
					null
				}
			}
			.reduce { acc, classes ->
				val accumulator = acc ?: mutableSetOf()
				if (classes !== null) accumulator.addAll(classes)
				accumulator
			} as Set<Class<*>>
}
