package pl.mpakula.shorturl.core;

import lombok.NonNull;

import java.util.Optional;

interface UrlProvider {

    String provideShortenedUrl(@NonNull String url);

    Optional<String> getOriginalUrl(String shortUrl);

}
