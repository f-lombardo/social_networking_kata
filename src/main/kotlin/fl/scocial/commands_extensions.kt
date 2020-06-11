package fl.scocial

import java.time.LocalDateTime

infix fun User.posts(message: String) = PostingCommand(this, message)
infix fun User.posts(timedMessage: Pair<String, LocalDateTime>) = PostingCommand(this, timedMessage.first, timedMessage.second)
fun User.readMessages() = ReadingCommand(this)
infix fun User.follows(other: User) = FollowingCommand(this, other)
fun User.wall() = WallCommand(this)
