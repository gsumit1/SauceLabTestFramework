package com.qa;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

public class BaseTest {

	public static AppiumDriver driver;
	protected static Properties prop;
	InputStream inputstream;
	private static AppiumDriverLocalService server;

	public BaseTest() {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}

	@BeforeSuite
	@Parameters({ "platformName","udid","deviceName" })
	public void setup(String platformName, String udid, String deviceName) throws IOException {

		// server = getAppiumServerDefault();
		server = getAppiumService();

		if (!checkIfAppiumServerIsRunning(4723)) {
			server.start();
			server.clearOutPutStreams();
			System.out.println("Appium Server Started");
		}

		else {
			System.out.println("Appium Server is already running");
		}

		prop = new Properties();
		inputstream = getClass().getClassLoader().getResourceAsStream("config.properties");
		prop.load(inputstream);
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, platformName);
		cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, prop.getProperty("AndroidAutomationName"));
		cap.setCapability(MobileCapabilityType.UDID,udid);
		cap.setCapability(MobileCapabilityType.APP, prop.getProperty("androidAppLocation"));
		cap.setCapability("appPackage", prop.getProperty("androidAppPackage"));
		cap.setCapability("appActivity", prop.getProperty("androidAppActivity"));

		URL url = new URL(prop.getProperty("appiumURL"));
		driver = new AndroidDriver(url, cap);

	}

	private boolean checkIfAppiumServerIsRunning(int port) {

		boolean flag = false;

		ServerSocket socket;

		try {
			socket = new ServerSocket(port);
			socket.close();
		} catch (Exception e) {
			flag = true;
		} finally {
			socket = null;
		}

		return flag;
	}

	// windows
	public AppiumDriverLocalService getAppiumServerDefault() {
		return AppiumDriverLocalService.buildDefaultService();

	}

	// MAC
	public AppiumDriverLocalService getAppiumService() {
		HashMap<String, String> environment = new HashMap<String, String>();
		environment.put("PATH",
				"/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users/riyaanghosh/Library/Android/sdk/tools:/Users/riyaanghosh/Library/Android/sdk/platform-tools:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin"
						+ System.getenv("PATH"));
		environment.put("ANDROID_HOME", "/Users/riyaanghosh/Library/Android/sdk");
		return AppiumDriverLocalService
				.buildService(new AppiumServiceBuilder().usingDriverExecutable(new File("/usr/local/bin/node"))
						.withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")).usingPort(4723)
						.withArgument(GeneralServerFlag.SESSION_OVERRIDE)
//				.withArgument(() -> "--allow-insecure","chromedriver_autodownload")
						.withEnvironment(environment).withLogFile(new File("ServerLogs/server.log")));
	}

	@AfterSuite
	public void tearDown() {
		server.stop();
	}

	void waitForVisiility(MobileElement e) {

		WebDriverWait wait = new WebDriverWait(driver, 10);

		wait.until(ExpectedConditions.visibilityOf(e));

	}

	public void sendKeys(MobileElement element, String userName) {
		waitForVisiility(element);
		element.sendKeys(userName);
	}

	public void clear(MobileElement element) {
		waitForVisiility(element);
		element.clear();
	}

	public void click(MobileElement element) {
		waitForVisiility(element);
		element.click();
	}
	
	public String getText(MobileElement e) {
		
		String str=null;
		
		str=getAttribute(e,"text");
		
		return str;
	
	}
	
	
	private String getAttribute(MobileElement e, String txt) {
		waitForVisiility(e);
		
		return e.getAttribute(txt);
		
	}

	public void closeApp() {
		((InteractsWithApps) driver).closeApp();
	}
	
	

	public void launchApp() {
		((InteractsWithApps) driver).launchApp();
	}
	
	

}
