package com.matsuyoido;

/**
 * LineEnd
 */
public enum LineEnd {
    PLATFORM(System.lineSeparator()),
    WINDOWS("\r\n"),
    LINUX("\n");

    private String line;
    LineEnd(String lineString) {
        this.line = lineString;
    }

    public String get() {
        return this.line;
    }
}