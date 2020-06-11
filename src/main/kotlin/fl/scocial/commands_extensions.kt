package fl.scocial

infix fun User.posts(message: String) = PostingCommand(this, message)
infix fun User.posts(socialMessage: TimedMessage) = PostingCommand(this, socialMessage)

fun User.readMessages() = ReadingCommand(this)
infix fun User.follows(other: User) = FollowingCommand(this, other)
fun User.wall() = WallCommand(this)
