package testng;

import java.io.IOException;
import java.util.HashMap;
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
import specificUtils.*;

public class starbucksTest extends BasicTest{
	
	@Factory(dataProvider="factoryData")
	public starbucksTest(DesiredCapabilities caps){
		super(caps);
	}
	
	@Test(dataProvider = "starbucksData")
	public void starbucks(String letsBlockSomeDomains) {
		Map<String, Object> params = new HashMap<>();
		long measuredLaunchTimer = 0; 
		long measuredNavTimer = 0;
		
		if(this.driver == null){
			Assert.fail("Resource not avalable: " + this.deviceDesc);
		}
		//WebCommunityBaseView mobileView = new WebCommunityBaseView(driver);
		try {
			
			
			String app = "Starbucks";
			try {
				
								
	// ## ===>>	Block domains			
				if(letsBlockSomeDomains=="true")
					WindTunnelUtils.blockDomains(driver, "www.google.com", "maps.google.com");
				
	// ## ===>>	Go Home
				WindTunnelUtils.setPointOfInterest(driver, "Script Start", WindTunnelUtils.pointOfInterestStatus.SUCCESS.getName()); 
				params = new HashMap<>();
				
	// ## ===>>	Close Application
				PerfectoUtils.closeApp(this.driver, app);
				
	// ## ===>>	Restart Application
				WindTunnelUtils.setPointOfInterest(driver, "Launch App", WindTunnelUtils.pointOfInterestStatus.SUCCESS.getName());
				PerfectoUtils.launchApp(this.driver, app);
	// ## ===>>	Start Application & device vitals measurement
				WindTunnelUtils.startAppVitals(driver, app, true);

	// ## ===>>	Validate the app launched- use OCR to get real UX metrics
				PerfectoUtils.ocrTextCheck(this.driver, "Stores", -1, 60);
	// ## ===>>	How long did it take to launch?
				measuredLaunchTimer = PerfectoUtils.getUXTimer(driver);
				
				WindTunnelUtils.setTimerReport(driver, measuredLaunchTimer, 1000, app + " Launch Time", "Launch");
				reportPass("LaunchTime", "Launch Time="+measuredLaunchTimer,"treshold=1000");
	// ## ===>>	click on stores
				WindTunnelUtils.setPointOfInterest(driver, "Find Stores", WindTunnelUtils.pointOfInterestStatus.SUCCESS.getName());
//				PerfectoUtils.switchToContext(driver, "NATIVE_APP");
//				driver.findElementByXPath("//*[text()='STORES']").click();
				PerfectoUtils.ocrTextClick(driver, "STORES", -1, 60);
				PerfectoUtils.ocrTextCheck(driver, "mi", -1, 60);
				measuredNavTimer = PerfectoUtils.getUXTimer(driver);
				PerfectoUtils.sleep(3000);
	// ## ===>>	click on a store address
				WindTunnelUtils.setPointOfInterest(driver, "Find Store", WindTunnelUtils.pointOfInterestStatus.SUCCESS.getName());
				PerfectoUtils.ocrTextClick(driver, "mi", -1, 60);
				PerfectoUtils.ocrTextCheck(driver, "Directions", -1, 60);
				measuredNavTimer += PerfectoUtils.getUXTimer(driver);
				PerfectoUtils.sleep(3000);

	// ## ===>>	get directions to store
				WindTunnelUtils.setPointOfInterest(driver, "Navigate", WindTunnelUtils.pointOfInterestStatus.SUCCESS.getName());
				PerfectoUtils.ocrTextClick(driver, "Directions", -1, 60);
				PerfectoUtils.ocrTextCheck(driver, "Start", -1, 60);
	// ## ===>>	How long did it take to launch?
				measuredNavTimer += PerfectoUtils.getUXTimer(driver);
				WindTunnelUtils.setTimerReport(driver, measuredNavTimer, 3000, app + " Navigate to store", "Nav 2 store");
				reportPass("NavigateToStore", "Navigate Time="+measuredNavTimer,"treshold=3000");
				WindTunnelUtils.stopVitals(driver);
			/*mobileView.init().login(username, password);
			String result = mobileView.getWelcomeMessage();
			if(result.equals(greetingMessage)){
				reportPass("Test passed", this.testName, username, password, greetingMessage);
				//addRowToDetailedSheet(true, username, password, greetingMessage);
			}
			else{
				//addRowToDetailedSheet(false, username, password, greetingMessage);
				reportFail(greetingMessage,result, this.testName, username, password, greetingMessage);
			}*/
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			reportFailWithMessage(e.getMessage());
		} finally{
			
		}} finally{
			
		}
	}
	
	@DataProvider (name = "starbucksData", parallel = false)
	public Object[][] starbucksDataProvider(){
		 Object[][] s = null;
		try {
		  ExcelDriver ed = new ExcelDriver(sysProp.get("inputDataSheet"), sysProp.get("starbucksSheet"), false);
		  s = ed.getData(1);
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
