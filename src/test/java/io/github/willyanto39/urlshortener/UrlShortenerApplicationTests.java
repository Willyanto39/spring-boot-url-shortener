package io.github.willyanto39.urlshortener;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.request.CreateShortUrlRequest;
import io.github.willyanto39.urlshortener.response.ApiResponse;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UrlShortenerApplicationTests {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  public void testGetAllUrls() {
    ResponseEntity<ApiResponse<List<UrlDto>>> response =
        restTemplate.exchange(
            createFullUrl("/api/urls"),
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<ApiResponse<List<UrlDto>>>() {});

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void testCreateUrl() {
    CreateShortUrlRequest request = new CreateShortUrlRequest();
    request.setUrl("https://www.spring.io");
    ResponseEntity<ApiResponse<UrlDto>> response =
        restTemplate.exchange(
            createFullUrl("/api/urls"),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<ApiResponse<UrlDto>>() {});
    UrlDto url = response.getBody().getData();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals("https://www.spring.io", url.getOriginalUrl());
  }

  @Test
  public void testCreateUrl_EmptyUrl() {
    CreateShortUrlRequest request = new CreateShortUrlRequest();
    request.setUrl("");
    ResponseEntity<ApiResponse<Void>> response = restTemplate.exchange(
        createFullUrl("/api/urls"),
        HttpMethod.POST,
        new HttpEntity<>(request),
        new ParameterizedTypeReference<ApiResponse<Void>>() {});

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Url cannot be empty", response.getBody().getMessage());
  }

  @Test
  public void testCreateUrl_InvalidProtocol() {
    CreateShortUrlRequest request = new CreateShortUrlRequest();
    request.setUrl("invalid url");

    ResponseEntity<ApiResponse<Void>> response =
        restTemplate.exchange(
            createFullUrl("/api/urls"),
            HttpMethod.POST,
            new HttpEntity<>(request),
            new ParameterizedTypeReference<ApiResponse<Void>>() {});

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Only http or https url is allowed", response.getBody().getMessage());
  }

  @Test
  public void testRedirect_ExistingShortUrl() {
    ResponseEntity<String> response =
        restTemplate.getForEntity(createFullUrl("/3w5e111f4vgk2"), String.class);

    assertTrue(response.getStatusCode().is3xxRedirection());
  }

  @Test
  public void testRedirect_NonExistentShortUrl() {
    ResponseEntity<String> response =
        restTemplate.getForEntity(createFullUrl("/url404"), String.class);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  private String createFullUrl(String path) {
    return "http://localhost:" + port + path;
  }
}
