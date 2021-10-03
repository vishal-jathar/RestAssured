package com.vishal.vol.storefront.api.gamification;

import com.vishal.vol.storefront.common.api.ApiTestRequestBase;

class PositiveTestRequest extends ApiTestRequestBase {


	private String language;
	private String requestgamificationclienid;

	PositiveTestRequest(String id, Integer storeId, String msisdn, Integer httpStatusCode) {
		super(id, storeId, msisdn, httpStatusCode);
	}


	public String getLanguages() {
		return language;
	}


	public void setLangauges(String language) {
		this.language =language;

	}
	
	public String getGamificationClientId() {
		return requestgamificationclienid;
	}

	public void setGamificationClientId(String requestgamificationclienid) {
		this.requestgamificationclienid = requestgamificationclienid;
	}
	
	

}
