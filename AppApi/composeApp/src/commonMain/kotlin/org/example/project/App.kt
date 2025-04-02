package org.example.project

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import appapi.composeapp.generated.resources.Res
import appapi.composeapp.generated.resources.compose_multiplatform
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.internal.throwMissingFieldException
import network.NetworkUtils.httpClient
import network.model.ApiResponse
import network.model.Hero
import network.model.SuperheroCard
import network.model.SuperheroDetail

@Composable
@Preview
fun App() {
    MaterialTheme {
        var superheroName by remember { mutableStateOf("") }
        var superheroList by remember { mutableStateOf<List<Hero>>(emptyList()) }
        var selectedHero by remember { mutableStateOf<Hero?>(null) }

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                TextField(value = superheroName, onValueChange = { superheroName = it } )
                Button(onClick = { getSuperheroList(superheroName){superheroList = it} }){
                    Text("Load")
                }
            }
            //List
            LazyColumn {
                items(superheroList){ hero ->
                    SuperheroCard(hero) { selectedHero = hero }
                }
            }

            // Detalle: Mostrar más info si se selecciona un héroe
            selectedHero?.let { hero ->
                SuperheroDetail(hero = hero, onDismiss = { selectedHero = null })
            }
        }
    }
}
fun getSuperheroList(superheroName: String, onSuccessResponse: (List<Hero>) -> Unit){
    if(superheroName.isBlank()) return
    val url = "https://www.superheroapi.com/api.php/9873024bb43444abf177320a637b583b/search/$superheroName"

    CoroutineScope(Dispatchers.IO).launch {
        try{
            val response = httpClient.get(url).body<ApiResponse>()
            onSuccessResponse(response.results)
        } catch (e: Exception){
            println("Error al obtener datos: ${e.message}")
        }
    }
}