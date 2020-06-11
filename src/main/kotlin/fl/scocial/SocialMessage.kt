package fl.scocial

import java.time.LocalDateTime

data class SocialMessage (val message: String, val dateTime: LocalDateTime = LocalDateTime.now())

