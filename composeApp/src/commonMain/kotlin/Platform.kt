import app.cash.sqldelight.db.SqlDriver
import org.diose.bibliacomposekmp.BibliaDatabase

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
expect class DriverFactory {
    fun createDriver(): SqlDriver
}

fun createDatabase(driverFactory: DriverFactory): BibliaDatabase {
    val database = BibliaDatabase(driverFactory.createDriver())
    return database
}