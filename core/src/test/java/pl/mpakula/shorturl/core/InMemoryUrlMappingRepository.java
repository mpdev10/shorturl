package pl.mpakula.shorturl.core;


import com.google.common.collect.Maps;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import pl.mpakula.shorturl.core.model.UrlMapping;
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository;

import java.math.BigInteger;
import java.util.Map;
import java.util.Optional;

class InMemoryUrlMappingRepository implements UrlMappingRepository {

    Map<BigInteger, UrlMapping> shortenedUrls = Maps.newConcurrentMap();

    @NotNull
    @Override
    public UrlMapping save(@NonNull UrlMapping urlMapping) {
        BigInteger id = Optional.ofNullable(urlMapping.getId())
                .orElseGet(() -> shortenedUrls.keySet()
                        .stream()
                        .max(BigInteger::compareTo)
                        .map(i -> i.add(BigInteger.ONE))
                        .orElse(BigInteger.ZERO)
                );
        UrlMapping savedMapping = new UrlMapping(id, urlMapping.getOriginalUrl());
        shortenedUrls.put(id, savedMapping);
        return savedMapping;
    }

    @NotNull
    @Override
    public Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl) {
        return shortenedUrls.values().stream()
                .filter(mapping -> mapping.getOriginalUrl().equals(originalUrl))
                .findFirst();
    }

    @NotNull
    @Override
    public Optional<UrlMapping> findById(BigInteger id) {
        return Optional.ofNullable(shortenedUrls.get(id));
    }

}
