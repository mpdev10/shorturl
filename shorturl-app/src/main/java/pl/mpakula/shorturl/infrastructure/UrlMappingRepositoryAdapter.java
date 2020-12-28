package pl.mpakula.shorturl.infrastructure;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.mpakula.shorturl.core.model.UrlMapping;
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UrlMappingRepositoryAdapter implements UrlMappingRepository {

    private final UrlMappingJpaRepository repository;

    @Override
    public UrlMapping save(@NonNull UrlMapping urlMapping) {
        return Optional.of(urlMapping)
                .map(UrlMappingRepositoryAdapter::toJpa)
                .map(repository::save)
                .map(UrlMappingRepositoryAdapter::toDomain)
                .orElseThrow();
    }

    @Override
    public Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl) {
        return repository.findFirstByOriginalUrl(originalUrl)
                .map(UrlMappingRepositoryAdapter::toDomain);
    }

    @Override
    public Optional<UrlMapping> findById(BigInteger id) {
        return repository.findById(id)
                .map(UrlMappingRepositoryAdapter::toDomain);
    }

    private static UrlMappingJpa toJpa(UrlMapping urlMapping) {
        return new UrlMappingJpa(urlMapping.getId(), urlMapping.getOriginalUrl());
    }

    private static UrlMapping toDomain(UrlMappingJpa urlMapping) {
        return new UrlMapping(urlMapping.getId(), urlMapping.getOriginalUrl());
    }

}
