package pl.mpakula.shorturl.infrastructure;


import lombok.NonNull;
import org.springframework.data.repository.Repository;

import java.math.BigInteger;
import java.util.Optional;

interface UrlMappingJpaRepository extends Repository<UrlMappingJpa, BigInteger> {

    UrlMappingJpa save(@NonNull UrlMappingJpa urlMapping);

    Optional<UrlMappingJpa> findFirstByOriginalUrl(String originalUrl);

    Optional<UrlMappingJpa> findById(BigInteger id);

}
