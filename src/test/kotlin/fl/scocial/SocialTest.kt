package fl.scocial

import com.github.michaelbull.result.mapBoth
import com.github.michaelbull.result.unwrap
import java.lang.RuntimeException
import kotlin.test.*

class SocialTest {
    @Test
    fun `a command parser can understand good posting commands`() {
        val command: SocialCommand = parse("Alice -> I love the weather today")

        assertTrue(command is PostingCommand)
        assertEquals(User("Alice"), command.user)
        assertEquals("I love the weather today", command.message)
    }

    @Test
    fun `a command parser can understand good reading commands`() {
        val command: SocialCommand = parse("Alice")

        assertTrue(command is ReadingCommand)
        assertEquals(User("Alice"), command.user)
    }

    @Test
    fun `a command parser can understand good following commands`() {
        val command: SocialCommand = parse("Charlie follows Alice")

        assertTrue(command is FollowingCommand)
        assertEquals(User("Charlie"), command.follower)
        assertEquals(User("Alice"), command.followed)
    }

    @Test
    fun `a command parser can understand good wall commands`() {
        val command: SocialCommand = parse("Charlie wall")

        assertTrue(command is WallCommand)
        assertEquals(User("Charlie"), command.user)
    }

    private fun parse(sample: String): SocialCommand =
        CommandParser(SimpleStringSource(sample))
            .evaluate()
            .mapBoth(
                success = { it },
                failure = { throw RuntimeException(it) }
            )
}
