package pl.mpakula.shorturl.core;

import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;

@SuppressWarnings("JpaObjectClassSignatureInspection")
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@RequiredArgsConstructor
@Getter
@Setter
class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private BigInteger id;

    @Column(unique = true)
    private final String originalUrl;

}
