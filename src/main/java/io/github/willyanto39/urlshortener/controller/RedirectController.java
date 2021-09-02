package io.github.willyanto39.urlshortener.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.service.UrlService;

@Controller
public class RedirectController {
  private final UrlService urlService;

  @Autowired
  public RedirectController(UrlService urlService) {
    this.urlService = urlService;
  }

  @GetMapping("/{shortUrl:[a-z|0-9]+}")
  public String redirect(@PathVariable String shortUrl) {
    UrlDto url = urlService.findByShortUrl(shortUrl);
    String originalUrl = url.getOriginalUrl();

    return "redirect:" + originalUrl;
  }
}
