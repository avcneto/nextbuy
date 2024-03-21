package com.nextbuy.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.http.HttpClient;
import java.time.Duration;

import static java.time.Duration.ofSeconds;

@Configuration
public class HttpClientConfig {

  private static final Duration duration = ofSeconds(10);
  public static final String STANDARD_CLIENT = "standard_client";

  @Bean(name = STANDARD_CLIENT)
  public HttpClient.Builder getHttpClient() {
    return HttpClient.newBuilder().connectTimeout(duration);
  }
}
