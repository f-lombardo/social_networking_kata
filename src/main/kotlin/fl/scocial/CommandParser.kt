package fl.scocial

class CommandParser(private val stringSource: StringSource) {
    fun evaluate(): SocialCommand {
        val originalString = stringSource()
        val tokens = originalString.split("\\s".toRegex())
        return PostingCommand(User(tokens[0]), originalString.substringAfter("-> "))
    }
}
