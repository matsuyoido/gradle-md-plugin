package com.matsuyoido.plugin.markdown.task;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import javax.inject.Inject;

import com.vladsch.flexmark.pdf.converter.PdfConverterExtension;
import com.vladsch.flexmark.util.data.MutableDataSet;

/**
 * MarkdownToPdfTask
 */
public class MarkdownToPdfTask extends MarkdownToHtmlTask {
    private MutableDataSet option;

    @Inject
    public MarkdownToPdfTask(File mdDir, File outputDir, MarkdownParserOptionBuilder builder,
            MarkdownHtmlBuilder htmlBuilder) {
        super(mdDir, outputDir, builder, htmlBuilder);
        super.outputExtension = ".pdf";
        this.option = builder.build();
    }

    @Override
    protected void output(Path outputPath, String content) throws IOException {
        File outputFile = outputPath.toFile();
        outputFile.getParentFile().mkdirs();
        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            PdfConverterExtension.exportToPdf(fos, content, "", option);
        }
    }
}