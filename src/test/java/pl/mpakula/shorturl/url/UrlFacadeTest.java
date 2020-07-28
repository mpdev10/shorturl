package pl.mpakula.shorturl.url;

import org.apache.commons.validator.routines.UrlValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import pl.mpakula.shorturl.url.exception.InvalidUrlException;
import pl.mpakula.shorturl.url.exception.OriginalUrlNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UrlFacadeTest {

    private static final String INVALID_URL = "invalid..url";
    private static final String VALID_NO_PREFIX_URL = "mpakula.pl";
    private static final String VALID_URL = "http://mpakula.pl";
    private static final String VALID_URL2 = "google.pl";
    private static final UrlConfiguration configuration = new UrlConfiguration();
    private static final UrlValidator validator = UrlValidator.getInstance();

    @Test
    void shortenUrl_givenUrlIsNull_throwsInvalidUrlException() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        Executable action = () -> facade.shortenUrl(null);
        //then
        assertThrows(InvalidUrlException.class, action);
    }

    @Test
    void shortenUrl_givenUrlIsInvalid_throwsInvalidUrlException() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        Executable action = () -> facade.shortenUrl(INVALID_URL);
        //then
        assertThrows(InvalidUrlException.class, action);
    }

    @Test
    void shortenUrl_givenValidUrlHasNoPrefix_returnsValidNewUrl() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        String newUrl = facade.shortenUrl(VALID_NO_PREFIX_URL);
        boolean urlIsValid = validator.isValid(newUrl);
        //then
        assertThat(urlIsValid).isTrue();
    }

    @Test
    void shortenUrl_givenValidUrlWithPrefix_returnsValidNewUrl() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        String newUrl = facade.shortenUrl(VALID_URL);
        boolean urlIsValid = validator.isValid(newUrl);
        //then
        assertThat(urlIsValid).isTrue();
    }

    @Test
    void shortenUrl_givenValidUrlTwice_returnsSameUrl() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        String newUrl = facade.shortenUrl(VALID_URL);
        String otherUrl = facade.shortenUrl(VALID_URL);
        //then
        assertThat(newUrl).isEqualTo(otherUrl);
    }

    @Test
    void shortenUrl_givenTwoDistinctUrls_newUrlsAreDistinctToo() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        String newUrl = facade.shortenUrl(VALID_URL);
        String otherNewUrl = facade.shortenUrl(VALID_URL2);
        //then
        assertThat(newUrl).isNotEqualTo(otherNewUrl);
    }

    @Test
    void getOriginalUrl_givenNullOriginalUrl_throwsOriginalUrlNotFoundException() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        Executable action = () -> facade.getOriginalUrl(null);
        //then
        assertThrows(OriginalUrlNotFoundException.class, action);
    }

    @Test
    void getOriginalUrl_givenInvalidOriginalUrl_throwsOriginalUrlNotFoundException() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        Executable action = () -> facade.getOriginalUrl(INVALID_URL);
        //then
        assertThrows(OriginalUrlNotFoundException.class, action);
    }

    @Test
    void getOriginalUrl_givenNotExistingOriginalUrl_throwsOriginalUrlNotFoundException() {
        //given
        UrlFacade facade = configuration.urlFacade();
        //when
        Executable action = () -> facade.getOriginalUrl(VALID_URL);
        //then
        assertThrows(OriginalUrlNotFoundException.class, action);
    }

    @Test
    void getOriginalUrl_givenValidUrl_originalUrlIsPresentAndValid() {
        //given
        UrlFacade facade = configuration.urlFacade();
        String originalUrl = VALID_URL;
        //when
        String newUrl = facade.shortenUrl(originalUrl);
        String actualOriginalUrl = facade.getOriginalUrl(newUrl);
        //then
        assertThat(actualOriginalUrl).isEqualTo(originalUrl);
    }

}