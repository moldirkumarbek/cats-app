package com.example.catapps.model
import com.squareup.moshi.Json
data class ImageResultData(
    @field:Json(name="url") val imageUrl: String,
    val breds : List<CatBreedData>
)
