import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    val database = DriverFactory()
    val dbBiblia = createDatabase(database)
    Window(onCloseRequest = ::exitApplication, title = "BibliaKMP") {
        App(dbBiblia)
    }
}