package pl.mpakula.shorturl.application;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;

@Controller
@RequiredArgsConstructor
class ApiErrorController implements ErrorController {

    private final Map<HttpStatus, String> errorPages;

    @Override
    public String getErrorPath() {
        throw new UnsupportedOperationException();
    }

    @RequestMapping("/error")
    String handleError(HttpServletRequest request) {
        return Optional.ofNullable(request)
                .map(r -> (Integer) r.getAttribute(ERROR_STATUS_CODE))
                .map(HttpStatus::valueOf)
                .map(errorPages::get)
                .orElse("error");
    }

}
