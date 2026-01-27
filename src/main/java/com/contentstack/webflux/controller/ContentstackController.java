package com.contentstack.webflux.controller;

import com.contentstack.webflux.dto.ContentstackPageResponse;
import com.contentstack.webflux.dto.PersonalizeConfigResponse;
import com.contentstack.webflux.dto.WebConfigResponse;
import com.contentstack.webflux.dto.NavigationResponse;
import com.contentstack.webflux.dto.FeatureFlagConfigResponse;
import com.contentstack.webflux.service.ContentstackClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Contentstack API", description = "API endpoints for fetching content from Contentstack CMS")
public class ContentstackController {

    private static final Logger log = LoggerFactory.getLogger(ContentstackController.class);
    private final ContentstackClientService contentstackClientService;

    public ContentstackController(ContentstackClientService contentstackClientService) {
        this.contentstackClientService = contentstackClientService;
    }

    @Operation(
            summary = "Get Web Configuration",
            description = "Fetches web configuration entries from Contentstack based on content type UID, locale, and variant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved web configuration",
                    content = @Content(schema = @Schema(implementation = WebConfigResponse.Entry.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/web-config")
    public Mono<ResponseEntity<WebConfigResponse.Entry>> getWebConfig(
            @Parameter(description = "Content type UID", required = true, example = "tbw_web_config")
            @RequestParam String contentTypeUid,
            @Parameter(description = "Locale code", required = false, example = "en")
            @RequestParam(required = false) String locale,
            @Parameter(description = "Variant name", required = false, example = "")
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

    // ===== Feature Flag Configuration =====
    @Operation(
            summary = "Get Feature Flag Configuration",
            description = "Fetches feature flag configuration entries from Contentstack based on content type UID, locale, and variant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved web configuration",
                    content = @Content(schema = @Schema(implementation = FeatureFlagConfigResponse.Entry.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/feature-flag-config")
    public Mono<ResponseEntity<FeatureFlagConfigResponse.Entry>> getFeatureFlagConfig(
            @Parameter(description = "Content type UID", required = true, example = "feature_flag")
            @RequestParam String contentTypeUid,
            @Parameter(description = "Locale code", required = false, example = "en")
            @RequestParam(required = false) String locale,
            @Parameter(description = "Variant name", required = false, example = "")
            @RequestParam(required = false) String variant) {

        log.info("Received request to fetch entries for content type: {}", contentTypeUid);

        return contentstackClientService
                .fetchFeatureFlagConfig(contentTypeUid, locale, variant)
                .map(ResponseEntity::ok)
                .onErrorResume(error -> {
                    log.error("Error processing entries request: {}", error.getMessage());
                    return Mono.just(ResponseEntity
                            .status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .build());
                });
    }

    @Operation(
            summary = "Get Navigation Configuration",
            description = "Fetches navigation configuration entries from Contentstack based on content type UID, locale, and variant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved navigation configuration",
                    content = @Content(schema = @Schema(implementation = NavigationResponse.Entry.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/navigation-config")
    public Mono<ResponseEntity<NavigationResponse.Entry>> getNavigationConfig(
            @Parameter(description = "Content type UID", required = true, example = "navigation_config")
            @RequestParam String contentTypeUid,
            @Parameter(description = "Locale code", required = false, example = "en")
            @RequestParam(required = false) String locale,
            @Parameter(description = "Variant name", required = false, example = "")
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

    @Operation(
            summary = "Get Personalized Configuration",
            description = "Fetches personalized configuration entries from Contentstack based on content type UID, locale, and variant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved personalized configuration",
                    content = @Content(schema = @Schema(implementation = PersonalizeConfigResponse.Entry.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/personalized-config")
    public Mono<ResponseEntity<PersonalizeConfigResponse.Entry>> getPersonalizedConfig(
            @Parameter(description = "Content type UID", required = true, example = "personalize_config")
            @RequestParam String contentTypeUid,
            @Parameter(description = "Locale code", required = false, example = "en")
            @RequestParam(required = false) String locale,
            @Parameter(description = "Variant name", required = false, example = "")
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


    @Operation(
            summary = "Get Entries",
            description = "Fetches entries from Contentstack based on content type UID, locale, and personalized variant"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved entries",
                    content = @Content(schema = @Schema(implementation = ContentstackPageResponse.Entry.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/entries")
    public Mono<ResponseEntity<ContentstackPageResponse.Entry>> getEntryByUrl(
            @Parameter(description = "Content type UID", required = true, example = "home_page")
            @RequestParam String contentTypeUid,
            @Parameter(description = "Locale code", required = true, example = "en")
            @RequestParam(required = true) String locale,
            @Parameter(description = "Personalized variant name", required = false, example = "")
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


    @Operation(
            summary = "Health Check",
            description = "Returns the health status of the Contentstack WebFlux API"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API is running")
    })
    @GetMapping("/health")
    public Mono<ResponseEntity<String>> health() {
        return Mono.just(ResponseEntity.ok("Contentstack WebFlux API is running"));
    }
}

