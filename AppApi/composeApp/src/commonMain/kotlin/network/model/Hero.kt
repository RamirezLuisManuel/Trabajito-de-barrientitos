package network.model

import kotlinx.serialization.Serializable

@Serializable
data class PowerStats(
    val intelligence: String,
    val strength: String,
    val speed: String,
    val durability: String,
    val power: String,
    val combat: String
)

@Serializable
data class Image(
    val url: String
)

@Serializable
data class Hero (
    val id:String,
    val name:String,
    val powerstats:PowerStats,
    val image: Image 
)
