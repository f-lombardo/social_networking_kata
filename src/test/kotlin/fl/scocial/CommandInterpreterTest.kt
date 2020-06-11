package fl.scocial

import java.time.LocalDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class CommandInterpreterTest {
    @Test
    fun `interpretation of posting and reading`() {
        val commands = commandList() +
            (User("Alice") posts "I love the weather today") +
            (User("Bob") posts "Damn! We lost!") +
            (User("Bob") posts "Good game though.") +
            User("Alice").readMessages() +
            User("Bob").readMessages()

        val output = SimpleStringDestination()
        val interpreter = CommandInterpreter(output)
        commands.forEach { command -> interpreter.interpret(command) }
        assertEquals(
            listOf<String>("I love the weather today", "Damn! We lost!", "Good game though."),
            output.lines
        )
    }

    @Test
    fun `interpretation of subscribing and viewing simple list`() {
        val commands = commandList() +
            (User("Alice") posts  "I love the weather today") +
            (User("Bob") posts "Damn! We lost!") +
            (User("Bob") posts "Good game though.") +
            User("Alice").readMessages() +
            User("Bob").readMessages() +
            (User("Charlie") posts "I'm in New York today! Anyone wants to have a coffee?") +
            (User("Charlie") follows User("Alice")) +
            User("Charlie").wall()

        val output = SimpleStringDestination()
        val interpreter = CommandInterpreter(output)
        commands.forEach { command -> interpreter.interpret(command) }
        assertEquals(
            listOf<String>(
                "I love the weather today",
                "Damn! We lost!",
                "Good game though.",
                "I'm in New York today! Anyone wants to have a coffee?",
                "I love the weather today"
            ),
            output.lines
        )
    }

    @Test
    fun `messages have a time`() {
        val command = (User("Alice") posts ("I love the weather today" at "2020-06-11T13:01:00"))
        assertEquals(
            LocalDateTime.of(2020, 6, 11, 13, 1),
            command.dateTime
        )
    }
}

private infix fun String.at(dateTimeString: String) =
    (this to LocalDateTime.parse(dateTimeString))
private fun commandList() = mutableListOf<SocialCommand>()
