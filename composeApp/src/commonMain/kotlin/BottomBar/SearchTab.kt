package BottomBar

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
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.BibliaViewModel
import org.diose.bibliacomposekmp.Verse_table
import org.koin.compose.koinInject

object SearchTab : Tab {

    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter((Icons.Default.Search))
            return remember {
                TabOptions(
                    index = 2u,
                    title = "Buscar",
                    icon = icon
                )
            }

        }

    @Composable
    override fun Content() {
        showVersesSearch()
    }

}

@Composable
fun showVersesSearch(viewModel: BibliaViewModel = koinInject()){
    val books = viewModel.getBooks()
    var chapter by remember { mutableStateOf(0L) }
    var searchText by remember { mutableStateOf("") }
    chapter = viewModel.getChapter()
    val verses = remember {  mutableStateListOf<Verse_table>() }
    val navigator = LocalNavigator.current

    Column(modifier = Modifier.fillMaxSize().padding(8.dp)) {
        Box(modifier = Modifier.weight(1f)) {
            LazyColumn {
                items(verses) {
                    val filterBook = books.filter { bookTable ->
                        it.id_book == bookTable.id
                    }
                    ClickableText(
                        text = AnnotatedString(it.text_verse),
                        onClick = {onClick ->
                            VersesTab.title = filterBook[0].name
                            viewModel.setBook(filterBook[0])
                            viewModel.setChapter(it.chapter)
                            viewModel.setVerse(it.verse)
                            navigator?.push(VersesTab)
                        },
                        style = TextStyle(fontSize = 22.sp)
                    )
                    Text(text = "${filterBook[0].name} ${it.chapter}:${it.verse}", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth()
            .height(70.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.height(65.dp).weight(1f),
                value = searchText,
                label = { Text(text = "Escriba aqui") },
                onValueChange = { searchText = it },
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = {
                        if (searchText.isNotEmpty()){
                            val searchVerse = viewModel.getDatabase().databaseQueries.getAllVersesSearch(searchText).executeAsList()
                            verses.swapList(searchVerse)
                        }else{
                            verses.swapList(listOf())
                        }
                    }
                )
            )

            IconButton(onClick = {
                if (searchText.isNotEmpty()){
                    val searchVerse = viewModel.getDatabase().databaseQueries.getAllVersesSearch(searchText).executeAsList()
                    verses.swapList(searchVerse)
                }else{
                    verses.swapList(listOf())
                }
            }){
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }
    }
}