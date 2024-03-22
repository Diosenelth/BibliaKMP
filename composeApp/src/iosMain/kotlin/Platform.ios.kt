import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import org.diose.bibliacomposekmp.BibliaDatabase
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(BibliaDatabase.Schema, "biblia.db")
    }
}