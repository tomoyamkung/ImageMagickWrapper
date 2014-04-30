package net.tomoyamkung.library;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.tomoyamkung.library.size.AspectRatio;
import net.tomoyamkung.library.size.Size;
import net.tomoyamkung.library.util.ListUtil;

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
		Validator.validateCommandPath(commandPath);
		Validator.validateSrcFile(src);
		Validator.validateDestFile(dest);
		Validator.validatePixel(pixel);

		Size resizedSize = AspectRatio.measure(src, side, pixel);
		createThumbnail(commandPath, resizedSize.toString(),
				src.getAbsolutePath(), dest.getAbsolutePath());
	}

	/**
	 * サムネイル画像を作成する。
	 * 
	 * @param commandPath
	 *            convert コマンドの絶対パス。
	 * @param size
	 *            サムネイルサイズ。[0-9]{1,}x[0-9]{1,} で指定する
	 * @param srcAbsolutePath
	 *            生成元画像の絶対パス
	 * @param destAbsolutePath
	 *            生成先画像の絶対パス
	 * @throws InterruptedException
	 *             ImageMagick の操作に失敗した場合
	 * @throws IOException
	 *             commandPath に指定されているパスが convert コマンドではなかった場合
	 */
	private static void createThumbnail(String commandPath, String size,
			String srcAbsolutePath, String destAbsolutePath)
			throws InterruptedException, IOException {
		writeDebugLog(String.format(
				"commandPath:%s, srcPath:%s, destPath:%s, size:%s",
				commandPath, srcAbsolutePath, destAbsolutePath, size));

		ProcessBuilder builder = new ProcessBuilder(commandPath, "-thumbnail",
				size, srcAbsolutePath, destAbsolutePath);
		executeProcess(builder);
	}

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
		Validator.validateCommandPath(commandPath);
		Validator.validateSrcFile(src);
		Validator.validateDestFile(dest);
		Validator.validateSize(size, "サムネイルサイズ");

		ProcessBuilder builder = new ProcessBuilder(commandPath, "-thumbnail",
				size, src.getAbsolutePath(), dest.getAbsolutePath());
		executeProcess(builder);
		createThumbnail(commandPath, size, src.getAbsolutePath(),
				dest.getAbsolutePath());
	}

	/**
	 * ログに出力する。
	 * 
	 * ログレベルが debug に設定されている場合、ログに出力する。
	 * 
	 * @param message
	 *            出力するメッセージ
	 */
	private static void writeDebugLog(String message) {
		if (log.isDebugEnabled()) {
			log.debug(message);
		}
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
		Validator.validateCommandPath(commandPath);
		Validator.validateSrcFile(src);
		Validator.validateDestFile(dest);

		writeDebugLog(String.format("commandPath:%s, srcPath:%s, destPath:%s",
				commandPath, src, dest));

		ProcessBuilder builder = new ProcessBuilder(commandPath,
				src.getAbsolutePath(), "-strip", dest.getAbsolutePath());
		executeProcess(builder);
	}

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
	public static void runProcessDirectly(Command command) throws IOException,
			InterruptedException {
		command.validate();

		writeDebugLog(command.toString());

		ProcessBuilder builder = new ProcessBuilder(command.getCommand());
		executeProcess(builder);
	}

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
	public static void createMontage(String commandPath, List<File> srcFiles,
			String tile, String geometry, File dest) throws IOException,
			InterruptedException {
		Validator.validateCommandPath(commandPath);
		validateSrcFiles(srcFiles);
		Validator.validateSize(tile, "結合する形式");
		validateSrcFileSize(srcFiles, tile);
		Validator.validateSize(geometry, "結合元画像ファイルの大きさ");
		Validator.validateDestFile(dest);

		String message = String.format(
				"commandPath:%s, srcFiles:%s, tile:%s, geometry:%s, dest:%s",
				commandPath, srcFiles.toString(), tile, geometry, dest);
		writeDebugLog(message);

		List<String> command = new ArrayList<String>();
		command.add(commandPath);
		command.add("-tile");
		command.add(tile);
		command.add("-geometry");
		command.add(geometry);
		for (File src : srcFiles) {
			command.add(src.getAbsolutePath());
		}
		command.add(dest.getAbsolutePath());

		ProcessBuilder builder = new ProcessBuilder(command);
		executeProcess(builder);
	}

	/**
	 * 生成元画像の枚数と tile で指定した値が一致しているかを確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし <code>IllegalArgumentException</code> を生成する。
	 * 
	 * <ul>
	 * <li>生成元画像の枚数と tile で指定した値が一致していない</li>
	 * </ul>
	 * 
	 * @param srcFiles
	 *            生成元のファイルを格納したリスト
	 * @param tile
	 *            結合する形式
	 */
	private static void validateSrcFileSize(List<File> srcFiles, String tile) {
		String[] split = tile.split("x");
		int numOfColumns = Integer.parseInt(split[0]);
		int numOfRows = Integer.parseInt(split[1]);
		int numOfTiles = numOfColumns * numOfRows;
		if (numOfTiles != srcFiles.size()) {
			throw new IllegalArgumentException("結合する形式と画像の枚数が合っていません。");
		}
	}

	/**
	 * 生成元ファイルの妥当性を確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし例外を生成する。
	 * 
	 * <ul>
	 * <li>null である → <code>IllegalArgumentException</code> を生成する</li>
	 * <li>空である → <code>IllegalArgumentException</code> を生成する</li>
	 * <li>存在しないファイルが含まれている → <code>FileNotFoundException</code> を生成する</li>
	 * </ul>
	 * 
	 * @param srcFiles
	 *            生成元のファイルを格納したリスト
	 * @throws FileNotFoundException
	 *             ファイルが存在しない場合
	 */
	private static void validateSrcFiles(List<File> srcFiles)
			throws FileNotFoundException {
		if (ListUtil.isNullOrEmpty(srcFiles)) {
			throw new IllegalArgumentException("srcFiles may not be specified.");
		}
		for (File src : srcFiles) {
			Validator.validateSrcFile(src);
		}
	}

}
