package com.vishal.vol.storefront.api.gamification;

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
import com.onmobile.vol.storefront.api.gamification.NegativeTestRequest;
import com.onmobile.vol.storefront.api.gamification.PositiveTestRequest;
import com.onmobile.vol.storefront.api.gamification.GamificationTests.NegativeTestRequestAggregator;
import com.onmobile.vol.storefront.api.gamification.GamificationTests.PositiveTestRequestAggregator;
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

@Tag(TestTags.GAMIFICATION_SUITE)
@DisplayName("users_activity")
@ExtendWith(ConfigBeforeCallback.class)
public class GamificationTests extends AbstractCommonOperations implements ApiTestWithPayload {

	private static final Map<String, ApiTestResponseBase> TEST_DATA = new HashMap<>();

	private static final String API_URL = "/gamification/{store_id}/activities/RBT/";
	private static final String RESOURCE_FOLDER = "/gamification/useractivitiestest/";
	private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

	@Tag(TestTags.USER_ACTIVITIES)
	@Tag(TestTags.POSITIVE_TEST)
	@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, gamification_client_id: {4}, request_payload_file_name: {5},  response_schema_file_name: {6}, response_exact_matcher_file_name: {7},language:{8}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
	+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void getUsersActivitesPostiveTest(
			@AggregateWith(PositiveTestRequestAggregator.class) PositiveTestRequest positiveTestRequest)
					throws IOException {

		Response response = prepareRequest(positiveTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, true)
				.get(API_URL);

		performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

	}

	@Tag(TestTags.USER_ACTIVITIES)
	@Tag(TestTags.NEGATIVE_TEST)
	@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, gamification_client_id: {4}, request_payload_file_name: {5}, code: {6}, sub_code: {7},description:{8}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
	+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void getUsersActivitesNegativeTest(
			@AggregateWith(NegativeTestRequestAggregator.class) NegativeTestRequest negativeTestRequest)
					throws IOException {

		Response response = prepareRequest(negativeTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, false)
				.get(API_URL);

		performNegativeTestCaseValidations(response, negativeTestRequest);

	}

