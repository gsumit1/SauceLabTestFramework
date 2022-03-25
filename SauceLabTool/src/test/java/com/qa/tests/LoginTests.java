package com.qa.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pages.LoginPage;
import com.pages.ProductDetailPage;
import com.pages.ProductPage;
import com.qa.BaseTest;

public class LoginTests extends BaseTest {

	LoginPage loginPage;
	ProductPage productPage;
	ProductDetailPage productDetailPage;
	InputStream details;
	JSONObject loginUsers;

	@BeforeClass
	public void beforeClass() throws FileNotFoundException {

		details = new FileInputStream(
				"/Users/riyaanghosh/eclipse-workspace/SauceLabTool/src/test/resources/Data/data.json");
		JSONTokener jsonToken = new JSONTokener(details);
		loginUsers = new JSONObject(jsonToken);
		closeApp();
		launchApp();

	}

	@BeforeMethod
	public void beforeMethod(Method m) {
		System.out.println("*************** starting Test: " + m.getName() + "   *****************");
		loginPage = new LoginPage();
		productPage = new ProductPage();
	}

	@Test
	public void invalidUserName() {

		loginPage.enterUserName(loginUsers.getJSONObject("invalidUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidUser").getString("password"));
		loginPage.pressLogin();

		String actualErrorMsg = loginPage.gerErrorText();
		String expectedErrorMsg = "Username and password do not match any user in this service.";

		Assert.assertEquals(actualErrorMsg, expectedErrorMsg);

	}

	@Test
	public void invalidPassord() {

		loginPage.enterUserName(loginUsers.getJSONObject("invalidPassword").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("invalidPassword").getString("password"));

		loginPage.pressLogin();

		String actualErrorMsg = loginPage.gerErrorText();
		String expectedErrorMsg = "Username and password do not match any user in this service.";

		Assert.assertEquals(actualErrorMsg, expectedErrorMsg);

	}

	@Test

	public void validUser() {

		loginPage.enterUserName(loginUsers.getJSONObject("validUser").getString("username"));
		loginPage.enterPassword(loginUsers.getJSONObject("validUser").getString("password"));
		loginPage.pressLogin();

		String actualProductTitle = productPage.getTitle();
		String expectedProductTitle = "PRODUCTS";

		Assert.assertEquals(actualProductTitle, expectedProductTitle);
	}

	@AfterClass
	public void afterClass() throws IOException {

		details.close();

	}

}
