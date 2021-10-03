package com.vishal.vol.storefront.common.api;

import static io.restassured.RestAssured.given;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public interface ApiTestWithoutPayload extends ApiTestOperations {

	default RequestSpecification prepareRequest(ApiTestRequestBase requestBase) {
		RequestSpecification request = given().filter(new AllureRestAssured()).log().all()
				.contentType(ContentType.JSON);

		fillCommonParams(request, requestBase);

		enhanceRequest(request, requestBase);

		return request;
	}

	private void fillCommonParams(RequestSpecification request, ApiTestRequestBase requestBase) {
		if (requestBase.getStoreId() != null) {
			request.queryParam("store_id", requestBase.getStoreId());
		}
		if (requestBase.getMsisdn() != null) {
			request.queryParam("cred.msisdn", requestBase.getMsisdn());
		}
	}

}