	@Override
	public void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase) {
		request.pathParam("store_id", requestBase.getStoreId());
		//request.pathParam("gamification_client_id", requestBase.getGamificationClientId());

		//Adding user_languages in Query param
		if (requestBase instanceof PositiveTestRequest) {
			PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;
			if (positiveTestRequest.getLanguages() != null) {
				request.queryParam("language", positiveTestRequest.getLanguages());
			}
		}


	}

	@Override
	public void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase) {
		PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;


	}

	public static class PositiveTestRequestAggregator implements ArgumentsAggregator {
		@Override
		public PositiveTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			PositiveTestRequest req = new PositiveTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setGamificationClientId(arguments.getString(4));
			req.setRequestPayloadFileName(arguments.getString(5));
			req.setResponseSchemaFileName(arguments.getString(6));
			req.setResponseExactMatcherFileName(arguments.getString(7));
			req.setLangauges(arguments.getString(8));

			return req;
		}
	}

	public static class NegativeTestRequestAggregator implements ArgumentsAggregator {
		@Override
		public NegativeTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			NegativeTestRequest req = new NegativeTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setRequestPayloadFileName(arguments.getString(5));
			req.setFailureCode(arguments.getString(6));
			req.setFailureSubCode(arguments.getString(7));
			req.setFailureDescription(arguments.getString(8));
			return req;
		}
	}

	public static class PositiveTestRequestAggregatorForSubmitRewards implements ArgumentsAggregator {
		@Override
		public PositiveTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			PositiveTestRequest req = new PositiveTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setGamificationClientId(arguments.getString(5));
			req.setResponseSchemaFileName(arguments.getString(6));
			req.setResponseExactMatcherFileName(arguments.getString(7));
			

			return req;
		}
	}

	public static class NegativeTestRequestAggregatorForSubmitRewards implements ArgumentsAggregator {
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






	@DisplayName("Submit Rewards which is won by User")
	@Nested
	@ExtendWith(ConfigBeforeCallback.class)
	class SubmitRewardsForUser extends AbstractCommonOperations implements ApiTestWithPayload {
		private final Map<String, ApiTestResponseBase> TEST_DATA = new HashMap<>();

		private static final String API_URL = "/gamification/{store_id}/winnings/RBT/";
		private static final String RESOURCE_FOLDER = "/gamification/submitrewardtest/";
		private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

		@Tag(TestTags.SUBMIT_REWARDS)
		@Tag(TestTags.POSITIVE_TEST)
		@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, gamification_client_id: {4}, request_payload_file_name: {5},  response_schema_file_name: {6}, response_exact_matcher_file_name: {7}]")
		@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
		+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
		public void submitRewardsWonByUserPostiveTest(
				@AggregateWith(PositiveTestRequestAggregator.class) PositiveTestRequest positiveTestRequest)
						throws IOException {

			Response response = prepareRequest(positiveTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, true)
					.post(API_URL);

			performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

		}

		@Tag(TestTags.SUBMIT_REWARDS)
		@Tag(TestTags.NEGATIVE_TEST)
		@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, gamification_client_id: {4}, request_payload_file_name: {5}, code: {6}, sub_code: {7},description:{8}]")
		@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
		+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
		public void submitRewardsWonByUserNegativeTest(
				@AggregateWith(NegativeTestRequestAggregator.class) NegativeTestRequest negativeTestRequest)
						throws IOException {

			Response response = prepareRequest(negativeTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, false)
					.post(API_URL);

			performNegativeTestCaseValidations(response, negativeTestRequest);

		}

		@Override
		public void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase) {
			//pass storee_id in path param & gamification_client_id
			request.pathParam("store_id", requestBase.getStoreId());

			//request.pathParam("gamification_client_id", requestBase.getGamificationClientId());

			if (requestBase instanceof PositiveTestRequest) {
				PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;
				//Add extra query param here
			}


		}

		@Override
		public void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase) {
			PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;


		}

	}

	@DisplayName("GetRewards Won By User")
	@Nested
	@ExtendWith(ConfigBeforeCallback.class)
	class RewardsWonByUser extends AbstractCommonOperations implements ApiTestWithPayload {
		private final Map<String, ApiTestResponseBase> TEST_DATA = new HashMap<>();

		private static final String API_URL = "/gamification/{store_id}/winnings/RBT/";
		private static final String RESOURCE_FOLDER = "/gamification/getrewardswonbyusertest/";
		private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

		@Tag(TestTags.USER_WONREWARDS)
		@Tag(TestTags.POSITIVE_TEST)
		@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, gamification_client_id: {4}, request_payload_file_name: {5},  response_schema_file_name: {6}, response_exact_matcher_file_name: {7},language:{8}]")
		@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
		+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
		public void getRewardsWonByUserPostiveTest(
				@AggregateWith(PositiveTestRequestAggregator.class) PositiveTestRequest positiveTestRequest)
						throws IOException {

			Response response = prepareRequest(positiveTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, true)
					.get(API_URL);

			performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

		}

		@Tag(TestTags.USER_WONREWARDS)
		@Tag(TestTags.NEGATIVE_TEST)
		@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, gamification_client_id: {4}, request_payload_file_name: {5}, code: {6}, sub_code: {7},description:{8}]")
		@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
		+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
		public void getRewardsWonByUserNegativeTest(
				@AggregateWith(NegativeTestRequestAggregator.class) NegativeTestRequest negativeTestRequest)
						throws IOException {

			Response response = prepareRequest(negativeTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, false)
					.get(API_URL);

			performNegativeTestCaseValidations(response, negativeTestRequest);

		}

		@Override
		public void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase) {
			request.pathParam("store_id", requestBase.getStoreId());

			//request.pathParam("gamification_client_id", requestBase.getGamificationClientId());

			//passing langaue in query param
			if (requestBase instanceof PositiveTestRequest) {
				PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;
				if (positiveTestRequest.getLanguages() != null) {
					request.queryParam("language", positiveTestRequest.getLanguages());
				}
			}


		}

		@Override
		public void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase) {
			PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;


		}




	}
}
