package com.tecsup.galvanguerreromiscursosapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.tecsup.galvanguerreromiscursosapp.models.Curso

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CursosListScreen(
    onLogout: () -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val context = LocalContext.current
    val currentUser = auth.currentUser

    var cursos by remember { mutableStateOf<List<Curso>>(emptyList()) }
    var showDialog by remember { mutableStateOf(false) }
    var cursoToEdit by remember { mutableStateOf<Curso?>(null) }

    // Cargar cursos del usuario actual
    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { userId ->
            db.collection("cursos")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Toast.makeText(context, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                        return@addSnapshotListener
                    }

                    cursos = snapshot?.documents?.mapNotNull { doc ->
                        Curso(
                            id = doc.id,
                            nombre = doc.getString("nombre") ?: "",
                            descripcion = doc.getString("descripcion") ?: "",
                            creditos = doc.getLong("creditos")?.toInt() ?: 0,
                            userId = doc.getString("userId") ?: ""
                        )
                    } ?: emptyList()
                }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("GalvanGuerrero - Mis Cursos")
                        Text(
                            text = currentUser?.email ?: "",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        auth.signOut()
                        onLogout()
                    }) {
                        Icon(Icons.Default.ExitToApp, "Cerrar sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                cursoToEdit = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, "Agregar curso")
            }
        }
    ) { padding ->
        if (cursos.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "📚",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No hay cursos registrados")
                    Text("Click en + para agregar", style = MaterialTheme.typography.bodySmall)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(cursos) { curso ->
                    CursoCard(
                        curso = curso,
                        onEdit = {
                            cursoToEdit = curso
                            showDialog = true
                        },
                        onDelete = {
                            db.collection("cursos").document(curso.id)
                                .delete()
                                .addOnSuccessListener {
                                    Toast.makeText(context, "Curso eliminado", Toast.LENGTH_SHORT).show()
                                }
                                .addOnFailureListener { e ->
                                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    )
                }
            }
        }
    }

    if (showDialog) {
        CursoDialog(
            curso = cursoToEdit,
            onDismiss = { showDialog = false },
            onSave = { nombre, descripcion, creditos ->
                val userId = currentUser?.uid ?: return@CursoDialog

                val cursoData = hashMapOf(
                    "nombre" to nombre,
                    "descripcion" to descripcion,
                    "creditos" to creditos,
                    "userId" to userId
                )

                if (cursoToEdit == null) {
                    // Crear nuevo
                    db.collection("cursos")
                        .add(cursoData)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Curso creado ✅", Toast.LENGTH_SHORT).show()
                            showDialog = false
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    // Actualizar existente
                    db.collection("cursos").document(cursoToEdit!!.id)
                        .update(cursoData as Map<String, Any>)
                        .addOnSuccessListener {
                            Toast.makeText(context, "Curso actualizado ✅", Toast.LENGTH_SHORT).show()
                            showDialog = false
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            }
        )
    }
}

@Composable
fun CursoCard(
    curso: Curso,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = curso.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = curso.descripcion,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Créditos: ${curso.creditos}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Editar", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = onDelete) {
                    Icon(Icons.Default.Delete, "Eliminar", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }
}

@Composable
fun CursoDialog(
    curso: Curso?,
    onDismiss: () -> Unit,
    onSave: (String, String, Int) -> Unit
) {
    var nombre by remember { mutableStateOf(curso?.nombre ?: "") }
    var descripcion by remember { mutableStateOf(curso?.descripcion ?: "") }
    var creditos by remember { mutableStateOf(curso?.creditos?.toString() ?: "") }
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (curso == null) "Nuevo Curso" else "Editar Curso") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del curso") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
                OutlinedTextField(
                    value = creditos,
                    onValueChange = { if (it.all { char -> char.isDigit() }) creditos = it },
                    label = { Text("Créditos") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (nombre.isBlank()) {
                        Toast.makeText(context, "Ingrese nombre del curso", Toast.LENGTH_SHORT).show()
                        return@TextButton
                    }
                    if (creditos.toIntOrNull() == null || creditos.toInt() <= 0) {
                        Toast.makeText(context, "Ingrese créditos válidos", Toast.LENGTH_SHORT).show()
                        return@TextButton
                    }
                    onSave(nombre, descripcion, creditos.toInt())
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        }
    )
}