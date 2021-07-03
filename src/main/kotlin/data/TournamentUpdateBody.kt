package data

import annotations.SampleModel
import annotations.StringField
import java.time.LocalDateTime
import java.util.*

@SampleModel
data class TournamentUpdateBody(
    @StringField("The International 10")
    val name: String?,
    @StringField("2021-08-01T12:00:00+0300")
    val startDate: LocalDateTime?,
    @StringField("2021-08-12T12:00:00+0300")
    val endDate: LocalDateTime?,
    @StringField("https://example.com/logo/123.png")
    val logoUrl: String?,
    @StringField("new-sport-type-uuid")
    val sportTypeId: UUID?,
)
