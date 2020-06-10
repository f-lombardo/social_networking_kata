package fl.scocial

import kotlin.test.*

class SocialTest {
    @Test
    fun `a command parser can understand good posting commands`() {
        val parser = CommandParser (SimpleStringSource("Alice -> I love the weather today"))

        val command: SocialCommand =  parser.evaluate()

        assertTrue(command is PostingCommand)
    }
}
