package io.github.willyanto39.urlshortener.dto;

public class UrlDto {
  private String originalUrl;
  private String shortUrl;

  public UrlDto() {

  }

  public UrlDto(String originalUrl, String shortUrl) {
    this.originalUrl = originalUrl;
    this.shortUrl = shortUrl;
  }

  public String getOriginalUrl() {
    return originalUrl;
  }

  public void setOriginalUrl(String originalUrl) {
    this.originalUrl = originalUrl;
  }

  public String getShortUrl() {
    return shortUrl;
  }

  public void setShortUrl(String shortUrl) {
    this.shortUrl = shortUrl;
  }
}
