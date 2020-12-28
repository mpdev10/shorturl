package pl.mpakula.shorturl.core;

import org.apache.commons.validator.routines.UrlValidator;
import pl.mpakula.shorturl.core.exception.InvalidUrlException;
import pl.mpakula.shorturl.core.exception.OriginalUrlNotFoundException;

import java.util.Optional;

class UrlFacadeImpl implements UrlFacade {

    private final UrlValidator urlValidator;
    private final UrlProvider urlProvider;

    UrlFacadeImpl(UrlValidator urlValidator, UrlProvider urlProvider) {
        this.urlValidator = urlValidator;
        this.urlProvider = urlProvider;
    }

    @Override
    public String shortenUrl(String url) {
        return Optional.ofNullable(url)
                .map(UrlFacadeImpl::addSchemaIfNotPresent)
                .filter(urlValidator::isValid)
                .map(urlProvider::provideShortenedUrl)
                .orElseThrow(InvalidUrlException::new);
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(shortUrl)
                .flatMap(urlProvider::getOriginalUrl)
                .orElseThrow(OriginalUrlNotFoundException::new);
    }

    private static String addSchemaIfNotPresent(String url) {
        return Optional.ofNullable(url)
                .filter(u -> !u.contains("://"))
                .map("https://"::concat)
                .orElse(url);
    }

}
