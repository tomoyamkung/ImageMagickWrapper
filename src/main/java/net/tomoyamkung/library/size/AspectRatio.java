package net.tomoyamkung.library.size;

import java.io.File;
import java.io.IOException;

import net.tomoyamkung.library.SquareSide;

/**
 * 縦横比を維持してサイズ（高さ、幅）を計算するクラス。
 * 
 * @author tomoyamkung
 * 
 */
public class AspectRatio {

	/**
	 * 画像の縦横比を維持してサイズを測る。
	 * 
	 * 基準となる「辺」とその長さを指定し、もう一辺は縦横比により自動計算する。
	 * 
	 * @param src
	 *            元画像のオブジェクト
	 * @param side
	 *            基準となる「辺」。幅、もしくは、高さを指定する
	 * @param pixel
	 *            基準となる「辺」の長さ
	 * @return 計算したサイズを格納したオブジェクト
	 * @throws IOException
	 *             元画像の読み込みに失敗した場合
	 */
	public static Size measure(File src, SquareSide side, int pixel)
			throws IOException {
		return measureWithSize(new Size(src), side, pixel);
	}

	/**
	 * 縦横比を維持してサイズを測る。
	 * 
	 * <code>AspectRatio#measure</code> は <code>File</code> から計算するが、本メソッドは
	 * <code>Size</code> から計算する。
	 * 
	 * @param srcSize
	 *            基準となるサイズを格納したオブジェクト
	 * @param side
	 *            基準となる「辺」。幅、もしくは、高さを指定する
	 * @param pixel
	 *            基準となる「辺」の長さ
	 * @return 計算したサイズを格納したオブジェクト
	 */
	protected static Size measureWithSize(Size srcSize, SquareSide side,
			int pixel) {
		if (side == SquareSide.landscape) {
			int height = (srcSize.getHeight() * pixel) / srcSize.getWidth();
			return new Size(pixel, height);
		}

		if (side == SquareSide.portrait) {
			int width = (srcSize.getWidth() * pixel) / srcSize.getHeight();
			return new Size(width, pixel);
		}

		return null;
	}

}
