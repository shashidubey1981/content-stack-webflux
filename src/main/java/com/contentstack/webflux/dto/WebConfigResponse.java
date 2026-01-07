package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true) // important: Contentstack adds extra fields often
public class WebConfigResponse {

    // ============ TOP LEVEL WRAPPER ============
    private List<WebConfig> entries;

    

    // ============ ENTRY MODEL ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WebConfig {

        private String uid;
        private String locale;

        
        private String title;

        private Asset logo;

        @JsonProperty("main_navigation")
        private List<Navigation> mainNavigation;

        @JsonProperty("footer_navigation")
        private List<Menu> footerNavigation;

        // New fields present in your JSON
        @JsonProperty("consent_modal")
        private ConsentModal consentModal;

        @JsonProperty("user_form")
        private List<UserFormModal> userForm;

    }

    // ============ NAVIGATION ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Navigation {
        public String uid;
        public String locale;
        public String title;
        public List<NavItems> items;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NavItems {
        public String text;

        // In your sample: "link": [] OR array of referenced entries
        public List<InternalLinkRef> link;

        @JsonProperty("mega_menu")
        public List<MegaMenu> megaMenu;
    }

    // ============ MEGA MENU ============
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MegaMenu {
    
        private List<MegaMenuSection> sections;
        @JsonProperty("cta_group")
        private List<CtaGroup> ctaGroup;
        private String title;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CtaGroup {
        @JsonProperty("call_to_action")
        private List<CallToAction> callToAction;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MegaMenuSection {
        private String title;

        // In sample: link is [] (but keep it)
        private List<InternalLinkRef> link;

        // In sample: links is array of objects with text, thumbnail, link, link_text...
        private List<SectionLink> links;

        // Dynamic keys (future-proofing)
        private Map<String, Object> dynamic = new HashMap<>();

        @JsonAnySetter
        public void setDynamic(String key, Object value) {
            // Avoid overriding known fields
            if (!"title".equals(key) && !"link".equals(key) && !"links".equals(key)) {
                dynamic.put(key, value);
            }
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CallToAction {
        private Asset icon;
        private String text;

        @JsonProperty("internal_link")
        private List<InternalLinkRef> internalLink;

        @JsonProperty("external_link")
        private String externalLink;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SectionLink {
        private String text;
        private Asset thumbnail;

        // NOTE: in sample link[] contains referenced entry objects with url/title etc
        private List<InternalLinkRef> link;

        @JsonProperty("link_text")
        private String linkText;

        private String url;

        @JsonProperty("external_link")
        private String externalLink;
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
        private String heading;
        private String content;
        private Asset icon;

        @JsonProperty("consent_actions")
        private List<ConsentAction> consentActions;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ConsentAction {
        private String label;
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
        public String contentType;

        @JsonProperty("file_size")
        public String fileSize;

        public String filename;

        @JsonProperty("is_dir")
        public Boolean isDir;

        public String url;

        // optional: some assets include title/description
        public String title;
        public String description;

    }
}