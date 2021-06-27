package swagger

import de.nielsfalk.ktor.swagger.*
import de.nielsfalk.ktor.swagger.version.shared.Information
import de.nielsfalk.ktor.swagger.version.v2.Swagger
import de.nielsfalk.ktor.swagger.version.v3.OpenApi
import io.ktor.application.*
import io.ktor.locations.*

fun Application.installSwagger() {
    install(Locations)
    install(SwaggerSupport) {
        path = "docs"
        openApi = OpenApi()
        swagger = Swagger().apply {
            info = Information(
                version = "0.0.1",
                title = "anr4-bets-backend Swagger",
                description = """
                Date pattern: "yyyy-MM-dd'T'HH:mm:ssZ"
            """.trimIndent()
            )
            openApi = OpenApi().apply {
                security = listOf(mapOf("Bearer" to listOf("TOKEN")))
            }
        }
    }
}