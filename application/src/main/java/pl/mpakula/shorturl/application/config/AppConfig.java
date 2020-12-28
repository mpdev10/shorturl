package pl.mpakula.shorturl.application.config;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import pl.mpakula.shorturl.core.UrlFacade;
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository;

import java.util.Map;

@Configuration
@ComponentScan(basePackages = {"pl.mpakula.shorturl.infrastructure"})
class AppConfig {

    @Bean
    Map<HttpStatus, String> errorPages() {
        return Map.of(HttpStatus.NOT_FOUND, "error-404", HttpStatus.UNPROCESSABLE_ENTITY, "error-422");
    }

    @Bean
    UrlFacade urlFacade(@Value("${url.base_path}") String basePath, UrlMappingRepository urlMappingRepository) {
        return new UrlFacade(UrlValidator.getInstance(), basePath, urlMappingRepository);
    }

}
