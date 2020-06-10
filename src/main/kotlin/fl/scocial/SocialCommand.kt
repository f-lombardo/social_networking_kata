package fl.scocial

sealed class SocialCommand

data class PostingCommand(val user: User, val message: String): SocialCommand()
data class ReadingCommand(val user: User): SocialCommand()
