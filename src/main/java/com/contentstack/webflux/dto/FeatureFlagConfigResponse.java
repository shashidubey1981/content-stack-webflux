package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // important: Contentstack adds extra fields often
public class FeatureFlagConfigResponse {

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

        @JsonProperty("boolean_config")
        private List<BooleanConfig> boolean_config;

        @JsonProperty("numbers_config")
        private List<NumberConfig> numbers_config;

        @JsonProperty("strings_configs")
        private List<StringConfig> strings_configs;

        @JsonProperty("string_lists")
        private List<StringListConfig> string_lists;

        @JsonProperty("single_object")
        private List<SingleObjectConfig> single_object;

        @JsonProperty("json_list")
        private List<JsonListConfig> json_list;

    }

    // ===== boolean_config =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BooleanConfig {
        @JsonProperty("key")
        private String key;
        @JsonProperty("value")
        private Boolean value;
    }

    // ===== numbers_config (your "value" is coming as a string in sample) =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NumberConfig {
        @JsonProperty("key")
        private String key;
        @JsonProperty("value")
        private String value;
    }

    // ===== strings_configs =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StringConfig {
        private String key;
        private String value;
    }

    // ===== string_lists =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StringListConfig {
        private String key;
        private List<String> values;
    }

    // ===== single_object =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SingleObjectConfig {
        private String key;
        private List<CategoryItem> category;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CategoryItem {
        private String key;
        private String value;
    }

    // ===== json_list =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JsonListConfig {
        private String key;
        private List<JsonListItem> list_items;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class JsonListItem {
        private String key;
        private String value;
    }
    
}