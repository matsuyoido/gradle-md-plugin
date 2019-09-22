package com.matsuyoido.plugin.markdown;

import java.io.File;

import com.matsuyoido.plugin.markdown.task.MarkdownHtmlBuilder;
import com.matsuyoido.plugin.markdown.task.MarkdownParserOptionBuilder;
import com.matsuyoido.plugin.markdown.task.MarkdownToHtmlTask;
import com.matsuyoido.plugin.markdown.task.MarkdownToPdfTask;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.tasks.TaskContainer;

/**
 * MainPlugin
 */
public class MainPlugin implements Plugin<Project> {

    private RootExtension extension;

    @Override
    public void apply(Project project) {
        this.extension = project.getExtensions().create("markdown", RootExtension.class, project);

        project.afterEvaluate(this::setupTasks);
    }

    void setupTasks(Project project) {
        TaskContainer container = project.getTasks();
        File markdownDirectory = this.extension.getInputDir();
        File outputDirectory = this.extension.getOutputDir();
        HtmlExtension htmlExtension = this.extension.html;

        MarkdownParserOptionBuilder option = MarkdownParserOptionBuilder.builder();
        MarkdownHtmlBuilder html = MarkdownHtmlBuilder.builder();
        if (htmlExtension != null) {
            html.layout(htmlExtension.getBody())
                .line(htmlExtension.getLineEnding())
                .addCss(htmlExtension.getCssLinks())
                .addJs(htmlExtension.getJsLinks());
        }

        MarkdownToHtmlTask htmlTask = container.create("md2html", MarkdownToHtmlTask.class,
                markdownDirectory, outputDirectory, option, html);
        MarkdownToPdfTask pdfTask = container.create("md2pdf", MarkdownToPdfTask.class,
                markdownDirectory, outputDirectory, option, html);
        
        String taskGroup = "document";
        htmlTask.setGroup(taskGroup);
        htmlTask.setDescription("markdown to html.");
        htmlTask.getOutputs().upToDateWhen(t -> false); // always run setting
        pdfTask.setGroup(taskGroup);
        pdfTask.setDescription("markdown to pdf.");
        pdfTask.getOutputs().upToDateWhen(t -> false); // always run setting
    }


}