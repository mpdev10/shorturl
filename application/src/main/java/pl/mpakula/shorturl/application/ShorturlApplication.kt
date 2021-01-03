package pl.mpakula.shorturl.application

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class ShorturlApplication

fun main(args: Array<String>) {
    runApplication<ShorturlApplication>(*args)
}

