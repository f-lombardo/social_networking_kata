package fl.scocial

import java.time.Duration
import java.time.LocalDateTime

data class TimedMessage (val user: User, val message: String, val dateTime: LocalDateTime) {
    fun timeElapsedUntil(endTime: LocalDateTime) = Duration.between(dateTime, endTime)
    fun formatTimeElapsedUntil(endTime: LocalDateTime): String {
        val secondsElapsed = timeElapsedUntil(endTime).toMillis() / 1_000
        return when (secondsElapsed) {
            0L -> ""
            in 1..59 -> "($secondsElapsed seconds ago)"
            else -> {
                val minutesElapsed = timeElapsedUntil(endTime).toMinutes()
                "($minutesElapsed minutes ago)"
            }
        }
    }
}
