import data.adapters.LocalDateTimeAdapter
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import org.slf4j.event.Level
import routes.*
import swagger.installSwagger
import java.time.LocalDateTime

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

//    install(DatabaseFeature) {
//        url = AppEnv.getDatabaseUrl()
//        onDatabaseConnected = { database ->
//            SchemaUtils.createMissingTablesAndColumns(*tables)
//            install(Koin) {
//                repositoryModule.single { database }
//                modules(repositoryModule, serviceModule, appModule)
//            }
//        }
//    }

//    install(Authentication) {
//        jwt {
//            verifier(JwtConfig.verifier)
//            realm = AppEnv.JWT.realm
//            validate { credentials ->
//                return@validate if (AppEnv.isTest) {
//                    User("qwe", "login", "Login123", "")
//                } else {
//                    TODO("Implement JWT flow")
//                }
//            }
//        }
//    }

    install(ContentNegotiation) {
        gson {
            registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeAdapter())
        }
    }

    routing {
        userRoutes()
    }
}
