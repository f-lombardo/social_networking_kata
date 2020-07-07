package fl.social

import com.github.michaelbull.result.mapBoth
import java.lang.RuntimeException
import kotlin.test.*

class CommandParserTest {
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

    @Test
    fun `a command parser returns an error for unknown commands`() {
        assertFailsWith<TestException> {
            parse("Charlie buzz Foo")
        }

        assertFailsWith<TestException> {
            parse("Alice to Charlie ## Hello! How are you?")
        }
    }

    @Test
    fun `a command parser can understand good private message commands`() {
        val message = "Hello!           How are you?"
        val command: SocialCommand = parse("Alice to Charlie -> $message")

        assertTrue(command is PrivateMessageCommand)
        assertEquals(User("Alice"), command.userFrom)
        assertEquals(User("Charlie"), command.userTo)
        assertEquals(message, command.message)
    }

    private fun parse(sample: String): SocialCommand =
        CommandParser(SimpleStringSource(sample))
            .evaluate()
            .mapBoth(
                success = { command -> command },
                failure = { message -> throw TestException(message) }
            )

    class TestException(msg: String): RuntimeException(msg)
}
