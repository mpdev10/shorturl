package pl.mpakula.shorturl.url;

import org.junit.jupiter.api.Test;
import pl.mpakula.shorturl.url.exception.InvalidUrlException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlFacadeTest {

    private static final String INVALID_URL = "invalid..url";
    private static final String VALID_NO_PREFIX_URL = "mpakula.pl";
    private static final String VALID_URL = "http://mpakula.pl";
    private static final String VALID_URL2 = "google.pl";
    private static final UrlConfiguration configuration = new UrlConfiguration();

    @Test
    void shortenUrl_givenUrlIsNull_throwsInvalidUrlException() {
        assertThrows(InvalidUrlException.class, () -> configuration.urlFacade().shortenUrl(null));
    }

    @Test
    void shortenUrl_givenUrlIsInvalid_throwsInvalidUrlException() {
        assertThrows(InvalidUrlException.class, () -> configuration.urlFacade().shortenUrl(INVALID_URL));
    }

    @Test
    void shortenUrl_givenValidUrlHasNoPrefix_returnsNewUrl() {
        assertThat(configuration.urlFacade().shortenUrl(VALID_NO_PREFIX_URL)).isNotEqualTo(VALID_NO_PREFIX_URL);
    }

    @Test
    void shortenUrl_givenValidUrlWithPrefix_returnsNewUrl() {
        assertThat(configuration.urlFacade().shortenUrl(VALID_URL)).isNotEqualTo(VALID_URL);
    }

    @Test
    void shortenUrl_givenValidUrlTwice_returnsSameUrl() {
        UrlFacade facade = configuration.urlFacade();
        String url = facade.shortenUrl(VALID_URL);
        String url2 = facade.shortenUrl(VALID_URL);
        assertThat(url).isEqualTo(url2);
    }

    @Test
    void shortenUrl_givenTwoDistinctUrls_newUrlsAreDistinctToo() {
        UrlFacade facade = configuration.urlFacade();
        String url = facade.shortenUrl(VALID_URL);
        String url2 = facade.shortenUrl(VALID_URL2);
        assertThat(url).isNotEqualTo(url2);
    }

    @Test
    void getOriginalUrl_givenNullInvalidOrNotExistingOriginalUrl_originalUrlIsEmpty() {
        assertAll(
                () -> assertThat(configuration.urlFacade().getOriginalUrl(null)).isEmpty(),
                () -> assertThat(configuration.urlFacade().getOriginalUrl(INVALID_URL)).isEmpty(),
                () -> assertThat(configuration.urlFacade().getOriginalUrl(VALID_URL)).isEmpty()
        );
    }

    @Test
    void getOriginalUrl_givenValidUrl_originalUrlIsPresentAndValid() {
        UrlFacade facade = configuration.urlFacade();
        String shortUrl = facade.shortenUrl(VALID_URL);
        assertThat(facade.getOriginalUrl(shortUrl)).contains(VALID_URL);
    }

}