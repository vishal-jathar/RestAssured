package com.onmobile.vol.storefront.api.user;

import com.onmobile.vol.storefront.common.api.ApiTestRequestBase;

class PositiveTestRequest extends ApiTestRequestBase {

	PositiveTestRequest(String id, Integer storeId, String msisdn, Integer httpStatusCode) {
		super(id, storeId, msisdn, httpStatusCode);
	}

}