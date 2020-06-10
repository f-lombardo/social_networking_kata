package fl.scocial

class CommandParser(private val stringSource: StringSource) {
    fun evaluate(): SocialCommand {
        val originalString = stringSource()
        val tokens = originalString.split("\\s".toRegex())
        return when (tokens.size) {
            1 -> ReadingCommand(User(tokens[0]))
            else -> PostingCommand(User(tokens[0]), originalString.substringAfter("-> "))
        }
    }
}
