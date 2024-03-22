import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.diose.bibliacomposekmp.BibliaDatabase
import org.diose.bibliacomposekmp.Book_table
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(database: BibliaDatabase? = null) {
    MaterialTheme {
        if (database != null) {
            val listBook = database.databaseQueries.selectAllBooks().executeAsList()
            showList(listBook)
        }
    }
}

@Composable
fun showList(list: List<Book_table>) {
    val scroll = rememberScrollState()
    val density = LocalDensity.current
    var columnWidth by remember { mutableStateOf(0.dp) }
    LazyColumn(modifier = Modifier.fillMaxSize().scrollable(scroll, Orientation.Vertical)) {
        items(list) { item ->
            var expanded by remember { mutableStateOf(false) }
            Column(
                Modifier.clickable { expanded = !expanded }.onGloballyPositioned { layoutCoordinates ->
                    columnWidth = (layoutCoordinates.size.width / density.density).dp
                }
            ) {
                Text(
                    text = item.name,
                    modifier = Modifier.fillMaxWidth().height(50.dp).padding(8.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                if (expanded) {
                    val chapters = (1..item.num_chapter).map { "$it" }
                    FlowLayout(chapters, item.id, columnWidth)
                }
            }
        }
    }
}

@Composable
fun FlowLayout(items: List<String>, id: Long, columnWidth: Dp) {

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
                        text = AnnotatedString(items[i]),
                        onClick = { println("Libro $id capitulo ${items[i]}") },
                        modifier = Modifier.padding(end = 2.dp).width(50.dp).height(40.dp),
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
            start = end
        }
    }
}