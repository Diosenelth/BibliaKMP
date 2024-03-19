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
    val driver = driverFactory.createDriver()
    val database = Database(driver)


    // Do more work with the database (see below).
    return database
}