package pl.mpakula.shorturl.core;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigInteger;

@RequiredArgsConstructor
@Getter
public class UrlMapping {

    private final BigInteger id;

    private final String originalUrl;

}
