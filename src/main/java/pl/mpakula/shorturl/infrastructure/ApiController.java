package pl.mpakula.shorturl.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import pl.mpakula.shorturl.url.UrlFacade;

@Controller
@RequiredArgsConstructor
class ApiController {

    private final UrlFacade urlFacade;

    @PostMapping("/short")
    public String shortenUrl(Model model, @RequestParam String url) {
        model.addAttribute("shortUrl", urlFacade.shortenUrl(url));
        return "shortUrl";
    }

    @GetMapping("/{shortUrl}")
    public RedirectView redirectUrl(@PathVariable("shortUrl") String shortUrl) {
        return new RedirectView(urlFacade.getOriginalUrl(shortUrl), false);
    }


}