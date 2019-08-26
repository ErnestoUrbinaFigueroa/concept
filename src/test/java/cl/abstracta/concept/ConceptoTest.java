package cl.abstracta.concept;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ConceptoTest {
	private WebDriver driver;
	private ExtentReports extent;
	private ExtentTest test;

	private static String SUBDIR = "Concept\\";
	private static Boolean TAKE_SS = true;

	@BeforeSuite
	public void configExtentReports() {
		// ExtentReports config
		this.extent = new ExtentReports("ExtentReports/Concept.html");
	}

	@BeforeMethod
	public void configSelenium() {
		// Selenium config
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability("Empresa", "Abstracta");
		System.setProperty("webdriver.gecko.driver", "Drivers/geckodriver");
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.navigate().to("http://www.google.cl/");
	}

	@Test
	public void conceptTest() throws IOException {
		String subDir = SUBDIR + Thread.currentThread().getStackTrace()[1].getMethodName();
		test = extent.startTest("Prueba de concepto", "Prueba de concepto.");
		test.log(LogStatus.INFO, "Prueba de concepto.");

		System.out.println("Prueba de Concepto.");
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(LogStatus.FAIL, "Prueba Fallida.- <br>" + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(LogStatus.SKIP, "Prueba Saltada.- <br>" + result.getThrowable());
		} else {
			test.log(LogStatus.PASS, "Prueba Exitosa.-");
		}
		driver.close();
		extent.endTest(test);
	}

	@AfterSuite
	public void closeExtentReports() {
		// writing everything to document.
		extent.flush();
	}
}
