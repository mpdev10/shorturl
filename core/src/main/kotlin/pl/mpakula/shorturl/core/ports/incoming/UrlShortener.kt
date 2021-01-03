package pl.mpakula.shorturl.core.ports.incoming

interface UrlShortener {
    fun shortenUrl(url: String?): String
}