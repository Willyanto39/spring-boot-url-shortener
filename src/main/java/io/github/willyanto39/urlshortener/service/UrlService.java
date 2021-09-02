package io.github.willyanto39.urlshortener.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.github.willyanto39.urlshortener.converter.UrlConverter;
import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.entity.Url;
import io.github.willyanto39.urlshortener.exception.UrlNotFoundException;
import io.github.willyanto39.urlshortener.repository.UrlRepository;

@Service
public class UrlService {
  private final ShortenerService shortenerService;
  private final UrlRepository urlRepository;

  @Autowired
  public UrlService(ShortenerService shortenerService, UrlRepository urlRepository) {
    this.shortenerService = shortenerService;
    this.urlRepository = urlRepository;
  }

  public List<UrlDto> findAll() {
    List<Url> urls = urlRepository.findAll();

    return urls.stream().map(UrlConverter::toDto).collect(Collectors.toList());
  }

  public UrlDto findByShortUrl(String shortUrl) {
    Url url = urlRepository.findByShortUrl(shortUrl);

    if (url == null) {
      throw new UrlNotFoundException();
    }

    return UrlConverter.toDto(url);
  }

  public UrlDto createShortUrl(String originalUrl) {
    Url url = urlRepository.findByOriginalUrl(originalUrl);

    if (url == null) {
      String shortUrl = shortenerService.shorten(originalUrl);
      Url newUrl = new Url(originalUrl, shortUrl);

      return UrlConverter.toDto(urlRepository.save(newUrl));
    }

    return UrlConverter.toDto(url);
  }
}
