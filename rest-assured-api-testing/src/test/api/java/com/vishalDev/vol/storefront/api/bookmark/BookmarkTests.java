package com.vishaldev.vol.storefront.api.bookmark;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import com.onmobile.vol.storefront.common.api.ApiTestRequestBase;
import com.onmobile.vol.storefront.common.api.ApiTestResponseBase;
import com.onmobile.vol.storefront.common.api.ApiTestWithPayload;
import com.onmobile.vol.storefront.common.api.ApiTestWithoutPayload;
import com.onmobile.vol.storefront.common.api.JSONUtils;
import com.onmobile.vol.storefront.config.ConfigBeforeCallback;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@Tag(TestTags.BOOKMARKS_SUITE)
@DisplayName("Add Bookmarks")
@ExtendWith(ConfigBeforeCallback.class)
public class BookmarkTests extends AbstractCommonOperations implements ApiTestWithPayload {

	private static final Map<String, ApiTestResponseBase> TEST_DATA = new HashMap<>();

	private static final String API_URL = "/bookmarks";
	private static final String RESOURCE_FOLDER = "/user/addbookmarkstest/";
	private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

	@Tag(TestTags.ADD_BOOKMARKS)
	@Tag(TestTags.POSITIVE_TEST)
	@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, request_payload_file_name: {4}, request_schema_file_name: {5}, response_schema_file_name: {6}, response_exact_matcher_file_name: {7}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
			+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void addBookmarksPositiveTests(
			@AggregateWith(PositiveTestRequestAggregator.class) PositiveTestRequest positiveTestRequest)
			throws IOException {

		Response response = prepareRequest(positiveTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, true)
				.post(API_URL);

		performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

	}

