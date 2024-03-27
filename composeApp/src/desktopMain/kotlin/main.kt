import Koin.startKoinCommon
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.koin.core.logger.Level

fun main() = application {
    startKoinCommon {
        printLogger(Level.DEBUG)
    }
    val database = DriverFactory()
    val dbBiblia = createDatabase(database)
    Window(onCloseRequest = ::exitApplication, title = "BibliaKMP") {
        App(dbBiblia)
    }
}