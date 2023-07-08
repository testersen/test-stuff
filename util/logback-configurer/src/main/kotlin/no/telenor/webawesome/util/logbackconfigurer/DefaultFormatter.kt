package no.telenor.webawesome.util.logbackconfigurer

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.PatternLayout
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender
import ch.qos.logback.core.encoder.LayoutWrappingEncoder
import java.nio.charset.Charset

class DefaultFormatter : SetupFormatter {
	companion object {
		var defaultLoggingFormat = "%level %logger %msg%n"
	}

	override fun setup(
		loggerContext: LoggerContext,
		appender: ConsoleAppender<ILoggingEvent>,
		ctx: LCtx
	) {
		val layout = PatternLayout()
		layout.context = loggerContext
		layout.pattern = defaultLoggingFormat
		layout.start()

		val encoder = LayoutWrappingEncoder<ILoggingEvent>()
		encoder.context = loggerContext
		encoder.charset = Charset.forName("utf-8")
		encoder.layout = layout

		appender.encoder = encoder
	}
}
