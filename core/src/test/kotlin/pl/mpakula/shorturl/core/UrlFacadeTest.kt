package pl.mpakula.shorturl.core

import org.apache.commons.validator.routines.UrlValidator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.function.Executable
import pl.mpakula.shorturl.core.exception.InvalidUrlException
import pl.mpakula.shorturl.core.exception.OriginalUrlNotFoundException

internal class UrlFacadeTest {

    @Test
    fun shortenUrl_givenUrlIsNull_throwsInvalidUrlException() {
        //given
        val facade = urlFacade()
        //when
        val action = Executable { facade.shortenUrl(null) }
        //then
        Assertions.assertThrows(InvalidUrlException::class.java, action)
    }

    @Test
    fun shortenUrl_givenUrlIsInvalid_throwsInvalidUrlException() {
        //given
        val facade = urlFacade()
        //when
        val action = Executable { facade.shortenUrl(INVALID_URL) }
        //then
        Assertions.assertThrows(InvalidUrlException::class.java, action)
    }

    @Test
    fun shortenUrl_givenValidUrlHasNoPrefix_returnsValidNewUrl() {
        //given
        val facade = urlFacade()
        //when
        val newUrl = facade.shortenUrl(VALID_NO_PREFIX_URL)
        val urlIsValid = validator.isValid(newUrl)
        //then
        assertThat(urlIsValid).isTrue
    }

    @Test
    fun shortenUrl_givenValidUrlWithPrefix_returnsValidNewUrl() {
        //given
        val facade = urlFacade()
        //when
        val newUrl = facade.shortenUrl(VALID_URL)
        val urlIsValid = validator.isValid(newUrl)
        //then
        assertThat(urlIsValid).isTrue
    }

    @Test
    fun shortenUrl_givenValidUrlTwice_returnsSameUrl() {
        //given
        val facade = urlFacade()
        //when
        val newUrl = facade.shortenUrl(VALID_URL)
        val otherUrl = facade.shortenUrl(VALID_URL)
        //then
        assertThat(newUrl).isEqualTo(otherUrl)
    }

    @Test
    fun shortenUrl_givenTwoDistinctUrls_newUrlsAreDistinctToo() {
        //given
        val facade = urlFacade()
        //when
        val newUrl = facade.shortenUrl(VALID_URL)
        val otherNewUrl = facade.shortenUrl(VALID_URL2)
        //then
        assertThat(newUrl).isNotEqualTo(otherNewUrl)
    }

    @Test
    fun originalUrl_givenNullOriginalUrl_throwsOriginalUrlNotFoundException() {
        //given
        val facade = urlFacade()
        //when
        val action = Executable { facade.getOriginalUrl(null) }
        //then
        Assertions.assertThrows(OriginalUrlNotFoundException::class.java, action)
    }


    @Test
    fun originalUrl_givenInvalidOriginalUrl_throwsOriginalUrlNotFoundException() {
        //given
        val facade = urlFacade()
        //when
        val action = Executable { facade.getOriginalUrl(INVALID_URL) }
        //then
        Assertions.assertThrows(OriginalUrlNotFoundException::class.java, action)
    }


    @Test
    fun originalUrl_givenNotExistingOriginalUrl_throwsOriginalUrlNotFoundException() {
        //given
        val facade = urlFacade()
        //when
        val action = Executable { facade.getOriginalUrl(VALID_URL) }
        //then
        Assertions.assertThrows(OriginalUrlNotFoundException::class.java, action)
    }


    @Test
    fun originalUrl_givenValidUrl_originalUrlIsPresentAndValid() {
        //given
        val facade = urlFacade()
        val originalUrl = VALID_URL
        //when
        val newUrl = facade.shortenUrl(originalUrl)
        val actualOriginalUrl = facade.getOriginalUrl(newUrl)
        //then
        assertThat(actualOriginalUrl).isEqualTo(originalUrl)
    }

    companion object {
        private const val INVALID_URL = "invalid..url"
        private const val VALID_NO_PREFIX_URL = "mpakula.pl"
        private const val VALID_URL = "http://mpakula.pl"
        private const val VALID_URL2 = "google.pl"
        private val validator = UrlValidator.getInstance()
        private fun urlFacade(): UrlFacade {
            return UrlFacade(UrlValidator.getInstance(), "http://127.0.0.1:8080/", InMemoryUrlMappingRepository())
        }
    }
}