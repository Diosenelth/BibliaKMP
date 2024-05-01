package BottomBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import domain.BibliaViewModel
import org.diose.bibliacomposekmp.Book_table
import org.koin.compose.koinInject

object BooksTab : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val icon = rememberVectorPainter(Icons.Default.Home)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Libros",
                    icon = icon
                )
            }
        }

    @Composable
    override fun Content() {
        ListBooks()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListBooks(viewModel: BibliaViewModel = koinInject()) {
    val listBook = remember { viewModel.getBooks() }
    val density = LocalDensity.current
    var columnWidth by remember { mutableStateOf(0.dp) }

    LazyColumn(
        flingBehavior = ScrollableDefaults.flingBehavior(),
        state = rememberLazyListState(),
        horizontalAlignment = Alignment.CenterHorizontally,
        content = {
            val myList = listBook.groupBy { (it.old == "true") }

            myList.entries.forEach { entry ->
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.LightGray)
                            .padding(6.dp)
                    ) {
                        Text(
                            text = if (entry.key) {
                                "Antiguo"
                            } else {
                                "Nuevo"
                            },
                            style = MaterialTheme.typography.titleMedium,
                        )

                    }
                }

                items(items = entry.value, key = { it.id }) { item ->
                    var expanded by remember { mutableStateOf(false) }
                    Column(
                        Modifier.clickable { expanded = !expanded }
                            .onGloballyPositioned { layoutCoordinates ->
                                columnWidth = (layoutCoordinates.size.width / density.density).dp
                            }
                    ) {
                        Text(
                            text = item.name,
                            modifier = Modifier.fillMaxWidth().height(55.dp).padding(8.dp),
                            fontSize = 25.sp,
                            fontWeight = FontWeight.Bold,
                        )
                        if (expanded) {
                            val chapters = (1..item.num_chapter).map { it }
                            FlowLayout(chapters, item, columnWidth)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun FlowLayout(
    items: List<Long>,
    id: Book_table,
    columnWidth: Dp,
    viewModel: BibliaViewModel = koinInject()
) {
    val navigator = LocalNavigator.current
    val density = LocalDensity.current
    val screenWidthPx = with(LocalDensity.current) { columnWidth.toPx() }

    val countMax =
        ((screenWidthPx - with(density) { 20.dp.toPx() }) / (with(density) { 50.dp.toPx() })).toInt()

    Column(Modifier.padding(start = 10.dp).padding(end = 10.dp)) {
        var start = 0
        while (start < items.size) {
            val end = minOf(start + countMax, items.size)
            Row(Modifier.padding(bottom = 8.dp)) {
                for (i in start until end) {
                    ClickableText(
                        text = AnnotatedString("${items[i]}"),
                        onClick = {
                            VersesTab.title = id.name
                            viewModel.setBook(id)
                            viewModel.setVerse(0)
                            viewModel.setChapter(items[i])
                            navigator?.push(VersesTab)
                        },
                        modifier = Modifier
                            .padding(end = 2.dp)
                            .width(50.dp)
                            .height(40.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .align(Alignment.CenterVertically),
                        style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                    )
                }
            }
            start = end
        }
    }
}