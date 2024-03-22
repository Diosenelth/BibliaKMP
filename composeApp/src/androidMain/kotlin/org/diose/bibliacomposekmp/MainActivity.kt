package org.diose.bibliacomposekmp

import App
import DriverFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import createDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = DriverFactory(this)
        val dbBiblia = createDatabase(database)

        setContent {
            App(dbBiblia)
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}