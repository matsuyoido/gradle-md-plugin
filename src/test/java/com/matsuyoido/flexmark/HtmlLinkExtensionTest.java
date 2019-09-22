package com.matsuyoido.flexmark;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * HtmlLinkExtensionTest
 */
public class HtmlLinkExtensionTest {

    private Parser parser;
    private HtmlRenderer render;

    @BeforeEach
    public void setup() {
        MutableDataSet option = new MutableDataSet().set(Parser.EXTENSIONS, 
                Arrays.asList(new HtmlLinkExtension()));
        this.parser = Parser.builder(option).build();
        this.render = HtmlRenderer.builder(option).build();
    }

    @Test
    public void render() {
        String markdown = String.join(System.lineSeparator(),
                "[test1](./test.md)",
                "[test2](test.md)",
                "[test3](./../test.md)",
                "[test4](http://example.com/test.md)",
                "[test5](https://example.com/test.md)",
                "[test6](C:\\Document\\test.md)"
        );

        String result = render.render(parser.parse(markdown));

        assertThat(result).contains(
            "<a href=\"./test.html\">test1</a>",
            "<a href=\"test.html\">test2</a>",
            "<a href=\"./../test.html\">test3</a>",
            "<a href=\"http://example.com/test.md\">test4</a>",
            "<a href=\"https://example.com/test.md\">test5</a>",
            "<a href=\"C:\\Document\\test.html\">test6</a>");
    }
}