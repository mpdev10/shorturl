package pl.mpakula.shorturl.url;

import lombok.NonNull;

interface UrlProvider {

    String provideShortenedUrl(@NonNull String url);

}
