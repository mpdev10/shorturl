package pl.mpakula.shorturl.core.ports.incoming

interface OriginalUrlProvider {
    fun getOriginalUrl(shortUrl: String?): String
}