package cl.stgoneira.android.openlibraryws

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.stgoneira.android.openlibraryws.ws.Libro
import cl.stgoneira.android.openlibraryws.ws.LibroServiceFactory
import coil.compose.AsyncImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusquedaLibrosUI()
        }
    }
}

@Composable
fun BusquedaLibrosUI() {
    val (busqueda, setBusqueda) = remember { mutableStateOf("") }
    val (libros, setLibros)     = remember { mutableStateOf( emptyList<Libro>() ) }
    val coroutineScope          = rememberCoroutineScope()

    LaunchedEffect(busqueda) {
        coroutineScope.coroutineContext.cancelChildren()

        coroutineScope.launch(Dispatchers.IO) {
            if( busqueda.isNotBlank() ) {
                delay(1500)
                val service = LibroServiceFactory.getLibroService()
                val resultado = service.buscar(busqueda)
                setLibros( resultado.libros )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = busqueda,
            onValueChange = { setBusqueda(it) },
            label = { Text("Buscar libros") },
            modifier = Modifier.fillMaxWidth()
        )
        if( busqueda.isBlank() ) {
            Text("Escriba un término a buscar...")
        } else {
            ListadoLibrosUI(libros)
        }
    }
}

@Composable
fun ListadoLibrosUI(libros:List<Libro>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(libros) {libro ->
            ListItemLibroUI(libro)
        }
    }
}

@Composable
fun ListItemLibroUI(libro:Libro) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp)
    ) {
        Spacer(modifier = Modifier.width(10.dp))
        Box(
            modifier = Modifier.widthIn(min = 100.dp, max = 100.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = libro.imagenUrl,
                contentDescription = "Portada libro: ${libro.titulo}"
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Column() {
            Text(libro.titulo, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text(libro.autores.joinToString())
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListItemLibroUIPreview() {
    val libro = Libro("Android Security", listOf("Juan Pérez"), 12480229)
    ListItemLibroUI(libro)
}