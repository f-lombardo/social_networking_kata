package fl.scocial

class CommandInterpreter (val output: StringDestination) {
    private val timeLine = mutableMapOf<User, MutableList<SocialMessage>>()

    fun interpret(command: SocialCommand): Unit {
        when(command) {
            is PostingCommand -> {
                    val messages = timeLine.getOrPut(command.user) {
                        mutableListOf<SocialMessage>()
                    }
                    messages.add(SocialMessage(command.message))
            }
            is ReadingCommand -> {
                timeLine[command.user]?.forEach {
                    output(it.message)
                }
            }
            is FollowingCommand -> TODO()
            is WallCommand -> TODO()
        }
    }
}
