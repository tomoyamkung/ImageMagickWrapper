package net.tomoyamkung.library.size;

import static net.tomoyamkung.library.SquareSide.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.hamcrest.beans.SamePropertyValuesAs;
import org.junit.Test;

public class AspectRatioTest {

	@Test
	public void fromLandspace() {
		Size actual = AspectRatio.measureWithSize(new Size(250, 500), landscape, 300);
		assertThat(actual, is(SamePropertyValuesAs.samePropertyValuesAs(new Size(150, 300))));
	}
	
	@Test
	public void fromPortlait() {
		Size actual = AspectRatio.measureWithSize(new Size(500, 250), portrait, 300);
		assertThat(actual, is(SamePropertyValuesAs.samePropertyValuesAs(new Size(300, 150))));
	}

}
