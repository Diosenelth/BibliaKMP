import app.cash.sqldelight.db.SqlDriver
import org.diose.bibliacomposekmp.Database

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): Database {
    val database = Database(driverFactory.createDriver())
    return database
}