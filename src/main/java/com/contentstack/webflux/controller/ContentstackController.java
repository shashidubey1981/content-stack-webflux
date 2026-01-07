package com.contentstack.webflux.controller;

import com.contentstack.webflux.dto.ContentstackEntryResponse;
import com.contentstack.webflux.dto.EntryRequest;
import com.contentstack.webflux.dto.WebConfigResponse;
import com.contentstack.webflux.service.ContentstackClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/contentstack")
@Slf4j
@CrossOrigin(origins = "*")
public class ContentstackController {

    private static final Logger log = LoggerFactory.getLogger(ContentstackController.class);
    private final ContentstackClientService contentstackClientService;

    public ContentstackController(ContentstackClientService contentstackClientService) {
        this.contentstackClientService = contentstackClientService;
    }

    /**
     * Fetch entries endpoint
     * GET /api/contentstack/entries?contentTypeUid={uid}&locale={locale}&variant={variant}
     */
    @GetMapping("/web-config")
    public Mono<ResponseEntity<WebConfigResponse>> getEntries(
            @RequestParam String contentTypeUid,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String variant) {

        log.info("Received request to fetch entries for content type: {}", contentTypeUid);

        return contentstackClientService
                .fetchWebConfig(contentTypeUid, locale, variant)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Error processing entries request: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
                });
    }

    /**
     * Fetch entries endpoint using POST with request body
     * POST /api/contentstack/entries
     */
    @PostMapping("/entries")
    public Mono<ResponseEntity<ContentstackEntryResponse>> getEntriesPost(
            @Valid @RequestBody EntryRequest request) {

        log.info("Received POST request to fetch entries for content type: {}", 
                request.getContentTypeUid());

        return contentstackClientService
                .fetchEntries(
                        request.getContentTypeUid(),
                        request.getLocale(),
                        request.getVariant(),
                        request.getInclude(),
                        request.getExclude(),
                        request.getQuery(),
                        request.getSkip(),
                        request.getLimit())
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Error processing entries request: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
                });
    }

    /**
     * Fetch entry by URL endpoint
     * GET /api/contentstack/entries/url?url={url}&locale={locale}&variant={variant}
     */
    @GetMapping("/entries/url")
    public Mono<ResponseEntity<ContentstackEntryResponse>> getEntryByUrl(
            @RequestParam String url,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String variant) {

        log.info("Received request to fetch entry by URL: {}", url);

        return contentstackClientService
                .fetchEntryByUrl(url, locale, variant)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Error processing entry by URL request: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
                });
    }

    /**
     * Fetch entry by URL endpoint using POST
     * POST /api/contentstack/entries/url
     */
    @PostMapping("/entries/url")
    public Mono<ResponseEntity<ContentstackEntryResponse>> getEntryByUrlPost(
            @Valid @RequestBody EntryRequest request) {

        if (request.getUrl() == null || request.getUrl().isEmpty()) {
            return Mono.just(ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build());
        }

        log.info("Received POST request to fetch entry by URL: {}", request.getUrl());

        return contentstackClientService
                .fetchEntryByUrl(request.getUrl(), request.getLocale(), request.getVariant())
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Error processing entry by URL request: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
                });
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public Mono<ResponseEntity<String>> health() {
        return Mono.just(ResponseEntity.ok("Contentstack WebFlux API is running"));
    }
}

