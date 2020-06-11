package fl.scocial

import kotlin.test.Ignore
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
    fun `interpretation of subscribing and viewing aggregate list`() {
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
            listOf<String>("I love the weather today",
                           "Damn! We lost!",
                           "Good game though.",
                            "I'm in New York today! Anyone wants to have a coffee?",
                            "I love the weather today"
            ),
            output.lines
        )
    }

}

private fun commandList() = mutableListOf<SocialCommand>()
