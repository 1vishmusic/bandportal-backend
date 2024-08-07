package cz.cvut.fit.stehlvo2.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import cz.cvut.fit.stehlvo2.db.dao.*
import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun createHikariConfig(): HikariDataSource {
    val dotenv = dotenv {
        ignoreIfMissing = true
    }
    val config = HikariConfig()

    config.driverClassName = dotenv["DB_DRIVER"]
    config.jdbcUrl = dotenv["DB_URL"]
    config.username = dotenv["DB_USERNAME"]
    config.password = dotenv["DB_PASSWORD"]
    config.maximumPoolSize = dotenv["DB_MAX_POOL_SIZE"]?.toInt() ?: 3
    config.isAutoCommit = dotenv["DB_AUTO_COMMIT"]?.toBoolean() ?: false
    config.transactionIsolation = dotenv["DB_TRANSACTION_ISOLATION"] ?: "TRANSACTION_REPEATABLE_READ"
    config.validate()

    return HikariDataSource(config)
}

object DatabaseSingleton {
    fun init() {
        Database.connect(HikariDataSource(createHikariConfig()))

        transaction {
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(Bands)
            SchemaUtils.create(Places)
            SchemaUtils.create(Events)
            SchemaUtils.create(EventBands)
            SchemaUtils.create(Tickets)
        }
    }
}

