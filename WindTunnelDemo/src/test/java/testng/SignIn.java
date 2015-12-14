package testng;

import java.io.IOException;
import java.util.Map;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.uncommons.reportng.ReportNGUtils;

import com.perfectomobile.dataDrivers.excelDriver.ExcelDriver;
import com.perfectomobile.test.BasicTest;
import com.perfectomobile.web_community_pom.WebCommunityBaseView;


public class SignIn extends BasicTest{
	
	@Factory(dataProvider="factoryData")
	public SignIn(DesiredCapabilities caps){
		super(caps);
	}
	
	@Test(dataProvider = "logInData")
	public void signIn(String username, String password, String greetingMessage) {
		if(this.driver == null){
			Assert.fail("Resource not avalable: " + this.deviceDesc);
		}
		WebCommunityBaseView mobileView = new WebCommunityBaseView(driver);
		try {
			mobileView.init().login(username, password);
			String result = mobileView.getWelcomeMessage();
			if(result.equals(greetingMessage)){
				reportPass("Test passed", this.testName, username, password, greetingMessage);
				//addRowToDetailedSheet(true, username, password, greetingMessage);
			}
			else{
				//addRowToDetailedSheet(false, username, password, greetingMessage);
				reportFail(greetingMessage,result, this.testName, username, password, greetingMessage);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			reportFailWithMessage(e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@DataProvider (name = "logInData", parallel = false)
	public Object[][] logInDataProvider(){
		 Object[][] s = null;
		try {
		  ExcelDriver ed = new ExcelDriver(sysProp.get("inputDataSheet"), sysProp.get("signInSheet"), false);
		  s = ed.getData(3);
		} catch(IOException e) {
			System.out.println("Not able to search data from excel: " + sysProp.get("inputDataSheet"));
			System.err.println("IndexOutOfBoundsException: " + e.getMessage());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return s;
	}
}
