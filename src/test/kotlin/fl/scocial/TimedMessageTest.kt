package fl.scocial

import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class TimedMessageTest {
    @Test
    fun `a message displays number of minutes since its creation`() {
        val now = LocalDateTime.now()
        val timedMessage = TimedMessage(User("Bob"), "Some message", now.minusMinutes(2))
        assertEquals(
            "(2 minutes ago)",
            timedMessage.formatTimeElapsedUntil(now)
        )
    }

    @Test
    fun `a message displays number of seconds since its creation`() {
        val now = LocalDateTime.now()
        val timedMessage = TimedMessage(User("Bob"), "Some message", now.minusSeconds(10))
        assertEquals(
            "(10 seconds ago)",
            timedMessage.formatTimeElapsedUntil(now)
        )
    }

    @Test
    fun `a message does not display anything if less than 1 second elapsed`() {
        val now = LocalDateTime.now()
        val timedMessage = TimedMessage(User("Bob"), "Some message", now)
        assertEquals(
            "",
            timedMessage.formatTimeElapsedUntil(now)
        )
    }
}
