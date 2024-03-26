import BottomBar.BottomBarScreen
import BottomBar.VersesTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import domain.BibliaViewModel
import org.diose.bibliacomposekmp.BibliaDatabase
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.koinInject

@Composable
@Preview
fun App(db: BibliaDatabase, viewModel: BibliaViewModel = koinInject()) {
    KoinContext {
        MaterialTheme {
            viewModel.setDatabase(db)
            val listBook = db.databaseQueries.selectAllBooks().executeAsList()
            if (listBook.isNotEmpty()){
                viewModel.setBook(listBook[0])
                VersesTab.title = listBook[0].name
            }
            viewModel.setBooks(listBook)
            Navigator(screen = BottomBarScreen()){ navigator ->
                SlideTransition(navigator)
            }
        }
    }
}

