package pl.mpakula.shorturl.domain;

public interface UrlFacade {

    String shortenUrl(String url);

    String getOriginalUrl(String shortUrl);

}
