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
fun SuperheroCard(hero: Hero, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.Gray,
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Cargar imagen de la API
            KamelImage(
                resource = asyncPainterResource(hero.image.url),
                contentDescription = "Imagen de ${hero.name}",
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = hero.name, style = MaterialTheme.typography.h6)
                Text(text = "Inteligencia: ${hero.powerstats.intelligence}")
                Text(text = "Fuerza: ${hero.powerstats.strength}")
            }
        }
    }
}
