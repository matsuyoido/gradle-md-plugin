package com.matsuyoido.plugin.markdown;

import java.io.File;

import org.gradle.api.Project;

import groovy.lang.Closure;

/**
 * RootExtension
 */
public class RootExtension {

    private final Project project;
    final HtmlExtension html;
    private File inputDir;
    private File outputDir;

    public RootExtension(Project project) {
        this.project = project;
        this.html = new HtmlExtension();
    }

    public void html(Closure<HtmlExtension> closure) {
        this.project.configure(this.html, closure);
    }
    public void setInputDir(File input) {
        this.inputDir = input;
    }
    public void setOutputDir(File output) {
        this.outputDir = output;
    }

    public File getInputDir() {
        return this.inputDir;
    }
    public File getOutputDir() {
        return this.outputDir;
    }
}