package pl.mpakula.shorturl.infrastructure

import java.math.BigInteger
import javax.persistence.*

@Entity
internal class UrlMappingJpa(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        var id: BigInteger? = null,
        @Column(unique = true)
        var originalUrl: String? = null,
)