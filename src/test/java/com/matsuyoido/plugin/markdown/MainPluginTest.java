package com.matsuyoido.plugin.markdown;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.BuildTask;
import org.gradle.testkit.runner.GradleRunner;
import org.gradle.testkit.runner.TaskOutcome;
import org.gradle.testkit.runner.UnexpectedBuildFailure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * MainPluginTest
 */
public class MainPluginTest {

    @TempDir
    Path projectDir;

    @Test
    public void extension_bodyNotMdTag() throws IOException {
        setup(
            "markdown {",
            "  inputDir = file(\"$rootDir/markdown\")",
            "  outputDir = file(\"$rootDir/html\")",
            "  html {",
            "    css = [",
            "      'https://unpkg.com/spectre.css/dist/spectre.min.css'",
            "    ]",
            "    js = [",
            "      'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js'",
            "    ]",
            "    body = '''\\",
            "<div class=\"container\">",
            "</div>",
            "    '''",
            "  }",
           "}"
        );

        UnexpectedBuildFailure exception = assertThrows(UnexpectedBuildFailure.class, () -> {
            run("tasks", "--all").getOutput(); 
        });
        assertThat(exception.getLocalizedMessage()).contains("body requires");
    }


    @Test
    public void enabledTasks_all() throws IOException {
        setup(
            "markdown {",
            "  inputDir = file(\"$rootDir/markdown\")",
            "  outputDir = file(\"$rootDir/html\")",
            "  html {",
            "    css = [",
            "      'https://unpkg.com/spectre.css/dist/spectre.min.css'",
            "    ]",
            "    js = [",
            "      'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js'",
            "    ]",
            "    body = '''\\",
            "<div class=\"container\">",
            "  <md></md>",
            "</div>",
            "    '''",
            "  }",
           "}"
        );

        String result = run("tasks", "--all").getOutput();
        assertThat(result).contains("md2html", "md2pdf");
    }

    @Test
    public void htmlTask() throws IOException {
        Path inputDir = projectDir.resolve("markdown");
        inputDir.toFile().mkdirs();
        Files.write(inputDir.resolve("compiled.md"), "test".getBytes());
        Files.write(inputDir.resolve("_notCompiled.md"), "test".getBytes());
        setup(
            "markdown {",
            "  inputDir = file(\"$rootDir/markdown\")",
            "  outputDir = file(\"$rootDir/html\")",
            "  html {",
            "    css = [",
            "      'https://unpkg.com/spectre.css/dist/spectre.min.css',",
            "      'https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/3.0.1/github-markdown.min.css'",
            "    ]",
            "    js = [",
            "      'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js'",
            "    ]",
            "    body = '''\\",
            "<div class=\"container\">",
            "  <md></md>",
            "</div>",
            "    '''",
            "    lineEnding = 'linux'",
            "  }",
           "}"
        );
        String testTask = "md2html";

        BuildTask result = run(testTask).task(":" + testTask);
        
        File outputDir = projectDir.resolve("html").toFile();
        assertThat(result.getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
        assertThat(outputDir.list()).containsOnly("compiled.html");
    }

    @Test
    public void htmlTask_bodyNot() throws IOException {
        Path inputDir = projectDir.resolve("markdown");
        inputDir.toFile().mkdirs();
        Files.write(inputDir.resolve("compiled.md"), "test".getBytes());
        Files.write(inputDir.resolve("_notCompiled.md"), "test".getBytes());
        setup(
            "markdown {",
            "  inputDir = file(\"$rootDir/markdown\")",
            "  outputDir = file(\"$rootDir/html\")",
           "}"
        );
        String testTask = "md2html";

        BuildTask result = run(testTask).task(":" + testTask);
        
        File outputDir = projectDir.resolve("html").toFile();
        assertThat(result.getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
        assertThat(outputDir.list()).containsOnly("compiled.html");
    }

    @Test
    public void pdfTask() throws IOException {
        Path inputDir = projectDir.resolve("markdown");
        inputDir.toFile().mkdirs();
        Files.write(inputDir.resolve("compiled.md"), "test".getBytes());
        Files.write(inputDir.resolve("_notCompiled.md"), "test".getBytes());
        setup(
            "markdown {",
            "  inputDir = file(\"$rootDir/markdown\")",
            "  outputDir = file(\"$rootDir/pdf\")",
            "  html {",
            "    css = [",
            "      'https://unpkg.com/spectre.css/dist/spectre.min.css'",
            "    ]",
            "    js = [",
            "      'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js'",
            "    ]",
            "    body = '''\\",
            "<div class=\"container\">",
            "  <md></md>",
            "</div>",
            "    '''",
            "    lineEnding = 'windows'",
            "  }",
           "}"
        );
        String testTask = "md2pdf";

        BuildTask result = run(testTask).task(":" + testTask);
        
        File outputDir = projectDir.resolve("pdf").toFile();
        assertThat(result.getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
        assertThat(outputDir.list()).containsOnly("compiled.pdf");
    }

    @Test
    public void alwaysRunMultipleCall() throws IOException {
        Path inputDir = projectDir.resolve("markdown");
        inputDir.toFile().mkdirs();
        setup(
            "markdown {",
            "  inputDir = file(\"$rootDir/markdown\")",
            "  outputDir = file(\"$rootDir/html\")",
            "  html {",
            "    css = [",
            "      'https://unpkg.com/spectre.css/dist/spectre.min.css'",
            "    ]",
            "    js = [",
            "      'https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js'",
            "    ]",
            "    body = '''\\",
            "<div class=\"container\">",
            "  <md></md>",
            "</div>",
            "    '''",
            "    lineEnding = 'linux'",
            "  }",
           "}"
        );

        assertThat(run("md2html").task(":md2html").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
        assertThat(run("md2html").task(":md2html").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);

        assertThat(run("md2pdf").task(":md2pdf").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
        assertThat(run("md2pdf").task(":md2pdf").getOutcome()).isEqualTo(TaskOutcome.SUCCESS);
    }



    private BuildResult run(String... taskName) throws IOException {
        return GradleRunner.create()
                    .withGradleVersion("5.0")
                    .withProjectDir(projectDir.toFile())
                    .withPluginClasspath()
                    .withDebug(true)
                    .withArguments(taskName)
                    .forwardOutput()
                    .build();
    }

    private void setup(String... extension) throws IOException {
        projectDir.resolve("settings.gradle")
                  .toFile()
                  .createNewFile();
        File buildGradle = projectDir.resolve("build.gradle")
                                     .toFile();
        buildGradle.createNewFile();
        String classpath = GradleRunner.create()
                .withPluginClasspath()
                .getPluginClasspath()
                .stream()
                .map(f -> String.format("'%s'", f.getAbsolutePath()))
                .collect(Collectors.joining(", "))
                .replace("\\", "/");
        StringBuilder buildScript = new StringBuilder();
        buildScript.append(String.join(System.lineSeparator(), 
            "buildscript {",
            "  dependencies {",
            "    classpath files(" + classpath + ")",
            "  }",
            "}",
            "import com.matsuyoido.plugin.markdown.MainPlugin",
            "apply plugin: MainPlugin")).append(System.lineSeparator());
        buildScript.append(Arrays.stream(extension).collect(Collectors.joining(System.lineSeparator())));
        String text = buildScript.toString();

        System.out.println(text);
        // Files.writeString(buildGradle.toPath(), text);
        Files.write(buildGradle.toPath(), text.getBytes());
    }
}