package data.auth

import annotations.SampleModel
import annotations.StringField

@SampleModel
data class LoginRequestBody(
    @StringField("228322000")
    val id: String,
    @StringField("truetripled")
    val name: String,
    @StringField("https://example.com/pic.png")
    val profileImageUrl: String,
)
