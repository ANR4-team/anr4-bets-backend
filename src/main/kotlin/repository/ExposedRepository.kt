package repository

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.statements.InsertStatement
import org.jetbrains.exposed.sql.transactions.experimental.suspendedTransactionAsync

abstract class ExposedRepository<E : Table, T>(private val database: Database) {

    protected abstract val table: E

    protected abstract fun ResultRow.convert(): T

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

    suspend fun getAll(): List<T>? {
        return dbCall {
            table.selectAll()
                .map { it.convert() }
        }
    }

    protected fun Query.querySingle(): T? {
        return limit(1)
            .singleOrNull()
            ?.convert()
    }

    protected fun InsertStatement<Number>.inserted(): T? {
        return resultedValues
            ?.singleOrNull()
            ?.convert()
    }
}