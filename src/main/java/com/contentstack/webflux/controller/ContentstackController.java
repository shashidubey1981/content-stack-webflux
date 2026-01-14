package com.contentstack.webflux.controller;

import com.contentstack.webflux.dto.ContentstackPageResponse;
import com.contentstack.webflux.dto.PersonalizeConfigResponse;
import com.contentstack.webflux.dto.WebConfigResponse;
import com.contentstack.webflux.dto.NavigationResponse;
import com.contentstack.webflux.service.ContentstackClientService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


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

    @GetMapping("/web-config")
    public Mono<ResponseEntity<WebConfigResponse.Entry>> getWebConfig(
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

    @GetMapping("/navigation-config")
    public Mono<ResponseEntity<NavigationResponse.Entry>> getNavigationConfig(
            @RequestParam String contentTypeUid,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String variant) {

        log.info("Received request to fetch navigation config for content type: {}", contentTypeUid);

        return contentstackClientService
                .fetchNavigationConfig(contentTypeUid, locale, variant)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Error processing navigation config request: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
                });
    }

    @GetMapping("/personalized-config")
    public Mono<ResponseEntity<PersonalizeConfigResponse.Entry>> getPersonalizedConfig(
            @RequestParam String contentTypeUid,
            @RequestParam(required = false) String locale,
            @RequestParam(required = false) String variant) {

        log.info("Received request to fetch entries for content type: {}", contentTypeUid);

        return contentstackClientService
                .fetchPersonalizedConfig(contentTypeUid, locale, variant)
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
    @GetMapping("/entries")
    public Mono<ResponseEntity<ContentstackPageResponse.Entry>> getEntryByUrl(
        @RequestParam String contentTypeUid,
        @RequestParam(required = true) String locale,
        @RequestParam(required = false) String personalizedVariant) {

        log.info("Received request to fetch entries for content type: {}", contentTypeUid);

        return contentstackClientService
                .fetchEntries(contentTypeUid, locale, personalizedVariant)
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

