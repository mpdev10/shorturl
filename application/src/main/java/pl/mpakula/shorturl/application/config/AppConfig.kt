package pl.mpakula.shorturl.application.config

import org.apache.commons.validator.routines.UrlValidator
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import pl.mpakula.shorturl.core.UrlFacade
import pl.mpakula.shorturl.core.ports.outgoing.UrlMappingRepository

@Configuration
@ComponentScan(basePackages = ["pl.mpakula.shorturl.infrastructure"])
internal open class AppConfig {

    @Bean
    open fun errorPages(): Map<HttpStatus, String> =
        mapOf(HttpStatus.NOT_FOUND to "error-404", HttpStatus.UNPROCESSABLE_ENTITY to "error-422")

    @Bean
    open fun urlFacade(
        @Value("\${url.base_path}") basePath: String,
        urlMappingRepository: UrlMappingRepository
    ): UrlFacade = UrlFacade(UrlValidator.getInstance(), basePath, urlMappingRepository)

}