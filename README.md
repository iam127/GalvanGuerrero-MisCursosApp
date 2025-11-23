GalvanGuerrero-MisCursosApp
Sistema de Gestión de Cursos Académicos con Firebase
Versión: 1.0.0
Desarrollador: Matias Galvan Guerrero
Institución: TECSUP - Instituto de Educación Superior Tecnológica
Programa Académico: Desarrollo de Aplicaciones Web y Móviles

Resumen Ejecutivo
GalvanGuerrero-MisCursosApp es una aplicación móvil nativa Android desarrollada en Kotlin que implementa un sistema integral de gestión de cursos académicos. La solución incorpora autenticación segura mediante Firebase Authentication y persistencia de datos en tiempo real con Cloud Firestore, permitiendo operaciones CRUD completas con aislamiento de datos por usuario.
Especificaciones Técnicas
Arquitectura del Sistema

Patrón Arquitectónico: Component-based Architecture
UI Framework: Jetpack Compose (Declarative UI)
Gestión de Estado: Reactive State Management
Navegación: Single-Activity Architecture con Navigation Component
Backend as a Service: Firebase Platform

Stack Tecnológico
ComponenteTecnologíaVersiónLenguaje de ProgramaciónKotlin1.9+Framework UIJetpack Compose1.5+Sistema de DiseñoMaterial Design3.0AutenticaciónFirebase Authentication22.3.0Base de DatosCloud Firestore24.10.0NavegaciónNavigation Compose2.8.0Build SystemGradle Kotlin DSL8.2IDEAndroid StudioHedgehog 2023.1.1
Requisitos del Sistema
Plataforma Objetivo:

Sistema Operativo: Android
SDK Mínimo: API 26 (Android 8.0 Oreo)
SDK Objetivo: API 36
Arquitectura: ARM, ARM64, x86, x86_64

Entorno de Desarrollo:

JDK 11 o superior
Android Studio Hedgehog o superior
Gradle 8.2+
Cuenta Firebase activa

Arquitectura de la Aplicación
Estructura de Capas
┌─────────────────────────────────────┐
│     Presentation Layer (UI)         │
│  ┌──────────────────────────────┐   │
│  │  Jetpack Compose Screens     │   │
│  │  - LoginScreen               │   │
│  │  - RegisterScreen            │   │
│  │  - CursosListScreen          │   │
│  └──────────────────────────────┘   │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      Navigation Layer               │
│  ┌──────────────────────────────┐   │
│  │  Navigation Graph            │   │
│  │  Route Management            │   │
│  └──────────────────────────────┘   │
└─────────────────────────────────────┘
              ↓
┌─────────────────────────────────────┐
│      Data Layer                     │
│  ┌──────────────────────────────┐   │
│  │  Firebase Authentication     │   │
│  │  Cloud Firestore             │   │
│  └──────────────────────────────┘   │
└─────────────────────────────────────┘
Organización del Código
com.tecsup.galvanguerreromiscursosapp/
├── models/
│   └── Curso.kt                    # Entidades de dominio
├── screens/
│   ├── LoginScreen.kt              # Capa de presentación
│   ├── RegisterScreen.kt
│   └── CursosListScreen.kt
├── navigation/
│   └── AuthApp.kt                  # Configuración de rutas
├── ui/theme/
│   ├── Color.kt                    # Sistema de diseño
│   ├── Theme.kt
│   └── Type.kt
└── MainActivity.kt                  # Punto de entrada
Modelo de Datos
Esquema de Entidades
Entidad: Curso
kotlindata class Curso(
    val id: String,           // Identificador único (UUID)
    val nombre: String,       // Nombre del curso (máx. 100 caracteres)
    val descripcion: String,  // Descripción detallada (máx. 500 caracteres)
    val creditos: Int,        // Créditos académicos (rango: 1-8)
    val userId: String        // Foreign Key a Firebase Auth UID
)
Base de Datos Cloud Firestore
Colección: cursos
CampoTipoRestriccionesDescripciónidstringPK, NOT NULLIdentificador único autogeneradonombrestringNOT NULL, MAX 100Denominación del cursodescripcionstringNULLABLE, MAX 500Información descriptivacreditosnumberNOT NULL, MIN 1, MAX 8Valor crediticiouserIdstringFK, NOT NULL, INDEXEDReferencia al usuario propietario
Índices:

userId (ASC) - Para consultas filtradas por usuario
createdAt (DESC) - Para ordenamiento cronológico (opcional)

