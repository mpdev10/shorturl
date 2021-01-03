package pl.mpakula.shorturl.infrastructure

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Repository
import pl.mpakula.shorturl.core.model.UrlMapping
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository
import java.math.BigInteger

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

    override fun findFirstByOriginalUrl(originalUrl: String?): UrlMapping? {
        return repository.findFirstByOriginalUrl(originalUrl)
            ?.let(::toDomain)

    }

    override fun findById(id: BigInteger?): UrlMapping? {
        return repository.findById(id)
            ?.let(::toDomain)
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