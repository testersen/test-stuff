package no.telenor.webawesome.util.logbackconfigurer

import kotlin.test.Test
import kotlin.test.assertTrue
import org.slf4j.LoggerFactory

class ConfigurationTest {

  // we need this to be defined so that logback will scan for configurators
  // if this is not defined, configurationTest() will not be ran.
  @Suppress("unused") val logger = LoggerFactory.getLogger(ConfigurationTest::class.java)!!

  companion object {
    var hasRan = false
  }

  @Test
  fun `configurationText has ran`() {
    assertTrue(hasRan, "Configuration was not run")
  }
}

@Suppress("unused")
@LogbackConfigurer
fun configurationTest(ctx: LCtx) {
  ConfigurationTest.hasRan = true
}
