package com.matsuyoido.plugin.markdown.task;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.matsuyoido.LineEnd;
import com.vladsch.flexmark.ext.admonition.AdmonitionExtension;

/**
 * MarkdownHtmlBuilder
 */
public class MarkdownHtmlBuilder {
    public static final String REPLACE_MARKDOWN_PREFIX = "<md>";
    public static final String REPLACE_MARKDOWN_SUFFIX = "</md>";

    private MarkdownHtmlBuilder() {
    }

    public static MarkdownHtmlBuilder builder() {
        MarkdownHtmlBuilder builder = new MarkdownHtmlBuilder();
        builder.cssText.add(AdmonitionExtension.getDefaultCSS());
        builder.jsText.add(AdmonitionExtension.getDefaultScript());
        return builder;
    }

    private LineEnd line = LineEnd.PLATFORM;
    private String language;
    private String title;
    private List<String> cssLinks = new ArrayList<>();
    private List<String> jsLinks = new ArrayList<>();
    private List<String> cssText = new ArrayList<>();
    private List<String> jsText = new ArrayList<>();
    private String layoutPrefix;
    private String body;
    private String layoutSuffix;

    public MarkdownHtmlBuilder addCss(String link) {
        try {
            if (isLocalFile(link)) {
                this.cssText.add(Files.readAllLines(Paths.get(link))
                                      .stream()
                                      .collect(Collectors.joining(this.line.get())));
            } else {
                this.cssLinks.add(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    public MarkdownHtmlBuilder addCss(Collection<String> links) {
        if (links != null && !links.isEmpty()) {
            links.forEach(this::addCss);
        }
        return this;
    }

    public MarkdownHtmlBuilder addJs(String link) {
        try {
            if (isLocalFile(link)) {
                this.jsText.add(Files.readAllLines(Paths.get(link))
                                     .stream()
                                     .collect(Collectors.joining(this.line.get())));
            } else {
                this.jsLinks.add(link);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    public MarkdownHtmlBuilder addJs(Collection<String> links) {
        if (links != null && !links.isEmpty()) {
            links.forEach(this::addJs);
        }
        return this;
    }

    public MarkdownHtmlBuilder line(LineEnd lineEnd) {
        this.line = lineEnd;
        return this;
    }
    public MarkdownHtmlBuilder language(String language) {
        this.language = language;
        return this;
    }

    public MarkdownHtmlBuilder layout(String body) {
        if (body != null && !body.isEmpty()) {
            this.layoutPrefix = body.split(REPLACE_MARKDOWN_PREFIX)[0];
            this.layoutSuffix = body.split(REPLACE_MARKDOWN_SUFFIX)[1];
        }
        return this;
    }

    MarkdownHtmlBuilder replaceBody(String body) {
        this.body = body;
        return this;
    }
    MarkdownHtmlBuilder replaceTitle(String title) {
        this.title = title;
        return this;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("<!DOCTYPE html>").append(this.line.get());
        builder.append((this.language == null) ? "<html>" 
                    : String.format("<html lang=\"%s\">", this.language))
               .append(this.line.get());

        //#region head
        builder.append("<head>").append(this.line.get());
        builder.append("  <meta charset=\"UTF-8\">").append(this.line.get());
        builder.append("  <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">").append(this.line.get());
        builder.append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">").append(this.line.get());
        builder.append(String.format("  <title>%s</title>", this.title)).append(this.line.get());
        this.cssLinks.forEach(link -> 
                builder.append(String.format("  <link rel=\"stylesheet\" href=\"%s\">", link))
                       .append(this.line.get()));
        builder.append("</head>").append(this.line.get());
        //#endregion

        //#region body
        builder.append("<body>").append(this.line.get());
        this.cssText.forEach(text -> {
            builder.append("  <style>").append(this.line.get());
            builder.append(text).append(this.line.get());
            builder.append("  </style>").append(this.line.get());
        });

        if (this.layoutPrefix != null) {
            builder.append(this.layoutPrefix).append(this.line.get());
        }
        builder.append(this.body).append(this.line.get());
        if (this.layoutSuffix != null) {
            builder.append(this.layoutSuffix).append(this.line.get());
        }

        this.jsLinks.forEach(link ->
                builder.append(String.format("  <script type=\"application/javascript\" src=\"%s\"></script>", link))
                       .append(this.line.get()));
        this.jsText.forEach(text -> {
            builder.append("  <script>").append(this.line.get());
            builder.append(text).append(this.line.get());
            builder.append("  </script>").append(this.line.get());
        });
        builder.append("</body>").append(this.line.get());
        //#endregion

        builder.append("</html>");
        return builder.toString();
    }

    private boolean isLocalFile(String path) throws MalformedURLException {
        try {
            URL url = Paths.get(path).toUri().toURL();
            return url.toExternalForm().startsWith("file");
        } catch (InvalidPathException e) {
            return false;
        }
    }
}