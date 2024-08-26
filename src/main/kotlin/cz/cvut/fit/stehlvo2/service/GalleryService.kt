package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.service.gallery.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.xml.*
import cz.cvut.fit.stehlvo2.routing.response.AlbumResponse as DownstreamAlbumResponse

object GalleryService {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            xml(contentType = ContentType.parse("application/rss+xml"))
        }
    }

    suspend fun readAlbums(): List<DownstreamAlbumResponse> {
        val response: AlbumListResponse = client.get("https://www.rajce.idnes.cz/export/user/1vish/albums.rss").body()
        val albums = response.channel.items.map {
            val albumResponse: AlbumResponse = client.get(it.guid + "/media.rss").body()

            DownstreamAlbumResponse(it.title.substring(8), it.description, it.image.url, albumResponse.channel.items.map { p -> p.mediaContent.url })
        }

        return albums
    }
}