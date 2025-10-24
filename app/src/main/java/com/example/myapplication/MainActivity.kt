package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

// Data class para representar cada elemento de la lista
data class Item(
    val id: String,
    var nombre: String,
    var edad: Int
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppPrincipal()
                }
            }
        }
    }
}

@Composable
fun AppPrincipal() {
    // Estado para la lista de elementos
    var listaItems by remember { mutableStateOf(
        listOf(
            Item("1", "Juan", 25),
            Item("2", "María", 30),
            Item("3", "Carlos", 22),
            Item("4", "Ana", 28)
        )
    ) }

    // Estados para los campos de texto
    var idBuscado by remember { mutableStateOf("") }
    var nuevoDato by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Título
        Text(
            text = "Modificador de Lista",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Campo 1: ID a buscar
        OutlinedTextField(
            value = idBuscado,
            onValueChange = { idBuscado = it },
            label = { Text("ID del elemento a modificar") },
            placeholder = { Text("Ej: 1, 2, 3...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )

        // Campo 2: Nuevo dato (nombre)
        OutlinedTextField(
            value = nuevoDato,
            onValueChange = { nuevoDato = it },
            label = { Text("Nuevo nombre") },
            placeholder = { Text("Escribe el nuevo nombre aquí") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        )

        // Botón Modificar
        Button(
            onClick = {
                if (idBuscado.isNotBlank() && nuevoDato.isNotBlank()) {
                    // Buscar el elemento por ID y actualizar su nombre
                    listaItems = listaItems.map { item ->
                        if (item.id == idBuscado) {
                            item.copy(nombre = nuevoDato)
                        } else {
                            item
                        }
                    }
                    // Limpiar campos después de modificar
                    nuevoDato = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            Text("MODIFICAR ELEMENTO")
        }

        // Botón Mostrar Lista (solo decorativo en este caso)
        Button(
            onClick = {
                // Aquí podrías agregar lógica adicional si necesitas
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("MOSTRAR LISTA (Actual)")
        }

        // Separador
        Spacer(modifier = Modifier.height(20.dp))

        // Lista de elementos con LazyColumn
        Text(
            text = "Lista de Elementos:",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 10.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            items(listaItems) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "ID: ${item.id}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = "Nombre: ${item.nombre}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Text(
                            text = "Edad: ${item.edad}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPrincipalPreview() {
    MyApplicationTheme {
        AppPrincipal()
    }
}