package pl.mpakula.shorturl.url;

import lombok.NonNull;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface UrlMappingRepository extends Repository<UrlMapping, String> {

    UrlMapping save(@NonNull UrlMapping urlMapping);

    Optional<UrlMapping> findFirstByOriginalUrl(String originalUrl);

    Optional<UrlMapping> findFirstByOriginalUrlIsNull();


}
