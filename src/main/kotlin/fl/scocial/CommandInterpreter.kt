package fl.scocial

class CommandInterpreter (val output: StringDestination) {
    private val timeLine = mutableMapOf<User, MutableList<TimedMessage>>()
    private val influencers = mutableMapOf<User, MutableList<User>>()

    fun interpret(command: SocialCommand): Unit {
        when(command) {
            is PostingCommand -> {
                val messages = timeLine.getOrPut(command.user) {
                    mutableListOf<TimedMessage>()
                }
                messages.add(command.timedMessage)
            }
            is ReadingCommand -> {
                command.user.outputTimeline()
            }
            is FollowingCommand -> {
                val followed = influencers.getOrPut(command.follower) {
                    mutableListOf<User>()
                }
                followed.add(command.followed)
            }
            is WallCommand -> {
                command.user.outputTimeline()
                influencers[command.user]?.forEach {
                    influencer -> influencer.outputTimeline()
                }
            }
        }
    }

    private fun User.outputTimeline() =
        timeLine[this]?.forEach {
            output(it.message)
        }

}
