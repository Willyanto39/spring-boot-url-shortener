package io.github.willyanto39.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.github.willyanto39.urlshortener.exception.InvalidUrlException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ShortenerServiceTest {
  private ShortenerService shortenerService;

  @BeforeEach
  public void init() {
    shortenerService = new ShortenerService();
  }

  @Test
  public void testShorten_ValidUrl() {
    String url = "https://www.google.com";

    assertDoesNotThrow(() -> {
      shortenerService.shorten(url);
    });
  }

  @Test
  public void testShorten_InvalidUrl() {
    String url = "invalid url";

    assertThrows(InvalidUrlException.class, () -> {
      shortenerService.shorten(url);
    });
  }
}
