package net.tomoyamkung.library;

import java.io.File;
import java.io.FileNotFoundException;

import net.tomoyamkung.library.util.IntegerUtil;
import net.tomoyamkung.library.util.StringUtil;

public class Validator {

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
	public static void validateCommandPath(String commandPath) {
		if (StringUtil.isNullOrEmpty(commandPath)) {
			throw new IllegalArgumentException(
					"commandPath may not be specified.");
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
	public static void validateSrcFile(File src) throws FileNotFoundException {
		if (src == null) {
			throw new IllegalArgumentException("src may not be specified.");
		}
		if (!src.exists()) {
			throw new FileNotFoundException("src file may not Found.");
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
	public static void validateDestFile(File dest) {
		if (dest == null) {
			throw new IllegalArgumentException("dest may not be specified.");
		}
	}

	/**
	 * サイズ指定の妥当性を確認する。
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
	 * @param keyword
	 *            認する項目名
	 */
	public static void validateSize(String size, String keyword) {
		if (StringUtil.isNullOrEmpty(size)) {
			throw new IllegalArgumentException(String.format(
					"%s may not be specified.", keyword));
		}
	
		String regexp = "[0-9]{1,}x[0-9]{1,}";
		if (!size.matches(regexp)) {
			throw new IllegalArgumentException(String.format(
					"%s は %s で指定してください。", keyword, regexp));
		}
	}

	/**
	 * 画像の横幅や縦幅に指定するピクセルの妥当性を確認する。
	 * 
	 * 次の条件に当てはまる場合は不適切と見なし <code>IllegalArgumentException</code> を生成する。
	 * 
	 * <ul>
	 * <li>値が 0 以下である</li>
	 * </ul>
	 * 
	 * @param pixel ピクセル
	 */
	public static void validatePixel(int pixel) {
		if(IntegerUtil.isLessThanOrEqual(String.valueOf(pixel), 0)) {
			throw new IllegalArgumentException("ピクセルは 1 以上の値を指定してください。");
		}
		
	}

}
