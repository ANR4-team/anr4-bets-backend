package data.auth

import annotations.SampleModel
import annotations.StringField
import io.ktor.auth.*

@SampleModel
data class User(
    @StringField("228322000")
    val id: String,
    @StringField("truetripled")
    val name: String,
    @StringField("https://example.com/pic.png")
    val profileImageUrl: String,
) : Principal
