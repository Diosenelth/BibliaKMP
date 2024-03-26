package BottomBar

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator

class BottomBarScreen : Screen {
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
                    BottomNavigation {
                        val tabNavigator = LocalTabNavigator.current

                        BottomNavigationItem(
                            selected = tabNavigator.current.key == HomeTab.key,
                            label = { Text(HomeTab.options.title) },
                            icon = { Icon(painter = HomeTab.options.icon!!, contentDescription = null) },
                            onClick = { tabNavigator.current = HomeTab }
                        )
                        BottomNavigationItem(
                            selected = tabNavigator.current.key == VersesTab.key,
                            label = { Text(VersesTab.options.title) },
                            icon = { Icon(painter = VersesTab.options.icon!!, contentDescription = null) },
                            onClick = { tabNavigator.current = VersesTab }
                        )

                    }
                }, content = {
                    CurrentTab()
                }
            )
        }
    }
}