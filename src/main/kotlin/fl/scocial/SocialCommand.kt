package fl.scocial

import java.time.LocalDateTime

sealed class SocialCommand

data class PostingCommand(val user: User, val timedMessage: TimedMessage): SocialCommand() {
    val message: String
        get() = timedMessage.message
    val dateTime: LocalDateTime
        get() = timedMessage.dateTime
    companion object {
        operator fun invoke(user: User, message: String): PostingCommand =
            PostingCommand(user, TimedMessage(message, LocalDateTime.now()))
    }
}
data class ReadingCommand(val user: User): SocialCommand()
data class FollowingCommand(val follower: User, val followed: User): SocialCommand()
data class WallCommand(val user: User): SocialCommand()
