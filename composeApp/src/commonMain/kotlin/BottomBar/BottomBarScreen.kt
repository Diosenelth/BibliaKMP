package BottomBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

class BottomBarScreen : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        TabNavigator(
            HomeTab,
            tabDisposable = {
                TabDisposable(
                    it, listOf(HomeTab, VersesTab)
                )
            }
        ){
            Scaffold(
                topBar = {
                    TopAppBar(title = { Text(it.current.options.title) })
                },
                bottomBar = {
                    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                        val tabNavigator = LocalTabNavigator.current

                        NavigationBarItem(
                            selected = tabNavigator.current.key == HomeTab.key,
                            label = { Text(HomeTab.options.title) },
                            icon = { Icon(painter = HomeTab.options.icon!!, contentDescription = null) },
                            onClick = { tabNavigator.current = HomeTab }
                        )
                        NavigationBarItem(
                            selected = tabNavigator.current.key == VersesTab.key,
                            label = { Text("Versiculos") },
                            icon = { Icon(painter = VersesTab.options.icon!!, contentDescription = null) },
                            onClick = { tabNavigator.current = VersesTab }
                        )

                    }
                }, content = {
                    Box(modifier = Modifier.padding(
                        0.dp,
                        it.calculateTopPadding(),
                        0.dp,
                        it.calculateBottomPadding()
                    )){
                        CurrentTab()
                    }
                }
            )
        }
    }
}