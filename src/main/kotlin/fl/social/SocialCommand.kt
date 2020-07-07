package fl.social

import java.time.LocalDateTime

sealed class SocialCommand

data class PostingCommand(val user: User, val message: String, val time: LocalDateTime = LocalDateTime.now()): SocialCommand() {
    val timedMessage = TimedMessage(user, message, time)
}
data class ReadingCommand(val user: User): SocialCommand()
data class FollowingCommand(val follower: User, val followed: User): SocialCommand()
data class WallCommand(val user: User): SocialCommand()
data class PrivateMessageCommand(val userFrom: User, val userTo: User, val message: String): SocialCommand()
data class ReadPrivateMessageCommand(val userFrom: User, val userTo: User): SocialCommand()
