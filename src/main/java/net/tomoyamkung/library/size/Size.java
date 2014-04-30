package net.tomoyamkung.library.size;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 画像の幅と高さを保持するクラス。
 * 
 * @author tomoyamkung
 *
 */
public class Size {

	/**
	 * 画像の幅。
	 */
	private final int width;

	/**
	 * 画像の高さ。
	 */
	private final int height;

	/**
	 * コンストラクタ。
	 * 
	 * 幅と高さを計算する画像の <code>File</code> オブジェクトを受け取る。
	 * 
	 * @param file
	 *            幅と高さを計算する画像の <code>File</code> オブジェクト
	 * @throws IOException
	 *             画像の読み込みに失敗した場合
	 */
	public Size(File file) throws IOException {
		BufferedImage image = ImageIO.read(file);

		width = image.getWidth();
		height = image.getHeight();
	}

	/**
	 * コンストラクタ。
	 * 
	 * 幅と高さを受け取る。
	 * 
	 * @param width
	 *            幅
	 * @param height
	 *            高さ
	 */
	public Size(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public String toString() {
		return String.format("%sx%s", width, height);

	}

}
