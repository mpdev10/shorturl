package pl.mpakula.shorturl.core

import com.google.common.collect.Maps
import pl.mpakula.shorturl.core.model.UrlMapping
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository
import java.math.BigInteger

internal class InMemoryUrlMappingRepository : UrlMappingRepository {

    private val shortenedUrls: MutableMap<BigInteger, UrlMapping> = Maps.newConcurrentMap()

    override fun save(urlMapping: UrlMapping): UrlMapping {
        val id = urlMapping.retrieveId()
        val savedMapping = UrlMapping(id, urlMapping.originalUrl)
        shortenedUrls[id] = savedMapping
        return savedMapping
    }

    override fun findFirstByOriginalUrl(originalUrl: String?): UrlMapping? {
        return shortenedUrls.values.firstOrNull { it.originalUrl == originalUrl }
    }

    override fun findById(id: BigInteger?): UrlMapping? {
        return shortenedUrls[id]
    }

    private fun UrlMapping.retrieveId(): BigInteger {
        return id
            ?: shortenedUrls.keys.maxOrNull()?.add(BigInteger.ONE)
            ?: (BigInteger.ZERO)
    }
}
