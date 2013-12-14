package net.tomoyamkung.library;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ImageMagickTest {

	private static final String COMMAND_PATH = AppProperties.getInstance()
			.get("path.to.imagemagick.convert").trim();
	private static final File src = new File("./src/test/resources/src.jpg");
	private static final File dest = new File("./src/test/resources/dest.jpg");
	private static final File notFound = new File("./src/test/resources/notfound");

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
		public void commandPathに指定したパスがconvertコマンドではなかった場合() throws Exception {
			// Setup
			String wrongPath = "/path/to/imagemagick/convert";

			// Exercise
			ImageMagick.removeExif(wrongPath, src, dest);
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
			assertThat(dest.exists(), is(true));

			Date destModifiedDate = new Date(dest.lastModified());
			assertThat(destModifiedDate.after(srcModifiedDate), is(true));

			assertThat(dest.length() < src.length(), is(true));
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
