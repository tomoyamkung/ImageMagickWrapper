package net.tomoyamkung.library;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * <code>ImageMagick</code> クラスのテストケース。
 * 
 * @author tomoyamkung
 * 
 */
@RunWith(Enclosed.class)
public class ImageMagickTest {

	/**
	 * ImageMagick の convert コマンドのパス。
	 */
	private static final String COMMAND_PATH = AppProperties.getInstance()
			.get("path.to.imagemagick.convert").trim();

	/**
	 * convert コマンドの誤ったパス。
	 */
	private static final String WRONG_COMMAND_PATH = "/path/to/imagemagick/convert";

	/**
	 * テストで使用する画像ファイル。
	 */
	private static final File src = new File("./src/test/resources/src.jpg");

	/**
	 * テストで作成される画像ファイル。
	 */
	private static final File dest = new File("./src/test/resources/dest.jpg");

	/**
	 * 存在しないファイル。
	 */
	private static final File notFound = new File(
			"./src/test/resources/notfound");

	/**
	 * <code>ImageMagick#createThumbnail</code> についてのテストケース。
	 * 
	 * @author tomoyamkung
	 * 
	 */
	@RunWith(Enclosed.class)
	public static class CreateThumbnail {

		/**
		 * サムネイル画像の縦横サイズ。
		 */
		private static final String SIZE = "200x100";

		public static class 異常系 {

			@Before
			public void setUp() throws Exception {
				deleteTestFiles();
			}

			@After
			public void tearDown() throws Exception {
				deleteTestFiles();
			}

			@Test(expected = IllegalArgumentException.class)
			public void commandPathがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(null, src, dest, SIZE);
				// Verify
			}

			@Test(expected = IOException.class)
			public void commandPathに指定したパスがconvertコマンドではなかった場合()
					throws Exception {
				// Setup
				// Exercise
				ImageMagick
						.createThumbnail(WRONG_COMMAND_PATH, src, dest, SIZE);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_PATH, null, dest, SIZE);
				// Verify
			}

			@Test(expected = FileNotFoundException.class)
			public void srcのファイルが存在しない場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_PATH, notFound, dest, SIZE);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void destがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_PATH, src, null, SIZE);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void sizeがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_PATH, src, dest, null);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void sizeの書式が異なるの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_PATH, src, dest, "ax!00");
				// Verify
			}

		}

		public static class 正常系 {

			@Before
			public void setUp() throws Exception {
				deleteTestFiles();
			}

			@After
			public void tearDown() throws Exception {
				deleteTestFiles();
			}

			@Test
			public void 縦横比を維持してサイズ200x100のサムネイルを作成する() throws Exception {
				// Setup
				Date srcModifiedDate = new Date(src.lastModified());

				// Exercise
				ImageMagick.createThumbnail(COMMAND_PATH, src, dest, SIZE);

				// Verify
				assertThat("画像ファイルが生成されること", dest.exists(), is(true));

				Date destModifiedDate = new Date(dest.lastModified());
				assertThat("dest のほうが src よりも時間的に後に作成されていること",
						destModifiedDate.after(srcModifiedDate), is(true));

				assertThat("dest のほうが src よりもファイルサイズが小さいこと",
						dest.length() < src.length(), is(true));

				BufferedImage destImage = ImageIO.read(dest);
				assertThat("縦横比を維持したため width は 200 にならない",
						destImage.getWidth(), is(133));
				assertThat("dest の高さが 100 ピクセルであること", destImage.getHeight(),
						is(100));
			}

		}
	}

	/**
	 * <code>ImageMagick#removeExif</code> についてのテストケース。
	 * 
	 * @author tomoyamkung
	 * 
	 */
	@RunWith(Enclosed.class)
	public static class RemoveExif {

		public static class 異常系 {

			@Before
			public void setUp() throws Exception {
				deleteTestFiles();
			}

			@After
			public void tearDown() throws Exception {
				deleteTestFiles();
			}

			@Test(expected = IllegalArgumentException.class)
			public void commandPathがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(null, src, dest);
				// Verify
			}

			@Test(expected = IOException.class)
			public void commandPathに指定したパスがconvertコマンドではなかった場合()
					throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(WRONG_COMMAND_PATH, src, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(COMMAND_PATH, null, dest);
				// Verify
			}

			@Test(expected = FileNotFoundException.class)
			public void srcのファイルが存在しない場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(COMMAND_PATH, notFound, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void destがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(COMMAND_PATH, src, null);
				// Verify
			}

		}

		public static class 正常系 {

			@Before
			public void setUp() throws Exception {
				deleteTestFiles();
			}

			@After
			public void tearDown() throws Exception {
				deleteTestFiles();
			}

			@Test
			public void Exifを削除したファイルが作成される() throws Exception {
				// Setup
				assertThat(COMMAND_PATH.isEmpty(), is(false));
				assertThat(src, is(not(nullValue(File.class))));

				Date srcModifiedDate = new Date(src.lastModified());

				// Exercise
				ImageMagick.removeExif(COMMAND_PATH, src, dest);

				// Verify
				assertThat("画像ファイルが生成されること", dest.exists(), is(true));

				Date destModifiedDate = new Date(dest.lastModified());
				assertThat("dest のほうが src よりも時間的に後に作成されていること",
						destModifiedDate.after(srcModifiedDate), is(true));

				assertThat("dest のほうが src よりもファイルサイズが小さいこと",
						dest.length() < src.length(), is(true));
			}

		}
	}

	/**
	 * テスト用のファイルを削除する。
	 */
	private static void deleteTestFiles() {
		if (dest.exists()) {
			dest.delete();
		}
		if (notFound.exists()) {
			notFound.delete();
		}
	}

}
