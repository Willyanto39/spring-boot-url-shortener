package io.github.willyanto39.urlshortener.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "urls")
public class Url {
  @Id
  private String id;

  @Indexed(unique = true)
  private String originalUrl;

  @Indexed(unique = true)
  private String shortUrl;

  public Url() {}

  public Url(String originalUrl, String shortUrl) {
    this.originalUrl = originalUrl;
    this.shortUrl = shortUrl;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
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
