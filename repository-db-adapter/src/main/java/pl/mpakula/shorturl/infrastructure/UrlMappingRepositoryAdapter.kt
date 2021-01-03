package pl.mpakula.shorturl.infrastructure

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Repository
import pl.mpakula.shorturl.core.model.UrlMapping
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository
import java.math.BigInteger
import java.util.*

@Repository
@EnableAutoConfiguration
@ComponentScan
internal open class UrlMappingRepositoryAdapter
    (private val repository: UrlMappingJpaRepository) : UrlMappingRepository {

    override fun save(urlMapping: UrlMapping): UrlMapping {
        return urlMapping
            .let(::toJpa)
            .let(repository::save)
            .let(::toDomain)
    }

    override fun findFirstByOriginalUrl(originalUrl: String?): Optional<UrlMapping> {
        val mapping = repository.findFirstByOriginalUrl(originalUrl)
            ?.let(::toDomain)
        return Optional.ofNullable(mapping)
    }

    override fun findById(id: BigInteger?): Optional<UrlMapping> {
        val mapping = repository.findById(id)
            ?.let(::toDomain)
        return Optional.ofNullable(mapping)
    }

    companion object {
        @JvmStatic
        private fun toJpa(urlMapping: UrlMapping): UrlMappingJpa {
            return UrlMappingJpa(urlMapping.id, urlMapping.originalUrl)
        }

        @JvmStatic
        private fun toDomain(urlMapping: UrlMappingJpa): UrlMapping {
            return UrlMapping(urlMapping.id, urlMapping.originalUrl)
        }
    }
}