package com.serelik.surfbooks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.serelik.surfbooks.ui.MainScreen
import com.serelik.surfbooks.ui.theme.SurfBooksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            // enableEdgeToEdge()
        setContent {
            SurfBooksTheme {
               MainScreen()
            }
        }
    }
}
