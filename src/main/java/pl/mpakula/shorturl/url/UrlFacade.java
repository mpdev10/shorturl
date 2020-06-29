package pl.mpakula.shorturl.url;

public interface UrlFacade {

    String shortenUrl(String url);

    String getOriginalUrl(String shortUrl);

}
