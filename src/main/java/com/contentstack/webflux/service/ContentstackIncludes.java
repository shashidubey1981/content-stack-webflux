package com.contentstack.webflux.service;

import java.util.List;

/**
 * Contentstack Reference & RTE include paths
 * Used by CDA REST and GraphQL queries
 */
public final class ContentstackIncludes {

    private ContentstackIncludes() {}

    // ================= DYNAMIC CONTENT =================
    public static final List<String> DYNAMIC_COMPONENT_REFERENCE_INCLUDES = List.of(
        "dynamic_component"
        
);
    
    // ================= HERO =================
    public static final List<String> HERO_REFERENCE_INCLUDES = List.of(
            "hero",
            "hero.cta.link"
    );

    // ================= TEASER =================
    public static final List<String> TEASER_REFERENCE_INCLUDES = List.of(
            "components.teaser.cta.link"
    );

    // ================= TEXT & IMAGE =================
    public static final List<String> TEXT_AND_IMAGE_REFERENCE_INCLUDES = List.of(
            "components.text_and_image_carousel.carousel_items.cta.link"
    );

    // ================= IMAGE CARDS =================
    public static final List<String> IMAGE_CARDS_REFERENCE_INCLUDES = List.of(
            "components.card_collection.cards.cta.link"
    );

    // ================= FEATURED ARTICLES =================
    public static final List<String> FEATURED_ARTICLES_REFERENCE_INCLUDES = List.of(
            "featured_articles.articles"
    );

    // ================= QUICK LINKS =================
    public static final List<String> QUICK_LINKS_REFERENCE_INCLUDES = List.of(
            "quick_links",
            "quick_links.items",
            "quick_links.items.link"
    );
    // ================= NAVIGATION =================
    public static final List<String> NAVIGATION_REFERENCE_INCLUDES = List.of(
            "main_navigation",
            "main_navigation.items",
            "main_navigation.items.link",
            "main_navigation.items.mega_menu",
            "main_navigation.items.mega_menu.sections",
            "main_navigation.items.mega_menu.sections.link",
            "main_navigation.items.mega_menu.sections.links",
            "main_navigation.items.mega_menu.sections.links.link"
    );

    // ================= FOOTER =================
    public static final List<String> FOOTER_REFERENCE_INCLUDES = List.of(
            "footer_navigation",
            "footer_navigation.sections",
            "footer_navigation.sections.link",
            "footer_navigation.sections.links",
            "footer_navigation.sections.links.link"
    );

    // ================= USER FORM =================
    public static final List<String> USER_FORM_REFERENCE_INCLUDES = List.of(
            "user_form"
    );

    // ================= JSON RTE PATHS =================

    public static final List<String> TEXT_JSON_RTE_PATHS = List.of(
            "components.text.content"
    );

    public static final List<String> FOOTER_JSON_RTE_PATHS = List.of(
            "footer_navigation.copyright_info",
            "footer_navigation.built_by"
    );

    public static final List<String> ARTICLE_JSON_RTE_PATHS = List.of(
            "content"
    );

    public static final List<String> USER_FORM_JSON_RTE_PATHS = List.of(
            "user_form.form.user_consent_text"
    );

    // ================= COMPOSITE =================

    /**
     * Typical WebConfig includes (Header + Footer + Forms)
     */
    public static final List<String> WEB_CONFIG_REFERENCE_INCLUDES =
            concat(
                    NAVIGATION_REFERENCE_INCLUDES,
                    FOOTER_REFERENCE_INCLUDES,
                    USER_FORM_REFERENCE_INCLUDES
            );

    public static final List<String> WEB_CONFIG_JSON_RTE_PATHS =
            concat(
                    FOOTER_JSON_RTE_PATHS,
                    USER_FORM_JSON_RTE_PATHS
            );

    public static final List<String> WEB_LANDING_PAGE_REFERENCE_INCLUDES =
            concat(
                    DYNAMIC_COMPONENT_REFERENCE_INCLUDES,
                    HERO_REFERENCE_INCLUDES,
                    TEASER_REFERENCE_INCLUDES,
                    TEXT_AND_IMAGE_REFERENCE_INCLUDES,
                    IMAGE_CARDS_REFERENCE_INCLUDES,
                    FEATURED_ARTICLES_REFERENCE_INCLUDES,
                    QUICK_LINKS_REFERENCE_INCLUDES,
                    TEXT_JSON_RTE_PATHS
            );        

    // ================= UTIL =================
    @SafeVarargs
    private static List<String> concat(List<String>... lists) {
        return java.util.Arrays.stream(lists)
                .flatMap(List::stream)
                .distinct()
                .toList();
    }
}