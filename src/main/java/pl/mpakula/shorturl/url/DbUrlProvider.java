package pl.mpakula.shorturl.url;

import lombok.RequiredArgsConstructor;
import pl.mpakula.shorturl.url.exception.InvalidUrlException;

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
                .orElseGet(() -> Optional.of(new UrlMapping(url))
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
