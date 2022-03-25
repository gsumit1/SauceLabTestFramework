package com.pages;

import com.qa.BaseTest;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class ProductPage extends BaseTest {

	@AndroidFindBy(xpath = "//android.view.ViewGroup[@content-desc=\"test-Cart drop zone\"]/android.view.ViewGroup/android.widget.TextView")
	private MobileElement productTitleTxt;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Item title\"])[1]")
	private MobileElement SLBTitle;

	@AndroidFindBy(xpath = "(//android.widget.TextView[@content-desc=\"test-Price\"])[1]")
	private MobileElement SLBPrice;

	public String getTitle() {
		String title = getText(productTitleTxt);
		return title;
	}

	public String getSLBTitle() {
		String sblTitle = getText(SLBTitle);
		return sblTitle;
	}

	public String getSLBPrice() {
		String sblPrice = getText(SLBPrice);
		return sblPrice;
	}
	
	
	public void pressSBLTitle(){
		click(SLBTitle);
		
	}
	
	
	
	
	
	
	
}
