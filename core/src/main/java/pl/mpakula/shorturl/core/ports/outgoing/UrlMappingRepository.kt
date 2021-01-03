package pl.mpakula.shorturl.core.ports.outgoing

import pl.mpakula.shorturl.core.model.UrlMapping
import java.math.BigInteger
import java.util.*

interface UrlMappingRepository {
    fun save(urlMapping: UrlMapping): UrlMapping
    fun findFirstByOriginalUrl(originalUrl: String?): Optional<UrlMapping>
    fun findById(id: BigInteger?): Optional<UrlMapping>
}