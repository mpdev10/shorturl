package pl.mpakula.shorturl.infrastructure;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.util.URLEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import pl.mpakula.shorturl.url.UrlFacade;

import java.nio.charset.StandardCharsets;

@Controller
@RequiredArgsConstructor
class ApiController {

    private final UrlFacade urlFacade;

    @PostMapping("/short")
    String shortenUrl(Model model, @RequestParam String url) {
        model.addAttribute("shortUrl", urlFacade.shortenUrl(url));
        return "shortUrl";
    }

    @GetMapping("/{shortUrl}")
    RedirectView redirectUrl(@PathVariable("shortUrl") String shortUrl) {
        String encodedUrl = URLEncoder.DEFAULT.encode(urlFacade.getOriginalUrl(shortUrl), StandardCharsets.UTF_8);
        return new RedirectView(encodedUrl, false);
    }

}