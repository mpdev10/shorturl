package pl.mpakula.shorturl.url;

import java.util.Optional;

public interface UrlFacade {

    String shortenUrl(String url);

    Optional<String> getOriginalUrl(String shortUrl);

}
