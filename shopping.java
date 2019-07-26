package casestudy;

import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
public class shopping {
		WebDriver d;
		ExtentHtmlReporter htmlReporter;
		ExtentReports extent;
		ExtentTest test;
		@BeforeClass
		public void beforeclass() {
			System.setProperty("webdriver.chrome.driver", "C:\\drivers\\chromedriver_win32 (1)\\chromedriver.exe");
			d = new ChromeDriver();
			d.get("http://10.232.237.143:443/TestMeApp");
			d.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
			d.manage().window().maximize();
		}
		@Test(priority=1)
		public void testRegistration() {
			test=extent.createTest("tc_01","registration");
	  
			//register
			d.findElement(By.xpath("//a[@href='RegisterUser.htm']")).click();	
			d.findElement(By.xpath("//input[@name='userName']")).sendKeys("zxcvbi");	
			d.findElement(By.xpath("//input[@name='firstName']")).sendKeys("zxcvbb");
			d.findElement(By.xpath("//input[@name='lastName']")).sendKeys("zxcvbb");
			d.findElement(By.xpath("//input[@name='password']")).sendKeys("zxcvbb");
			d.findElement(By.xpath("//input[@name='confirmPassword']")).sendKeys("zxcvbb");
			d.findElement(By.xpath("//input[@value='Male']")).click();
			d.findElement(By.xpath("//input[@name='emailAddress']")).sendKeys("zxcvbnm@gmail.com");
			d.findElement(By.xpath("//input[@name='mobileNumber']")).sendKeys("2222222222");
			d.findElement(By.xpath("//input[@name='dob']")).sendKeys("07/02/2019");
			d.findElement(By.xpath("//textarea[contains(text(),'Address')]")).sendKeys("22hgdfukkgrdfuhiuw");
			d.findElement(By.xpath("//input[@name='answer']")).sendKeys("fdhtejuet");
			d.findElement(By.xpath("//input[@name='Submit']")).click();
			

			 String atitle="Login";
			  String etitle=d.getTitle();
			  Assert.assertEquals(atitle, etitle);
		}
			//login
		@Test(priority=2)
		public void testLogin() {
			test=extent.createTest("tc_02","login");
			//d.findElement(By.xpath("//a[@href='login.htm']")).click();
			d.findElement(By.xpath("//input[@name='userName']")).sendKeys("zxcvbb");
			d.findElement(By.xpath("//input[@name='password']")).sendKeys("zxcvbb");
			d.findElement(By.xpath("//input[@name='Login']")).click();
			
			 String atitle="Home";
			  String etitle=d.getTitle();
			  Assert.assertEquals(atitle, etitle);
		}
		//search
		@Test(priority=3)
		public void testCart() throws InterruptedException {
			test=extent.createTest("tc_03","search");
			d.findElement(By.xpath("//*[@id=\"menu3\"]/li[2]/a/span")).click();
			Actions a=new Actions(d);
			d.findElement(By.xpath("//span[contains(text(),'Home Appliances')]")).click();
			Thread.sleep(5000);
			d.findElement(By.xpath("//span[contains(text(),'Floor')]")).click(); 
	   
			// add to cart  
			d.findElement(By.xpath("//a[contains(text(),' Add to cart')]")).click();

			d.findElement(By.xpath("//a[@href='displayCart.htm']")).click();  
			String atitle="View Cart";
			  String etitle=d.getTitle();
			  Assert.assertEquals(atitle, etitle);
			d.findElement(By.xpath("//a[@href='checkout.htm']")).click();
			d.findElement(By.xpath("//textarea[@name='ShippingAdd']")).sendKeys("tehury6hyghr");
			d.findElement(By.xpath("//input[@value='Proceed to Pay']")).click();
			Thread.sleep(20000);
		}
		@Test(priority=4)
		public void testPayment() throws InterruptedException {
			//bank details
			test=extent.createTest("tc_04","payment");
			d.findElement(By.xpath("//label[contains(text(),'Andhra Bank')]")).click();
			d.findElement(By.xpath("//a[contains(text(),'Continue')]")).click();
			d.findElement(By.xpath("//input[@name='username']")).sendKeys("123456");
			d.findElement(By.xpath("//input[@name='password']")).sendKeys("Pass@456");
			d.findElement(By.xpath("//input[@value='LOGIN']")).click();
			Thread.sleep(5000);
			d.findElement(By.xpath("//input[@name='transpwd']")).sendKeys("Trans@456");
			d.findElement(By.xpath("//input[@value='PayNow']")).click();
			
			
		}
		  @AfterMethod
		  public void afterMethod(ITestResult result) throws IOException {
			  if(result.getStatus()==ITestResult.FAILURE) {
				  test.log(Status.FAIL,MarkupHelper.createLabel(result.getName()+" FAILED ", ExtentColor.RED));
				  TakesScreenshot snapshot=(TakesScreenshot)d;
				  File src=snapshot.getScreenshotAs(OutputType.FILE);
				  String Path=System.getProperty("user.dir")+"/test-output/screens/"+result.getName()+".png";
				  FileUtils.copyFile(src,new File(Path));
				  test.addScreenCaptureFromPath(Path,result.getName());
				  test.fail(result.getThrowable());  
				  
			  }
			  else if(result.getStatus()==ITestResult.SUCCESS) {
				  test.log(Status.PASS, MarkupHelper.createLabel(result.getName()+"PASSED", ExtentColor.GREEN));	  
			  	}
			  else {
				  test.log(Status.SKIP, MarkupHelper.createLabel(result.getName()+"SKIPPED", ExtentColor.ORANGE));
				  test.skip(result.getThrowable());
			  }
		  }
		
		@AfterClass
		public void afterClass() {
			//logout
			d.findElement(By.xpath("//a[@href='logout.htm']")).click();
			d.close();
		}

		 @Parameters({"OS","browser"})
		  @BeforeTest
		  public void startReport(String OS,String browser) {
			  htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/testReport.html");
			  extent = new ExtentReports();
			  extent.attachReporter(htmlReporter);
			  extent.setSystemInfo("OS",OS);
			  extent.setSystemInfo("Browser",browser);
//			  htmlReporter.config().setChartVisibilityOnOpen(true);
			  htmlReporter.config().setDocumentTitle("Extent Report Demo");
			  htmlReporter.config().setReportName("Test Report");
//			  htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			  htmlReporter.config().setTheme(Theme.STANDARD);
			  htmlReporter.config().setTimeStampFormat("EEEE,MMMM dd, yyyy, hh:mm a '('zzz')'");
			  
			  
		  }

		 
		 @AfterTest
		  public void afterTest() {
			  extent.flush();
		  }
		  

}
