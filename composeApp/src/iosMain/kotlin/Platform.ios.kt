import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import org.diose.bibliacomposekmp.BibliaDatabase
import platform.Foundation.*
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class DriverFactory {
    @OptIn(ExperimentalForeignApi::class)
    actual fun createDriver(): SqlDriver {
//        return NativeSqliteDriver(BibliaDatabase.Schema, "biblia.db")

        // Obtener el directorio de documentos de la aplicación
//        val documentsDir = NSSearchPathForDirectoriesInDomains(NSLibraryDirectory, NSUserDomainMask, true)[0] as String //carpeta correcta - da error
        val documentsDir = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)[0] as String

        // Crear el directorio de la base de datos si no existe
//        val dbDirPath = "$documentsDir/Application Support/Databases"//carpeta correcta - da error
        val dbDirPath = "$documentsDir/Databases"
        NSFileManager.defaultManager.createDirectoryAtPath(dbDirPath, true, null, null)

        // Crear el archivo de base de datos dentro del directorio de la aplicación
        val dbFilePath = "$dbDirPath/biblia.db"

        // Verificar si el archivo ya existe
        if (!NSFileManager.defaultManager.fileExistsAtPath(dbFilePath)) {
            // Abrir el archivo desde los recursos y copiarlo al directorio de archivos
            val resourcePath = NSBundle.mainBundle.pathForResource("biblia", "db")
            //val ruta = NSBundle.mainBundle.URLForResource("biblia", "db")
            NSFileManager.defaultManager.copyItemAtPath(resourcePath!!, dbFilePath, null)
        }

        // Crear el driver de la base de datos
        return NativeSqliteDriver(BibliaDatabase.Schema, "biblia.db")
    }
}