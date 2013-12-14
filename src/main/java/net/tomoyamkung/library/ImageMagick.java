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
	public static void removeExif(String commandPath, File src, File dest)
			throws IOException, InterruptedException {
		if (commandPath == null || commandPath.isEmpty()) {
			throw new IllegalArgumentException(
					"commandPath may not be specified.");
		}
		if (src == null) {
			throw new IllegalArgumentException("src may not be specified.");
		}
		if (!src.exists()) {
			throw new FileNotFoundException("src file may not Found.");
		}
		if (dest == null) {
			throw new IllegalArgumentException("dest may not be specified.");
		}

		if (log.isDebugEnabled()) {
			log.debug(String.format("commandPath:%s, srcPath:%s, destPath:%s",
					commandPath, src, dest));
		}

		ProcessBuilder builder = new ProcessBuilder(commandPath,
				src.getAbsolutePath(), "-strip", dest.getAbsolutePath());

		Process process = builder.start();
		process.waitFor();
	}

}
