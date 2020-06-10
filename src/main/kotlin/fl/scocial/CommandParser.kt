package fl.scocial

import com.github.michaelbull.result.*

class CommandParser(private val stringSource: StringSource) {
    fun evaluate(): Result<SocialCommand, Throwable> {
        val originalString = stringSource()
        val tokens = originalString.split("\\s".toRegex())
        return when (tokens.size) {
            1 -> Ok(ReadingCommand(User(tokens[0])))
            else ->
                if (tokens[1] == "->") {
                    Ok(PostingCommand(User(tokens[0]), originalString.substringAfter("-> ")))
                } else {
                    Ok(FollowingCommand(User(tokens[0]), User(tokens[2])))
                }
        }
    }
}
