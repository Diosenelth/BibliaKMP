import Koin.getSaherdModules
import org.koin.core.context.startKoin


fun initKoin() {
    startKoin {
        modules(getSaherdModules())
    }.koin
}
