package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentstackEntryResponse {
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

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Entry {
        @JsonProperty("uid")
        private String uid;

        @JsonProperty("_content_type_uid")
        private String contentTypeUid;

        @JsonProperty("title")
        private String title;

        @JsonProperty("url")
        private String url;

        @JsonProperty("locale")
        private String locale;

        @JsonProperty("publish_details")
        private List<PublishDetail> publishDetails;

        @JsonProperty("created_at")
        private String createdAt;

        @JsonProperty("updated_at")
        private String updatedAt;

        @JsonProperty("created_by")
        private String createdBy;

        @JsonProperty("updated_by")
        private String updatedBy;

        @JsonProperty("_version")
        private Integer version;

        @JsonProperty("_in_progress")
        private Boolean inProgress;

        @JsonProperty("tags")
        private List<String> tags;

        @JsonProperty("ACL")
        private Map<String, Object> acl;

        @JsonProperty("_metadata")
        private Map<String, Object> metadata;

        // Dynamic fields from content type
        private Map<String, Object> fields;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PublishDetail {
        @JsonProperty("environment")
        private String environment;

        @JsonProperty("locale")
        private String locale;

        @JsonProperty("time")
        private String time;

        @JsonProperty("user")
        private String user;
    }
}

