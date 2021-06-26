package repository

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

abstract class BaseRepository(
    private val database: Database,
) {

    protected suspend fun <T> dbCall(block: Transaction.() -> T?): T? {
        return suspendedTransactionAsync(Dispatchers.IO, database) {
            try {
                block.invoke(this)
            } catch (e: Exception) {
                e.printStackTrace()
                return@suspendedTransactionAsync null
            }
        }.await()
    }
}