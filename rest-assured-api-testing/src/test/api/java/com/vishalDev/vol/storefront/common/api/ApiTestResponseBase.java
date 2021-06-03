package com.onmobile.vol.storefront.common.api;

public class ApiTestResponseBase {

	private ApiTestRequestBase request;

	private String responseString;

	public ApiTestResponseBase(ApiTestRequestBase request, String responseString) {
		super();
		this.request = request;
		this.responseString = responseString;
	}

	public ApiTestRequestBase getRequest() {
		return request;
	}

	public String getResponseString() {
		return responseString;
	}

}
