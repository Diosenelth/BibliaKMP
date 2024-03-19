import android.content.Context
import android.os.Build
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.diose.bibliacomposekmp.Database
import java.io.FileOutputStream
import java.io.InputStream

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        // 1. get the database file from the application context obtained in the DatabaseDriverFactory constructor
        val database = context.getDatabasePath("biblia.db")

        // 2. check the database file (in first launch the app it does not exist yet)
        if (!database.exists()) {
            // 3. copy your pre-populated database from resources into the Android database directory
            val inputStream = this::class.java.getResourceAsStream("/drawable/biblia.db")
            val outputStream = FileOutputStream(database.absolutePath)
            inputStream?.copyTo(outputStream)
        }

        // 4. create the driver
        return AndroidSqliteDriver(Database.Schema, context, "biblia.db")
    }
}