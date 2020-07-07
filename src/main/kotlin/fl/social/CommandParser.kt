package fl.social

import com.github.michaelbull.result.*

typealias SocialCommandResult = Result<SocialCommand, String>

class CommandParser(private val stringSource: StringSource) {
    fun evaluate(): SocialCommandResult {
        val originalString = stringSource() ?: return I_CANT_UNDERSTAND_THIS
        val tokens = originalString.split("\\s".toRegex())
        return parseReadingCommand(originalString, tokens)
            .or {
                parsePostingCommand(originalString, tokens)
            }.or {
                parseFollowingCommand(originalString, tokens)
            }.or {
                parseWallCommand(originalString, tokens)
            }.or {
                parsePrivateMessageCommand(originalString, tokens)
            }.or (::noCommandFound)
    }

    private fun parseReadingCommand(originalString: String, tokens: List<String>): SocialCommandResult =
        if (tokens.size == 1) {
            Ok(ReadingCommand(User(tokens[0])))
        } else {
            I_CANT_UNDERSTAND_THIS
        }

    private fun parsePostingCommand(originalString: String, tokens: List<String>): SocialCommandResult =
        if (tokens.isThreeTokensCommand("->")) {
            Ok(PostingCommand(User(tokens[0]), originalString.substringAfter("-> ")))
        } else {
            I_CANT_UNDERSTAND_THIS
        }

    private fun parseFollowingCommand(originalString: String, tokens: List<String>): SocialCommandResult =
        if (tokens.isThreeTokensCommand("follows")) {
            Ok(FollowingCommand(User(tokens[0]), User(tokens[2])))
        } else {
            I_CANT_UNDERSTAND_THIS
        }

    private fun parseWallCommand(originalString: String, tokens: List<String>): SocialCommandResult =
        if (tokens.isTwoTokensCommand("wall")) {
            Ok(WallCommand(User(tokens[0])))
        } else {
            I_CANT_UNDERSTAND_THIS
        }

    private fun parsePrivateMessageCommand(originalString: String, tokens: List<String>): SocialCommandResult =
        if (tokens.isFourTokensCommand("to", "->")) {
            Ok(PrivateMessageCommand(User(tokens[0]), User(tokens[2]), tokens.subList(4, tokens.size).joinToString(" ")))
        } else {
            I_CANT_UNDERSTAND_THIS
        }

    private fun List<String>.isFourTokensCommand(secondToken: String, fourhtToken:String): Boolean =
        size >= 4  && get(1) == secondToken && get(3) == fourhtToken

    private fun List<String>.isThreeTokensCommand(secondToken: String): Boolean =
        size >= 3  && get(1) == secondToken

    private fun List<String>.isTwoTokensCommand(secondToken: String): Boolean =
        size == 2  && get(1) == secondToken

    private fun noCommandFound(): SocialCommandResult = I_CANT_UNDERSTAND_THIS

    private val I_CANT_UNDERSTAND_THIS = Err("I cannot understand this command")
}
