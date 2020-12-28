package pl.mpakula.shorturl.core;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationPropertiesScan
class UrlConfiguration {

    UrlFacade urlFacade() {
        UrlProvider provider = new DbUrlProvider(new UrlProps("http://127.0.0.1:8080/"), new InMemoryUrlMappingRepository());
        return new UrlFacadeImpl(new UrlValidator(), provider);
    }

    @Bean
    UrlProvider urlProvider(UrlProps urlProps, UrlMappingRepository urlMappingRepository) {
        return new DbUrlProvider(urlProps, urlMappingRepository);
    }

    @Bean
    UrlFacade urlFacade(UrlProvider urlProvider) {
        return new UrlFacadeImpl(UrlValidator.getInstance(), urlProvider);
    }

}
