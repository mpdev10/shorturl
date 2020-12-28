package pl.mpakula.shorturl.core;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import pl.mpakula.shorturl.core.exception.InvalidUrlException;

import java.math.BigInteger;
import java.util.Optional;

@RequiredArgsConstructor
class DbUrlProvider implements UrlProvider {

    private final UrlProps urlProps;
    private final UrlMappingRepository urlMappingRepository;

    @Override
    public String provideShortenedUrl(String url) {
        return Optional.of(url)
                .map(urlMappingRepository::findFirstByOriginalUrl)
                .filter(Optional::isPresent)
                .orElseGet(() -> Optional.of(new UrlMapping(null, url))
                        .map(urlMappingRepository::save))
                .map(this::computeShortenedUrl)
                .orElseThrow(InvalidUrlException::new);
    }

    @Override
    public Optional<String> getOriginalUrl(String shortUrl) {
        return Optional.ofNullable(shortUrl)
                .map(url -> url.replace(urlProps.getBasePath(), ""))
                .filter(StringUtils::isAlphanumeric)
                .map(url -> new BigInteger(url, 36))
                .flatMap(urlMappingRepository::findById)
                .map(UrlMapping::getOriginalUrl);
    }


    private String computeShortenedUrl(UrlMapping urlMapping) {
        return Optional.of(urlMapping)
                .map(UrlMapping::getId)
                .map(id -> id.toString(36))
                .map(urlProps.getBasePath()::concat)
                .orElse("");
    }

}
