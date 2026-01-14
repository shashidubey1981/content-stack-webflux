package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // important: Contentstack adds extra fields often
public class WebConfigResponse {

    @JsonProperty("entries")
    private List<Entry> entries;

    @JsonProperty("count")
    private Integer count;

    // Explicit getters for Maven compilation
    public List<Entry> getEntries() {
        return entries;
    }

    public Integer getCount() {
        return count;
    }

    

    // ============ ENTRY MODEL ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Entry {

        @JsonProperty("logo")
        private Asset logo;

        @JsonProperty("quick_links")
        private QuickLink quickLinks;

        @JsonProperty("consent_modal")
        private ConsentModal consentModal;

    }

    // ============ QUICK LINKS ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuickLink {
        public String title;
        public List<QuickLinkCategory> items;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuickLinkCategory {
        @JsonProperty("link_text")
        public String linkText;
        @JsonProperty("link")
        public String link;
    }

    // ============ CONSENT MODAL ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConsentModal {
        @JsonProperty("heading")
        private String heading;
        @JsonProperty("content")
        private String content;
        @JsonProperty("icon")
        private Asset icon;

        @JsonProperty("consent_actions")
        private List<ConsentAction> consentActions;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConsentAction {
        @JsonProperty("label")
        private String label;
        @JsonProperty("action")
        private String action;
    }

    
    // ============ ASSET ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Asset {

        
        @JsonProperty("content_type")
        public String contentType;

        @JsonProperty("file_size")
        public String fileSize;

        @JsonProperty("filename")
        public String filename;

        @JsonProperty("is_dir")
        public Boolean isDir;

        @JsonProperty("url")
        public String url;

        // optional: some assets include title/description
        @JsonProperty("title")
        public String title;
        @JsonProperty("description")
        public String description;

    }
}