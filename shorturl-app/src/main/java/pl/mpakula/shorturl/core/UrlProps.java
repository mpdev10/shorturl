package pl.mpakula.shorturl.core;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "url")
@ConstructorBinding
class UrlProps {

    @Getter
    private final String basePath;

    UrlProps(String basePath) {
        this.basePath = basePath;
    }

}

