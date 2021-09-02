package io.github.willyanto39.urlshortener.protocol;

public enum SupportedProtocol {
  HTTP("http://"), HTTPS("https://");

  private String protocol;

  private SupportedProtocol(String protocol) {
    this.protocol = protocol;
  }

  public static boolean isIn(String url) {
    for (SupportedProtocol supportedProtocol : SupportedProtocol.values()) {
      if (url.startsWith(supportedProtocol.protocol)) {
        return true;
      }
    }

    return false;
  }
}
