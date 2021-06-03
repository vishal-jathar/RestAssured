package com.vishaldev.vol.storefront.api.authentication;

import static org.hamcrest.Matchers.hasKey;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.provider.CsvFileSource;

import com.onmobile.vol.storefront.Constants;
import com.onmobile.vol.storefront.TestTags;
import com.onmobile.vol.storefront.common.api.AbstractCommonOperations;
import com.onmobile.vol.storefront.common.api.ApiTestOperations;
import com.onmobile.vol.storefront.common.api.ApiTestRequestBase;
import com.onmobile.vol.storefront.common.api.ApiTestResponseBase;
import com.onmobile.vol.storefront.common.api.ApiTestWithPayload;
import com.onmobile.vol.storefront.common.api.ApiTestWithoutPayload;
import com.onmobile.vol.storefront.config.ConfigBeforeCallback;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Tag(TestTags.AUTHENTICATION_SUITE)
@DisplayName("generate_token_1_0)")
@ExtendWith(ConfigBeforeCallback.class)
public class Authentication1_0Tests extends AbstractCommonOperations implements ApiTestWithPayload {

	private static final Map<String, ApiTestResponseBase> TEST_DATA = new HashMap<>();

	private static final String API_URL = "/1.0/authentication/token/";
	private static final String RESOURCE_FOLDER = "/authentication/generatetoken1_0test/";;
	private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

	@Tag(TestTags.GENERATE_TOKEN_1_0)
	@Tag(TestTags.POSITIVE_TEST)
	@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, request_payload_file_name: {4}, request_schema_file_name: {5}, response_schema_file_name: {6}, response_exact_matcher_file_name: {7}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
	+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void generateTokenPositiveTests(
			@AggregateWith(PositiveTestRequestAggregator.class) PositiveTestRequest positiveTestRequest)
					throws IOException {

		Response response = prepareRequest(positiveTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, true)
				.post(API_URL);

		performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

	}

	@Tag(TestTags.GENERATE_TOKEN_1_0)
	@Tag(TestTags.NEGATIVE_TEST)
	@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, request_payload_file_name: {4}, code: {5}, sub_code: {6},description:{7}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
	+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void generateTokenNegativeTests(
			@AggregateWith(NegativeTestRequestAggregator.class) NegativeTestRequest negativeTestRequest)
					throws IOException {

		Response response = prepareRequest(negativeTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, false)
				.post(API_URL);

		performNegativeTestCaseValidations(response, negativeTestRequest);

	}

	@Override
	public void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase) {
		// request.pathParam("store_id", requestBase.getStoreId());

	}

	@Override
	public void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase) {
		PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;


	}

	private static class PositiveTestRequestAggregator implements ArgumentsAggregator {
		@Override
		public PositiveTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			PositiveTestRequest req = new PositiveTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setRequestPayloadFileName(arguments.getString(4));
			req.setRequestSchemaFileName(arguments.getString(5));
			req.setResponseSchemaFileName(arguments.getString(6));
			req.setResponseExactMatcherFileName(arguments.getString(7));
			return req;
		}
	}

	private static class NegativeTestRequestAggregator implements ArgumentsAggregator {
		@Override
		public NegativeTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			NegativeTestRequest req = new NegativeTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setRequestPayloadFileName(arguments.getString(4));
			req.setFailureCode(arguments.getString(5));
			req.setFailureSubCode(arguments.getString(6));
			req.setFailureDescription(arguments.getString(7));
			return req;
		}
	}

	public static class PositiveTestRequestAggregatorForGenerateToken implements ArgumentsAggregator {
		@Override
		public PositiveTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			PositiveTestRequest req = new PositiveTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setResponseSchemaFileName(arguments.getString(4));
			req.setResponseExactMatcherFileName(arguments.getString(5));

			return req;
		}
	}

	public static class NegativeTestRequestAggregatorForGenerateToken implements ArgumentsAggregator {
		@Override
		public NegativeTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			NegativeTestRequest req = new NegativeTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setFailureCode(arguments.getString(4));
			req.setFailureSubCode(arguments.getString(5));
			req.setFailureDescription(arguments.getString(6));
			return req;
		}
	}





}