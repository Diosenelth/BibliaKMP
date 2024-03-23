import Koin.sharedModule
import androidx.compose.ui.window.ComposeUIViewController
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {
    initKoin()
    App()
}

fun initKoin(){
    startKoin {
        modules(sharedModule)
    }
}