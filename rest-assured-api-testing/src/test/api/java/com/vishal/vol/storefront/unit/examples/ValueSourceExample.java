package com.vishal.vol.storefront.unit.examples;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.onmobile.vol.storefront.TestTags;

@Tag(TestTags.UNIT_TESTS)
public class ValueSourceExample {

	@ParameterizedTest
	@ValueSource(strings = { "racecar", "radar", "able was I ere I saw elba" })
	void palindromes(String candidate) {
		assertTrue(isPalindrome(candidate));
	}

	private static boolean isPalindrome(String str) {
		if (str == null)
			return false;
		int len = str.length();
		for (int i = 0; i < len / 2; i++) {
			if (str.charAt(i) != str.charAt(len - i - 1)) {
				return false;
			}
		}
		return true;
	}

}
