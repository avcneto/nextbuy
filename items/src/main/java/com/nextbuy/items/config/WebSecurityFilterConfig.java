package com.nextbuy.items.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextbuy.items.exception.ExceptionMessage;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.List;

@Configuration
public class WebSecurityFilterConfig extends OncePerRequestFilter {

  @Value("${jwt.secret}")
  private String secret;
  private static final String AUTHORIZATION = "Authorization";
  private static final String BEARER = "Bearer ";
  private static final String UNAUTHORIZED_USER = "unauthorized user";
  private static final String TOKEN_IS_REQUIRED_OR_INVALID = "token is required or invalid";
  private static final String UTF_8 = "UTF-8";
  private static final String ROLE = "role";
  private static final String ADMIN = "ADMIN";
  private static final String ITEM_PATH = "/item";

  private static final Integer TOKEN_INDEX = 7;
  private static final List<Router> ROUTER_REQUIRED = List.of(
          new Router(ITEM_PATH, HttpMethod.PUT),
          new Router(ITEM_PATH, HttpMethod.DELETE),
          new Router(ITEM_PATH, HttpMethod.PATCH)
  );
  private final ObjectMapper mapper;

  public WebSecurityFilterConfig(ObjectMapper mapper) {
    this.mapper = mapper;
  }

  @SneakyThrows
  @Override
  protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) {
    var token = request.getHeader(AUTHORIZATION);

    if(!hasTokenRequired(request)) {
      filterChain.doFilter(request, response);

        return;
    }

    if (hasToken(token)) {
      token = token.substring(TOKEN_INDEX);

      try {
        Claims claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        var isAdmin = claims.get(ROLE, List.class).contains(ADMIN);

        if (!isAdmin) {
          setHandlerException(response, UNAUTHORIZED_USER);
          return;
        }

        filterChain.doFilter(request, response);
      } catch (Exception e) {
        setHandlerException(response, UNAUTHORIZED_USER);
      }
    } else {
      setHandlerException(response, TOKEN_IS_REQUIRED_OR_INVALID);
    }
  }

  private void setHandlerException(HttpServletResponse response, String message) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(UTF_8);
    response.getWriter().write(mapper.writeValueAsString(new ExceptionMessage(message)));
  }

  private Boolean hasToken(String token) {
    return token != null && token.startsWith(BEARER);
  }

  private Boolean hasTokenRequired(HttpServletRequest request) {
    return ROUTER_REQUIRED.stream()
            .anyMatch(router -> request.getServletPath().startsWith(router.path())
                    && router.method().matches(request.getMethod()));
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
  }
}
