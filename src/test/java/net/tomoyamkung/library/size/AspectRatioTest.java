package net.tomoyamkung.library.size;

import static net.tomoyamkung.library.SquareSide.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Test;

public class AspectRatioTest {

	/**
	 * 横幅を基準にサイズを測る。
	 * 
	 */
	@Test
	public void measureLandspace() {
		Size actual = AspectRatio.measureWithSize(new Size(500, 250), landscape, 300);
		assertThat("横幅を 300px にリサイズする",
				actual, is(SamePropertyValuesAs.samePropertyValuesAs(new Size(300, 150))));
	}
	
	/**
	 * 高さを基準にサイズを測る。
	 * 
	 */
	@Test
	public void measurePortlait() {
		Size actual = AspectRatio.measureWithSize(new Size(250, 500), portrait, 300);
		assertThat("高さを 300px にリサイズする",
				actual, is(SamePropertyValuesAs.samePropertyValuesAs(new Size(150, 300))));
	}

}
