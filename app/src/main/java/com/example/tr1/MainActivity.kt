package com.example.tr1

import MenuScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.rememberNavController
import com.example.tr1.ui.TakeAwayApp
import com.example.tr1.ui.theme.TR1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TR1Theme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // Llamar a TakeAwayApp sin NavController
                    val navController = rememberNavController()
                    TakeAwayApp(navController, this) // Pasar 'this' como contexto
                }
                 // Pasar el ViewModel aquí


            }
        }
    }
}