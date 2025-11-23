GalvanGuerrero-MisCursosApp
Aplicación Android de Gestión de Cursos con Firebase
Autor
Matias Galvan Guerrero
Instituto de Educación Superior Tecnológica TECSUP
Carrera: Desarrollo de Aplicaciones Web y Móviles
Curso: Programación Móvil - Laboratorio 12

Descripción
Aplicación móvil Android desarrollada en Kotlin que implementa un sistema completo de gestión de cursos académicos con autenticación de usuarios mediante Firebase Authentication y almacenamiento en tiempo real con Cloud Firestore. La aplicación permite a cada usuario registrar, visualizar, editar y eliminar sus propios cursos de manera segura e independiente.
Características Técnicas
Arquitectura y Patrones

Arquitectura basada en componentes con Jetpack Compose
Patrón de navegación mediante Navigation Compose
Separación de responsabilidades (Models, Screens, Navigation)
Gestión de estado reactivo con State y MutableState
Composables reutilizables y modulares

Funcionalidades Principales

Sistema completo de autenticación (registro, login, logout)
Operaciones CRUD en Cloud Firestore
Filtrado de datos por usuario autenticado
Validación de formularios en tiempo real
Sincronización automática de datos
Manejo de estados de carga y errores
Interfaz de usuario responsive

Stack Tecnológico
Frontend:

Kotlin 1.9+
Jetpack Compose (UI declarativa)
Material Design 3
Compose Navigation 2.8.0

Backend & Servicios:

Firebase Authentication (Email/Password)
Cloud Firestore Database
Firebase BOM 33.1.0

Herramientas de Desarrollo:

Android Studio Hedgehog
Gradle Kotlin DSL
JDK 11
Min SDK: 26 (Android 8.0)
Target SDK: 36

Estructura del Proyecto
app/src/main/java/com/tecsup/galvanguerreromiscursosapp/
│
├── models/
│   └── Curso.kt                    # Data class del modelo de dominio
│
├── screens/
│   ├── LoginScreen.kt              # Pantalla de autenticación
│   ├── RegisterScreen.kt           # Pantalla de registro de usuarios
│   └── CursosListScreen.kt         # Pantalla principal con operaciones CRUD
│
├── navigation/
│   └── AuthApp.kt                  # Configuración del grafo de navegación
│
├── ui/theme/
│   ├── Color.kt                    # Definición de paleta de colores
│   ├── Theme.kt                    # Configuración del tema Material
│   └── Type.kt                     # Sistema de tipografía
│
└── MainActivity.kt                  # Activity principal y punto de entrada
Modelo de Datos
Entidad Curso
kotlindata class Curso(
    val id: String = "",
    val nombre: String = "",
    val descripcion: String = "",
    val creditos: Int = 0,
    val userId: String = ""
)
Esquema Firestore
Colección: cursos
CampoTipoDescripciónidStringIdentificador único generado automáticamentenombreStringNombre del cursodescripcionStringDescripción detallada del cursocreditosIntNúmero de créditos académicosuserIdStringUID del usuario propietario (Firebase Auth)
Configuración e Instalación
Requisitos Previos

Android Studio Hedgehog (2023.1.1) o superior
JDK 11 o superior
Cuenta de Google/Firebase
Dispositivo Android con API 26+ o emulador configurado
Git instalado

Configuración de Firebase

Crear proyecto en Firebase Console
Nombre del proyecto: GalvanGuerrero-MisCursosApp
Habilitar Firebase Authentication:

Método: Correo electrónico/Contraseña
Activar proveedor de inicio de sesión


Habilitar Cloud Firestore:

Modo: Iniciar en modo de prueba
Ubicación: southamerica-east1 (São Paulo)


Registrar aplicación Android:

Package name: com.tecsup.galvanguerreromiscursosapp
Descargar archivo google-services.json



Instalación Local
bash# Clonar el repositorio
git clone https://github.com/[tu-usuario]/GalvanGuerrero-MisCursosApp.git

# Navegar al directorio del proyecto
cd GalvanGuerrero-MisCursosApp

# Copiar google-services.json al directorio app/
cp /ruta/a/google-services.json app/

# Abrir el proyecto en Android Studio
# File > Open > Seleccionar carpeta del proyecto

