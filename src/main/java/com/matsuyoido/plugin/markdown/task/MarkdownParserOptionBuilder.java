package com.matsuyoido.plugin.markdown.task;

import java.util.ArrayList;
import java.util.List;

import com.matsuyoido.flexmark.HtmlLinkExtension;
// import com.vladsch.flexmark.docx.converter.DocxRenderer;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.ext.media.tags.MediaTagsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.MutableDataSetter;

/**
 * MarkdownParserOptionBuilder
 */
public class MarkdownParserOptionBuilder {

    private MarkdownParserOptionBuilder() {}
    public static MarkdownParserOptionBuilder builder() {
        return new MarkdownParserOptionBuilder();
    }

    private MutableDataSetter profile;

    //#region format profile
    public MarkdownParserOptionBuilder kramdown() {
        this.profile = ParserEmulationProfile.KRAMDOWN;
        return this;
    }
    public MarkdownParserOptionBuilder multiMarkdown() {
        this.profile = ParserEmulationProfile.MULTI_MARKDOWN;
        return this;
    }
    public MarkdownParserOptionBuilder markdown() {
        this.profile = ParserEmulationProfile.MARKDOWN;
        return this;
    }
    //#endregion


    MutableDataSet build() {
        MutableDataSet option = new MutableDataSet();
        List<Extension> extensions = getDefaultExtension();
        if (this.profile != null) {
            option.setFrom(profile);
        }
        option.set(Parser.EXTENSIONS, extensions);
        option.set(HtmlRenderer.INDENT_SIZE, 2);
        // option.set(DocxRenderer.SUPPRESS_HTML, true);
        return option;
    }


    private List<Extension> getDefaultExtension() {
        List<Extension> extensions = new ArrayList<>();
        // https://github.com/vsch/flexmark-java/wiki/Usage#include-markdown-and-html-file-content
        extensions.add(JekyllTagExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Extensions#tables
        extensions.add(TablesExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Extensions#admonition
        extensions.add(AdmonitionExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Extensions#anchorlink
        extensions.add(AnchorLinkExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Extensions#attributes
        extensions.add(AttributesExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Extensions#footnotes
        extensions.add(FootnoteExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Extensions#gfm-tasklist
        extensions.add(TaskListExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Extensions#gitlab-flavoured-markdown
        extensions.add(GitLabExtension.create());
        // https://github.com/vsch/flexmark-java/wiki/Macros-Extension
        extensions.add(MacrosExtension.create());

        extensions.add(AsideExtension.create());
        extensions.add(TypographicExtension.create());
        extensions.add(MediaTagsExtension.create());
        extensions.add(DefinitionExtension.create());
        extensions.add(TypographicExtension.create());
        extensions.add(TocExtension.create());
        extensions.add(new HtmlLinkExtension());

        return extensions;
    }

}