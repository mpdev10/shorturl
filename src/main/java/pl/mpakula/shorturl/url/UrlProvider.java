package pl.mpakula.shorturl.url;

import lombok.NonNull;

import java.util.Optional;

interface UrlProvider {

    String provideShortenedUrl(@NonNull String url);

    Optional<String> getOriginalUrl(String shortUrl);

}
