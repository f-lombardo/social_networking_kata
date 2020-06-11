package fl.scocial

import com.github.michaelbull.result.mapBoth
import kotlin.test.*

class CommandInterpreterTest {
    @Test @Ignore
    fun `interpretation of posting and reading`() {
        val commands = listOf<SocialCommand>(
            PostingCommand(User("Alice"), "I love the weather today"),
            PostingCommand(User("Bob"), "Damn! We lost!"),
            PostingCommand(User("Bob"), "Good game though."),
            ReadingCommand(User("Alice")),
            ReadingCommand(User("Bob"))
        )
        val output = SimpleStringDestination()
        val interpreter = CommandInterpreter(output)
        commands.forEach { command -> interpreter.interpret(command) }
        assertEquals(listOf<String>("I love the weather today", "Damn! We lost!",  "Good game though."),
                    output.lines)
    }
}
