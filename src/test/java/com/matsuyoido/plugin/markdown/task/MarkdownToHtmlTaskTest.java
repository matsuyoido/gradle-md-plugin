package com.matsuyoido.plugin.markdown.task;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * MarkdownToHtmlTaskTest
 */
public class MarkdownToHtmlTaskTest {

    @TempDir
    Path dir;
    MarkdownHtmlBuilder html;

    private Path inputDir;
    private Path outputDir;
    private MarkdownToHtmlTask task;

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
        task = project.getTasks().create("markdownToHtmlTaskTest", MarkdownToHtmlTask.class,
                input, output, option, html);
    }

    @Test
    public void oneFile_noCssAndJs() throws IOException {
        Files.write(inputDir.resolve("simple.md"), "text".getBytes());

        task.taskAction(new MockIncrementalTaskInputs());

        assertThat(outputDir.resolve("simple.html").toFile()).hasContent(String.join(System.lineSeparator(),
        //#region assertText
        "<!DOCTYPE html>",
        "<html>",
        "<head>",
        "  <meta charset=\"UTF-8\">",
        "  <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">",
        "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">",
        "  <title>simple</title>",
        "</head>",
        "<body>",
        "  <style>",
        ".adm-block {",
        "    display: block;",
        "    width: 99%;",
        "    border-radius: 6px;",
        "    padding-left: 10px;",
        "    margin-bottom: 1em;",
        "    border: 1px solid;",
        "    border-left-width: 4px;",
        "    box-shadow: 2px 2px 6px #cdcdcd;",
        "}",
        "",
        ".adm-heading {",
        "    display: block;",
        "    font-weight: bold;",
        "    font-size: 0.9em;",
        "    height: 1.8em;",
        "    padding-top: 0.3em;",
        "    padding-bottom: 2em;",
        "    border-bottom: solid 1px;",
        "    padding-left: 10px;",
        "    margin-left: -10px;",
        "}",
        "",
        ".adm-body {",
        "    display: block;",
        "    padding-bottom: 0.5em;",
        "    padding-top: 0.5em;",
        "    margin-left: 1.5em;",
        "    margin-right: 1.5em;",
        "}",
        "",
        ".adm-heading > span {",
        "    color: initial;",
        "}",
        "",
        ".adm-icon {",
        "    height: 1.6em;",
        "    width: 1.6em;",
        "    display: inline-block;",
        "    vertical-align: middle;",
        "    margin-right: 0.25em;",
        "    margin-left: -0.25em;",
        "}",
        "",
        ".adm-hidden {",
        "    display: none !important;",
        "}",
        "",
        ".adm-block.adm-collapsed > .adm-heading, .adm-block.adm-open > .adm-heading {",
        "    position: relative;",
        "    cursor: pointer;",
        "}",
        "",
        ".adm-block.adm-collapsed > .adm-heading {",
        "    margin-bottom: 0;",
        "}",
        "",
        ".adm-block.adm-collapsed .adm-body {",
        "    display: none !important;",
        "}",
        "",
        ".adm-block.adm-open > .adm-heading:after,",
        ".adm-block.adm-collapsed > .adm-heading:after {",
        "    display: inline-block;",
        "    position: absolute;",
        "    top:calc(50% - .65em);",
        "    right: 0.5em;",
        "    font-size: 1.3em;",
        "    content: '▼';",
        "}",
        "",
        ".adm-block.adm-collapsed > .adm-heading:after {",
        "    right: 0.50em;",
        "    top:calc(50% - .75em);",
        "    transform: rotate(90deg);",
        "}",
        "",
        "/* default scheme */",
        "",
        ".adm-block {",
        "    border-color: #ebebeb;",
        "    border-bottom-color: #bfbfbf;",
        "}",
        "",
        ".adm-block.adm-abstract {",
        "    border-left-color: #48C4FF;",
        "}",
        "",
        ".adm-block.adm-abstract .adm-heading {",
        "    background: #E8F7FF;",
        "    color: #48C4FF;",
        "    border-bottom-color: #dbf3ff;",
        "}",
        "",
        ".adm-block.adm-abstract.adm-open > .adm-heading:after,",
        ".adm-block.adm-abstract.adm-collapsed > .adm-heading:after {",
        "    color: #80d9ff;",
        "}",
        "",
        "",
        ".adm-block.adm-bug {",
        "    border-left-color: #F50057;",
        "}",
        "",
        ".adm-block.adm-bug .adm-heading {",
        "    background: #FEE7EE;",
        "    color: #F50057;",
        "    border-bottom-color: #fcd9e4;",
        "}",
        "",
        ".adm-block.adm-bug.adm-open > .adm-heading:after,",
        ".adm-block.adm-bug.adm-collapsed > .adm-heading:after {",
        "    color: #f57aab;",
        "}",
        "",
        ".adm-block.adm-danger {",
        "    border-left-color: #FE1744;",
        "}",
        "",
        ".adm-block.adm-danger .adm-heading {",
        "    background: #FFE9ED;",
        "    color: #FE1744;",
        "    border-bottom-color: #ffd9e0;",
        "}",
        "",
        ".adm-block.adm-danger.adm-open > .adm-heading:after,",
        ".adm-block.adm-danger.adm-collapsed > .adm-heading:after {",
        "    color: #fc7e97;",
        "}",
        "",
        ".adm-block.adm-example {",
        "    border-left-color: #7940ff;",
        "}",
        "",
        ".adm-block.adm-example .adm-heading {",
        "    background: #EFEBFF;",
        "    color: #7940ff;",
        "    border-bottom-color: #e0d9ff;",
        "}",
        "",
        ".adm-block.adm-example.adm-open > .adm-heading:after,",
        ".adm-block.adm-example.adm-collapsed > .adm-heading:after {",
        "    color: #b199ff;",
        "}",
        "",
        ".adm-block.adm-fail {",
        "    border-left-color: #FE5E5E;",
        "}",
        "",
        ".adm-block.adm-fail .adm-heading {",
        "    background: #FFEEEE;",
        "    color: #Fe5e5e;",
        "    border-bottom-color: #ffe3e3;",
        "}",
        "",
        ".adm-block.adm-fail.adm-open > .adm-heading:after,",
        ".adm-block.adm-fail.adm-collapsed > .adm-heading:after {",
        "    color: #fcb1b1;",
        "}",
        "",
        ".adm-block.adm-faq {",
        "    border-left-color: #5ED116;",
        "}",
        "",
        ".adm-block.adm-faq .adm-heading {",
        "    background: #EEFAE8;",
        "    color: #5ED116;",
        "    border-bottom-color: #e6fadc;",
        "}",
        "",
        ".adm-block.adm-faq.adm-open > .adm-heading:after,",
        ".adm-block.adm-faq.adm-collapsed > .adm-heading:after {",
        "    color: #98cf72;",
        "}",
        "",
        ".adm-block.adm-info {",
        "    border-left-color: #00B8D4;",
        "}",
        "",
        ".adm-block.adm-info .adm-heading {",
        "    background: #E8F7FA;",
        "    color: #00B8D4;",
        "    border-bottom-color: #dcf5fa;",
        "}",
        "",
        ".adm-block.adm-info.adm-open > .adm-heading:after,",
        ".adm-block.adm-info.adm-collapsed > .adm-heading:after {",
        "    color: #83ced6;",
        "}",
        "",
        ".adm-block.adm-note {",
        "    border-left-color: #448AFF;",
        "}",
        "",
        ".adm-block.adm-note .adm-heading {",
        "    background: #EDF4FF;",
        "    color: #448AFF;",
        "    border-bottom-color: #e0edff;",
        "}",
        "",
        ".adm-block.adm-note.adm-open > .adm-heading:after,",
        ".adm-block.adm-note.adm-collapsed > .adm-heading:after {",
        "    color: #8cb8ff;",
        "}",
        "",
        ".adm-block.adm-quote {",
        "    border-left-color: #9E9E9E;",
        "}",
        "",
        ".adm-block.adm-quote .adm-heading {",
        "    background: #F4F4F4;",
        "    color: #9E9E9E;",
        "    border-bottom-color: #e8e8e8;",
        "}",
        "",
        ".adm-block.adm-quote.adm-open > .adm-heading:after,",
        ".adm-block.adm-quote.adm-collapsed > .adm-heading:after {",
        "    color: #b3b3b3;",
        "}",
        "",
        ".adm-block.adm-success {",
        "    border-left-color: #1DCD63;",
        "}",
        "",
        ".adm-block.adm-success .adm-heading {",
        "    background: #E9F8EE;",
        "    color: #1DCD63;",
        "    border-bottom-color: #dcf7e5;",
        "}",
        "",
        ".adm-block.adm-success.adm-open > .adm-heading:after,",
        ".adm-block.adm-success.adm-collapsed > .adm-heading:after {",
        "    color: #7acc98;",
        "}",
        "",
        ".adm-block.adm-tip {",
        "    border-left-color: #01BFA5;",
        "}",
        "",
        ".adm-block.adm-tip .adm-heading {",
        "    background: #E9F9F6;",
        "    color: #01BFA5;",
        "    border-bottom-color: #dcf7f2;",
        "}",
        "",
        ".adm-block.adm-tip.adm-open > .adm-heading:after,",
        ".adm-block.adm-tip.adm-collapsed > .adm-heading:after {",
        "    color: #7dd1c0;",
        "}",
        "",
        ".adm-block.adm-warning {",
        "    border-left-color: #FF9001;",
        "}",
        "",
        ".adm-block.adm-warning .adm-heading {",
        "    background: #FEF3E8;",
        "    color: #FF9001;",
        "    border-bottom-color: #Fef3e8;",
        "}",
        "",
        ".adm-block.adm-warning.adm-open > .adm-heading:after,",
        ".adm-block.adm-warning.adm-collapsed > .adm-heading:after {",
        "    color: #fcbb6a;",
        "}",
        "",
        "",
        "  </style>",
        "<p>text</p>",
        "",
        "  <script>",
        "(() => {",
        "    let divs = document.getElementsByClassName(\"adm-block\");",
        "    for (let i = 0; i < divs.length; i++) {",
        "        let div = divs[i];",
        "        if (div.classList.contains(\"adm-collapsed\") || div.classList.contains(\"adm-open\")) {",
        "            let headings = div.getElementsByClassName(\"adm-heading\");",
        "            if (headings.length > 0) {",
        "                headings[0].addEventListener(\"click\", event => {",
        "                    let el = div;",
        "                    event.preventDefault();",
        "                    event.stopImmediatePropagation();",
        "                    if (el.classList.contains(\"adm-collapsed\")) {",
        "                        console.debug(\"Admonition Open\", event.srcElement);",
        "                        el.classList.remove(\"adm-collapsed\");",
        "                        el.classList.add(\"adm-open\");",
        "                    } else {",
        "                        console.debug(\"Admonition Collapse\", event.srcElement);",
        "                        el.classList.add(\"adm-collapsed\");",
        "                        el.classList.remove(\"adm-open\");",
        "                    }",
        "                });",
        "            }",
        "        }",
        "    }",
        "})();",
        "",
        "  </script>",
        "</body>",
        "</html>"
        //#endregion
        ));
    }

    @Test
    public void oneFile_localCssAndLocalJs() throws IOException {
        Path cssFile = inputDir.resolve("test.css");
        Path jsFile = inputDir.resolve("test.js");
        Files.write(cssFile, "p { color: red; }".getBytes());
        Files.write(jsFile, "console.log('a');".getBytes());
        Files.write(inputDir.resolve("simple.md"), "text".getBytes());
        this.html.addCss(cssFile.toString());
        this.html.addJs(jsFile.toString());

        task.taskAction(new MockIncrementalTaskInputs());

        assertContains(outputDir.resolve("simple.html").toFile(), 
                "<p>text</p>",
                "p { color: red; }",
                "console.log('a');");
    }

    @Test
    public void oneFile_localAndUrl() throws IOException {
        Path cssFile = inputDir.resolve("test.css");
        Path jsFile = inputDir.resolve("test.js");
        Files.write(cssFile, "p { color: red; }".getBytes());
        Files.write(jsFile, "console.log('a');".getBytes());
        Files.write(inputDir.resolve("simple.md"), "text".getBytes());
        this.html.addCss(cssFile.toString())
                 .addCss("https://unpkg.com/spectre.css/dist/spectre.min.css");
        this.html.addJs(jsFile.toString())
                 .addJs("https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js");

        task.taskAction(new MockIncrementalTaskInputs());

        assertContains(outputDir.resolve("simple.html").toFile(), 
                "<p>text</p>",
                "p { color: red; }",
                "console.log('a');",
                "<script type=\"application/javascript\" src=\"https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>",
                "<link rel=\"stylesheet\" href=\"https://unpkg.com/spectre.css/dist/spectre.min.css\">");
    }

    @Test
    public void nestFile() throws IOException {
        Path nestPath = inputDir.resolve("nest");
        nestPath.toFile().mkdir();
        Files.write(inputDir.resolve("simple.md"), "text".getBytes());
        Files.write(nestPath.resolve("test.md"), "## title".getBytes());

        task.taskAction(new MockIncrementalTaskInputs());

        assertThat(outputDir.toFile().listFiles()).allSatisfy(file -> {
            if (file.isDirectory()) {
                assertThat(file.listFiles()).allSatisfy(nestFile -> {
                    assertThat(nestFile).isFile()
                                        .hasName("test.html");
                    assertContains(nestFile, "<h2 id=\"title\"><a href=\"#title\" id=\"title\">title</a></h2>");
                });
            } else {
                assertThat(file).isFile()
                                .hasName("simple.html");
                assertContains(file, "<p>text</p>");
            }
        });
    }

    @Test
    public void _fileIsExclude() throws IOException {
        Files.write(inputDir.resolve("simple.md"), "text {% include _include.md %}".getBytes());
        Files.write(inputDir.resolve("_include.md"), "test".getBytes());

        task.taskAction(new MockIncrementalTaskInputs());

        assertContains(outputDir.resolve("simple.html").toFile(), "<p>text <p>test</p></p>");
    }

    @Test
    public void include_fileNotExistSpecify() throws IOException {
        Files.write(inputDir.resolve("simple.md"), "text {% include _not_include.md %}".getBytes());
        Files.write(inputDir.resolve("_include.md"), "test".getBytes());

        task.taskAction(new MockIncrementalTaskInputs());

        assertContains(outputDir.resolve("simple.html").toFile(), "<p>text </p>");
    }

    @Test
    public void linkLocalMarkdown() throws IOException {
        Files.write(inputDir.resolve("simple.md"), "[test](./test.md)".getBytes());

        task.taskAction(new MockIncrementalTaskInputs());

        assertContains(outputDir.resolve("simple.html").toFile(), "<a href=\"./test.html\">test</a>");
    }


    private void assertContains(File file, String... expected) {
        try {
            StringBuilder sb = new StringBuilder();
            try (FileReader reader = new FileReader(file);
                    BufferedReader bReader = new BufferedReader(reader)) {
                boolean readable = true;
                do {
                    String line = bReader.readLine();
                    if (line == null) {
                        readable = false;
                    } else {
                        sb.append(line);
                    }
                } while(readable);
            }
            for (String expect : expected) {
                assertThat(sb.indexOf(expect)).isGreaterThan(-1);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}