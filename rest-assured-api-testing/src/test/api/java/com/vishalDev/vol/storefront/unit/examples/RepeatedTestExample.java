package com.onmobile.vol.storefront.unit.examples;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;

import com.onmobile.vol.storefront.TestTags;

@Tag(TestTags.UNIT_TESTS)
public class RepeatedTestExample {

	@RepeatedTest(3)
	void repeatedTest() {
		// ...
	}

}
