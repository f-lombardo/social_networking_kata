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
                "(Charlie) I'm in New York today! Anyone wants to have a coffee?",
                "(Alice) I love the weather today"
            ),
            output.lines
        )
    }

    @Test
    fun `interpretation of subscribing and viewing simple list handling time`() {
        val currentTime = LocalDateTime.now()
        val timeSource = { currentTime }
        val commands = commandList() +
            (User("Alice") posts  ("I love the weather today" at 10.minutesSince(currentTime))) +
            (User("Bob") posts ("Damn! We lost!" at 5.minutesSince(currentTime))) +
            (User("Bob") posts ("Good game though." at 3.minutesSince(currentTime))) +
            (User("Charlie") posts ("I'm in New York today! Anyone wants to have a coffee?" at 2.minutesSince(currentTime))) +
            (User("Charlie") follows User("Alice")) +
            User("Charlie").wall() +
            (User("Charlie") follows User("Bob")) +
            User("Charlie").wall()

        val output = SimpleStringDestination()
        val interpreter = CommandInterpreter(output, timeSource)
        commands.forEach { command -> interpreter.interpret(command) }
        assertEquals(
            listOf<String>(
                "(Charlie) I'm in New York today! Anyone wants to have a coffee? (2 minutes ago)",
                "(Alice) I love the weather today (10 minutes ago)",
                "(Charlie) I'm in New York today! Anyone wants to have a coffee? (2 minutes ago)",
                "(Bob) Good game though. (3 minutes ago)",
                "(Bob) Damn! We lost! (5 minutes ago)",
                "(Alice) I love the weather today (10 minutes ago)"
                ),
            output.lines
        )
    }

    private fun commandList() = mutableListOf<SocialCommand>()

    private fun Int.minutesSince(time: LocalDateTime) = time.minusMinutes(this.toLong())

    private infix fun String.at(time: LocalDateTime) = this to time
}

