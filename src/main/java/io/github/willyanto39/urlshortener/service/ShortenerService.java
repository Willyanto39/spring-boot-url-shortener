package io.github.willyanto39.urlshortener.service;

import io.github.willyanto39.urlshortener.exception.InvalidUrlException;
import io.github.willyanto39.urlshortener.protocol.SupportedProtocol;
import org.springframework.stereotype.Service;

@Service
public class ShortenerService {
  private static final int RADIX = 36;

  public String shorten(String originalUrl) {
    if (!SupportedProtocol.isIn(originalUrl)) {
      throw new InvalidUrlException("Only http or https url is allowed");
    }

    return Long.toUnsignedString(originalUrl.hashCode(), RADIX);
  }
}
