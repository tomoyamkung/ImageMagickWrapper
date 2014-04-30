ImageMagickWrapper
==================

# ImageMagickWrapper とは

[ImageMagick](http://www.imagemagick.org/script/index.php) を Java から扱うためのラッパーライブラリです。


# ImageMagickWrapper でできること

ImageMagickWrapper は次の機能をラップするライブラリです。

- 画像をリサイズする
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

プロジェクトは [Apache Maven](http://maven.apache.org/) を使ってビルドしています。バージョンは 3.0.4 です。

```sh
mvn -v
:
Apache Maven 3.0.4 (r1232337; 2012-01-17 17:44:56+0900)
:
:
```


# ImageMagickWrapper の使い方


## ライブラリの組込みについて

~~このライブラリの JAR をコミットしてあります。~~

- ~~ImageMagickWrapper-0.1.jar~~
- ~~ImageMagickWrapper-0.1-sources.jar~~

~~この JAR をプロジェクトにインポートしてください。~~

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
  <version>0.2</version>
</dependency>
```


## 設定ファイルの用意

本ライブラリは [ImageMagick](http://www.imagemagick.org/script/index.php) を使って画像を加工するので、事前に環境にインストールしてください。

プロパティファイルには ImageMagick のコマンドのパスを定義します。
次のコマンドの絶対パスを定義してください。

- convert コマンド
- montage コマンド

以下は、プロパティファイルの定義例になります。

```java
## ImageMagick
# convert のコマンドのパス
path.to.imagemagick.convert = /path/to/convert

# montage コマンドのパス
path.to.imagemagick.montage = /path/to/montage
```

"/path/to/convert" と "/path/to/montage" を環境に合わせた絶対パスに変更してください。


# お知らせ


## 2014/05/01


### 画像をリサイズする機能を作成しました

画像をリサイズするメソッド `ImageMagick#resize` を作成しました。

元画像を指定して、「その画像の横幅（もしくは、高さ）を○○ピクセルにして、高さ（もしくは、横幅）は縦横比を維持した状態で自動計算したサイズにリサイズする」機能です。

```java
/**
 * 画像をリサイズする。
 * 
 * <code>src</code> を元画像として <code>dest</code> にリサイズした画像を作成する。
 * 
 * 「元画像の横幅を 300px にしてリサイズする（高さは縦横比を維持して自動計算する）」と指定する。
 * <code>SquareSide</code> は、基準とする横幅、もしくは、高さを指定する。 <code>pixel</code>
 * は、基準とする「辺」のピクセルを指定する。
 * 
 * @param commandPath
 *            convert コマンドの絶対パス。
 * @param src
 *            生成元のファイル。リサイズしたい画像ファイル
 * @param dest
 *            生成先のファイル。このファイルにリサイズした画像を作成する
 * @param side
 *            基準とする「辺」
 * @param pixel
 *            基準とする「辺」のピクセル
 * @throws IOException
 *             commandPath に指定されているパスが convert コマンドではなかった場合
 * @throws InterruptedException
 *             ImageMagick の操作に失敗した場合
 */
public static void resize(String commandPath, File src, File dest,
    SquareSide side, int pixel) throws IOException,
    InterruptedException {
```

`SquareSide` は次の値を取る Enum です。

- SquareSide.portrait → 「横幅」を基準にリサイズしたい場合に指定します
- SquareSide.landscape → 「高さ」を基準にリサイズしたい場合に指定します

使い方については次のテストケースを参考にしてください。

- net.tomoyamkung.library.ImageMagickTest


### ライブラリのバージョンを v0.2 にバージョンアップしました。

pom.xml の dependency を次のようにすると v0.2 のライブラリがダウンロードされます。

```xml
<dependency>
  <groupId>net.tomoyamkung.library</groupId>
  <artifactId>ImageMagickWrapper</artifactId>
  <version>0.2</version>
</dependency>
```


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
