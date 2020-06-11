package fl.scocial

import com.github.michaelbull.result.mapBoth

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        executionLoop(::readLine, ::println)
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
