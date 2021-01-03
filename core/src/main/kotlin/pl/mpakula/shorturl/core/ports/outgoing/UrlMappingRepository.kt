package pl.mpakula.shorturl.core.ports.outgoing

import pl.mpakula.shorturl.core.model.UrlMapping
import java.math.BigInteger

interface UrlMappingRepository {
    fun save(urlMapping: UrlMapping): UrlMapping
    fun findFirstByOriginalUrl(originalUrl: String?): UrlMapping?
    fun findById(id: BigInteger?): UrlMapping?
}