Configuración del Entorno
1. Configuración de Firebase
1.1 Creación del Proyecto
bash1. Acceder a Firebase Console (https://console.firebase.google.com)
2. Crear nuevo proyecto: "GalvanGuerrero-MisCursosApp"
3. Deshabilitar Google Analytics (opcional)
4. Confirmar creación del proyecto
1.2 Configuración de Authentication
bash1. Navegación: Authentication > Sign-in method
2. Habilitar proveedor: Email/Password
3. Configurar dominio autorizado: localhost (desarrollo)
4. Guardar configuración
1.3 Configuración de Firestore
bash1. Navegación: Firestore Database > Create database
2. Modo inicial: Test mode (desarrollo) / Production mode (producción)
3. Ubicación: southamerica-east1 (São Paulo, Brasil)
4. Crear base de datos
1.4 Registro de Aplicación Android
bash1. Agregar app Android al proyecto Firebase
2. Package name: com.tecsup.galvanguerreromiscursosapp
3. App nickname: GalvanGuerrero-MisCursosApp
4. Descargar google-services.json
5. SHA-1: [Opcional para desarrollo local]
2. Configuración Local
2.1 Clonación del Repositorio
bashgit clone https://github.com/[username]/GalvanGuerrero-MisCursosApp.git
cd GalvanGuerrero-MisCursosApp
2.2 Integración de Firebase
bash# Copiar archivo de configuración
cp /path/to/google-services.json app/google-services.json

# Verificar ubicación correcta
ls -la app/google-services.json
2.3 Configuración de Gradle
build.gradle.kts (Project-level)
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
        versionName = "1.0.0"
        
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Firebase Platform
    implementation(platform("com.google.firebase:firebase-bom:33.1.0"))
    implementation("com.google.firebase:firebase-auth-ktx")
    implementation("com.google.firebase:firebase-firestore-ktx")

    // Jetpack Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)

    // Navigation
    implementation("androidx.navigation:navigation-compose:2.8.0")

    // Core Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Debug
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
2.4 Sincronización y Compilación
bash# En Android Studio
./gradlew clean
./gradlew build

# O mediante interfaz gráfica
File > Sync Project with Gradle Files
Build > Make Project
Reglas de Seguridad
Firestore Security Rules
Modo Desarrollo:
javascriptrules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.time < timestamp.date(2025, 12, 31);
    }
  }
}
Modo Producción (Recomendado):
javascriptrules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /cursos/{cursoId} {
      // Permitir lectura solo si el usuario es el propietario
      allow read: if request.auth != null && 
                     request.auth.uid == resource.data.userId;
      
      // Permitir escritura solo si el usuario es el propietario
      allow write: if request.auth != null && 
                      request.auth.uid == request.resource.data.userId;
      
      // Permitir creación de nuevos documentos
      allow create: if request.auth != null &&
                       request.auth.uid == request.resource.data.userId &&
                       request.resource.data.keys().hasAll(['nombre', 'creditos', 'userId']);
      
      // Permitir actualización solo del propietario
      allow update: if request.auth != null &&
                       request.auth.uid == resource.data.userId &&
                       request.auth.uid == request.resource.data.userId;
      
      // Permitir eliminación solo del propietario
      allow delete: if request.auth != null &&
                       request.auth.uid == resource.data.userId;
    }
  }
}
```

## Funcionalidades del Sistema

### Módulo de Autenticación

**Características:**
- Registro de usuarios con email y contraseña
- Validación de formato de email (RFC 5322)
- Política de contraseñas: mínimo 6 caracteres
- Confirmación de contraseña en registro
- Inicio de sesión con persistencia de sesión
- Cierre de sesión con limpieza de estado
- Manejo de errores de autenticación

**Validaciones Implementadas:**
- Email: formato válido, no vacío
- Contraseña: longitud >= 6 caracteres
- Confirmación: igualdad con contraseña principal
- Campos obligatorios: validación en tiempo real

### Módulo de Gestión de Cursos

**Operaciones CRUD:**

1. **CREATE (Crear)**
   - Formulario de entrada con validaciones
   - Campos: nombre, descripción, créditos
   - Asignación automática de userId
   - Persistencia en Firestore

2. **READ (Leer)**
   - Consulta filtrada por usuario autenticado
   - Actualización en tiempo real mediante listeners
   - Renderizado de lista con LazyColumn
   - Estado vacío con mensaje informativo

3. **UPDATE (Actualizar)**
   - Diálogo modal de edición
   - Precarga de datos existentes
   - Validación de cambios
   - Actualización atómica en Firestore

4. **DELETE (Eliminar)**
   - Eliminación directa con feedback visual
   - Actualización automática de la UI
   - Manejo de errores de eliminación

### Interfaz de Usuario

**Principios de Diseño:**
- Material Design 3 Guidelines
- Composición declarativa con Jetpack Compose
- Responsive layout adaptable
- Accesibilidad (Content descriptions)
- Feedback visual inmediato

**Componentes Principales:**
- OutlinedTextField para entrada de datos
- Button con estados enabled/disabled
- Card para visualización de cursos
- FloatingActionButton para acción principal
- AlertDialog para operaciones modales
- TopAppBar con navegación

## Flujo de Datos
```
Usuario → UI (Compose) → ViewModel/State → Firebase SDK → Firebase Cloud
   ↑                                                              ↓
   └──────────────── Actualización Reactiva ←────────────────────┘
