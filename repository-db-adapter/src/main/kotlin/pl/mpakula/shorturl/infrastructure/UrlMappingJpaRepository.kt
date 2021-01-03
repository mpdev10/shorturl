package pl.mpakula.shorturl.infrastructure

import org.springframework.data.repository.Repository
import java.math.BigInteger

internal interface UrlMappingJpaRepository : Repository<UrlMappingJpa, BigInteger> {
    fun save(urlMapping: UrlMappingJpa): UrlMappingJpa
    fun findFirstByOriginalUrl(originalUrl: String?): UrlMappingJpa?
    fun findById(id: BigInteger?): UrlMappingJpa?
}