package pl.mpakula.shorturl.core

import org.apache.commons.lang3.StringUtils
import org.apache.commons.validator.routines.UrlValidator
import pl.mpakula.shorturl.core.exception.InvalidUrlException
import pl.mpakula.shorturl.core.exception.OriginalUrlNotFoundException
import pl.mpakula.shorturl.core.model.UrlMapping
import pl.mpakula.shorturl.core.ports.incoming.OriginalUrlProvider
import pl.mpakula.shorturl.core.ports.incoming.UrlShortener
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository
import java.math.BigInteger


class UrlFacade(
    private val urlValidator: UrlValidator,
    private val basePath: String,
    private val urlMappingRepository: UrlMappingRepository
) : OriginalUrlProvider, UrlShortener {

    override fun shortenUrl(url: String?): String {
        return url
            ?.let(::addSchemaIfNotPresent)
            ?.takeIf(urlValidator::isValid)
            ?.let(::provideShortenedUrl)
            ?: throw InvalidUrlException()
    }

    override fun getOriginalUrl(shortUrl: String?): String {
        return shortUrl?.replace(basePath, "")
            ?.takeIf(StringUtils::isAlphanumeric)
            ?.let { BigInteger(it, 36) }
            ?.let(urlMappingRepository::findById)
            ?.let(UrlMapping::originalUrl)
            ?: throw OriginalUrlNotFoundException()
    }

    private fun provideShortenedUrl(url: String): String {
        val mapping = urlMappingRepository.findFirstByOriginalUrl(url)
            ?: UrlMapping(null, url).let(urlMappingRepository::save)
        return mapping.let(::computeShortenedUrl)
    }

    private fun computeShortenedUrl(urlMapping: UrlMapping): String {
        return urlMapping
            .let(UrlMapping::id)?.toString(36)
            ?.let { """$basePath$it""" }
            ?: throw InvalidUrlException()
    }

    companion object {
        private fun addSchemaIfNotPresent(url: String): String {
            return url
                .takeIf { "://" in it }
                ?: """https://$url"""

        }
    }
}