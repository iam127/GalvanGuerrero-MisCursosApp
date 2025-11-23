package com.tecsup.galvanguerreromiscursosapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.firebase.FirebaseApp
import com.tecsup.galvanguerreromiscursosapp.models.Curso
import com.tecsup.galvanguerreromiscursosapp.navigation.AuthApp
import com.tecsup.galvanguerreromiscursosapp.screens.CursoCard
import com.tecsup.galvanguerreromiscursosapp.screens.CursoDialog
import com.tecsup.galvanguerreromiscursosapp.screens.CursosListScreen
import com.tecsup.galvanguerreromiscursosapp.screens.LoginScreen
import com.tecsup.galvanguerreromiscursosapp.screens.RegisterScreen
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

// ======================== PREVIEWS ========================

@Preview(showBackground = true, name = "Login Screen")
@Composable
fun PreviewLoginScreen() {
    GalvanGuerreroMisCursosAppTheme {
        LoginScreen(
            onLoginSuccess = { },
            onNavigateToRegister = { }
        )
    }
}

@Preview(showBackground = true, name = "Register Screen")
@Composable
fun PreviewRegisterScreen() {
    GalvanGuerreroMisCursosAppTheme {
        RegisterScreen(
            onRegisterSuccess = { },
            onNavigateBack = { }
        )
    }
}

@Preview(showBackground = true, name = "Cursos List - Empty")
@Composable
fun PreviewCursosListEmpty() {
    GalvanGuerreroMisCursosAppTheme {
        CursosListScreen(
            onLogout = { }
        )
    }
}

@Preview(showBackground = true, name = "Curso Card")
@Composable
fun PreviewCursoCard() {
    GalvanGuerreroMisCursosAppTheme {
        CursoCard(
            curso = Curso(
                id = "1",
                nombre = "Programación Móvil",
                descripcion = "Desarrollo de aplicaciones Android con Kotlin y Jetpack Compose",
                creditos = 4,
                userId = "user123"
            ),
            onEdit = { },
            onDelete = { }
        )
    }
}

@Preview(showBackground = true, name = "Curso Dialog - Nuevo")
@Composable
fun PreviewCursoDialogNuevo() {
    GalvanGuerreroMisCursosAppTheme {
        CursoDialog(
            curso = null,
            onDismiss = { },
            onSave = { _, _, _ -> }
        )
    }
}

@Preview(showBackground = true, name = "Curso Dialog - Editar")
@Composable
fun PreviewCursoDialogEditar() {
    GalvanGuerreroMisCursosAppTheme {
        CursoDialog(
            curso = Curso(
                id = "1",
                nombre = "Programación Móvil",
                descripcion = "Desarrollo Android con Kotlin",
                creditos = 4,
                userId = "user123"
            ),
            onDismiss = { },
            onSave = { _, _, _ -> }
        )
    }
}

@Preview(
    showBackground = true,
    name = "Login Screen - Dark Mode",
    uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun PreviewLoginScreenDark() {
    GalvanGuerreroMisCursosAppTheme {
        LoginScreen(
            onLoginSuccess = { },
            onNavigateToRegister = { }
        )
    }
}