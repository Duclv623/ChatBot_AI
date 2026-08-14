package com.jurisai.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jurisai.configs.RouteValidator;
import com.jurisai.service.TokenValidationService;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.util.*;

@Component
@Slf4j
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private TokenValidationService tokenValidationService;
    private final List<String> publicPaths;
    private final ObjectMapper objectMapper;

    private static final String JWT_PREFIX_REDIS = "jwt:";

    public AuthenticationFilter(TokenValidationService tokenValidationService, RouteValidator routeValidator, ObjectMapper objectMapper) {
        super(AuthenticationFilter.Config.class);
        this.tokenValidationService = tokenValidationService;
        this.publicPaths = routeValidator.getPublicPaths();
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if (isPublicPath(request)) {
                return chain.filter(exchange);
            }

            // get token from request
            String tokenFromHeader = getTokenFromHeader(request);
            if (tokenFromHeader == null) {
                return onError(exchange, "Authorization header trống hoặc không hợp lệ", HttpStatus.UNAUTHORIZED);
            }

            // validate token
            return tokenValidationService.validateAndGetClaims(tokenFromHeader)
                    .map(Optional::of)
                    .defaultIfEmpty(Optional.empty())
                    .flatMap(optionalClaims -> {
                        if (optionalClaims.isPresent()) {
                            JWTClaimsSet claimsSet = optionalClaims.get();
                            ServerHttpRequest enrichedRequest = enrichRequestWithClaims(exchange, claimsSet);
                            // send request enriched to account service
                            return chain.filter(exchange.mutate().request(enrichedRequest).build());
                        } else {
                            return onError(exchange, "Phiên làm việc của bạn không tìm thấy hoặc hết hạn", HttpStatus.UNAUTHORIZED);
                        }
                    });
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String errorMessage, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("timestamp", new Date().toString());
        errorBody.put("path", exchange.getRequest().getURI().getPath());
        errorBody.put("status", httpStatus.value());
        errorBody.put("error", httpStatus.getReasonPhrase());
        errorBody.put("message", errorMessage);

        try {
            byte[] responseBytes = objectMapper.writeValueAsBytes(errorBody);
            DataBuffer buffer = response.bufferFactory().wrap(responseBytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            return response.setComplete();
        }
    }

    private String getTokenFromHeader(ServerHttpRequest request) {
        final String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private boolean isPublicPath(ServerHttpRequest request) {
        return publicPaths.stream().anyMatch(path -> request.getURI().getPath().matches(path.replace("/**", ".*")));
    }

    // enrich request from token
    private ServerHttpRequest enrichRequestWithClaims(ServerWebExchange exchange, JWTClaimsSet claims) {
        try {
            String accountIdStr = claims.getStringClaim("accountId");
            String userIdStr = claims.getStringClaim("userId");
            String email = claims.getStringClaim("email");
            List<String> scopes = claims.getStringListClaim("scope");

            return exchange.getRequest().mutate()
                    .header("X-Account-ID", accountIdStr)
                    .header("X-User-ID", userIdStr)
                    .header("X-User-Email", email)
                    .header("X-User-Scopes", String.join(",", scopes))
                    .build();
        } catch (ParseException e) {
            return exchange.getRequest();
        }
    }

    public static class Config {}
}
