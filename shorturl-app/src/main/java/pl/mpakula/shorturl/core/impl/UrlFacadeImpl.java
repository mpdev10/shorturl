package pl.mpakula.shorturl.core.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import pl.mpakula.shorturl.core.UrlFacade;
import pl.mpakula.shorturl.core.exception.InvalidUrlException;
import pl.mpakula.shorturl.core.exception.OriginalUrlNotFoundException;
import pl.mpakula.shorturl.core.model.UrlMapping;
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository;

import java.math.BigInteger;
import java.util.Optional;

@RequiredArgsConstructor
class UrlFacadeImpl implements UrlFacade {

    private final UrlValidator urlValidator;
    private final UrlProps urlProps;
    private final UrlMappingRepository urlMappingRepository;

    @Override
    public String shortenUrl(String url) {
        return Optional.ofNullable(url)
                .map(UrlFacadeImpl::addSchemaIfNotPresent)
                .filter(urlValidator::isValid)
                .map(this::provideShortenedUrl)
                .orElseThrow(InvalidUrlException::new);
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(shortUrl)
                .map(url -> url.replace(urlProps.getBasePath(), ""))
                .filter(StringUtils::isAlphanumeric)
                .map(url -> new BigInteger(url, 36))
                .flatMap(urlMappingRepository::findById)
                .map(UrlMapping::getOriginalUrl)
                .orElseThrow(OriginalUrlNotFoundException::new);
    }

    private static String addSchemaIfNotPresent(String url) {
        return Optional.ofNullable(url)
                .filter(u -> !u.contains("://"))
                .map("https://"::concat)
                .orElse(url);
    }

    private String provideShortenedUrl(String url) {
        return Optional.of(url)
                .map(urlMappingRepository::findFirstByOriginalUrl)
                .filter(Optional::isPresent)
                .orElseGet(() -> Optional.of(new UrlMapping(null, url))
                        .map(urlMappingRepository::save))
                .map(this::computeShortenedUrl)
                .orElseThrow(InvalidUrlException::new);
    }

    private String computeShortenedUrl(UrlMapping urlMapping) {
        return Optional.of(urlMapping)
                .map(UrlMapping::getId)
                .map(id -> id.toString(36))
                .map(urlProps.getBasePath()::concat)
                .orElse("");
    }

}
