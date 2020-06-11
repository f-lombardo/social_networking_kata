package fl.scocial

import java.time.LocalDateTime

sealed class SocialCommand

data class PostingCommand(val user: User, val message: String, val dateTime: LocalDateTime = LocalDateTime.now()): SocialCommand() {
    val socialMessage = SocialMessage(message, dateTime)
}
data class ReadingCommand(val user: User): SocialCommand()
data class FollowingCommand(val follower: User, val followed: User): SocialCommand()
data class WallCommand(val user: User): SocialCommand()
