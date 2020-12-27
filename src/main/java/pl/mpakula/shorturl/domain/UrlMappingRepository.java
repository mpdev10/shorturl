package pl.mpakula.shorturl.domain;

import lombok.NonNull;
import org.springframework.data.repository.Repository;

import java.math.BigInteger;
import java.util.Optional;

interface UrlMappingRepository extends Repository<UrlMapping, String> {

    UrlMapping save(@NonNull UrlMapping urlMapping);

    Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl);

    Optional<UrlMapping> findById(BigInteger id);

}
