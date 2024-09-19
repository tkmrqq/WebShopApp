package com.tkmrqq.pmsapp.ui.screen

import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.tkmrqq.pmsapp.R

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    AndroidView(
        factory = { context ->
            LayoutInflater.from(context).inflate(R.layout.account_screen, null)
        },
        modifier = modifier
    )
}