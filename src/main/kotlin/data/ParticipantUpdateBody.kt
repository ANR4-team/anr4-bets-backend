package data

import annotations.SampleModel
import annotations.StringField
import java.util.*

@SampleModel
data class ParticipantUpdateBody(
    @StringField("FC Manchester City")
    val name: String?,
    @StringField("https://example.com/logo.png")
    val logoUrl: String?,
    @StringField("uuid-id-string-example")
    val sportTypeId: UUID?,
)
