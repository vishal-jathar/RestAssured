package com.onmobile.vol.storefront.unit.examples;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import com.onmobile.vol.storefront.TestTags;

@Tag(TestTags.UNIT_TESTS)
public class MethodSourceExample {

	@Order(2)
	@ParameterizedTest
	@MethodSource("stringProvider")
	void testWithExplicitLocalMethodSource(String argument) {
		assertNotNull(argument);
	}

	static Stream<String> stringProvider() {
		return Stream.of("apple", "banana");
	}

	@Order(1)
	@ParameterizedTest
	@MethodSource("stringIntAndListProvider")
	void testWithMultiArgMethodSource(String str, int num, List<String> list) {
		assertEquals(5, str.length());
		assertTrue(num >= 1 && num <= 2);
		assertEquals(2, list.size());
	}

	static Stream<Arguments> stringIntAndListProvider() {
		return Stream.of(Arguments.of("apple", 1, Arrays.asList("a", "b")),
				Arguments.of("lemon", 2, Arrays.asList("x", "y")));
	}

}
