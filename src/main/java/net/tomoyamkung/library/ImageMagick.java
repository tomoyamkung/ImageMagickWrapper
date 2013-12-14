package net.tomoyamkung.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * ImageMagick を操作する Wrapper クラス。
 * 
 * @author tomoyamkung
 * 
 */
public class ImageMagick {

	private static final Logger log = Logger.getLogger(ImageMagick.class);

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
	public static void createThumbnail(String commandPath, File src, File dest,
			String size) throws IOException, InterruptedException {
		validateCommandPath(commandPath);
		validateSrcFile(src);
		validateDestFile(dest);
		validateThumbnailSize(size);

		if (log.isDebugEnabled()) {
			log.debug(String.format(
					"commandPath:%s, srcPath:%s, destPath:%s, size:%s",
					commandPath, src, dest, size));
		}

		ProcessBuilder builder = new ProcessBuilder(commandPath, "-thumbnail",
				size, src.getAbsolutePath(), dest.getAbsolutePath());
		executeProcess(builder);
	}

	/**
	 * ImageMagick のコマンドを実行する。
	 * 
	 * @param builder
	 *            コマンドの <code>ProcessBuilder</code> オブジェクト
	 * @throws IOException
	 *             コマンドパスに誤りがあった場合
	 * @throws InterruptedException
	 *             ImageMagick コマンドの操作に失敗した場合
	 */
	private static void executeProcess(ProcessBuilder builder)
			throws IOException, InterruptedException {
		Process process = builder.start();
		process.waitFor();
	}

	/**
	 * サムネイルサイズの妥当性を確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし <code>IllegalArgumentException</code> を生成する。
	 * 
	 * <ul>
	 * <li>null である</li>
	 * <li>ブランクである</li>
	 * <li>[0-9]{1,}x[0-9]{1,} に当てはまっていない</li>
	 * </ul>
	 * 
	 * @param size
	 */
	private static void validateThumbnailSize(String size) {
		if (size == null || size.isEmpty()) {
			throw new IllegalArgumentException("size may not be specified.");
		}

		String regexp = "[0-9]{1,}x[0-9]{1,}";
		if (!size.matches(regexp)) {
			throw new IllegalArgumentException(String.format(
					"size は %s で指定してください。", regexp));
		}
	}

	/**
	 * 生成先ファイルの妥当性を確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし <code>IllegalArgumentException</code> を生成する。
	 * 
	 * <ul>
	 * <li>null である</li>
	 * </ul>
	 * 
	 * @param dest
	 *            生成先のファイル
	 */
	private static void validateDestFile(File dest) {
		if (dest == null) {
			throw new IllegalArgumentException("dest may not be specified.");
		}
	}

	/**
	 * 生成元ファイルの妥当性を確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし例外を生成する。
	 * 
	 * <ul>
	 * <li>null である → <code>IllegalArgumentException</code> を生成する</li>
	 * <li>ファイルが存在しない → <code>FileNotFoundException</code> を生成する</li>
	 * </ul>
	 * 
	 * @param src
	 *            生成元のファイル
	 * @throws FileNotFoundException
	 *             ファイルが存在しない場合
	 */
	private static void validateSrcFile(File src) throws FileNotFoundException {
		if (src == null) {
			throw new IllegalArgumentException("src may not be specified.");
		}
		if (!src.exists()) {
			throw new FileNotFoundException("src file may not Found.");
		}
	}

	/**
	 * コマンドパスの妥当性を確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし <code>IllegalArgumentException</code> を生成する。
	 * 
	 * <ul>
	 * <li>null である</li>
	 * <li>ブランクである</li>
	 * </ul>
	 * 
	 * @param commandPath
	 *            使用するコマンドのパス
	 */
	private static void validateCommandPath(String commandPath) {
		if (commandPath == null || commandPath.isEmpty()) {
			throw new IllegalArgumentException(
					"commandPath may not be specified.");
		}
	}

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
	 *             commandPath に指定されているパスが convert コマンドではなかった場合
	 * @throws InterruptedException
	 *             ImageMagick の操作に失敗した場合
	 */
	public static void removeExif(String commandPath, File src, File dest)
			throws IOException, InterruptedException {
		validateCommandPath(commandPath);
		validateSrcFile(src);
		validateDestFile(dest);

		if (log.isDebugEnabled()) {
			log.debug(String.format("commandPath:%s, srcPath:%s, destPath:%s",
					commandPath, src, dest));
		}

		ProcessBuilder builder = new ProcessBuilder(commandPath,
				src.getAbsolutePath(), "-strip", dest.getAbsolutePath());
		executeProcess(builder);
	}

}
