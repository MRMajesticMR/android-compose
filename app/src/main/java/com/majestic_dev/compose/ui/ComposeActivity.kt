package com.majestic_dev.compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.majestic_dev.compose.ui.theme.ComposeTheme

class ComposeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MessageCard(name = "Android")
        }

    }

    @Composable
    fun MessageCard(name: String) {
        Text(text = "Hello $name")
    }

    @Preview
    @Composable
    fun PreviewMessageCard() {
        MessageCard(name = "IOS")
    }
}