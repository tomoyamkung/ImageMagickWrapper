package net.tomoyamkung.library;

import java.io.IOException;

/**
 * ImageMagick を操作する Wrapper クラス。
 * 
 * @author tomoyamkung
 * 
 */
public class ImageMagick {

	/**
	 * Exif を削除する。
	 * 
	 * @param commandPath
	 *            convert コマンドの絶対パス。
	 * @param srcPath
	 *            生成元の絶対パス。Exif を削除したい画像の絶対パス
	 * @param destPath
	 *            生成先の絶対パス。このパスに画像を作成する
	 * @throws IOException
	 *             ImageMagick の操作に失敗した場合
	 * @throws InterruptedException
	 *             ImageMagick の操作に失敗した場合
	 */
	public static void removeExif(String commandPath, String srcPath,
			String destPath) throws IOException, InterruptedException {
		ProcessBuilder builder = new ProcessBuilder(commandPath, srcPath,
				"-strip", destPath);

		Process process = builder.start();
		process.waitFor();
	}

}
