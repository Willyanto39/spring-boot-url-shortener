package io.github.willyanto39.urlshortener.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.entity.Url;
import io.github.willyanto39.urlshortener.exception.UrlNotFoundException;
import io.github.willyanto39.urlshortener.repository.UrlRepository;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UrlServiceTest {
  @Mock
  private UrlRepository urlRepository;

  @Mock
  private ShortenerService shortenerService;

  @InjectMocks
  private UrlService urlService;

  @Test
  public void testFindAll() {
    List<Url> urls = List.of(new Url("https://www.google.com", "abcdef"));
    when(urlRepository.findAll()).thenReturn(urls);
    List<UrlDto> result = urlService.findAll();
    UrlDto urlResult = result.get(0);

    assertEquals("https://www.google.com", urlResult.getOriginalUrl());
    assertEquals("abcdef", urlResult.getShortUrl());
  }

  @Test
  public void testCreateShortUrl_NewUrl() {
    String originalUrl = "https://www.google.com";
    String shortUrl = "abcdef";
    Url url = new Url(originalUrl, shortUrl);
    when(urlRepository.findByOriginalUrl(originalUrl)).thenReturn(null);
    when(shortenerService.shorten(originalUrl)).thenReturn(shortUrl);
    when(urlRepository.save(any(Url.class))).thenReturn(url);
    UrlDto result = urlService.createShortUrl(originalUrl);

    verify(urlRepository, times(1)).save(any(Url.class));
    assertEquals(originalUrl, result.getOriginalUrl());
    assertEquals(shortUrl, result.getShortUrl());
  }

  @Test
  public void testCreateShortUrl_ExistingUrl() {
    String url = "https://www.google.com";
    when(urlRepository.findByOriginalUrl(url)).thenReturn(new Url(url, "abcdef"));
    UrlDto result = urlService.createShortUrl(url);

    verify(shortenerService, times(0)).shorten(url);
    verify(urlRepository, times(0)).save(any(Url.class));
    assertEquals(url, result.getOriginalUrl());
    assertEquals("abcdef", result.getShortUrl());
  }

  @Test
  public void testFindByShortUrl_ExistingShortUrl() {
    String shortUrl = "abcdef";
    when(urlRepository.findByShortUrl(shortUrl))
        .thenReturn(new Url("https://www.google.com", shortUrl));
    UrlDto result = urlService.findByShortUrl(shortUrl);

    assertEquals("https://www.google.com", result.getOriginalUrl());
    assertEquals(shortUrl, result.getShortUrl());
  }

  @Test
  public void testFindByShortUrl_NotExistentShortUrl() {
    String shortUrl = "abcdef";
    when(urlRepository.findByShortUrl(shortUrl)).thenReturn(null);

    assertThrows(UrlNotFoundException.class, () -> {
      urlService.findByShortUrl(shortUrl);
    });
  }
}
