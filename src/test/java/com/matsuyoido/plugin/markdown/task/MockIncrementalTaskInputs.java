package com.matsuyoido.plugin.markdown.task;

import java.io.File;

import org.gradle.api.Action;
import org.gradle.api.tasks.incremental.IncrementalTaskInputs;
import org.gradle.api.tasks.incremental.InputFileDetails;

/**
 * MockIncrementalTaskInputs
 */
public class MockIncrementalTaskInputs implements IncrementalTaskInputs {

    private Iterable<File> target;

    public MockIncrementalTaskInputs(Iterable<File> target) {
        this.target = target;
    }
    public MockIncrementalTaskInputs() {
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void outOfDate(Action<? super InputFileDetails> action) {
        if (target != null) {
            target.forEach(file -> action.execute(mockInputFileDetails(file)));
        }
    }

    @Override
    public void removed(Action<? super InputFileDetails> action) {
        // do nothing
    }

    private static InputFileDetails mockInputFileDetails(File file) {
        return new InputFileDetails() {
            @Override
            public boolean isAdded() {
                return false;
            }

            @Override
            public boolean isModified() {
                return false;
            }

            @Override
            public boolean isRemoved() {
                return false;
            }

            @Override
            public File getFile() {
                return file;
            }
        };
    }
}