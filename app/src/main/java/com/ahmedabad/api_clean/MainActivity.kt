package com.ahmedabad.api_clean

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ahmedabad.api_clean.ui.screen.UserScreen
import com.ahmedabad.api_clean.ui.theme.APICleanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APICleanTheme {
                UserScreen(modifier = Modifier.fillMaxSize())
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    APICleanTheme {
        UserScreen(modifier = Modifier)
    }
}