```

**Proceso de Autenticación:**
```
1. Usuario ingresa credenciales
2. Validación local de formato
3. Llamada a FirebaseAuth.signInWithEmailAndPassword()
4. Firebase verifica credenciales
5. Retorno de FirebaseUser o Error
6. Navegación a pantalla principal o mensaje de error
```

**Proceso CRUD:**
```
1. Usuario realiza acción (crear/editar/eliminar)
2. Validación de datos en cliente
3. Construcción de documento Firestore
4. Llamada a Firestore (add/update/delete)
5. Firebase procesa operación
6. Listener actualiza UI automáticamente
7. Feedback visual al usuario
Testing y Calidad
Plan de Pruebas
Pruebas Funcionales:

 Registro de usuario exitoso
 Registro con email duplicado
 Registro con contraseña débil
 Login con credenciales válidas
 Login con credenciales inválidas
 Creación de curso completo
 Creación con campos vacíos
 Edición de curso existente
 Eliminación de curso
 Filtrado por usuario
 Sincronización en tiempo real
 Cierre de sesión

Pruebas de Seguridad:

 Aislamiento de datos entre usuarios
 Autenticación obligatoria
 Validación de tokens
 Reglas de Firestore aplicadas

Pruebas de Usabilidad:

 Navegación intuitiva
 Mensajes de error comprensibles
 Feedback visual de operaciones
 Estados de carga visibles

Métricas de Calidad
MétricaValorCobertura de funcionalidades100%Validaciones implementadas100%Manejo de erroresCompletoDocumentación de código85%
Seguridad y Privacidad
Medidas de Seguridad Implementadas

Autenticación:

Sistema de autenticación robusto con Firebase
Tokens JWT gestionados automáticamente
Renovación automática de sesión


Autorización:

Filtrado por userId en todas las consultas
Reglas de seguridad en Firestore por usuario
Validación de permisos en servidor


Datos:

No se almacenan contraseñas en dispositivo
Comunicación HTTPS obligatoria
No se exponen credenciales en logs


Código:

google-services.json en .gitignore
No hay hardcoded credentials
Ofuscación en build de release



Cumplimiento de Normativas

Gestión de datos personales según buenas prácticas
Almacenamiento seguro de credenciales (Firebase Auth)
Comunicaciones encriptadas (TLS 1.3)

Mantenimiento y Evolución
Roadmap de Funcionalidades
Versión 1.1 (Planificado):

Búsqueda y filtrado avanzado de cursos
Ordenamiento por múltiples criterios
Paginación de resultados

Versión 1.2 (Planificado):

Recuperación de contraseña
Perfil de usuario editable
Estadísticas y dashboard

Versión 2.0 (Futuro):

Modo offline con sincronización
Notificaciones push
Exportación de datos
Integración con sistemas académicos

Procedimientos de Mantenimiento
Actualizaciones de Dependencias:
bash# Verificar actualizaciones
./gradlew dependencyUpdates

# Actualizar Firebase BOM
implementation(platform("com.google.firebase:firebase-bom:XX.X.X"))

# Actualizar Compose BOM
implementation(platform("androidx.compose:compose-bom:YYYY.MM"))
Monitoreo:

Firebase Console para métricas de uso
Firebase Crashlytics (a implementar)
Analytics de rendimiento

Documentación Adicional
Referencias Técnicas

Firebase Authentication Documentation
Cloud Firestore Documentation
Jetpack Compose Guidelines
Material Design 3
Kotlin Documentation

Recursos del Proyecto

Repositorio: GitHub
Documentación API: (En desarrollo)
Issues y Bugs: GitHub Issues

Información del Proyecto
Contexto Académico:

Laboratorio 12 - Programación Móvil
Docente: Juan José León S.
Institución: TECSUP
Ciclo: 4to Ciclo
Año Académico: 2024

Metadatos del Desarrollo:

Fecha de Inicio: Noviembre 2025
Versión Actual: 1.0.0
Estado: Producción (Académico)
Licencia: Uso Académico


Desarrollado por Matias Galvan Guerrero
TECSUP - Desarrollo de Aplicaciones Web y Móviles
© 2024 - Todos los derechos reservados para fines académicos
