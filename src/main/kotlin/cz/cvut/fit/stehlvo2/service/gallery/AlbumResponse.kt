package cz.cvut.fit.stehlvo2.service.gallery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@SerialName("rss")
data class AlbumResponse(
    val version: String,
    @XmlElement(true)
    val channel: Album
)

@Serializable
@SerialName("channel")
data class Album(
    @XmlElement(true)
    val title: String,
    @XmlElement(true)
    val language: String,
    @XmlElement(true)
    val items: List<Photo>
)

@Serializable
@SerialName("item")
data class Photo(
    @XmlElement(true)
    val title: String? = null,
    @XmlElement(true)
    val link: String,
    @XmlElement(true)
    val mediaTitle: MediaTitle? = null,
    @XmlElement(true)
    val mediaDescription: MediaDescription? = null,
    @XmlElement(true)
    val mediaCredit: MediaCredit,
    @XmlElement(true)
    val mediaThumbnail: MediaThumbnail,
    @XmlElement(true)
    val mediaContent: MediaContent,
    @XmlElement(true)
    val guid: String
)

@XmlSerialName("title", "http://search.yahoo.com/mrss/")
@Serializable
data class MediaTitle(
    val type: String? = null
)

@XmlSerialName("description", "http://search.yahoo.com/mrss/")
@Serializable
data class MediaDescription(
    val type: String? = null
)

@XmlSerialName("credit", "http://search.yahoo.com/mrss/")
@Serializable
data class MediaCredit(
    val role: String,
    val scheme: String,
    @XmlValue
    val content: String
)

@XmlSerialName("thumbnail", "http://search.yahoo.com/mrss/")
@Serializable
data class MediaThumbnail(
    val url: String
)

@XmlSerialName("content", "http://search.yahoo.com/mrss/")
@Serializable
data class MediaContent(
    val url: String,
    val type: String,
    val medium: String
)