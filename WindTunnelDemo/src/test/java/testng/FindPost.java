package testng;

import java.io.IOException;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;

import com.perfectomobile.dataDrivers.excelDriver.ExcelDriver;
import com.perfectomobile.test.BasicTest;
import com.perfectomobile.web_community_pom.WebCommunityBaseView;
import com.perfectomobile.web_community_pom.WebSearchResultsPageView;


public class FindPost extends BasicTest {
	
	@Factory (dataProvider="factoryData")
	public FindPost(DesiredCapabilities caps) {
		super(caps);
		// TODO Auto-generated constructor stub
	}

	@Test(dataProvider = "findPostDP")
	public void findPost(String q, String postIndex, String postTitle) throws Exception {
		if(driver == null){
			return;
		}
		float f = Float.valueOf(postIndex);
		int idx = (int)f;
		WebCommunityBaseView mobileView = new WebCommunityBaseView(driver);
		try {
			WebSearchResultsPageView resultView = mobileView.init().searchItem(q);
			String actualPostTitle = resultView.clickPost(idx).getPostTitle();
			if(actualPostTitle.equals(postTitle)){
				reportPass("Test passed", this.testName, q, postIndex, postTitle);
				//addRowToDetailedSheet(true, q, postIndex, postTitle);
			}
			else{
				//addRowToDetailedSheet(false, q, postIndex, postTitle);
				reportFail(postTitle, actualPostTitle, this.testName, q, postIndex, postTitle);
			}
			//System.out.println(actualPostTitle);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//addRowToDetailedSheet(false, q, postIndex, postTitle);
			reportFailWithMessage(e.getMessage(), this.testName, q, postIndex, postTitle);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//addRowToDetailedSheet(false, q, postIndex, postTitle);
			reportFailWithMessage(e.getMessage(), this.testName, q, postIndex, postTitle);
		}
	}

	@DataProvider (name="findPostDP")
	public Object[][] findPostDP() {
		Object[][] s = null;
		try {
		  ExcelDriver ed = new ExcelDriver(sysProp.get("inputDataSheet"), sysProp.get("findPostSheet"), false);
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
