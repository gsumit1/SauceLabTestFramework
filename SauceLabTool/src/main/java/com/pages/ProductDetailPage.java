package com.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductDetailPage extends BaseTest {
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[1]")
	private MobileElement SLBTitle;

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Description\"]/android.widget.TextView[2]")
	private MobileElement SLBText;
	
	
	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-BACK TO PRODUCTS\"]/android.widget.TextView")
	private MobileElement backToProductPageBtn;
	

	String getSLBTitle() {
		String sblTitle = getText(SLBTitle);
		return sblTitle;
	}

	String getSLBText() {
		String slbText = getText(SLBText);
		return slbText;
	}
	
	void pressBackToPreviousPage(){
		click(backToProductPageBtn);
		
	}

}
