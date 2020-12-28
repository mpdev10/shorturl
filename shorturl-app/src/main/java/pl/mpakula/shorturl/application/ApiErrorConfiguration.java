package pl.mpakula.shorturl.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Configuration
class ApiErrorConfiguration {

    @Bean
    Map<HttpStatus, String> errorPages() {
        return Map.of(HttpStatus.NOT_FOUND, "error-404", HttpStatus.UNPROCESSABLE_ENTITY, "error-422");
    }

}
