package com.contentstack.webflux.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class EntryRequest {
    @NotBlank(message = "Content type UID is required")
    private String contentTypeUid;

    private String locale;
    private String variant;
    private List<String> include;
    private List<String> exclude;
    private Map<String, Object> query;
    private String url;
    private Integer skip;
    private Integer limit;

    // Explicit getters for Maven compilation
    public String getContentTypeUid() {
        return contentTypeUid;
    }

    public String getLocale() {
        return locale;
    }

    public String getVariant() {
        return variant;
    }

    public List<String> getInclude() {
        return include;
    }

    public List<String> getExclude() {
        return exclude;
    }

    public Map<String, Object> getQuery() {
        return query;
    }

    public String getUrl() {
        return url;
    }

    public Integer getSkip() {
        return skip;
    }

    public Integer getLimit() {
        return limit;
    }
}

