package fl.scocial

class CommandParser(private val stringSource: StringSource) {
    fun evaluate(): SocialCommand = PostingCommand()
}
