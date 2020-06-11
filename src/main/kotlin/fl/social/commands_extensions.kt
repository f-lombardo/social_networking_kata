package fl.social

import java.time.LocalDateTime

infix fun User.posts(message: String) = PostingCommand(this, message)
infix fun User.posts(messageAtTime: Pair<String, LocalDateTime>) =
    PostingCommand(this, messageAtTime.first, messageAtTime.second)

fun User.readMessages() = ReadingCommand(this)
infix fun User.follows(other: User) = FollowingCommand(this, other)
fun User.wall() = WallCommand(this)
