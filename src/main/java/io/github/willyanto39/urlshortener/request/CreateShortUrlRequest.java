package io.github.willyanto39.urlshortener.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class CreateShortUrlRequest {
  @NotNull(message = "Missing url field")
  @NotEmpty(message = "Url cannot be empty")
  private String url;

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
