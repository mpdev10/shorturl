package pl.mpakula.shorturl.core;

import pl.mpakula.shorturl.core.ports.incoming.OriginalUrlProvider;
import pl.mpakula.shorturl.core.ports.incoming.UrlShortener;

public interface UrlFacade extends OriginalUrlProvider, UrlShortener {

    String shortenUrl(String url);

    String getOriginalUrl(String shortUrl);

}
