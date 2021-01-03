package pl.mpakula.shorturl.infrastructure;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Repository;
import pl.mpakula.shorturl.core.model.UrlMapping;
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@EnableAutoConfiguration
class UrlMappingRepositoryAdapter implements UrlMappingRepository {

    private final UrlMappingJpaRepository repository;

    @NotNull
    @Override
    public UrlMapping save(@NotNull UrlMapping urlMapping) {
        return Optional.of(urlMapping)
                .map(UrlMappingRepositoryAdapter::toJpa)
                .map(repository::save)
                .map(UrlMappingRepositoryAdapter::toDomain)
                .orElseThrow();
    }

    @NotNull
    @Override
    public Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl) {
        return Optional.ofNullable(repository.findFirstByOriginalUrl(originalUrl))
                .map(UrlMappingRepositoryAdapter::toDomain);
    }

    @NotNull
    @Override
    public Optional<UrlMapping> findById(BigInteger id) {
        return Optional.ofNullable(repository.findById(id))
                .map(UrlMappingRepositoryAdapter::toDomain);
    }

    private static UrlMappingJpa toJpa(UrlMapping urlMapping) {
        return new UrlMappingJpa(urlMapping.getId(), urlMapping.getOriginalUrl());
    }

    private static UrlMapping toDomain(UrlMappingJpa urlMapping) {
        return new UrlMapping(urlMapping.getId(), urlMapping.getOriginalUrl());
    }

}
