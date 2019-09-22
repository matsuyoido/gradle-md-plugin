package com.matsuyoido.plugin.markdown;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.matsuyoido.LineEnd;
import com.matsuyoido.plugin.markdown.task.MarkdownHtmlBuilder;

import org.gradle.api.GradleException;

/**
 * HtmlExtension
 */
public class HtmlExtension {

    private Set<String> css;
    private Set<String> js;
    private String body;
    private LineEnd lineEnding;

    //#region setter
    public void setCss(String... cssLinks) {
        this.css = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(cssLinks)));
    }

    public void setJs(String... jsLinks) {
        this.js = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(jsLinks)));
    }

    public void setBody(String bodyText) {
        if ( !(bodyText.contains(MarkdownHtmlBuilder.REPLACE_MARKDOWN_PREFIX) && bodyText.contains(MarkdownHtmlBuilder.REPLACE_MARKDOWN_SUFFIX)) ) {
            throw new GradleException("body requires <md></md> text.");
        }
        this.body = bodyText;
    }
    public void setLineEnding(String value) {
        if (value == null) {
            this.lineEnding = LineEnd.PLATFORM;
        } else {
            switch (value.toLowerCase()) {
                case "windows":
                    this.lineEnding = LineEnd.WINDOWS;
                    break;
                case "linux":
                    this.lineEnding = LineEnd.LINUX;
                    break;
                default:
                    this.lineEnding = LineEnd.PLATFORM;
                    break;
            }
        }
    }
    //#endregion


    public Set<String> getCssLinks() {
        return this.css;
    }
    public Set<String> getJsLinks() {
        return this.js;
    }
    public String getBody() {
        return this.body;
    }
    public LineEnd getLineEnding() {
        return (this.lineEnding == null) ? LineEnd.PLATFORM : this.lineEnding;
    }

}