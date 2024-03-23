import Koin.sharedModule
import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    val database = DriverFactory()
    val dbBiblia = createDatabase(database)
    App(dbBiblia)
}
