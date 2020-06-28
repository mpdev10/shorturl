package pl.mpakula.shorturl.url;


import com.google.common.collect.Maps;
import lombok.NonNull;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

class InMemoryUrlMappingRepository implements UrlMappingRepository {

    Map<BigInteger, UrlMapping> shortenedUrls = Maps.newConcurrentMap();

    @Override
    public UrlMapping save(@NonNull UrlMapping urlMapping) {
        BigInteger id = Optional.ofNullable(urlMapping.getId())
                .orElseGet(() -> shortenedUrls.keySet()
                        .stream()
                        .max(BigInteger::compareTo)
                        .map(i -> i.add(BigInteger.ONE))
                        .orElse(BigInteger.ZERO)
                );
        urlMapping.setId(id);
        shortenedUrls.put(id, urlMapping);
        return urlMapping;
    }

    @Override
    public Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl) {
        return shortenedUrls.values().stream()
                .filter(mapping -> mapping.getOriginalUrl().equals(originalUrl))
                .findFirst();
    }

    @Override
    public Optional<UrlMapping> findFirstByOriginalUrlIsNull() {
        return shortenedUrls.values().stream()
                .filter(urlMapping -> isNull(urlMapping.getOriginalUrl()))
                .findFirst();
    }
}
