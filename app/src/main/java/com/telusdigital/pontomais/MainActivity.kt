package com.telusdigital.pontomais

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.telusdigital.pontomais.navigation.PontoNavHost
import com.telusdigital.pontomais.ui.theme.PontoMaisTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PontoMaisTheme {
                PontoNavHost()
            }
        }
    }
}
