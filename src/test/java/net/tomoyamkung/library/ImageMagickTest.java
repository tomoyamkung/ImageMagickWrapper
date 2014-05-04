package net.tomoyamkung.library;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import net.tomoyamkung.library.props.AppProperties;
import net.tomoyamkung.library.size.Size;

import org.hamcrest.beans.SamePropertyValuesAs;
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
	private static final String COMMAND_CONVERT_PATH = AppProperties
			.getInstance().get("path.to.imagemagick.convert");

	/**
	 * convert コマンドの誤ったパス。
	 */
	private static final String WRONG_COMMAND_CONVERT_PATH = "/path/to/imagemagick/convert";

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
	 * サムネイル画像の縦横サイズ。
	 */
	private static final String SIZE = "200x100";

	@RunWith(Enclosed.class)
	public static class Resize {

		public static class 異常系 {

			@Test(expected = IllegalArgumentException.class)
			public void commandPathがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(null, src, dest, SquareSide.portrait, 1);
				// Verify
			}

			@Test(expected = IOException.class)
			public void commandPathに指定したパスがconvertコマンドではなかった場合()
					throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(WRONG_COMMAND_CONVERT_PATH, src, dest,
						SquareSide.portrait, 1);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(COMMAND_CONVERT_PATH, null, dest,
						SquareSide.portrait, 1);
				// Verify
			}

			@Test(expected = FileNotFoundException.class)
			public void srcのファイルが存在しない場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(COMMAND_CONVERT_PATH, notFound, dest,
						SquareSide.portrait, 1);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void destがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(COMMAND_CONVERT_PATH, src, null,
						SquareSide.portrait, 1);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void pixelが負の場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(COMMAND_CONVERT_PATH, src, dest,
						SquareSide.portrait, -1);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void pixelが0の場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(COMMAND_CONVERT_PATH, src, dest,
						SquareSide.portrait, 0);
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
			public void 元画像を400x300にリサイズ() throws Exception {
				// Setup
				// Exercise
				ImageMagick.resize(COMMAND_CONVERT_PATH, src, dest,
						SquareSide.landscape, 400);
				// Verify
				assertThat("リサイズした画像の大きさが 400,300 であること", new Size(dest),
						is(SamePropertyValuesAs.samePropertyValuesAs(new Size(
								400, 300))));
			}
		}

	}

	@RunWith(Enclosed.class)
	public static class CreateMontage {

		/**
		 * ImageMagick の montage コマンドのパス。
		 */
		private static final String COMMAND_MONTAGE_PATH = AppProperties
				.getInstance().get("path.to.imagemagick.montage");

		/**
		 * montage コマンドの誤ったパス。
		 */
		private static final String WRONG_COMMAND_MONTAGE_PATH = "/path/to/imagemagick/montage";

		/**
		 * テストで使用する結合用画像ファイル（アルファ25）。
		 */
		private static final File srcMontage25 = new File(
				"./src/test/resources/src_montage_25.png");

		/**
		 * テストで使用する結合用画像ファイル（アルファ50）。
		 */
		private static final File srcMontage50 = new File(
				"./src/test/resources/src_montage_50.png");

		/**
		 * テストで使用する結合用画像ファイル（アルファ75）。
		 */
		private static final File srcMontage75 = new File(
				"./src/test/resources/src_montage_75.png");

		/**
		 * テストで使用する結合用画像ファイル（アルファ100）。
		 */
		private static final File srcMontage100 = new File(
				"./src/test/resources/src_montage_100.png");

		/**
		 * 結合用画像ファイルを格納したリスト。
		 */
		private static List<File> srcFiles;

		/**
		 * 結合する形式（2x2 とか 9x5 といった形式で指定する）。
		 */
		private static final String TILE = "2x2";

		/**
		 * 結合元画像ファイルの大きさ（100x100 といった形式で指定する）。
		 */
		private static final String GEOMETRY = "100x100";

		public static class 異常系 {

			@Before
			public void setUp() throws Exception {
				deleteTestFiles();

				srcFiles = new ArrayList<File>();
				srcFiles.add(srcMontage25);
				srcFiles.add(srcMontage50);
				srcFiles.add(srcMontage75);
				srcFiles.add(srcMontage100);
			}

			@After
			public void tearDown() throws Exception {
				deleteTestFiles();
			}

			@Test(expected = IllegalArgumentException.class)
			public void commandPathがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(null, srcFiles, TILE, GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IOException.class)
			public void commandPathに指定したパスがmontageコマンドではなかった場合()
					throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(WRONG_COMMAND_MONTAGE_PATH, srcFiles,
						TILE, GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcFilesがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, null, TILE,
						GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcFilesが空の場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH,
						new ArrayList<File>(), TILE, GEOMETRY, dest);
				// Verify
			}

			@Test(expected = FileNotFoundException.class)
			public void srcFilesに値は格納されているがファイルが存在しない場合() throws Exception {
				// Setup
				List<File> srcFiles = new ArrayList<File>();
				srcFiles.add(notFound);

				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles, TILE,
						GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void tileがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles, null,
						GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void tileがブランクの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles, "",
						GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void tileの書式が異なっている() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles,
						"1x!", GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcFilesに格納されている数とtileで指定した数が異なっている() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles,
						"2x3", GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void geometryがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles, TILE,
						null, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void geometryがブランクの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles, TILE,
						"", dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void geometryの書式が異なっている() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles,
						"1x!", GEOMETRY, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void destがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles, TILE,
						GEOMETRY, null);
				// Verify
			}

		}

		public static class 正常系 {

			@Before
			public void setUp() throws Exception {
				deleteTestFiles();

				srcFiles = new ArrayList<File>();
				srcFiles.add(srcMontage25);
				srcFiles.add(srcMontage50);
				srcFiles.add(srcMontage75);
				srcFiles.add(srcMontage100);
			}

			@After
			public void tearDown() throws Exception {
				deleteTestFiles();
			}

			@Test
			public void モンタージュ画像を作成() throws Exception {
				// Setup
				Date srcModifiedDate = new Date(src.lastModified());

				// Exercise
				ImageMagick.createMontage(COMMAND_MONTAGE_PATH, srcFiles, TILE,
						GEOMETRY, dest);

				// Verify
				assertThat("モンタージュ画像が生成されること", dest.exists(), is(true));

				Date destModifiedDate = new Date(dest.lastModified());
				assertThat("モンタージュ画像のほうが元画像よりも時間的に後に作成されていること",
						destModifiedDate.after(srcModifiedDate), is(true));

				BufferedImage destImage = ImageIO.read(dest);
				assertThat("モンタージュ画像の横幅が 200px であること", destImage.getWidth(),
						is(200));
				assertThat("モンタージュ画像の高さが 200px であること", destImage.getHeight(),
						is(200));

			}

		}

	}

	/**
	 * <code>ImageMagick#createThumbnail</code> についてのテストケース。
	 * 
	 * @author tomoyamkung
	 * 
	 */
	@RunWith(Enclosed.class)
	public static class CreateThumbnail {

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
				ImageMagick.createThumbnail(WRONG_COMMAND_CONVERT_PATH, src,
						dest, SIZE);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_CONVERT_PATH, null, dest,
						SIZE);
				// Verify
			}

			@Test(expected = FileNotFoundException.class)
			public void srcのファイルが存在しない場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_CONVERT_PATH, notFound,
						dest, SIZE);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void destがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_CONVERT_PATH, src, null,
						SIZE);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void sizeがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_CONVERT_PATH, src, dest,
						null);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void sizeの書式が異なるの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.createThumbnail(COMMAND_CONVERT_PATH, src, dest,
						"ax!00");
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
				ImageMagick.createThumbnail(COMMAND_CONVERT_PATH, src, dest,
						SIZE);

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
				ImageMagick.removeExif(WRONG_COMMAND_CONVERT_PATH, src, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void srcがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(COMMAND_CONVERT_PATH, null, dest);
				// Verify
			}

			@Test(expected = FileNotFoundException.class)
			public void srcのファイルが存在しない場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(COMMAND_CONVERT_PATH, notFound, dest);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void destがNullの場合() throws Exception {
				// Setup
				// Exercise
				ImageMagick.removeExif(COMMAND_CONVERT_PATH, src, null);
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
				assertThat(COMMAND_CONVERT_PATH.isEmpty(), is(false));
				assertThat(src, is(not(nullValue(File.class))));

				Date srcModifiedDate = new Date(src.lastModified());

				// Exercise
				ImageMagick.removeExif(COMMAND_CONVERT_PATH, src, dest);

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
	 * <code>ImageMagick#runProcessDirectly</code> についてのテストケース。
	 * 
	 * @author tomoyamkung
	 * 
	 */
	@RunWith(Enclosed.class)
	public static class RunProcessDirectly {

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
			public void コマンドパスがnullの場合() throws Exception {
				// Setup
				Command command = new Command(null);
				command.addParameter("dummy");

				// Exercise
				ImageMagick.runProcessDirectly(command);
				// Verify
			}

			@Test(expected = IllegalArgumentException.class)
			public void パラメータが空の場合() throws Exception {
				// Setup
				Command command = new Command(COMMAND_CONVERT_PATH);

				// Exercise
				ImageMagick.runProcessDirectly(command);
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
			public void パラメータを直接指定してExifを削除する() throws Exception {
				// Setup
				Command command = new Command(COMMAND_CONVERT_PATH);
				command.addParameter(src.getAbsolutePath())
						.addParameter("-strip")
						.addParameter(dest.getAbsolutePath());

				Date srcModifiedDate = new Date(src.lastModified());

				// Exercise
				ImageMagick.runProcessDirectly(command);

				// Verify
				assertThat("画像ファイルが生成されること", dest.exists(), is(true));

				Date destModifiedDate = new Date(dest.lastModified());
				assertThat("dest のほうが src よりも時間的に後に作成されていること",
						destModifiedDate.after(srcModifiedDate), is(true));

				assertThat("dest のほうが src よりもファイルサイズが小さいこと",
						dest.length() < src.length(), is(true));
			}

			@Test
			public void 縦横比を維持してサイズ200x100のサムネイルを作成する() throws Exception {
				// Setup
				Command command = new Command(COMMAND_CONVERT_PATH);
				command.addParameter("-thumbnail").addParameter(SIZE)
						.addParameter(src.getAbsolutePath())
						.addParameter(dest.getAbsolutePath());

				Date srcModifiedDate = new Date(src.lastModified());

				// Exercise
				ImageMagick.runProcessDirectly(command);

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
