package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // important: Contentstack adds extra fields often
public class NavigationResponse {

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

        @JsonProperty("header")
        private List<Navigation> header;

    
    }

    // ============ NAVIGATION ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Navigation {

        @JsonProperty("logo")
        private Asset logo;

        @JsonProperty("promotion_bar")
        private PromotionBar promotionBar;

        @JsonProperty("catalog_search_place_holder")
        private String catalogSearchPlaceholder;

        @JsonProperty("nav_items")
        private List<NavItems> items;
    }

    // ========= PROMOTION BAR =========

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PromotionBar {
        @JsonProperty("enabled")
        private Boolean enabled;
        @JsonProperty("text")
        private String text;
        @JsonProperty("link")
        private String link;
    }
    // ========= NAVIGATION ITEMS =========

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NavItems {
        
        @JsonProperty("text")
        public String text;

        @JsonProperty("mega_menu")
        private List<MegaMenu> megaMenu;
    }

    // ========= MEGA MENU =========
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MegaMenu {

        @JsonProperty("sections")
        private Section sections;

        @JsonProperty("sub_sections")
        private SubSection subSections;

        @JsonProperty("feature_cards")
        private List<FeatureCards> featureCards;
    }

    // ========= SECTION =========
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Section {

        @JsonProperty("links")
        private List<SectionLink> links;
    }

    // =========SUB SECTION =========
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubSection {

        @JsonProperty("links")
        private List<SubSectionLink> links;
    }

    // ========= LINK =========
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SectionLink {

        @JsonProperty("link")
        private String link;

        @JsonProperty("link_text")
        private String linkText;
    }

    // ========= SUB SECTION LINK =========
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SubSectionLink {

        @JsonProperty("link")
        private String link;

        @JsonProperty("link_text")
        private String linkText;
    }

    // ========= FEATURE CARDS =========
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FeatureCards {
        @JsonProperty("image")
        private Asset image;
        @JsonProperty("title")
        private String title;
        @JsonProperty("subtitle")
        private String subtitle;
        @JsonProperty("link")
        private String link;
    }


    // ============ FOOTER MENU (your sample footer_navigation items are content_type "menu") ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Menu {
        private String title;

        // Rich text JSON objects (NOT plain strings)
        @JsonProperty("built_by")
        private JsonNode builtBy;

        @JsonProperty("copyright_info")
        private JsonNode copyrightInfo;

        private List<FooterSection> sections;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FooterSection {
        private String title;

        // In your sample: "link": []
        private List<InternalLinkRef> link;

        // In your sample: "links": [ { text, link: [ref entry], external_link } ]
        private List<FooterLink> links;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FooterLink {
        private String text;
        private List<InternalLinkRef> link;

        @JsonProperty("external_link")
        private String externalLink;
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

    // ============ USER FORM MODAL ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserFormModal {
        
        private String title;

        private Asset icon;

        private String heading;

        @JsonProperty("display_button")
        private String displayButton;

        @JsonProperty("cookies_consent")
        private CookiesConsent cookiesConsent;

        // Form is a nested object with fields[] + richtext + submit
        private Form form;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CookiesConsent {
        private Asset icon;
        private String heading;
        private String message;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Form {
        private List<FormField> fields;

        @JsonProperty("user_consent_text")
        private JsonNode userConsentText; // Rich text JSON

        private Submit submit;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FormField {
        private String label;
        private String name;
        private String placeholder;
        private Boolean required;
        private String type;
        private String pattern;
        private String message;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Submit {
        @JsonProperty("submit_button_text")
        private String submitButtonText;

        @JsonProperty("submitting_button_text")
        private String submittingButtonText;

        @JsonProperty("submitted_heading")
        private String submittedHeading;

        @JsonProperty("submitted_message")
        private String submittedMessage;
    }

    // ============ INTERNAL LINK (REFERENCE OBJECTS) ============
    /**
     * In your JSON, "link" arrays contain full referenced entry objects
     * (with _content_type_uid, uid, url, title, etc.). So model it as a reference DTO.
     */
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InternalLinkRef {

        private String url;

        private String title;

        private String locale;
    }

    // ============ ASSET ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Asset {

        
        @JsonProperty("content_type")
        private String contentType;

        @JsonProperty("dimension")
        private Dimension dimension;

        @JsonProperty("file_size")
        private String fileSize;

        @JsonProperty("filename")
        private String filename;

        @JsonProperty("is_dir")
        private Boolean isDir;

        @JsonProperty("url")
        private String url;

    }
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dimension {
        private Integer height;
        private Integer width;
    }
}