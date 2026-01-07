package com.contentstack.webflux.service;

import com.contentstack.webflux.config.ContentstackConfig;
import com.contentstack.webflux.dto.ContentstackEntryResponse;
import com.contentstack.webflux.dto.WebConfigResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ContentstackClientService {

    private final ContentstackConfig config;
    private final WebClient.Builder webClientBuilder;
    private final ObjectMapper objectMapper;

    public ContentstackClientService(ContentstackConfig config, WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.config = config;
        this.webClientBuilder = webClientBuilder;
        this.objectMapper = objectMapper;
    }

    private WebClient getWebClient() {
        final String baseUrl = Objects.requireNonNullElse(
                (config.getApi() != null ? config.getApi().getBaseUrl() : null),
                "https://api.contentstack.io/v3");
        final String apiKey = Objects.requireNonNullElse(config.getApiKey(), "");
        final String deliveryToken = Objects.requireNonNullElse(config.getDeliveryToken(), "");

        return webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader("api_key", apiKey)
                .defaultHeader("access_token", deliveryToken)
                .build();
    }

    public Mono<WebConfigResponse> fetchWebConfig(
            String contentTypeUid,
            String locale,
            String variant) {

        log.info("Fetching entries for content type: {}, locale: {}, variant: {}",
                contentTypeUid, locale, variant);

        final String baseUrl = Objects.requireNonNullElse(
                (config.getApi() != null ? config.getApi().getBaseUrl() : null),
                "https://api.contentstack.io/v3");
        final String environment = Objects.requireNonNullElse(config.getEnvironment(), "production");
        log.info("Environment: {}", environment);
        log.info("Base URL: {}", baseUrl);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/content_types/{contentTypeUid}/entries");

        // Add locale if provided
        if (locale != null && !locale.isEmpty()) {
            uriBuilder.queryParam("locale", locale);
        }

        // Add variant if provided
        if (variant != null && !variant.isEmpty()) {
            uriBuilder.queryParam("variant", variant);
        }
        
        String uri = uriBuilder.buildAndExpand(contentTypeUid).toUriString();
        log.debug("Request URI: {}", uri);

        
        return getWebClient()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(WebConfigResponse.class)
                .flatMap(responseMap -> {
                    
                    
                    log.info("Found {} entries, using first entry", responseMap.getEntries().size());
                    
                    try {
                        // Convert the entry map directly to WebConfigResponse
                        // This preserves all dynamic fields from Contentstack
                        log.info("Successfully converted entry to WebConfigResponse");
                        return Mono.just(responseMap);
                    } catch (Exception e) {
                        log.error("Error converting entry to WebConfigResponse: {}", e.getMessage(), e);
                        return Mono.error(e);
                    }
                })
                .doOnSuccess(response -> log.info("Successfully fetched web config"))
                .doOnError(error -> log.error("Error fetching web config: {}", error.getMessage(), error));
    }

    /**
     * Fetch entries from Contentstack with support for variants and locale
     *
     * @param contentTypeUid Content type UID
     * @param locale         Locale code (e.g., "en-us")
     * @param variant        Variant name
     * @param include        Fields to include
     * @param exclude        Fields to exclude
     * @param query          Query parameters
     * @param skip           Skip count for pagination
     * @param limit          Limit count for pagination
     * @return Mono of ContentstackEntryResponse
     */
    public Mono<ContentstackEntryResponse> fetchEntries(
            String contentTypeUid,
            String locale,
            String variant,
            List<String> include,
            List<String> exclude,
            Map<String, Object> query,
            Integer skip,
            Integer limit) {

        log.info("Fetching entries for content type: {}, locale: {}, variant: {}",
                contentTypeUid, locale, variant);

        final String baseUrl = Objects.requireNonNullElse(
                (config.getApi() != null ? config.getApi().getBaseUrl() : null),
                "https://api.contentstack.io/v3");
        final String environment = Objects.requireNonNullElse(config.getEnvironment(), "production");
        log.info("Environment: {}", environment);
        log.info("Base URL: {}", baseUrl);
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/content_types/{contentTypeUid}/entries");

        // Add locale if provided
        if (locale != null && !locale.isEmpty()) {
            uriBuilder.queryParam("locale", locale);
        }

        // Add variant if provided
        if (variant != null && !variant.isEmpty()) {
            uriBuilder.queryParam("variant", variant);
        }

        // Add include fields
        if (include != null && !include.isEmpty()) {
            String[] includeArray = include.toArray(new String[0]);
            for (String field : includeArray) {
                uriBuilder.queryParam("include[]", field);
            }
        }

        // Add exclude fields
        if (exclude != null && !exclude.isEmpty()) {
            String[] excludeArray = exclude.toArray(new String[0]);
            for (String field : excludeArray) {
                uriBuilder.queryParam("exclude[]", field);
            }
        }
        // Add query parameters
        // if (query != null && !query.isEmpty()) {
        //     query.forEach((key, value) -> {
        //         if (value != null) {
        //             uriBuilder.queryParam(key, value);
        //         }
        //     });
        // }

        // // Add pagination
        // if (skip != null) {
        //     uriBuilder.queryParam("skip", skip);
        // }
        // if (limit != null) {
        //     uriBuilder.queryParam("limit", limit);
        // }

        String uri = uriBuilder.buildAndExpand(contentTypeUid).toUriString();
        log.debug("Request URI: {}", uri);

        return getWebClient()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ContentstackEntryResponse.class)
                .doOnSuccess(response -> log.info("Successfully fetched {} entries",
                        response.getCount() != null ? response.getCount() : 0))
                .doOnError(error -> log.error("Error fetching entries: {}", error.getMessage()));
    }

    /**
     * Fetch entry by URL from Contentstack
     *
     * @param url     URL of the entry
     * @param locale  Locale code (e.g., "en-us")
     * @param variant Variant name
     * @return Mono of ContentstackEntryResponse
     */
    public Mono<ContentstackEntryResponse> fetchEntryByUrl(
            String url,
            String locale,
            String variant) {

        log.info("Fetching entry by URL: {}, locale: {}, variant: {}", url, locale, variant);

        final String baseUrl = Objects.requireNonNullElse(
                (config.getApi() != null ? config.getApi().getBaseUrl() : null),
                "https://api.contentstack.io/v3");
        final String environment = Objects.requireNonNullElse(config.getEnvironment(), "production");

        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/content_types/entries")
                .queryParam("environment", environment)
                .queryParam("url", url);

        // Add locale if provided
        if (locale != null && !locale.isEmpty()) {
            uriBuilder.queryParam("locale", locale);
        }

        // Add variant if provided
        if (variant != null && !variant.isEmpty()) {
            uriBuilder.queryParam("variant", variant);
        }

        String uri = uriBuilder.build().toUriString();
        log.debug("Request URI: {}", uri);

        return getWebClient()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ContentstackEntryResponse.class)
                .doOnSuccess(response -> log.info("Successfully fetched entry by URL: {}", url))
                .doOnError(error -> log.error("Error fetching entry by URL: {}", error.getMessage()));
    }
}

