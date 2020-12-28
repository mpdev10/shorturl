package pl.mpakula.shorturl.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OriginalUrlNotFoundException extends RuntimeException {
}
