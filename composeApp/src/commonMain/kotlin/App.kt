import BottomBar.BottomBarScreen
import androidx.compose.material.MaterialTheme
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
            if (listBook.isNotEmpty())viewModel.setBook(listBook[0])
            viewModel.setBooks(listBook)
            Navigator(screen = BottomBarScreen()){ navigator ->
                SlideTransition(navigator)
            }
        }
    }
}

