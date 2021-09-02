package io.github.willyanto39.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String redirectToHomePage() {
    return "index.html";
  }
}
