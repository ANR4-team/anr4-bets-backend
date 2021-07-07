import data.StageType
import data.adapters.LocalDateTimeAdapter
import data.adapters.StageTypeAdapter
import data.adapters.UuidAdapter
import db.tables
import di.appModule
import di.repositoryModule
import di.serviceModule
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.features.DataConversion
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import io.ktor.util.converters.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.slf4j.event.Level
import repository.UserRepository
import routes.*
import swagger.installSwagger
import utils.AppEnv
import utils.JwtConfig
import utils.features.DatabaseFeature
import utils.tryOrNull
import java.time.LocalDateTime
import java.util.*

fun main(args: Array<String>) = EngineMain.main(args)

@Suppress("unused")
fun Application.module() {

    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        header(HttpHeaders.Referrer)
        header(HttpHeaders.UserAgent)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlRequestMethod)
        header(HttpHeaders.Authorization)
        allowCredentials = true
        allowNonSimpleContentTypes = true
        anyHost()
    }

    install(DefaultHeaders) {
        header("Backend-Version", "0.0.1")
    }

    installSwagger()

    install(DatabaseFeature) {
        url = AppEnv.getDatabaseUrl()
        onDatabaseConnected = { database ->
            SchemaUtils.createMissingTablesAndColumns(*tables)
            install(Koin) {
                repositoryModule.single { database }
                modules(repositoryModule, serviceModule, appModule)
            }
        }
    }

    install(Authentication) {

        val userRepository by inject<UserRepository>()

        if (AppEnv.isTest) {
            basic {
                validate {
                    userRepository.getUserByName("truetripled")
                }
            }
        } else {
            jwt {
                verifier(JwtConfig.verifier)
                realm = AppEnv.JWT.realm
                validate { credentials ->
                    val userId = credentials.payload.getClaim("id").asString()
                    userRepository.getUser(userId)
                }
            }
        }
    }

    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
            registerTypeAdapter(UUID::class.java, UuidAdapter())
            registerTypeAdapter(StageType::class.java, StageTypeAdapter())
        }
    }

    install(DataConversion) {
        convert<UUID> {
            decode { values, _ ->
                values.singleOrNull()?.let { tryOrNull { UUID.fromString(it) } }
            }
            encode { value ->
                when (value) {
                    null -> listOf()
                    is UUID -> listOf(value.toString())
                    else -> throw DataConversionException("Cannot convert $value")
                }
            }
        }
    }

    routing {
        get("/") {
            call.respond("Server is powered by Ktor 1.6.0")
        }
        userRoutes()
        sportTypesRoutes()
        participantRoutes()
        tournamentRoutes()
        stageRoutes()
    }
}
