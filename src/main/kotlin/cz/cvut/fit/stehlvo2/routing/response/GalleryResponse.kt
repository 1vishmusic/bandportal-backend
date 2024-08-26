package cz.cvut.fit.stehlvo2.routing.response

import kotlinx.serialization.Serializable

@Serializable
data class GalleryResponse(
    val albums: List<AlbumResponse>,
)

@Serializable
data class AlbumResponse(
    val title: String,
    val description: String,
    val thumbnail: String,
    val photos: List<String>,
)