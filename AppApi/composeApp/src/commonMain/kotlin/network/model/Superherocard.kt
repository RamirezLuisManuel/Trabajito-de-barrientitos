package network.model

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import network.model.Hero

@Composable
fun SuperheroCard(hero: Hero, onClick: () -> Unit) {
    // Calcular el poder total del héroe
    val totalPower = (hero.powerstats.intelligence.toIntOrNull() ?: 0) +
                     (hero.powerstats.strength.toIntOrNull() ?: 0) +
                     (hero.powerstats.speed.toIntOrNull() ?: 0) +
                     (hero.powerstats.durability.toIntOrNull() ?: 0) +
                     (hero.powerstats.power.toIntOrNull() ?: 0) +
                     (hero.powerstats.combat.toIntOrNull() ?: 0)
    
    // Determinar si es un héroe poderoso (más de 400 puntos totales)
    val isPowerful = totalPower > 400
    
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isPowerful) 8.dp else 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isPowerful) Color(0xFFB6B490) else Color(0xFF679FB4)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Hero image with circular shape and gradient border
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        Brush.radialGradient(
                            colors = if (isPowerful) {
                                listOf(
                                    Color(0xFFFFD700), // Dorado para héroes poderosos
                                    Color(0xFFFF7700)
                                )
                            } else {
                                listOf(
                                    Color(0xFF236494),
                                    Color(0xFF2577C5)
                                )
                            }
                        )
                    )
                    .padding(3.dp)
            ) {
                KamelImage(
                    resource = asyncPainterResource(hero.image.url),
                    contentDescription = "Imagen de ${hero.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Hero information
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = hero.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isPowerful) Color(0xFFFF5722) else Color(0xFF005396),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))

                // Power stats with visual indicators
                Text("Inteligencia ----> "+hero.powerstats.intelligence,
                    color = Color(0xD2FFFFFF)
                )
                
                Text("Fuerza       ----> "+hero.powerstats.strength,
                    color = Color(0xD2FFFFFF)
                )
                
                Text("Velocidad    ----> "+hero.powerstats.speed,
                    color = Color(0xD2FFFFFF)
                )
            }
        }
    }
}

@Composable
private fun PowerStatBar(label: String, value: String, color: Color) {
    val numericValue = value.toIntOrNull() ?: 0
    val percentage = (numericValue / 100f).coerceIn(0f, 1f)
    val isHighStat = numericValue >= 80
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = 2.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isHighStat) Color(0xFF4B4B4B) else Color(0xC94B4B4B),
            fontWeight = if (isHighStat) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.width(90.dp)
        )
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Box(
            modifier = Modifier
                .weight(1f)
                .height(8.dp)
                .background(Color(0xFF757272), RoundedCornerShape(4.dp))
        ) {
            // Usar diferentes enfoques para el fondo según si es una estadística alta o no
            if (isHighStat) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(percentage)
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(color, Color.White)
                            ),
                            RoundedCornerShape(4.dp)
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(percentage)
                        .background(color, RoundedCornerShape(4.dp))
                )
            }
        }
        
        Spacer(modifier = Modifier.width(8.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isHighStat) FontWeight.ExtraBold else FontWeight.Bold,
            color = if (isHighStat) color else Color(0xFF4B4B4B)
        )
    }
}
