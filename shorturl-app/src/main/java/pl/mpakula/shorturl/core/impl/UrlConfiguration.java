package pl.mpakula.shorturl.core.impl;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import pl.mpakula.shorturl.core.UrlFacade;

@Configuration
@ConfigurationPropertiesScan
class UrlConfiguration {

    UrlFacade urlFacade() {
        return new UrlFacadeImpl(UrlValidator.getInstance(), new UrlProps("http://127.0.0.1:8080/"), new InMemoryUrlMappingRepository());
    }

}
