package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebConfigResponse {

    // ================= ROOT =================

    private Boolean scrolled;

    private Asset logo;

    @JsonProperty("quick_links")
    private QuickLinks quickLinks;

    @JsonProperty("main_navigation")
    private List<Navigation> mainNavigation;

    @JsonProperty("footer_navigation")
    private List<Footer> footerNavigation;

    // Header nav items
    private List<NavItems> items;

    // ================= NAVIGATION =================

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Navigation {
        private List<NavItems> items;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class NavItems {
        private String text;
        private List<InternalLink> link;

        @JsonProperty("mega_menu")
        private List<MegaMenu> megaMenu;
    }

    // ================= MEGA MENU =================

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MegaMenu {
        private List<MegaMenuSection> sections;

        @JsonProperty("cta_group")
        private List<CtaGroup> ctaGroup;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CtaGroup {
        @JsonProperty("call_to_action")
        private List<CallToAction> callToAction;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MegaMenuSection {

        private String title;
        private List<InternalLink> link;
        private List<SectionLink> links;

        /**
         * Handles dynamic CMS keys like:
         * color_pallet, type, grid, etc.
         */
        private Map<String, Object> dynamic = new HashMap<>();

        @JsonAnySetter
        public void setDynamic(String key, Object value) {
            dynamic.put(key, value);
        }
    }

    // ================= CTA & LINKS =================

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class CallToAction {
        private String text;
        private Asset icon;

        @JsonProperty("internal_link")
        private List<InternalLink> internalLink;

        @JsonProperty("external_link")
        private String externalLink;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class SectionLink {
        private Asset thumbnail;
        private String text;
        private List<InternalLink> link;

        @JsonProperty("link_text")
        private String linkText;

        private String url;
    }

    // ================= FOOTER =================

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Footer {
        private List<FooterSection> sections;

        @JsonProperty("copyright_info")
        private String copyrightInfo;

        @JsonProperty("built_by")
        private String builtBy;

        private Asset logo;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FooterSection {
        private String title;
        private String text;
        private List<InternalLink> link;

        @JsonProperty("external_link")
        private String externalLink;

        private List<FooterLink> links;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class FooterLink {
        private String title;
        private String text;
        private List<InternalLink> link;

        @JsonProperty("external_link")
        private String externalLink;
    }

    // ================= QUICK LINKS =================

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QuickLinks {
        private String id;
        private String title;
        private List<QuickLinkCategory> items;
        private List<String> slug;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class QuickLinkCategory {
        @JsonProperty("link_text")
        private String linkText;

        private String link;
    }

    // ================= SHARED =================

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class InternalLink {
        private String uid;
        private String url;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Asset {
        @JsonProperty("content_type")
        private String contentType;

        private Dimension dimension;

        @JsonProperty("file_size")
        private String fileSize;

        private String filename;

        @JsonProperty("is_dir")
        private Boolean isDir;

        private String url;

        @JsonProperty("_version")
        private Integer version;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Dimension {
        private Integer height;
        private Integer width;
    }
}