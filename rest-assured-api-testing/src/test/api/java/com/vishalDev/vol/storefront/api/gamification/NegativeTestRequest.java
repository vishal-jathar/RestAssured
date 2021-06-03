package com.vishaldev.vol.storefront.api.gamification;

import com.onmobile.vol.storefront.common.api.ApiTestRequestBase;

class NegativeTestRequest extends ApiTestRequestBase {

	NegativeTestRequest(String id, Integer storeId, String msisdn, Integer httpStatusCode) {
		super(id, storeId, msisdn, httpStatusCode);
	}

}
