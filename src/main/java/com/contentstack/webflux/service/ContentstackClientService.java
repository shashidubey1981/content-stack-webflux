package com.contentstack.webflux.service;

import com.contentstack.webflux.config.ContentstackConfig;
import com.contentstack.webflux.dto.ContentstackPageResponse;
import com.contentstack.webflux.dto.PersonalizeConfigResponse;
import com.contentstack.webflux.dto.WebConfigResponse;
import com.contentstack.webflux.dto.NavigationResponse;
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

    public Mono<WebConfigResponse.Entry> fetchWebConfig(
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


        uriBuilder.queryParam("include[][]", ContentstackIncludes.WEB_CONFIG_REFERENCE_INCLUDES);


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

                    WebConfigResponse.Entry firstEntry = response.getEntries().get(0);
                    log.info("Found {} entries, using first entry", response.getEntries().size());
                    log.info("Successfully extracted web config entry");
                    return Mono.just(firstEntry);
                })
                .doOnSuccess(response -> log.info("Successfully fetched web config"))
                .doOnError(error -> log.error("Error fetching web config: {}", error.getMessage(), error));
    }

    public Mono<NavigationResponse.Entry> fetchNavigationConfig(
            String contentTypeUid,
            String locale,
            String variant) {

        log.info("Fetching navigation config for content type: {}, locale: {}, variant: {}",
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


        uriBuilder.queryParam("include[][]", ContentstackIncludes.WEB_CONFIG_REFERENCE_INCLUDES);
        uriBuilder.queryParam("include[][]", ContentstackIncludes.WEB_CONFIG_JSON_RTE_PATHS);


        String uri = uriBuilder.buildAndExpand(contentTypeUid).toUriString();

        return getWebClient()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(NavigationResponse.class)
                .flatMap(response -> {
                    if (response == null || response.getEntries() == null || response.getEntries().isEmpty()) {
                        log.warn("No entries found in response");
                        return Mono.error(new RuntimeException("No entries found in response"));
                    }

                    NavigationResponse.Entry firstEntry = response.getEntries().get(0);
                    log.info("Found {} entries, using first entry", response.getEntries().size());
                    log.info("Successfully extracted navigation config entry");
                    return Mono.just(firstEntry);
                })
                .doOnSuccess(response -> log.info("Successfully fetched web config"))
                .doOnError(error -> log.error("Error fetching web config: {}", error.getMessage(), error));
    }


    public Mono<PersonalizeConfigResponse.Entry> fetchPersonalizedConfig(
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

                    PersonalizeConfigResponse.Entry firstEntry = response.getEntries().get(0);
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
     */
    public Mono<ContentstackPageResponse.Entry> fetchEntries(
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

        // Add include fields
        uriBuilder.queryParam("include[][]", ContentstackIncludes.WEB_LANDING_PAGE_REFERENCE_INCLUDES);

        String uri = uriBuilder.buildAndExpand(contentTypeUid).toUriString();
        log.debug("Request URI: {}", uri);

        return getWebClient()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(ContentstackPageResponse.class)
                .flatMap(response -> {
                    if (response == null || response.getEntries() == null || response.getEntries().isEmpty()) {
                        log.warn("No entries found in response");
                        return Mono.error(new RuntimeException("No entries found in response"));
                    }

                    ContentstackPageResponse.Entry firstEntry = response.getEntries().get(0);
                    log.info("Found {} entries, using first entry", response.getEntries().size());
                    log.info("Successfully extracted page entry");
                    return Mono.just(firstEntry);
                })
                .doOnSuccess(response -> log.info("Successfully fetched entries for content type: {}", contentTypeUid))
                .doOnError(error -> log.error("Error fetching entries for content type: {}: {}", contentTypeUid, error.getMessage(), error));
    }
}