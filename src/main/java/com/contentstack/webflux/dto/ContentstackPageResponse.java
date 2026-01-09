package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import java.util.*;

/**
 * Models:
 *  - Homepage: { entry: PageEntry & pageRenderProps }
 *  - LandingPage: { entry: PageEntry & pageRenderProps }
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentstackPageResponse {

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

    // ======================= ENTRY (PageEntry + pageRenderProps) =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Entry {

        // -------- PageEntry --------
        @JsonProperty("url")
        private String url;
        @JsonProperty("taxonomies")
        private List<Taxonomy> taxonomies;

        // -------- pageRenderProps --------
        @JsonProperty("components")
        private List<PageBlock> components;
        @JsonProperty("hero")
        private List<Hero> hero;
        @JsonProperty("seo")
        private SeoProps seo;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PageBlock {
        @JsonProperty("api_component")
        private APIComponent apiComponent;

        @JsonProperty("teaser")
        private Teaser teaser;

        @JsonProperty("text_and_image_carousel")
        private TextAndImageCarousel textAndImageCarousel;

        @JsonProperty("text")
        private Text text;

        @JsonProperty("card_collection")
        private CardCollection cardCollection;

        @JsonProperty("pgp_collection")
        private PGPCardCollection pgpCollection;

        @JsonProperty("image_preset")
        private Image imagePreset;

    }

    // ======================= PGP COLLECTION =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PGPCardCollection {
        @JsonProperty("header")
        private CardCollectionHeader header;
        @JsonProperty("cards")
        private List<PGPImageCardItem> cards;
        
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PGPImageCardItem {
        @JsonProperty("contnet")
        private String content;
        @JsonProperty("cta")
        private PGPCTA cta;
        @JsonProperty("image")
        private Asset image;
        @JsonProperty("image_alt_text")
        private String imageAltText;
        @JsonProperty("is_thumbnail")
        private Boolean isThumbnail;
        @JsonProperty("title")
        private String title;
        @JsonProperty("subtitle")
        private String subtitle;
    }

    // ======================= CARD COLLECTION =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardCollection {
        @JsonProperty("header")
        private CardCollectionHeader header;

        // body fields
        private String id;
        @JsonProperty("cards")
        private List<ImageCardItem> cards;
        @JsonProperty("count")
        private Integer count;
        @JsonProperty("edit_key")
        private String editKey;
        @JsonProperty("class_name")
        private String className;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardCollectionHeader {
        private String id;
        @JsonProperty("heading")
        private String heading;
        @JsonProperty("content")
        private String content;
        @JsonProperty("sub_heading")
        private String subHeading;
        @JsonProperty("class_name")
        private String className;
        @JsonProperty("cta")
        private CTA cta;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageCardItem {
        private Object id;     // string|number
        private Object key;    // string|number
        private Integer count;
        private Object index;  // any

        // Image
        private Asset image;
        @JsonProperty("cover_image")
        private Asset coverImage;
        @JsonProperty("image_alt_text")
        private String imageAltText;
        @JsonProperty("image_position")
        private String imagePosition;
        @JsonProperty("is_thumbnail")
        private Boolean isThumbnail;
        @JsonProperty("alt")
        private String alt;

        // Text
        @JsonProperty("title")
        private String title;
        @JsonProperty("subtitle")
        private String subtitle;
        @JsonProperty("cta")
        private CTA cta;
        @JsonProperty("url")
        private String url;
        @JsonProperty("summary")
        private String summary;
    }

    // ======================= API COMPONENT =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class APIComponent {
        private String id;

        @JsonProperty("component_name")
        private String componentName;
    }

    // ======================= TEASER / HERO =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Teaser {
        private String id;
        @JsonProperty("heading")
        private String heading;
        @JsonProperty("content")
        private String content;
        @JsonProperty("cta")
        private List<CTA> cta;
        @JsonProperty("image")
        private List<Image> image;
        @JsonProperty("video")
        private Video video;

        @JsonProperty("isABEnabled")
        private Boolean isABEnabled;

        @JsonProperty("styles")
        private Styles styles;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Hero {
        private String id;
        
        @JsonProperty("heading")
        private String heading;
        
        @JsonProperty("cta")
        private List<CTA> cta;
        
        @JsonProperty("image")
        private List<Image> image;
        
        @JsonProperty("video")
        private Video video;
        
        @JsonProperty("styles")
        private Styles styles;

        @JsonProperty("summary")
        private String summary;
        
        @JsonProperty("title")
        private String title;
        
        @JsonProperty("url")
        private String url;

        @JsonProperty("cover_image")
        private Asset coverImage;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Styles {
        @JsonProperty("text_align")
        private String textAlign;

        // For carousel items / theme usage
        private String theme;

        @JsonProperty("image_position")
        private String imagePosition;
    }

    // ======================= TEXT + CAROUSEL =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TextAndImageCarousel {
        private String id;

        @JsonProperty("carousel_items")
        private List<TextAndImage> carouselItems;

        @JsonProperty("styles")
        private Styles styles;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TextAndImage {
        private String id;
        
        @JsonProperty("heading")
        private String heading;
        
        // from Image
        @JsonProperty("image")
        private Asset image;

        // item extras
        @JsonProperty("cta")
        private List<CTA> cta;
        
        @JsonProperty("styles")
        private Styles styles;

        // icon uses Image['image'] in TS
        @JsonProperty("icon")
        private Asset icon;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Text {
        private String id;

        /**
         * In Contentstack, Text often comes as JSON RTE doc.
         * If your API converts it to HTML/string, change this to String.
         */
        private JsonNode content;
    }

    // ======================= LINKS / CTA =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CTA {
        
        @JsonProperty("text")
        private String text;
        @JsonProperty("external_url")
        private String externalUrl;
        @JsonProperty("link")
        private List<InternalLink> link;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PGPCTA {
        @JsonProperty("text")
        private String text;
        @JsonProperty("link")
        private String link;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InternalLink {
        private String uid;

        @JsonProperty("_content_type_uid")
        private String contentTypeUid;

        private String url;
    }

    // ======================= IMAGE / VIDEO / ASSET =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        private Object id; // string|number

        private Asset image;

        @JsonProperty("cover_image")
        private Asset coverImage;

        @JsonProperty("image_alt_text")
        private String imageAltText;

        private String image_position;
        private Boolean is_thumbnail;
        private String alt;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Video {
        private Object id; // string|number
        private Asset video;

        @JsonProperty("video_alt_text")
        private String videoAltText;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
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
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Dimension {
        private Integer height;
        private Integer width;
    }

    // ======================= SEO =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SeoProps {
        @JsonProperty("title")
        private String title;
        @JsonProperty("description")
        private String description;

        @JsonProperty("canonical_url")
        private String canonicalUrl;

        @JsonProperty("no_follow")
        private Boolean noFollow;

        @JsonProperty("no_index")
        private Boolean noIndex;
    }

    // ======================= LOCALES =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class LocaleItem {
        private String code;

        @JsonProperty("fallback_locale")
        private String fallbackLocale;

        private String name;
    }

    // ======================= TAXONOMY =======================
    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Taxonomy {
        @JsonProperty("taxonomy_uid")
        private String taxonomyUid;

        @JsonProperty("term_uid")
        private String termUid;
    }
}
