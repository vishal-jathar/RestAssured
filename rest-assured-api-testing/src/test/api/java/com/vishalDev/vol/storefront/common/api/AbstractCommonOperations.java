package com.onmobile.vol.storefront.common.api;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.junit.jupiter.api.Assertions;

import com.onmobile.vol.storefront.Constants;
import com.onmobile.vol.storefront.common.api.ApiTestOperations.ValidateHttpHeaders;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public abstract class AbstractCommonOperations implements ApiTestOperations {

	public static final ValidateHttpStatusCode DEFAULT_HTTP_STATUS_CODE_VALIDATOR = (response,
			expectedHttpStatusCode) -> {
		response.then().statusCode(expectedHttpStatusCode).log().status().log().all();
	};

	public static final ValidateHttpHeaders NO_HTTP_HEADERS_VALIDATION = (response) -> {
	};

	public static final ValidateHttpHeaders DEFAULT_HTTP_HEADERS_VALIDATOR = (response) -> {
		Assertions.assertEquals(response.header(HttpHeaders.CONTENT_TYPE),
				Constants.STOREFRONT_CONTENT_TYPE_RESPONSE_HEADER_VALUE);
	};

	public static final ValidateResponseSchema DEFAULT_RESPONSE_SCHEMA_VALIDATOR = (response, schemaFilePath) -> {
		assertThat(response.body().asString(), matchesJsonSchemaInClasspath(schemaFilePath));
	};

	public final MatchResponseBody DEFAULT_RESPONSE_BODY_MATCHER = (response, resourceFolderPath,
			responseExactMatcherFileName) -> {
		try {
			String responseToMatchWith = Files.readString(
					Path.of("src", "test", "api", "resources", resourceFolderPath, responseExactMatcherFileName));

			JsonPath jsonResponseToMatchWith = JsonPath.from(responseToMatchWith);
			response.then().body("", equalTo(jsonResponseToMatchWith.getMap("")));
		} catch (IOException ioe) {
			throw new RuntimeException("Failed to read responseExactMatcherFile: " + responseExactMatcherFileName
					+ " from:" + resourceFolderPath + ". Exception message: " + ioe.getMessage());
		}

	};

	@Override
	public void performPositiveTestCaseValidations(Response response, String apiTestResourceContextPath,
			ApiTestRequestBase requestBase, ValidateHttpStatusCode validateHttpStatusCode,
			ValidateHttpHeaders validateHttpHeaders, ValidateResponseSchema validateResponseSchema,
			MatchResponseBody matchResponseBody) {
		// HTTP STATUS CODE VALIDATION
		validateHttpStatusCode.validateHttpStatusCode(response, requestBase.getHttpStatusCode());

		// RESPONSE HEADERS VALIDATION
		validateHttpHeaders.validateHttpHeaders(response);

		// RESPONSE BODY JSON SCHEMA VALIDATION
		if (StringUtils.isNotBlank(requestBase.getResponseSchemaFileName())) {
			validateResponseSchema.validateResponseSchema(response,
					apiTestResourceContextPath + requestBase.getResponseSchemaFileName());
		}
		
		

		// RESPONSE BODY VALIDATION
		if (System.getProperty("test.storefront.mode").equalsIgnoreCase(Constants.TEST_EXECUTION_MODE_MOCK)) {

			if (StringUtils.isNotBlank(requestBase.getResponseExactMatcherFileName())) {
				matchResponseBody.matchResponseBody(response, apiTestResourceContextPath,
						requestBase.getResponseExactMatcherFileName());
			}
			validateResponseForPositiveTestCase(response, requestBase);
		}
	}

	@Override
	public void performPositiveTestCaseValidations(Response response, String apiTestResourceContextPath,
			ApiTestRequestBase requestBase) {

		performPositiveTestCaseValidations(response, apiTestResourceContextPath, requestBase,
				DEFAULT_HTTP_STATUS_CODE_VALIDATOR, DEFAULT_HTTP_HEADERS_VALIDATOR, DEFAULT_RESPONSE_SCHEMA_VALIDATOR,
				DEFAULT_RESPONSE_BODY_MATCHER);

	}

	@Override
	public void performPositiveTestCaseValidations(Response response, String apiTestResourceContextPath,
			ApiTestRequestBase requestBase, ValidateHttpHeaders validateHttpHeaders) {
		performPositiveTestCaseValidations(response, apiTestResourceContextPath, requestBase,
				DEFAULT_HTTP_STATUS_CODE_VALIDATOR, validateHttpHeaders, DEFAULT_RESPONSE_SCHEMA_VALIDATOR,
				DEFAULT_RESPONSE_BODY_MATCHER);
	}

	@Override
	public void performNegativeTestCaseValidations(Response response, ApiTestRequestBase requestBase,
			ValidateHttpStatusCode validateHttpStatusCode, ValidateHttpHeaders validateHttpHeaders) {
		// HTTP STATUS CODE VALIDATION
		validateHttpStatusCode.validateHttpStatusCode(response, requestBase.getHttpStatusCode());

		// RESPONSE HEADERS VALIDATION
		validateHttpHeaders.validateHttpHeaders(response);

		// RESPONSE BODY VALIDATION
		validateResponseForNegativeTestCase(response, requestBase);
	}

	@Override
	public void performNegativeTestCaseValidations(Response response, ApiTestRequestBase requestBase) {
		performNegativeTestCaseValidations(response, requestBase, DEFAULT_HTTP_STATUS_CODE_VALIDATOR,
				DEFAULT_HTTP_HEADERS_VALIDATOR);
	}

	@Override
	public void performNegativeTestCaseValidations(Response response, ApiTestRequestBase requestBase,
			ValidateHttpHeaders validateHttpHeaders) {
		performNegativeTestCaseValidations(response, requestBase, DEFAULT_HTTP_STATUS_CODE_VALIDATOR,
				validateHttpHeaders);
	}

	@Override
	public Path getPayloadRelativePath(String folder, String requestPayloadFileName) {
		Assertions.assertNotNull(folder, "AbstractCommonOperations#getPayloadRelativePath param 'folder' is null.");
		Assertions.assertNotNull(requestPayloadFileName,
				"AbstractCommonOperations#getPayloadRelativePath param 'requestPayloadFileName' is null.");
		return Paths.get("src", "test", "api", "resources", System.getProperty("test.storefront.mode"), "com",
				"onmobile", "vol", "storefront", "api", folder, requestPayloadFileName);
	}

	public void validateResponseForNegativeTestCase(Response response, ApiTestRequestBase requestBase) {
		response.then().body("$", hasKey("code")).body("$", hasKey("sub_code")).body("$", hasKey("description"));

		response.then().body("code", equalTo(requestBase.getFailureCode()))
				.body("sub_code", equalTo(requestBase.getFailureSubCode()))
				.body("description", equalTo(requestBase.getFailureDescription()));
	}

}