	@Tag(TestTags.ADD_BOOKMARKS)
	@Tag(TestTags.NEGATIVE_TEST)
	@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, request_payload_file_name: {4}, code: {5}, sub_code: {6}]")
	@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
			+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
	public void addBookmarksNegativeTests(
			@AggregateWith(NegativeTestRequestAggregator.class) NegativeTestRequest negativeTestRequest)
			throws IOException {

		Response response = prepareRequest(negativeTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, false)
				.post(API_URL);

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
			return req;
		}
	}

	private static class PositiveTestRequestAggregatorForGetBookmarks implements ArgumentsAggregator {
		@Override
		public PositiveTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			PositiveTestRequest req = new PositiveTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setResponseSchemaFileName(arguments.getString(4));
			req.setResponseExactMatcherFileName(arguments.getString(5));
			req.setOffset(arguments.getInteger(6));
			req.setMax(arguments.getInteger(7));
			return req;
		}
	}

	private static class NegativeTestRequestAggregatorForGetBookmarks implements ArgumentsAggregator {
		@Override
		public NegativeTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			NegativeTestRequest req = new NegativeTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setFailureCode(arguments.getString(4));
			req.setFailureSubCode(arguments.getString(5));
			return req;
		}
	}

	private static class PositiveTestRequestAggregatorForDeleteBookmarks implements ArgumentsAggregator {
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

	private static class NegativeTestRequestAggregatorForDeleteBookmarks implements ArgumentsAggregator {
		@Override
		public NegativeTestRequest aggregateArguments(ArgumentsAccessor arguments, ParameterContext context) {
			NegativeTestRequest req = new NegativeTestRequest(arguments.getString(0), arguments.getInteger(1),
					arguments.getString(2), arguments.getInteger(3));
			req.setRequestPayloadFileName(arguments.getString(4));
			req.setFailureCode(arguments.getString(5));
			req.setFailureSubCode(arguments.getString(6));
			return req;
		}
	}

	@DisplayName("Get Bookmarks")
	@Nested
	@ExtendWith(ConfigBeforeCallback.class)
	class GetBookmarksTest extends AbstractCommonOperations implements ApiTestWithoutPayload {

		private static final String API_URL = "/bookmarks";
		private static final String RESOURCE_FOLDER = "/user/getbookmarkstest/";
		private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

		@Tag(TestTags.GET_BOOKMARKS)
		@Tag(TestTags.POSITIVE_TEST)
		@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, response_schema_file_name: {4}, response_exact_matcher_file_name: {5}, offset: {6}, max: {7}]")
		@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
				+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
		public void getBookmarksPositiveTests(
				@AggregateWith(PositiveTestRequestAggregatorForGetBookmarks.class) PositiveTestRequest positiveTestRequest)
				throws IOException {

			Response response = prepareRequest(positiveTestRequest).get(API_URL);

			performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

			TEST_DATA.put(positiveTestRequest.getId(),
					new ApiTestResponseBase(positiveTestRequest, response.body().asString()));

		}

		@Tag(TestTags.GET_BOOKMARKS)
		@Tag(TestTags.NEGATIVE_TEST)
		@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, code: {4}, sub_code: {5}]")
		@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
				+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
		public void getBookmarksNegativeTests(
				@AggregateWith(NegativeTestRequestAggregatorForGetBookmarks.class) NegativeTestRequest negativeTestRequest)
				throws IOException {

			Response response = prepareRequest(negativeTestRequest).get(API_URL);

			performNegativeTestCaseValidations(response, negativeTestRequest);

		}

		@Override
		public void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase) {
			if (requestBase instanceof PositiveTestRequest) {
				PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;

				if (positiveTestRequest.getOffset() != null) {
					request.queryParam("offset", positiveTestRequest.getOffset());
				}
				if (positiveTestRequest.getMax() != null) {
					request.queryParam("size", positiveTestRequest.getMax());
				}
			}

		}

		@Override
		public void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase) {
			PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;
			// additional common value validations

			JsonPath responseJsonPath = response.jsonPath();

			if (responseJsonPath
					.getInt("total_items_count") >= (positiveTestRequest.getOffset() + positiveTestRequest.getMax())) {

				response.then().assertThat().body("size", equalTo(positiveTestRequest.getMax())).body("bookmarks",
						hasSize(positiveTestRequest.getMax()));
			}

		}

		@DisplayName("Delete Bookmarks")
		@Nested
		@ExtendWith(ConfigBeforeCallback.class)
		class DeleteBookmarksTest extends AbstractCommonOperations implements ApiTestWithPayload {

			private static final String API_URL = "/bookmarks";
			private static final String RESOURCE_FOLDER = "/user/deletebookmarkstest/";
			private static final String COMPLETE_RESOURCE_PATH = Constants.BASE_PACKAGE_PATH + RESOURCE_FOLDER;

			@Tag(TestTags.DELETE_BOOKMARKS)
			@Tag(TestTags.POSITIVE_TEST)
			@ParameterizedTest(name = "Positive Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, request_payload_file_name: {4}, request_schema_file_name: {5}, response_schema_file_name: {6}, response_exact_matcher_file_name: {7}]")
			@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
					+ "positive_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
			public void deleteBookmarksPositiveTests(
					@AggregateWith(PositiveTestRequestAggregatorForDeleteBookmarks.class) PositiveTestRequest positiveTestRequest)
					throws IOException {

				Response response = prepareRequest(positiveTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, true)
						.delete(API_URL);

				performPositiveTestCaseValidations(response, COMPLETE_RESOURCE_PATH, positiveTestRequest);

			}

			@Tag(TestTags.DELETE_BOOKMARKS)
			@Tag(TestTags.NEGATIVE_TEST)
			@ParameterizedTest(name = "Negative Test Case: [id: {0}, store_id: {1}, msisdn: {2}, http_status: {3}, request_payload_file_name: {4}, code: {5}, sub_code: {6}]")
			@CsvFileSource(resources = "/" + COMPLETE_RESOURCE_PATH
					+ "negative_test_cases.csv", numLinesToSkip = 1, nullValues = { "null" })
			public void deleteBookmarksNegativeTests(
					@AggregateWith(NegativeTestRequestAggregatorForDeleteBookmarks.class) NegativeTestRequest negativeTestRequest)
					throws IOException {

				Response response = prepareRequest(negativeTestRequest, RESOURCE_FOLDER, COMPLETE_RESOURCE_PATH, false)
						.delete(API_URL);

				performNegativeTestCaseValidations(response, negativeTestRequest);

			}

			@Override
			public void enhanceRequest(RequestSpecification request, ApiTestRequestBase requestBase) {
				if (requestBase.getRequestPayload() == null) {
					ApiTestResponseBase responseBase = TEST_DATA.get(requestBase.getId());

					JsonPath jsonPath = JsonPath.from(responseBase.getResponseString());
					List<Long> bookmarkIdsList = jsonPath.getList("bookmarks.bookmark_id");

					request.body(bookmarkIdsList);
					requestBase.setRequestPayload(JSONUtils.objectToJsonString(bookmarkIdsList));
				}
			}

			@Override
			public void validateResponseForPositiveTestCase(Response response, ApiTestRequestBase requestBase) {
				PositiveTestRequest positiveTestRequest = (PositiveTestRequest) requestBase;
				// additional common value validations

			}

		}
	}

}
