package com.contentstack.webflux.dto;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;

import java.io.IOException;
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

        @JsonProperty("media")
        private List<Media> media;

        @JsonProperty("details")
        private List<PDPPageBlock> details;

        @JsonProperty("marketing")
        private List<PDPPageBlock> marketing;
    }
    

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Media {
        @JsonProperty("badge")
        private Boolean badge;
        @JsonProperty("identifier")
        private String identifier;
        @JsonProperty("name")
        private String name;
        @JsonProperty("image_url")
        private String imageUrl;
        @JsonProperty("alt")
        private String alt;
        @JsonProperty("link")
        private String link;
        @JsonProperty("description")
        private String description;
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

        @JsonProperty("quick_links")
        private QuickLinks quickLinks;

        @JsonProperty("image_preset")
        private Image imagePreset;


    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PDPPageBlock {
        
        @JsonProperty("brand_link")
        private DynamicComponent brandLink;

        @JsonProperty("product_title")
        private DynamicComponent productTitle;

        @JsonProperty("rating_summary")
        private DynamicComponent ratingSummary;

        @JsonProperty("current_price")
        private DynamicComponent currentPrice;

        @JsonProperty("promotion_link")
        private DynamicComponent promotionLink;

        @JsonProperty("color_variant")
        private DynamicComponent colorVariant;

        @JsonProperty("inline_bundle")
        private DynamicComponent inlineBundle;

        @JsonProperty("size_selection")
        private DynamicComponent sizeSelection;

        @JsonProperty("length_selection")
        private DynamicComponent lengthSelection;

        @JsonProperty("cross_sell_panel")
        private DynamicComponent crossSellPanel;

        @JsonProperty("purchase_action")
        private DynamicComponent purchaseAction;

        @JsonProperty("personalized_list")
        private DynamicComponent personalizedList;

        @JsonProperty("payment")
        private DynamicComponent payment;

        @JsonProperty("trust_assurance")
        private DynamicComponent trustAssurance;

        @JsonProperty("fulfillment_options")
        private DynamicComponent fulfillmentOptions;

        @JsonProperty("shipping_promo_banner")
        private ShippingPromoBannerComponent shippingPromoBanner;

        @JsonProperty("accordion_group")
        private AccordionGroupComponent accordionGroup;

        @JsonProperty("style_inspiration")
        private DynamicComponent styleInspiration;

        @JsonProperty("frequently_bought")
        private DynamicComponent frequentlyBought;

        @JsonProperty("shop_similar")
        private DynamicComponent shopSimilar;

        @JsonProperty("reviews")
        private DynamicComponent reviews;

        @JsonProperty("quick_links")
        private QuickLinks quickLinks;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class DynamicComponent {
        @JsonProperty("dynamic_component")
        private Boolean dynamic_component;

        @JsonProperty("label")
        private String label;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShippingPromoBannerComponent {
        @JsonProperty("dynamic_component")
        private Boolean dynamic_component;

        @JsonProperty("icon")
        private String icon;

        @JsonProperty("guest")
        private ShippingPromoBannerContent guest;

        @JsonProperty("signed")
        private ShippingPromoBannerContent signed;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ShippingPromoBannerContent {
        @JsonProperty("title")
        private RichText title;
        @JsonProperty("sub_title")
        private RichText subtitle;
        
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonDeserialize(using = RichTextDeserializer.class)
    public static class RichText {
        private JsonNode content;
    }

    /** Deserializes RichText from either a string (e.g. HTML) or an object. */
    public static class RichTextDeserializer extends JsonDeserializer<RichText> {
        @Override
        public RichText deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            JsonNode node = p.getCodec().readTree(p);
            RichText rt = new RichText();
            rt.setContent(node);
            return rt;
        }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccordionGroupComponent {
        @JsonProperty("dynamic_component")
        private Boolean dynamic_component;

        @JsonProperty("items")
        private List<AccordionItem> items;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AccordionItem {
        @JsonProperty("dynamic")
        private Boolean dynamic;
        @JsonProperty("title")
        private String title;
        @JsonProperty("content")
        private RichText content;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuickLinks {
        @JsonProperty("title")
        private String title;
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

        @JsonProperty("content")
        private String content;
        
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

        @JsonProperty("image")
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
