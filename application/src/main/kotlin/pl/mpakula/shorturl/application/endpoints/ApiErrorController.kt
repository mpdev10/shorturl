package pl.mpakula.shorturl.application.endpoints

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.RequestDispatcher
import javax.servlet.http.HttpServletRequest

@Controller
internal class ApiErrorController(private val errorPages: Map<HttpStatus, String>) : ErrorController {

    override fun getErrorPath(): String {
        throw UnsupportedOperationException()
    }

    @RequestMapping("/error")
    fun handleError(request: HttpServletRequest): String {
        return request
            .let { it.getAttribute(RequestDispatcher.ERROR_STATUS_CODE) as Int? }
            ?.let(HttpStatus::valueOf)
            ?.let { errorPages[it] }
            ?: ("error")
    }
}