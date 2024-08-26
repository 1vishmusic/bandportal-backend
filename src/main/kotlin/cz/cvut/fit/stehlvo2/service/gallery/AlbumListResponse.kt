package cz.cvut.fit.stehlvo2.service.gallery

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlElement

@Serializable
@SerialName("rss")
data class AlbumListResponse(
    @SerialName("version")
    val version: String,
    @XmlElement(true)
    val channel: AlbumListChannel
)

@Serializable
@SerialName("channel")
data class AlbumListChannel(
    @XmlElement(true)
    val title: String,
    @XmlElement(true)
    val link: String,
    @XmlElement(true)
    val description: String,
    @XmlElement(true)
    val language: String,
    @XmlElement(true)
    val items: List<Item>
)

@Serializable
@SerialName("item")
data class Item(
    @XmlElement(true)
    val image: ItemImage,
    @XmlElement(true)
    val title: String,
    @XmlElement(true)
    val link: String,
    @XmlElement(true)
    val description: String,
    @XmlElement(true)
    val guid: String,
    @XmlElement(true)
    val pubDate: String
)

@Serializable
@SerialName("image")
data class ItemImage(
    @XmlElement(true)
    val url: String,
    @XmlElement(true)
    val link: String
)
