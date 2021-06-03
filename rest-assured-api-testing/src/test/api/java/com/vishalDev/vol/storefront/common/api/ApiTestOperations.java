package com.onmobile.vol.storefront.common.api;

import java.nio.file.Path;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public interface ApiTestOperations {

	@FunctionalInterface
	interface ValidateHttpStatusCode {
		void validateHttpStatusCode(Response response, int httpStatusCode);
	}

	@FunctionalInterface
	interface ValidateHttpHeaders {
		void validateHttpHeaders(Response response);
	}

	@FunctionalInterface
	interface ValidateResponseSchema {
		void validateResponseSchema(Response response, String schemaFilePath);
	}

	@FunctionalInterface
	interface MatchResponseBody {
		void matchResponseBody(Response response, String resourceFolderName, String responseExactMatcherFileName);
	}

	// General
	Path getPayloadRelativePath(String folder, String requestPayloadFileName);

	void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase);

	void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase);

	void validateResponseForNegativeTestCase(Response response, ApiTestRequestBase requestBase);

	// Perform Negative Test Validation methods
	void performNegativeTestCaseValidations(Response response, ApiTestRequestBase requestBase);

	void performNegativeTestCaseValidations(Response response, ApiTestRequestBase requestBase,
			ValidateHttpHeaders validateHttpHeaders);

	void performNegativeTestCaseValidations(Response response, ApiTestRequestBase requestBase,
			ValidateHttpStatusCode validateHttpStatusCode, ValidateHttpHeaders validateHttpHeaders);

	// Perform Positive Test Validation methods
	void performPositiveTestCaseValidations(Response response, String responseJsonSchemaPath,
			ApiTestRequestBase requestBase);

	void performPositiveTestCaseValidations(Response response, String responseJsonSchemaPath,
			ApiTestRequestBase requestBase, ValidateHttpHeaders validateHttpHeaders);

	void performPositiveTestCaseValidations(Response response, String responseJsonSchemaPath,
			ApiTestRequestBase requestBase, ValidateHttpStatusCode validateHttpStatusCode,
			ValidateHttpHeaders validateHttpHeaders, ValidateResponseSchema validateResponseSchema,
			MatchResponseBody matchResponseBody);

}
