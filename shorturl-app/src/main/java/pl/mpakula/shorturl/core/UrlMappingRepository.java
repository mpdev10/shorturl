package pl.mpakula.shorturl.core;

import lombok.NonNull;

import java.math.BigInteger;
import java.util.Optional;

public interface UrlMappingRepository {

    UrlMapping save(@NonNull UrlMapping urlMapping);

    Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl);

    Optional<UrlMapping> findById(BigInteger id);

}
