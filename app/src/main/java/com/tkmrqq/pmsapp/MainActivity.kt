package com.tkmrqq.pmsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.tkmrqq.pmsapp.ui.screen.AccountScreen
import com.tkmrqq.pmsapp.ui.screen.CartScreen
import com.tkmrqq.pmsapp.ui.theme.PMSAppTheme
import com.tkmrqq.pmsapp.ui.screen.HomeScreen;


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_login)

        setContent{
            PMSAppTheme { MainScreen() }
        }
    }
}


@Composable
fun MainScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = { BottomNavigationBar(selectedItem) { selectedItem = it } }
    ) { innerPadding ->
        when (selectedItem) {
            0 -> HomeScreen(Modifier.padding(innerPadding))
            1 -> CartScreen(Modifier.padding(innerPadding))
            2 -> AccountScreen(Modifier.padding(innerPadding))
        }
    }
}

@Composable
fun BottomNavigationBar(selectedItem: Int, onItemSelected: (Int) -> Unit) {
    val items = listOf("Home", "Cart", "Account")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.ShoppingCart,
        Icons.Filled.AccountCircle
    )
    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(icons[index], contentDescription = item) },
                label = { Text(item) },
                selected = selectedItem == index,
                onClick = { onItemSelected(index) }
            )
        }
    }
}


