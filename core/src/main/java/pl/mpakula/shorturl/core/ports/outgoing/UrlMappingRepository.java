package pl.mpakula.shorturl.core.ports.outgoing;

import lombok.NonNull;
import pl.mpakula.shorturl.core.model.UrlMapping;

import java.math.BigInteger;
import java.util.Optional;

public interface UrlMappingRepository {

    UrlMapping save(@NonNull UrlMapping urlMapping);

    Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl);

    Optional<UrlMapping> findById(BigInteger id);

}
