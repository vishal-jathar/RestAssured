package com.vishaldev.vol.storefront.api.bookmark;

import com.onmobile.vol.storefront.common.api.ApiTestRequestBase;

class PositiveTestRequest extends ApiTestRequestBase {

	private Integer offset;
	private Integer max;

	PositiveTestRequest(String id, Integer storeId, String msisdn, Integer httpStatusCode) {
		super(id, storeId, msisdn, httpStatusCode);
	}

	public Integer getOffset() {
		return offset;
	}

	public void setOffset(Integer offset) {
		this.offset = offset;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

}
