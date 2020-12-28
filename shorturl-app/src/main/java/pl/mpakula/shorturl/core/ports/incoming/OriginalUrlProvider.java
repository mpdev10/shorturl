package pl.mpakula.shorturl.core.ports.incoming;

public interface OriginalUrlProvider {

    String getOriginalUrl(String shortUrl);

}
