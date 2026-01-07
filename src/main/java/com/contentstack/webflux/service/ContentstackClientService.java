package com.contentstack.webflux.service;

import com.contentstack.webflux.config.ContentstackConfig;
import com.contentstack.webflux.dto.ContentstackEntryResponse;
import com.contentstack.webflux.dto.PersonalizeConfigResponse;
import com.contentstack.webflux.dto.WebConfigResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class ContentstackClientService {

    private static final Logger log = LoggerFactory.getLogger(ContentstackClientService.class);

    private final ContentstackConfig config;
    private final WebClient.Builder webClientBuilder;

    public ContentstackClientService(ContentstackConfig config, WebClient.Builder webClientBuilder) {
        this.config = config;
        this.webClientBuilder = webClientBuilder;
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

    public Mono<WebConfigResponse.WebConfig> fetchWebConfig(
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


        uriBuilder.queryParam("include[][]", ContentstackIncludes.QUICK_LINKS_REFERENCE_INCLUDES);
        uriBuilder.queryParam("include[][]", ContentstackIncludes.WEB_CONFIG_REFERENCE_INCLUDES);
        uriBuilder.queryParam("include[][]", ContentstackIncludes.WEB_CONFIG_JSON_RTE_PATHS);


        String uri = uriBuilder.buildAndExpand(contentTypeUid).toUriString();

        return getWebClient()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(WebConfigResponse.class)
                .flatMap(response -> {
                    if (response == null || response.getEntries() == null || response.getEntries().isEmpty()) {
                        log.warn("No entries found in response");
                        return Mono.error(new RuntimeException("No entries found in response"));
                    }

                    WebConfigResponse.WebConfig firstEntry = response.getEntries().get(0);
                    log.info("Found {} entries, using first entry", response.getEntries().size());
                    log.info("Successfully extracted web config entry");
                    return Mono.just(firstEntry);
                })
                .doOnSuccess(response -> log.info("Successfully fetched web config"))
                .doOnError(error -> log.error("Error fetching web config: {}", error.getMessage(), error));
    }


    public Mono<PersonalizeConfigResponse.PersonalizeConfig> fetchPersonalizedConfig(
            String contentTypeUid,
            String locale,
            String variant) {


        final String baseUrl = Objects.requireNonNullElse(
                (config.getApi() != null ? config.getApi().getBaseUrl() : null),
                "https://api.contentstack.io/v3");
        final String environment = Objects.requireNonNullElse(config.getEnvironment(), "production");
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromUriString(baseUrl)
                .path("/content_types/{contentTypeUid}/entries");

        // Add locale if provided
        if (locale != null && !locale.isEmpty()) {
            uriBuilder.queryParam("locale", locale);
        }


        String uri = uriBuilder.buildAndExpand(contentTypeUid).toUriString();

        return getWebClient()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(PersonalizeConfigResponse.class)
                .flatMap(response -> {
                    if (response == null || response.getEntries() == null || response.getEntries().isEmpty()) {
                        log.warn("No entries found in response");
                        return Mono.error(new RuntimeException("No entries found in response"));
                    }

                    PersonalizeConfigResponse.PersonalizeConfig firstEntry = response.getEntries().get(0);
                    log.info("Found {} entries, using first entry", response.getEntries().size());
                    log.info("Successfully extracted personalized config entry");
                    return Mono.just(firstEntry);
                })
                .doOnSuccess(response -> log.info("Successfully fetched personalized config"))
                .doOnError(error -> log.error("Error fetching personalized config: {}", error.getMessage(), error));
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

