package pl.mpakula.shorturl.url;

import org.apache.commons.validator.routines.UrlValidator;
import pl.mpakula.shorturl.url.exception.InvalidUrlException;

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
                .map(this::addPrefixIfNotPresent)
                .filter(urlValidator::isValid)
                .map(urlProvider::provideShortenedUrl)
                .orElseThrow(InvalidUrlException::new);
    }

    @Override
    public Optional<String> getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(shortUrl)
                .map(this::addPrefixIfNotPresent)
                .flatMap(urlProvider::getOriginalUrl);
    }

    private String addPrefixIfNotPresent(String url) {
        return Optional.ofNullable(url)
                .filter(u -> !u.contains("://"))
                .map("http://"::concat)
                .orElse(url);
    }
}
