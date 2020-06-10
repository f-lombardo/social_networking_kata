package fl.scocial

import kotlin.test.*

class SocialTest {
    @Test
    fun `a command parser can understand good posting commands`() {
        val parser = CommandParser (SimpleStringSource("Alice -> I love the weather today"))

        val command: SocialCommand =  parser.evaluate()

        assertTrue(command is PostingCommand)
        assertEquals(User("Alice"), command.user)
        assertEquals("I love the weather today", command.message)
    }

    @Test
    fun `a command parser can understand good reading commands`() {
        val parser = CommandParser (SimpleStringSource("Alice"))

        val command: SocialCommand =  parser.evaluate()

        assertTrue(command is ReadingCommand)
        assertEquals(User("Alice"), command.user)
    }

    @Test
    fun `a command parser can understand good following commands`() {
        val parser = CommandParser (SimpleStringSource("Charlie follows Alice"))

        val command: SocialCommand =  parser.evaluate()

        assertTrue(command is FollowingCommand)
        assertEquals(User("Charlie"), command.follower)
        assertEquals(User("Alice"), command.followed)
    }
}
