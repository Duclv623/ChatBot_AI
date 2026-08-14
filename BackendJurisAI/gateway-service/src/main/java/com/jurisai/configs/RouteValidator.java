package com.jurisai.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
public class RouteValidator {
    @Value("${gateway.security.public-paths:}")
    private List<String> publicPaths;
}
