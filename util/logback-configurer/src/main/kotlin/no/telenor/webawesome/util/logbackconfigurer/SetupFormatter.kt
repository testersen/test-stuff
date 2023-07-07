package no.telenor.webawesome.util.logbackconfigurer

import ch.qos.logback.classic.LoggerContext
import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.core.ConsoleAppender

interface SetupFormatter {
	fun setup(loggerContext: LoggerContext, appender: ConsoleAppender<ILoggingEvent>, ctx: LCtx)
}
