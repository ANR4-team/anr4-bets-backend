package data

import annotations.CustomField
import annotations.IntField
import annotations.SampleModel
import annotations.StringField
import data.auth.User
import java.time.LocalDateTime

@SampleModel
data class Tournament(
    @IntField(992)
    val id: Int,
    @StringField("UEFA Champions League 21/22")
    val name: String,
    @StringField("2021-08-01T12:00:00+0300")
    val startDate: LocalDateTime,
    @StringField("2022-05-31T12:00:00+0300")
    val endDate: LocalDateTime,
    @StringField("https://example.com/logo/123.png")
    val logoUrl: String,
    @CustomField(SportType::class)
    val sportType: SportType,
    @CustomField(User::class)
    val creator: User,
)
