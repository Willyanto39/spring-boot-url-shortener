package io.github.willyanto39.urlshortener.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.exception.InvalidUrlException;
import io.github.willyanto39.urlshortener.request.CreateShortUrlRequest;
import io.github.willyanto39.urlshortener.service.UrlService;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UrlController.class)
public class UrlControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private UrlService urlService;

  @Test
  public void testGetAllUrls() throws Exception {
    when(urlService.findAll())
        .thenReturn(List.of(
            new UrlDto("https://www.google.com", "abcdef"),
            new UrlDto("https://www.github.com", "a1b2c3")));

    mockMvc.perform(get("/api/urls"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data").isArray())
        .andExpect(jsonPath("$.data", hasSize(2)));
  }

  @Test
  public void testCreateUrl_ValidUrl() throws Exception {
    String url = "https://www.google.com";
    CreateShortUrlRequest request = new CreateShortUrlRequest();
    request.setUrl(url);

    when(urlService.createShortUrl(url)).thenReturn(new UrlDto(url, "abcdef"));

    mockMvc
        .perform(post("/api/urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.data.originalUrl").value(url))
        .andExpect(jsonPath("$.data.shortUrl").value("abcdef"));
  }

  @Test
  public void testCreateUrl_InvalidUrl() throws Exception {
    String url = "invalid url";
    CreateShortUrlRequest request = new CreateShortUrlRequest();
    request.setUrl(url);

    when(urlService.createShortUrl(url)).thenThrow(new InvalidUrlException("url invalid"));

    mockMvc
        .perform(post("/api/urls")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message").value("url invalid"));
  }
}
