package io.github.willyanto39.urlshortener.controller;

import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.request.CreateShortUrlRequest;
import io.github.willyanto39.urlshortener.response.ApiResponse;
import io.github.willyanto39.urlshortener.service.UrlService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/urls")
public class UrlController {
  private final UrlService urlService;

  @Autowired
  public UrlController(UrlService urlService) {
    this.urlService = urlService;
  }

  @GetMapping
  public ResponseEntity<ApiResponse<List<UrlDto>>> getAllUrls() {
    List<UrlDto> urls = urlService.findAll();
    ApiResponse<List<UrlDto>> response = ApiResponse.success(urls);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  public ResponseEntity<ApiResponse<UrlDto>> createShortUrl(
      @Valid @RequestBody CreateShortUrlRequest request) {
    String originalUrl = request.getUrl();
    UrlDto url = urlService.createShortUrl(originalUrl);
    ApiResponse<UrlDto> response = ApiResponse.success(url);

    return ResponseEntity.ok(response);
  }
}
