package BottomBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.BibliaViewModel
import org.diose.bibliacomposekmp.Book_table
import org.diose.bibliacomposekmp.Verse_table
import org.koin.compose.koinInject

object VersesTab : Tab {
    var title = ""
    override val options: TabOptions
        @Composable
        get()  {
            val icon = rememberVectorPainter(Icons.Default.Info)
            return remember {
                TabOptions(
                    index = 1u,
                    title = title,
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
    var chapter by remember { mutableStateOf(0L)}
    chapter = viewModel.getChapter()
    val verses = remember {  mutableStateListOf<Verse_table>() }
    verses.swapList(viewModel.getDatabase().databaseQueries.getAllVersesByIdbookAndChapter(book.id, chapter).executeAsList())

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                items(verses) {
                    Text(text = "${it.verse}. ${it.text_verse}", fontSize = 22.sp)
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().height(50.dp), horizontalArrangement = Arrangement.SpaceBetween ) {
            FloatingActionButton(content = { Text("<") }, onClick = {
                if (chapter > 1) {
                    println("Click -")
                    viewModel.setChapter(chapter - 1)
                    chapter -= 1
                }
            })

            Text("${book.name}: $chapter", fontWeight = FontWeight.Bold, fontSize = 25.sp, textAlign = TextAlign.Center)

            FloatingActionButton(content = { Text(">") }, onClick = {
                if (chapter + 1 <= book.num_chapter) {
                    println("Click +")
                    viewModel.setChapter(chapter + 1)
                    chapter += 1
                }
            })
        }
    }
}

fun <T> SnapshotStateList<T>.swapList(newList: List<T>){
    clear()
    addAll(newList)
}