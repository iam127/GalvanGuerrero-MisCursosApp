package com.tecsup.galvanguerreromiscursosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.google.firebase.FirebaseApp
import com.tecsup.galvanguerreromiscursosapp.navigation.AuthApp
import com.tecsup.galvanguerreromiscursosapp.ui.theme.GalvanGuerreroMisCursosAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar Firebase
        FirebaseApp.initializeApp(this)

        setContent {
            GalvanGuerreroMisCursosAppTheme {
                AuthApp()
            }
        }
    }
}