package com.vishal.vol.storefront.api.user;

import static org.hamcrest.CoreMatchers.equalTo;

import org.junit.jupiter.api.DisplayName;
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
import com.onmobile.vol.storefront.common.api.ApiTestRequestBase;
import com.onmobile.vol.storefront.common.api.ApiTestWithoutPayload;
import com.onmobile.vol.storefront.config.ConfigBeforeCallback;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@DisplayName("Get User Info 1.0")
@Tag(TestTags.GET_USER_INFO)
@ExtendWith(ConfigBeforeCallback.class)
public class GetUserInfo1_0Test extends AbstractCommonOperations implements ApiTestWithoutPayload {

	private static final String API_URL = "/1.0/user";
	private static final String RESOURCE_FOLDER = "/user/getuserinfo1_0test/";
	private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

	@Tag(TestTags.POSITIVE_TEST)
	@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, response_schema_file_name: {4}, response_exact_matcher_file_name: {5}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
			+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void getUserInfoPositiveTests(
			@AggregateWith(PositiveTestRequestAggregator.class) PositiveTestRequest positiveTestRequest) {

		Response response = prepareRequest(positiveTestRequest).get(API_URL);

		performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

	}

	@Tag(TestTags.NEGATIVE_TEST)
	@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, code: {4}, sub_code: {5}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
			+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void getUserInfoNegativeTests(
			@AggregateWith(NegativeTestRequestAggregator.class) NegativeTestRequest negativeTestRequest) {

		Response response = prepareRequest(negativeTestRequest).get(API_URL);

		performNegativeTestCaseValidations(response, negativeTestRequest);

	}

	@Override
	public void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase) {
		// additional request parameters
	}

	@Override
	public void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase) {
		PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;
		// additional common value validations

		response.then().body("msisdn", equalTo(positiveTestRequest.getMsisdn()));
	}

	private static class PositiveTestRequestAggregator implements ArgumentsAggregator {
		@Override
		public PositiveTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			PositiveTestRequest req = new PositiveTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setResponseSchemaFileName(arguments.getString(4));
			req.setResponseExactMatcherFileName(arguments.getString(5));
			return req;
		}
	}

	private static class NegativeTestRequestAggregator implements ArgumentsAggregator {
		@Override
		public NegativeTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			NegativeTestRequest req = new NegativeTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setFailureCode(arguments.getString(4));
			req.setFailureSubCode(arguments.getString(5));
			return req;
		}
	}

}
