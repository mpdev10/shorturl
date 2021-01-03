package pl.mpakula.shorturl.application.endpoints

import org.apache.catalina.util.URLEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView
import pl.mpakula.shorturl.core.ports.incoming.OriginalUrlProvider
import pl.mpakula.shorturl.core.ports.incoming.UrlShortener
import java.nio.charset.StandardCharsets

@Controller
internal class ApiController(
    private val urlShortener: UrlShortener,
    private val originalUrlProvider: OriginalUrlProvider
) {

    @PostMapping("/short")
    fun shortenUrl(model: Model, @RequestParam url: String?): String {
        model.addAttribute("shortUrl", urlShortener.shortenUrl(url))
        return "shortUrl"
    }

    @GetMapping("/{shortUrl}")
    fun redirectUrl(@PathVariable("shortUrl") shortUrl: String?): RedirectView {
        val encodedUrl =
            URLEncoder.DEFAULT.encode(originalUrlProvider.getOriginalUrl(shortUrl), StandardCharsets.UTF_8)
        return RedirectView(encodedUrl, false)
    }
}