# Sincronizar dependencias de Gradle
# Build > Sync Project with Gradle Files

# Ejecutar la aplicación
# Run > Run 'app' (Shift + F10)
Configuración de Gradle
build.gradle.kts (Project)
kotlinplugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.gms.google-services") version "4.4.2" apply false
}
build.gradle.kts (Module: app)
kotlinplugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.tecsup.galvanguerreromiscursosapp"
    compileSdk = 36
    
    defaultConfig {
        applicationId = "com.tecsup.galvanguerreromiscursosapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}
Reglas de Seguridad Firestore
javascriptrules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /cursos/{curso} {
      allow read, write: if request.auth != null && 
                           request.auth.uid == resource.data.userId;
      allow create: if request.auth != null;
    }
  }
}
```

## Funcionalidades Implementadas

### Autenticación
- Registro de usuarios con validación de email y contraseña
- Inicio de sesión con credenciales persistentes
- Cierre de sesión con limpieza de estado
- Validación de contraseña mínima de 6 caracteres
- Confirmación de contraseña en registro
- Manejo de errores de autenticación

### Gestión de Cursos
- **Create:** Agregar nuevos cursos con nombre, descripción y créditos
- **Read:** Listado de cursos filtrados por usuario autenticado
- **Update:** Edición completa de información de cursos existentes
- **Delete:** Eliminación de cursos con confirmación
- Actualización en tiempo real mediante listeners de Firestore
- Validación de campos obligatorios y formato de datos

### Interfaz de Usuario
- Diseño Material Design 3 con componentes nativos
- Sistema de navegación entre pantallas sin stack infinito
- Feedback visual mediante Toast messages
- Estados de carga durante operaciones asíncronas
- Diálogos modales para creación y edición
- Lista vacía con mensaje informativo
- Iconografía descriptiva (Material Icons)

## Flujo de Navegación
```
LoginScreen
    ├─> RegisterScreen ──┐
    └─> CursosListScreen <┘
            ├─> Agregar Curso (Dialog)
            ├─> Editar Curso (Dialog)
            ├─> Eliminar Curso
            └─> Logout ──> LoginScreen
Testing y Validación
Casos de Prueba Implementados

Registro de usuario con email válido
Registro con contraseña menor a 6 caracteres (validación)
Inicio de sesión con credenciales correctas
Inicio de sesión con credenciales incorrectas
Creación de curso con todos los campos completos
Creación de curso con campos vacíos (validación)
Edición de curso existente
Eliminación de curso
Verificación de aislamiento de datos entre usuarios
Persistencia de sesión al cerrar y abrir app
Sincronización en tiempo real con Firestore

Manejo de Errores

Conexión a internet ausente
Credenciales de autenticación inválidas
Campos de formulario vacíos
Formato de email incorrecto
Contraseñas que no coinciden
Errores de permisos en Firestore

Seguridad
Medidas Implementadas

Autenticación obligatoria para acceder a la aplicación
Filtrado de datos por userId en todas las consultas
Reglas de seguridad en Firestore por usuario
No se exponen credenciales en el código
Uso de google-services.json excluido del control de versiones
Validación de entrada en cliente y servidor

Buenas Prácticas

Uso de Firebase BOM para gestión de versiones
Implementación de LaunchedEffect para listeners
Manejo de estados con remember y mutableStateOf
Composables sin efectos secundarios
Navegación con popUpTo para evitar stacks innecesarios

Mejoras Futuras

Implementación de búsqueda y filtros en lista de cursos
Ordenamiento por nombre, créditos o fecha de creación
Recuperación de contraseña mediante email
Perfil de usuario con foto y datos personales
Estadísticas de cursos y créditos totales
Exportación de datos a PDF
Notificaciones push para recordatorios
Modo offline con sincronización posterior
Implementación de pruebas unitarias y de UI
Integración continua con GitHub Actions

Licencia
Este proyecto fue desarrollado con fines académicos como parte del Laboratorio 12 del curso de Programación Móvil en TECSUP.
Contacto
Matias Galvan Guerrero
Estudiante de Desarrollo de Aplicaciones Web y Móviles
TECSUP - La Molina, Lima, Perú

Versión: 1.0
Fecha de Desarrollo: Noviembre 2025
Curso: Programación Móvil
Docente: Juan José León S.
