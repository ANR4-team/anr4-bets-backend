package utils.features

import io.ktor.application.*
import io.ktor.util.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import java.net.URI

class DatabaseFeature private constructor(
    private val url: String,
    private val onConnected: (Database) -> Unit,
) {

    class Configuration {
        var url: String = ""
        var onDatabaseConnected: (Database) -> Unit = {}
    }

    fun connect(): Database {
        val uri = URI(url)
        val username = uri.userInfo.split(":")[0]
        val password = uri.userInfo.split(":")[1]
        val dbUrl = "jdbc:postgresql://${uri.host}:${uri.port}${uri.path}?sslmode=require"
        return Database.connect(dbUrl, "org.postgresql.Driver", username, password).also {
            transaction(it) {
                onConnected.invoke(it)
            }
        }
    }

    companion object Feature : ApplicationFeature<Application, Configuration, DatabaseFeature> {
        override val key: AttributeKey<DatabaseFeature>
            get() = AttributeKey("DatabaseFeature")

        override fun install(pipeline: Application, configure: Configuration.() -> Unit): DatabaseFeature {
            val config = Configuration().apply(configure)
            val databaseFeature = DatabaseFeature(
                config.url,
                config.onDatabaseConnected,
            )
            databaseFeature.connect()
            return databaseFeature
        }
    }
}