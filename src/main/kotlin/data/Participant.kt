package data

import annotations.IntField
import annotations.SampleModel
import annotations.StringField

@SampleModel
data class Participant(
    @IntField(123)
    val id: Int,
    @StringField("Manchester United")
    val name: String,
    @StringField("https://example.com/logo/123.png")
    val logoUrl: String,
)
