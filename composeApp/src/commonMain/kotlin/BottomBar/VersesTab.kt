package BottomBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.BibliaViewModel
import org.koin.compose.koinInject

object VersesTab : Tab {

    override val options: TabOptions
        @Composable
        get()  {
            val icon = rememberVectorPainter(Icons.Default.Info)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Versiculos",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        showVerses()
    }

}

@Composable
fun showVerses(viewModel: BibliaViewModel = koinInject()){
    val book = viewModel.getBook()
    val chapter = viewModel.getChapter()
    val verses = viewModel.getDatabase().databaseQueries.getAllVersesByIdbookAndChapter(book.id, chapter).executeAsList()

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)){
        Text("${book.name}: $chapter", fontWeight = FontWeight.Bold, fontSize = 25.sp)
        Spacer(modifier = Modifier.fillMaxWidth().height(10.dp))
        LazyColumn {
            items(verses){
                Text(text = "${it.verse}. ${it.text_verse}", fontSize = 22.sp)
            }
        }
    }
}