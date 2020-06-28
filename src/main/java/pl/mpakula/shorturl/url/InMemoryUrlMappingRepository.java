package pl.mpakula.shorturl.url;


import com.google.common.collect.Maps;
import lombok.NonNull;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

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
    public Optional<UrlMapping> findById(BigInteger id) {
        return Optional.ofNullable(shortenedUrls.get(id));
    }

}
