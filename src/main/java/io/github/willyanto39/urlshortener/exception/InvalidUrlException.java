package io.github.willyanto39.urlshortener.exception;

@SuppressWarnings("serial")
public class InvalidUrlException extends RuntimeException {
  public InvalidUrlException(String message) {
    super(message);
  }
}
