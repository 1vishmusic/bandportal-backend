package cz.cvut.fit.stehlvo2.service

import cz.cvut.fit.stehlvo2.service.gallery.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.xml.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import cz.cvut.fit.stehlvo2.routing.response.AlbumResponse as DownstreamAlbumResponse

object GalleryService {
    private var albumResponse: AlbumListResponse? = null
    private var galleryCache: List<DownstreamAlbumResponse>? = null

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            xml(contentType = ContentType.parse("application/rss+xml"))
        }
    }

    suspend fun readAlbums(): List<DownstreamAlbumResponse> {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val response: AlbumListResponse = client.get("https://www.rajce.idnes.cz/export/user/1vish/albums.rss").body()
        if(response == albumResponse) {
            return galleryCache!!
        }

        albumResponse = response
        galleryCache = response.channel.items
            .sortedByDescending {
                val dateRegex = """\d{2}\.\d{2}\.\d{4}""".toRegex()
                val result = dateRegex.find(it.description)

                if(result === null) {
                    println("No date found in " + it.title)
                    LocalDate.now()
                } else {
                    LocalDate.parse(result.value, formatter)
                }
            }
            .map {
                val albumResponse: AlbumResponse = client.get(it.guid + "/media.rss").body()

                DownstreamAlbumResponse(
                    it.title.substring(8),
                    it.description,
                    it.image.url.replace("thumb", "images"),
                    albumResponse.channel.items
                        .map { p -> p.mediaContent.url }
                )
            }

        return galleryCache!!
    }
}