package com.vishal.vol.storefront.api.authentication;

import com.vishal.vol.storefront.common.api.ApiTestRequestBase;

class NegativeTestRequest extends ApiTestRequestBase {

	NegativeTestRequest(String id, Integer storeId, String msisdn, Integer httpStatusCode) {
		super(id, storeId, msisdn, httpStatusCode);
	}



}
