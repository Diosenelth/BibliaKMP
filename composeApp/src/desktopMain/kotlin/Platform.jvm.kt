import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.diose.bibliacomposekmp.Database
import java.io.File
import java.io.FileOutputStream

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        // Obtiene el directorio de datos específico de la aplicación
        val appDir = File(System.getProperty("user.home"), ".bibliaKmp")

        // Verifica si el directorio de la aplicación existe, si no, lo crea
        if (!appDir.exists()) {
            appDir.mkdir()
        }

        // Crea el archivo de base de datos dentro del directorio de la aplicación
        val fileDir = File(appDir, "biblia.db")
        // Verifica si el archivo ya existe
        if (!fileDir.exists()) {
            // Abre el archivo desde los recursos y copialo al directorio de archivos
            val inputStream = object {}.javaClass.getResourceAsStream("/drawable/biblia.db")
            val outputStream = FileOutputStream(fileDir)

            inputStream.use { input ->
                outputStream.use { output ->
                    input.copyTo(output)
                }
            }
        }
        val driver = JdbcSqliteDriver("jdbc:sqlite:${fileDir.absolutePath}")
        Database.Schema.create(driver)
        return driver
    }
}