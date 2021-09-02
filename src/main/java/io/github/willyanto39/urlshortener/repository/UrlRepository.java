package io.github.willyanto39.urlshortener.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import io.github.willyanto39.urlshortener.entity.Url;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> {
  public Url findByOriginalUrl(String originalUrl);

  public Url findByShortUrl(String shortUrl);
}
