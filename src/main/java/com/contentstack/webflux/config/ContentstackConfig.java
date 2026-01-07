package com.contentstack.webflux.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "contentstack")
@Data
public class ContentstackConfig {
    private String apiKey;
    private String deliveryToken;
    private String environment;
    private String region;
    private Api api = new Api();

    public Api getApi() {
        return api;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getDeliveryToken() {
        return deliveryToken;
    }

    public String getEnvironment() {
        return environment;
    }

    @Data
    public static class Api {
        private String baseUrl = "https://api.contentstack.io/v3";
        
        public String getBaseUrl() {
            return baseUrl;
        }
    }
}

