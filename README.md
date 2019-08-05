# gradle-md-plugin


## Markdown

https://github.com/vsch/flexmark-java

### 装飾

https://github.com/sindresorhus/github-markdown-css


### 実装

* Gradle のプラグインとして提供する
* 対象のディレクトリを指定し、mdファイルを、htmlファイルに変換する
  - ディレクトリ構造はそのままで。
  - _{ファイル名}.md で対象外にする(include 用のファイル)
* htmlファイルをpdfに変換する

### タスク
1. mdファイルをhtmlファイルに変換する
2. mdファイルをpdfファイルに変換する
3. 1を実行後、bootRunする
  - https://stackoverflow.com/questions/39839223/how-to-create-a-gradle-task-that-will-execute-bootrun-with-a-specific-profile
  - すべてのRequestを受け取り、URIからHTMLにアクセスをする
      - @RequestMapping("/**")
      - springAutoConfigurationの、 prefix を絶対パスで指定してみるとか？
      - https://github.com/spring-projects/spring-boot/blob/master/spring-boot-project/spring-boot-autoconfigure/src/main/java/org/springframework/boot/autoconfigure/thymeleaf/ThymeleafProperties.java#L147-L149


### Extension

* include できるようにする
  - https://github.com/vsch/flexmark-java/wiki/Usage#include-markdown-and-html-file-content
* 便利なNoteとかの表示をする
  - https://github.com/vsch/flexmark-java/wiki/Extensions#admonition
  - https://github.com/vsch/flexmark-java/wiki/Admonition-Extension
* Link にid を割り振る
  - https://github.com/vsch/flexmark-java/wiki/Extensions#anchorlink
* class や id を設定する
  - https://github.com/vsch/flexmark-java/wiki/Extensions#attributes
  - https://github.com/vsch/flexmark-java/wiki/Attributes-Extension
* 下部に注釈をつけられる
  - https://github.com/vsch/flexmark-java/wiki/Extensions#footnotes
* タスクリストを作成する
  - https://github.com/vsch/flexmark-java/wiki/Extensions#gfm-tasklist
* GitLab の記法ができる(mermaid とか使える)
  - https://github.com/vsch/flexmark-java/wiki/Extensions#gitlab-flavoured-markdown
* GitLab のテーブルを表示する
  - https://github.com/vsch/flexmark-java/wiki/Extensions#tables
  - https://github.com/vsch/flexmark-java/wiki/Tables-Extension
* Link を設定する
  - https://gitq.com/vsch/flexmark-java/topics/8/prepend-image-links-with-custom-url/1



* docxに変換する
  - https://github.com/vsch/flexmark-java/wiki/Extensions#docx-converter
  - https://github.com/vsch/flexmark-java/wiki/Customizing-Docx-Rendering
* pdfに変換する
  - https://github.com/vsch/flexmark-java/wiki/PDF-Renderer-Converter
  - https://github.com/vsch/flexmark-java/wiki/Usage#render-html-to-pdf
* htmlに変換する
  - https://github.com/vsch/flexmark-java/wiki/Usage#parse-and-render-to-html

