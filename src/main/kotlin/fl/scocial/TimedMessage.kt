package fl.scocial

import java.time.Duration
import java.time.LocalDateTime

data class TimedMessage (val message: String, val dateTime: LocalDateTime) {
    fun timeElapsedUntil(endTime: LocalDateTime) = Duration.between(dateTime, endTime)
    fun formatTimeElapsedUntil(endTime: LocalDateTime): String {
        val minutesElapsed = timeElapsedUntil(endTime).toMinutes()
        return if (minutesElapsed > 1) {
            " ($minutesElapsed minutes ago)"
        } else {
            ""
        }
    }
}

infix fun String.at(dateTimeString: String) = TimedMessage(this, LocalDateTime.parse(dateTimeString))
