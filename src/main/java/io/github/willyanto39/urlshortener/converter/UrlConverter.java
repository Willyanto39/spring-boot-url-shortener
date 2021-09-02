package io.github.willyanto39.urlshortener.converter;

import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.entity.Url;

public final class UrlConverter {
  private UrlConverter() {

  }

  public static UrlDto toDto(Url url) {
    String originalUrl = url.getOriginalUrl();
    String shortUrl = url.getShortUrl();

    return new UrlDto(originalUrl, shortUrl);
  }
}
