package net.tomoyamkung.library;

import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class ValidatorTest {

	public static class ValidatePixel {
		
		@Test(expected = IllegalArgumentException.class)
		public void ピクセルの値に負の値が指定された場合() {
			Validator.validatePixel(-1);
		}
		
		@Test(expected = IllegalArgumentException.class)
		public void ピクセルの値に0が指定された場合() {
			Validator.validatePixel(0);
		}
		
		@Test
		public void ピクセルの値に1が指定された場合() {
			Validator.validatePixel(1);
		}

	}

}
