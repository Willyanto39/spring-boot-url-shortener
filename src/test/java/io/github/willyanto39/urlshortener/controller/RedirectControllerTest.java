package io.github.willyanto39.urlshortener.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.willyanto39.urlshortener.dto.UrlDto;
import io.github.willyanto39.urlshortener.exception.UrlNotFoundException;
import io.github.willyanto39.urlshortener.service.UrlService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RedirectController.class)
public class RedirectControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UrlService urlService;

  @Test
  public void testRedirect_ExistingShortUrl() throws Exception {
    UrlDto url = new UrlDto("https://www.google.com", "abcdef");
    when(urlService.findByShortUrl("abcdef")).thenReturn(url);

    mockMvc.perform(get("/abcdef"))
        .andExpect(status().isFound());
  }

  @Test
  public void testRedirect_NonExistentShortUrl() throws Exception {
    when(urlService.findByShortUrl("url404")).thenThrow(new UrlNotFoundException());

    mockMvc.perform(get("/url404"))
        .andExpect(status().isNotFound());
  }
}
