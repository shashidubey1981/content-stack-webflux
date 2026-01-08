package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // important: Contentstack adds extra fields often
public class PersonalizeConfigResponse {


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

    @JsonProperty("audiences")
    private Audiences audiences;

    @JsonProperty("taxonomy_path")
    private String taxonomyPath;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Audiences {
        @JsonProperty("group")
        private List<Group> group;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Group {
        @JsonProperty("name")
        private String name;
        @JsonProperty("attributes")
        private List<Attributes> attributes;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attributes {
        
        @JsonProperty("key")
        private String key;
        @JsonProperty("value")
        private String value;
    }

    
}