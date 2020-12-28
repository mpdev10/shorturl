package pl.mpakula.shorturl.core;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.jetbrains.annotations.NotNull;
import pl.mpakula.shorturl.core.exception.InvalidUrlException;
import pl.mpakula.shorturl.core.exception.OriginalUrlNotFoundException;
import pl.mpakula.shorturl.core.model.UrlMapping;
import pl.mpakula.shorturl.core.ports.incoming.OriginalUrlProvider;
import pl.mpakula.shorturl.core.ports.incoming.UrlShortener;
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository;

import java.math.BigInteger;
import java.util.Optional;

@RequiredArgsConstructor
public class UrlFacade implements OriginalUrlProvider, UrlShortener {

    private final UrlValidator urlValidator;
    private final String basePath;
    private final UrlMappingRepository urlMappingRepository;

    @NotNull
    @Override
    public String shortenUrl(String url) {
        return Optional.ofNullable(url)
                .map(UrlFacade::addSchemaIfNotPresent)
                .filter(urlValidator::isValid)
                .map(this::provideShortenedUrl)
                .orElseThrow(InvalidUrlException::new);
    }

    @NotNull
    @Override
    public String getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(shortUrl)
                .map(url -> url.replace(basePath, ""))
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
                .map(basePath::concat)
                .orElse("");
    }

}
