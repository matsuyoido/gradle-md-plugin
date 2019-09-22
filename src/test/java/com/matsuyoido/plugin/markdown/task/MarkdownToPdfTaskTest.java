package com.matsuyoido.plugin.markdown.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * MarkdownToPdfTaskTest
 */
public class MarkdownToPdfTaskTest {

    @TempDir
    Path dir;
    MarkdownHtmlBuilder html;

    private Path inputDir;
    private Path outputDir;
    private MarkdownToPdfTask task;

    @BeforeEach
    public void setup() {
        Project project = ProjectBuilder.builder().build();
        MarkdownParserOptionBuilder option = MarkdownParserOptionBuilder.builder();
        this.html = MarkdownHtmlBuilder.builder();

        this.inputDir = dir.resolve("inputDir");
        this.outputDir = dir.resolve("outputDir");
        File input = inputDir.toFile();
        File output = outputDir.toFile();
        input.mkdirs();
        output.mkdirs();
        task = project.getTasks().create("markdownToPdfTaskTest", MarkdownToPdfTask.class,
                input, output, option, html);
    }

    @Test
    public void oneFile_noCssAndJs() throws IOException {
        Files.write(inputDir.resolve("simple.md"), "text".getBytes());

        task.taskAction(new MockIncrementalTaskInputs());

        assertThat(outputDir.toFile().list()).containsOnly("simple.pdf");
        try (PDDocument document = PDDocument.load(outputDir.resolve("simple.pdf").toFile())) {
            String actual = new PDFTextStripper().getText(document);
            assertThat(actual).contains("text");
        }
    }
}