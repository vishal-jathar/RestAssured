package com.vishal.vol.storefront.unit.examples;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import com.onmobile.vol.storefront.TestTags;

@Tag(TestTags.UNIT_TESTS)
@DisplayName("Some sample tests")
public class DisabledTestExample {

	@Disabled("This test case is disabled until some bug is fixed.")
	@Test
	void someTestThatWillBeSkippedUntilFixIsProvided() {
		// ...
	}

	@Test
	@DisabledIf("someCustomCondition")
	void conditionalDisabled() {
		// ...
	}

	boolean someCustomCondition() {
		return true;
	}
}
