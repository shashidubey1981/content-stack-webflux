package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

        @JsonProperty(value = "boolean_config", access = JsonProperty.Access.WRITE_ONLY)
        private List<BooleanConfig> boolean_config;

        @JsonProperty(value = "numbers_config", access = JsonProperty.Access.WRITE_ONLY)
        private List<NumberConfig> numbers_config;

        @JsonProperty(value = "strings_configs", access = JsonProperty.Access.WRITE_ONLY)
        private List<StringConfig> strings_configs;

        @JsonProperty(value = "string_lists", access = JsonProperty.Access.WRITE_ONLY)
        private List<StringListConfig> string_lists;

        @JsonProperty(value = "single_object", access = JsonProperty.Access.WRITE_ONLY)
        private List<SingleObjectConfig> single_object;

        @JsonProperty(value = "json_list", access = JsonProperty.Access.WRITE_ONLY)
        private List<JsonListConfig> json_list;

        @JsonIgnore
        private Map<String, Object> merged_config;

        // Explicit getters for config lists (for debugging and access)
        public List<BooleanConfig> getBoolean_config() {
            return boolean_config;
        }

        public List<NumberConfig> getNumbers_config() {
            return numbers_config;
        }

        public List<StringConfig> getStrings_configs() {
            return strings_configs;
        }

        public List<StringListConfig> getString_lists() {
            return string_lists;
        }

        /**
         * Merges all configuration lists (boolean_config, numbers_config, strings_configs, string_lists)
         * into a single JSON object (Map) with all keys and values.
         * 
         * @return Map containing all keys and values from the configuration lists
         */
        public Map<String, Object> getMergedConfig() {
            if (merged_config == null) {
                merged_config = new HashMap<>();
                
                // Iterate boolean_config
                if (boolean_config != null) {
                    for (BooleanConfig config : boolean_config) {
                        if (config != null && config.getKey() != null) {
                            merged_config.put(config.getKey(), config.getValue());
                        }
                    }
                }
                
                // Iterate numbers_config
                if (numbers_config != null) {
                    for (NumberConfig config : numbers_config) {
                        if (config != null && config.getKey() != null) {
                            merged_config.put(config.getKey(), config.getValue());
                        }
                    }
                }
                
                // Iterate strings_configs
                if (strings_configs != null) {
                    for (StringConfig config : strings_configs) {
                        if (config != null && config.getKey() != null) {
                            merged_config.put(config.getKey(), config.getValue());
                        }
                    }
                }
                
                // Iterate string_lists
                if (string_lists != null) {
                    for (StringListConfig config : string_lists) {
                        if (config != null && config.getKey() != null) {
                            merged_config.put(config.getKey(), config.getValues());
                        }
                    }
                }
            }
            return merged_config;
        }

        /**
         * Sets the merged_config field. This method can be used to manually set the merged config.
         */
        public void setMergedConfig(Map<String, Object> merged_config) {
            this.merged_config = merged_config;
        }

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

        // Explicit getters for Maven compilation
        public String getKey() {
            return key;
        }

        public Boolean getValue() {
            return value;
        }
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

        // Explicit getters for Maven compilation
        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    // ===== strings_configs =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StringConfig {
        private String key;
        private String value;

        // Explicit getters for Maven compilation
        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }
    }

    // ===== string_lists =====
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class StringListConfig {
        private String key;
        private List<String> values;

        // Explicit getters for Maven compilation
        public String getKey() {
            return key;
        }

        public List<String> getValues() {
            return values;
        }
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