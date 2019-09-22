package com.matsuyoido.plugin.markdown.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.Map;
import java.util.Objects;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.inject.Inject;

import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.data.MutableDataSet;

import org.gradle.api.DefaultTask;
import org.gradle.api.GradleException;
import org.gradle.api.tasks.TaskAction;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;

/**
 * MarkdownToHtml
 */
public class MarkdownToHtmlTask extends DefaultTask {

    protected String outputExtension;
    private final Path mdDir;
    private final Path outputDir;
    private final Parser parser;
    private final HtmlRenderer renderer;
    private final MarkdownHtmlBuilder builder;

    @Inject
    public MarkdownToHtmlTask(File mdDir, File outputDir, MarkdownParserOptionBuilder builder,
            MarkdownHtmlBuilder htmlBuilder) {
        this.outputExtension = ".html";
        this.mdDir = mdDir.toPath();
        this.outputDir = outputDir.toPath();
        MutableDataSet option = builder.build();
        this.parser = Parser.builder(option).build();
        this.renderer = HtmlRenderer.builder(option).build();
        this.builder = htmlBuilder;
    }

    @TaskAction
    public void taskAction(IncrementalTaskInputs inputs) {
        try {
            PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:[!_]*.md");
            Files.find(this.mdDir, Integer.MAX_VALUE, (filePath, fileAttr) -> {
                return fileAttr.isRegularFile() && matcher.matches(filePath.getFileName());
            }).forEach(this::execute);
        } catch (IOException e) {
            throw new GradleException("find file error.", e);
        }
    }

    private void execute(Path markdownPath) {
        try {
            Path outputPath = convetToOutputPath(mdDir, outputDir, markdownPath);
            StringBuilder markdownText = new StringBuilder();
            Files.readAllLines(markdownPath).forEach(markdownText::append);

            String fileName = getFileName(markdownPath);
            String htmlBody = renderHtml(markdownPath, markdownText.toString());

            output(outputPath, this.builder.replaceTitle(fileName).replaceBody(htmlBody).toString());
        } catch (IOException e) {
            throw new GradleException("execute error.", e);
        }
    }

    private Path convetToOutputPath(Path inputRootPath, Path outputRootPath, Path inputFilePath) {
        String relativePath = inputFilePath.toString().substring(inputRootPath.toString().length() + 1);
        String filePath = relativePath.substring(0, relativePath.lastIndexOf(".")) + this.outputExtension;
        return outputRootPath.resolve(filePath);
    }

    private String getFileName(Path path) {
        String fileName = path.getFileName().toString();
        int extensionIndex = fileName.lastIndexOf(".");
        return fileName.substring(0, extensionIndex);
    }

    private String renderHtml(Path markdownPath, String markdownText) {
        Document document = parser.parse(markdownText);
        if (document.contains(JekyllTagExtension.TAG_LIST)) {
            Map<String, String> includeHtmlMap = JekyllTagExtension.TAG_LIST.getFrom(document)
                    .stream()
                    .filter(tag -> {
                            String includeFile = tag.getParameters().toString();
                            return tag.getTag().matchChars("include") && !includeFile.isEmpty();
                    })
                    .map(tag -> {
                            String includeText = tag.getParameters().toString();
                            Path includePath = markdownPath.getParent().resolve(includeText);
                            if (includePath.toFile().exists()) {
                                try {
                                    String includeValue = Files.readAllLines(includePath).stream().collect(Collectors.joining(System.lineSeparator()));
                                    if (includeText.endsWith(".md")) {
                                        Document includeDocument = parser.parse(includeValue);
                                        parser.transferReferences(document, includeDocument);
                                        return new SimpleEntry<String, String>(includeText, renderer.render(includeDocument));
                                   } else {
                                        return new SimpleEntry<String, String>(includeText, includeValue);
                                   }
                                } catch (IOException e) {
                                }
                            }
                            return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toMap(Entry::getKey, Entry::getValue));
            if (!includeHtmlMap.isEmpty()) {
                document.set(JekyllTagExtension.INCLUDED_HTML, includeHtmlMap);
            }
        }
        String result = renderer.render(document);
        return result;
    }

    protected void output(Path outputPath, String content) throws IOException {
        File outputFile = outputPath.toFile();
        outputFile.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outputFile);
                OutputStreamWriter writer = new OutputStreamWriter(fos, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}