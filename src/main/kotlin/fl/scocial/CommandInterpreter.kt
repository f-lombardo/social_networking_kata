package fl.scocial

import java.time.LocalDateTime

typealias TimeSource = () -> LocalDateTime

object StandardTimeSource: TimeSource {
    override fun invoke(): LocalDateTime = LocalDateTime.now()
}

class CommandInterpreter (val output: StringDestination, private val timeSource: TimeSource = StandardTimeSource) {
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
                val composedTimeLine = timeLine.getOrDefault(command.user, emptyList<TimedMessage>())
                val influencersTimeLines = influencers.getOrDefault(command.user, emptyList<TimedMessage>()).map {
                    user -> timeLine[user]?.toList() ?: emptyList()
                }.flatten()
                (composedTimeLine + influencersTimeLines)
                    .sortedByDescending { it.dateTime }
                    .forEach {
                        output("(${it.user.name}) ${it.message}${it.formatTimeElapsedUntil(timeSource())}")
                    }
            }
        }
    }

    private fun User.outputTimeline() =
        timeLine[this]?.forEach {
            output(it.message)
        }
}
