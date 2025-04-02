package network.model

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import network.model.Hero

@Composable
fun SuperheroDetail(hero: Hero, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = hero.name, style = MaterialTheme.typography.h5) },
        text = {
            Column {
                KamelImage(
                    resource = asyncPainterResource(hero.image.url),
                    contentDescription = "Imagen de ${hero.name}",
                    modifier = Modifier.size(80.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text("Inteligencia: ${hero.powerstats.intelligence}")
                Text("Fuerza: ${hero.powerstats.strength}")
                Text("Velocidad: ${hero.powerstats.speed}")
                Text("Durabilidad: ${hero.powerstats.durability}")
                Text("Poder: ${hero.powerstats.power}")
                Text("Combate: ${hero.powerstats.combat}")
            }
        },
        confirmButton = {
            Button(onClick = onDismiss) { Text("Cerrar") }
        }
    )
}
