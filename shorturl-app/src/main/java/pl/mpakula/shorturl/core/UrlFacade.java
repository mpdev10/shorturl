package pl.mpakula.shorturl.core;

public interface UrlFacade {

    String shortenUrl(String url);

    String getOriginalUrl(String shortUrl);

}
