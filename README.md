ImageMagickWrapper
==================

# ImageMagickWrapper とは

[ImageMagick](http://www.imagemagick.org/script/index.php) を Java から扱うためのラッパーライブラリです。


# ImageMagickWrapper でできること

ImageMagickWrapper は次の機能をラップするライブラリです。

- Exif を削除する
- サムネイル画像を作成する
- 画像を結合する

上記のほかにも、ImageMagick の実行コマンドとパラメータを直接指定できる機能も備えています。
この機能を使えば上記メソッドを使用せずに同じことができますし、上記メソッド以外のことも実行することが可能です。


# 開発環境

ImageMagickWrapper は以下の環境で開発しています。

```sh
java -version
java version "1.6.0_37"
Java(TM) SE Runtime Environment (build 1.6.0_37-b06)
Java HotSpot(TM) 64-Bit Server VM (build 20.12-b01, mixed mode)
```

JVM のバージョンが 1.6 なのは仕事都合です。。。


```sh
mvn -v
:
Apache Maven 3.0.4 (r1232337; 2012-01-17 17:44:56+0900)
:
:
```

プロジェクトは [Apache Maven](http://maven.apache.org/) を使ってビルドしています。バージョンは 3.0.4 です。


# ImageMagickWrapper の使い方

このライブラリの JAR をコミットしてあります。

- ImageMagickWrapper-0.1.jar
- ImageMagickWrapper-0.1-sources.jar

この JAR をプロジェクトにインポートしてください。

---

[GitHubをMavenリポジトリにしよう | おいぬま日報](http://blog.lampetty.net/blog_ja/index.php/archives/527) を参考に mvn-repo ブランチに JAR をデプロイしました。

[Maven](http://maven.apache.org/) を使われている場合は pom.xml に次を追記いただくことでもご利用いただけます。

リポジトリの追加。

```xml
<repositories>
  <repository>
    <id>tomoyamkung-github</id>
    <url>https://raw.github.com/tomoyamkung/ImageMagickWrapper/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>
```

JAR の追加。

```xml
<dependency>
  <groupId>net.tomoyamkung.library</groupId>
  <artifactId>ImageMagickWrapper</artifactId>
  <version>0.1</version>
</dependency>
```


# お知らせ


## 2013/12/16


### mvn-repo ブランチに JAR をデプロイしました

[GitHubをMavenリポジトリにしよう | おいぬま日報](http://blog.lampetty.net/blog_ja/index.php/archives/527) を参考に mvn-repo ブランチに JAR をデプロイしました。

- [ImageMagickWrapper/net/tomoyamkung/library/ImageMagickWrapper/0.1 at mvn-repo · tomoyamkung/ImageMagickWrapper](https://github.com/tomoyamkung/ImageMagickWrapper/tree/mvn-repo/net/tomoyamkung/library/ImageMagickWrapper/0.1)

[Maven](http://maven.apache.org/) を使われている場合は pom.xml に次を追記いただくことでもご利用いただけます。

リポジトリの追加。

```xml
<repositories>
  <repository>
    <id>tomoyamkung-github</id>
    <url>https://raw.github.com/tomoyamkung/ImageMagickWrapper/mvn-repo/</url>
    <snapshots>
      <enabled>true</enabled>
      <updatePolicy>always</updatePolicy>
    </snapshots>
  </repository>
</repositories>
```

JAR の追加。

```xml
<dependency>
  <groupId>net.tomoyamkung.library</groupId>
  <artifactId>ImageMagickWrapper</artifactId>
  <version>0.1</version>
</dependency>
```


### 画像を結合する機能を作成しました

タイル状に画像を結合するメソッド `ImageMagick#createThumbnail` を作成しました。

```java
/**
 * 画像をタイル状に結合する。
 * 
 * @param commandPath
 *            montage コマンドの絶対パス。
 * @param srcFiles
 *            結合用画像ファイルを格納したリスト。
 * @param tile
 *            結合する形式（2x2 とか 9x5 といった形式で指定する）
 * @param geometry
 *            結合元画像ファイルの大きさ（100x100 といった形式で指定する）
 * @param dest
 *            生成先のファイル。このファイルに画像を作成する
 * @throws IOException
 *             commandPath に指定されているパスが montage コマンドではなかった場合
 * @throws InterruptedException
 *             ImageMagick の操作に失敗した場合
 */
public static void createMontage(String commandPath, List<File> srcFiles, String tile, String geometry, File dest) throws IOException, InterruptedException
```


## 2013/12/15


### コマンドとパラメータを直接指定する機能を作成しました

ImageMagick の実行コマンドとパラメータを直接指定できるメソッド `ImageMagick#runProcessDirectly` を作成しました。

このメソッドを使えば `ImageMagick#removeExif` や `ImageMagick#createThumbnail` を使うことなく同様の処理ができます。
おそらく ImageMagick のすべての機能を実行することができるはずです。

実行コマンドとパラメータは `Command` クラスで指定します。詳しくは `ImageMagickTest` を参照してください。

```java
/**
 * ImageMagick のコマンドを直接実行する。
 * 
 * @param command
 *            コマンドパスとパラメータを格納した <code>Command</code> オブジェクト
 * @throws IOException
 *             commandPath に指定されているパスが convert コマンドではなかった場合
 * @throws InterruptedException
 *             ImageMagick の操作に失敗した場合
 */
public static void runProcessDirectly(Command command) throws IOException, InterruptedException
```


## 2013/12/14


### サムネイル画像を作成する機能を作成しました

サムネイル画像を作成するメソッド `ImageMagick#createThumbnail` を作成しました。

```java
/**
 * サムネイル画像を作成する。
 * 
 * @param commandPath
 *            convert コマンドの絶対パス。
 * @param src
 *            生成元のファイル。Exif を削除したい画像ファイル
 * @param dest
 *            生成先のファイル。このファイルにサムネイル画像を作成する
 * @param size
 *            サムネイルサイズ。[0-9]{1,}x[0-9]{1,} で指定する
 * @throws IOException
 *             commandPath に指定されているパスが convert コマンドではなかった場合
 * @throws InterruptedException
 *             ImageMagick の操作に失敗した場合
 */
public static void createThumbnail(String commandPath, File src, File dest, String size) throws IOException, InterruptedException
```


### Exif を削除する機能を作成しました

Exif を削除するメソッド `ImageMagick#removeExif` を作成しました。

```java
/**
 * Exif を削除する。
 * 
 * @param commandPath
 *            convert コマンドの絶対パス。
 * @param src
 *            生成元のファイル。Exif を削除したい画像ファイル
 * @param dest
 *            生成先のファイル。このファイルに画像を作成する
 * @throws IOException
 *             ImageMagick の操作に失敗した場合
 * @throws InterruptedException
 *             ImageMagick の操作に失敗した場合
 */
public static void removeExif(String commandPath, File src, File dest) throws IOException, InterruptedException
```
