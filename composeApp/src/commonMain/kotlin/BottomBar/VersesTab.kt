package BottomBar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
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
import bibliakmp.composeapp.generated.resources.Res
import bibliakmp.composeapp.generated.resources.navigate_before
import bibliakmp.composeapp.generated.resources.navigate_next
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.BibliaViewModel
import org.diose.bibliacomposekmp.Verse_table
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
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

@OptIn(ExperimentalResourceApi::class)
@Composable
fun showVerses(viewModel: BibliaViewModel = koinInject()){
    val book = viewModel.getBook()
    var chapter by remember { mutableStateOf(0L)}
    var verse by remember { mutableStateOf(0)}
    val lazyColumnListState = rememberLazyListState()
    chapter = viewModel.getChapter()
    verse = viewModel.getVerse()
    val verses = remember {  mutableStateListOf<Verse_table>() }
    verses.swapList(viewModel.getDatabase().databaseQueries.getAllVersesByIdbookAndChapter(book.id, chapter).executeAsList())

    LaunchedEffect(key1 = Unit){
        if (verse == 0){
            lazyColumnListState.scrollToItem(0)
        }else{
            lazyColumnListState.animateScrollToItem((verse - 1))
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn(state = lazyColumnListState) {
                items(items = verses, key = { it.id }) {
                    Text(text = "${it.verse}. ${it.text_verse}", fontSize = 22.sp)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth().height(50.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FloatingActionButton(
                onClick = {
                    if (chapter > 1) {
                        verse = 0
                        viewModel.setVerse(0)
                        viewModel.setChapter(chapter - 1)
                        chapter -= 1
                    }
                }, content = {
                    Image(painterResource(Res.drawable.navigate_before), "Capitulo anterior.")
                }
            )

            Text(
                "${book.name}: $chapter",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                textAlign = TextAlign.Center
            )

            FloatingActionButton(
                onClick = {
                    if (chapter + 1 <= book.num_chapter) {
                        verse = 0
                        viewModel.setVerse(0)
                        viewModel.setChapter(chapter + 1)
                        chapter += 1
                    }
                }, content = {
                    Image(painterResource(Res.drawable.navigate_next), "Capitulo siguiente.")
                }
            )
        }
    }
}

@Immutable
data class Verses(
    val id: Long,
    val id_book: Long,
    val chapter: Long,
    val verse: Long,
    val text_verse: String,
    val favorite: Long,
    val text_note: String?,
)

fun List<Verse_table>.convertVerses() : List<Verses> {
    return this.map {
        Verses(
            it.id, it.id_book, it.chapter, it.verse, it.text_verse, it.favorite, it.text_note
        )
    }
}

fun <Verse_table> SnapshotStateList<Verse_table>.swapList(newList: List<Verse_table>){
    clear()
    addAll(newList)
}