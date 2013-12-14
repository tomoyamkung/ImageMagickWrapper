ImageMagickWrapper
==================

# ImageMagickWrapper とは

[ImageMagick](http://www.imagemagick.org/script/index.php) を Java から扱うためのラッパーライブラリです。


# ImageMagickWrapper でできること

ImageMagickWrapper は ImageMagick のすべての機能をラップするものではありません。

ImageMagickWrapper は次の機能だけを提供するライブラリです。

- Exif を削除する


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


# お知らせ

## 2013/12/14

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
