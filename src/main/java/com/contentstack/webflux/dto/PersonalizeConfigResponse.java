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

    // ============ TOP LEVEL WRAPPER ============
    private List<PersonalizeConfig> entries;

    

    // ============ ENTRY MODEL ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PersonalizeConfig {

    @JsonProperty("audiences")
    private Audiences audiences;

    @JsonProperty("taxonomy_path")
    private String taxonomyPath;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Audiences {
        private List<Group> group;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Group {
        private String name;
        private List<Attributes> attributes;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Attributes {
        private String key;
        private String value;
    }

    
}