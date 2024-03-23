package org.diose.bibliacomposekmp

import App
import DriverFactory
import Koin.sharedModule
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import createDatabase
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

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