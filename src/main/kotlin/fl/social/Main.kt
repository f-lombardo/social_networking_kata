package fl.social

import com.github.michaelbull.result.mapBoth

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        executionLoop(::readLineWithPrompt, ::println)
    }

    fun readLineWithPrompt(): String? {
        print("> ")
        return readLine()
    }

    fun executionLoop(stringSource: StringSource, stringDestination: StringDestination) {
        val parser = CommandParser(stringSource)
        val interpreter = CommandInterpreter(stringDestination)
        while (true) {
            parser
                .evaluate()
                .mapBoth(
                    success = { command -> interpreter.interpret(command) },
                    failure = { message -> println(message) }
                )
        }
    }
}
