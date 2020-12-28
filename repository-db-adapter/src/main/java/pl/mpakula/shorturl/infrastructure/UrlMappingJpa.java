package pl.mpakula.shorturl.infrastructure;


import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@AllArgsConstructor
@Getter
@Setter
class UrlMappingJpa {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(unique = true)
    private final String originalUrl;

}
