package com.vishal.vol.storefront.config;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

public class ConfigBeforeCallback implements BeforeAllCallback {

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {

		RestAssured.baseURI = System.getProperty("test.storefront.base-url");
		RestAssured.defaultParser = Parser.JSON;

	}

}
