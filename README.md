# gradle-md-plugin

## Feature

This gradle plugin convert `markdown file` into `html/pdf` file.

Markdown file is converted by [flexmark-java](https://github.com/vsch/flexmark-java).

I want to provide it to work alone.

## Tasks

1. `$ gradlew md2html`
1. `$ gradlew md2pdf`


## Extension

### Full extension

```
markdown {
  inputDir = file("$rootDir/markdown")
  outputDir = file("$rootDir/document")

  html {
    css = [
      // you can specify local file or server file.
      "$rootDir/your/want/to/adapt/cssfile.css",
      "https://cdnjs.cloudflare.com/ajax/libs/github-markdown-css/3.0.1/github-markdown.min.css"
    ]
    js = [
      // you can specify local file or server file.
      "$rootDir/your/want/to/adapt/jsfile.js",
      "https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js"
    ]
    // you can setup layout html.
    // But please set `<md></md>` tag. <md> tag replace markdown text.
    body = """\
<header>
  <h1>Document</h1>
</header>
<section>
  <md>
    <p>This place replace converted html text.</p>
    <p>If this extension's body text copy real html file, you can check visual.</p>
  </md>
</section>
<footer>
  <p><small>&copy; 2019- matsuyoido</small></p>
</footer>
    """
  }
}
```

### Minimum Extension

```
markdown {
  inputDir = file("$rootDir/markdown")
  outputDir = file("$rootDir/document")
}
```


## Bug or Request

please create New Issue in Japanese or English. (Japanese is better...)


## Q&A

### What's file target of conversion ?

Target is `.md` file specified at `inputDir`.

But, file name starts with `_` is excluded.


### What's extension enabled ?

Please see [this code](https://github.com/matsuyoido/gradle-md-plugin/blob/master/src/main/java/com/matsuyoido/plugin/markdown/task/MarkdownParserOptionBuilder.java#L72-L96).


### What does the title of generated HTML become ?

File name is title.

### Can I include image file in markdown ?

Yes. ex. `![imageAltText](imagePath.jpeg)`

If imagePath is local file, convert type of Base64.


## LICENSE

[This LICENSE](https://github.com/matsuyoido/gradle-md-plugin/blob/master/LICENSE).

below is [required](https://github.com/vsch/flexmark-java/blob/master/LICENSE.txt).

Copyright (c) 2015-2016 Atlassian and others.

Copyright (c) 2016-2018, Vladimir Schneider,