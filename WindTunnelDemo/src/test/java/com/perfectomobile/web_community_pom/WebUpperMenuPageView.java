package com.perfectomobile.web_community_pom;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.perfectomobile.utils.PerfectoUtils;

/**
 * The Class WebUpperMenuPageView.
 */
public class WebUpperMenuPageView{
	
	/** The logged in. */
	private boolean loggedIn;
	
	/** The community logo. */
	private By communityLogo	= By.xpath("//img[@class='brand-image']");
		
	/** The welcome message. */
	private By welcomeMessage = By.xpath("//span[@class='greeting']");
	
	/** The pop up. */
	private By popUp = By.xpath("//*[@class='no-thanks']");
	
	//login fields	
	/** The login button. */
	private String loginButton = "//*[text()='Log In']";
	
	/** The logout button. */
	private String logoutButton = "//*[@href='/logout']";
	
	//search fields
	/** The search button1. */
	private String searchButton1 = "(//header[1]/div[2]/button[1]/span[1])";
	
	/** The search button2. */
	private String searchButton2 = "(//header[1]/div[1]/button[1]/div[1]/span[1])";
	
	/** The search area input. */
	private By searchAreaInput	= By.xpath("//input[contains(@class,'js-search-input')]");

	/** The driver. */
	private RemoteWebDriver driver;
	
	
	/**
	 * 		Constructor.
	 *
	 * @param driver the driver
	 * @throws IOException 
	 */
	public WebUpperMenuPageView(RemoteWebDriver driver) throws IOException{
		this.driver = driver;
        
        //validate page loaded successfully before proceeding
        try{
        	PerfectoUtils.fluentWait(communityLogo, this.driver, 30);
        	handleAppPopup();
        	try {
        		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
				this.driver.findElement(By.xpath(logoutButton));
				this.loggedIn = true;
			} catch (Exception e) {
				
				this.loggedIn = false;
			}
        	
        	driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        catch(Exception e){
        	
        	throw new IllegalStateException();
        }
        
       
    }

	/**
	 * Checks if a user is already logged in.
	 *
	 * @return true, if is logged in
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * Sets the logged in flag.
	 *
	 * @param loggedIn the new logged in
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
	/**
	 * Performs a log out of the community.
	 *
	 * @return the web upper menu page view
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WebUpperMenuPageView logOut() throws IOException{
		try {
			//this.driver.findElement(By.xpath(logoutButton)).click();
			jsExecute("clickLogout");
			return new WebUpperMenuPageView(driver);
			
		} catch (Exception e) {
			
			throw new IllegalStateException();
		}
		
		
	}
	
	/**
	 * Handle optional pop ups the community might have on load.
	 */
	private void handleAppPopup(){
        try {
             
        	String os = PerfectoUtils.getDeviceProperty(driver, "os");
        	
              if (!PerfectoUtils.isDevice(this.driver) || !os.equals("iOS"))
                    return;
              
             
              driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
              this.driver.findElement(popUp).click();
              Thread.sleep(2000);
              
        } catch (Exception e) {
              //System.out.println("no popup");
        }
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        
  }

	
//	private void handleAppPopup(){
//		try {
//			//not a must
//			//Capabilities capabilities = this.driver.getCapabilities();
//			String os = driver.getCapabilities().asMap().get("os").toString();
//
//			//String os = (String) capabilities.getCapability("platformName");
//			System.out.println("os="+os);
//			if (!os.equals("iOS")){
//				System.out.println("No pop up to handle");
//				return;
//			}
//			System.out.println("handle popup iOS");
//			Thread.sleep(2000);
//			//must
//			//driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			this.driver.findElement(popUp).click();
//			Thread.sleep(2000);
//			//PerfectoUtils.fluentWait(popUp, driver, 0);
//		} catch (Exception e) {
//			//System.out.println("no popup");
//		}
//		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//		
//	}
	
	/**
	 * Login to the Community.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the web upper menu page view
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WebUpperMenuPageView login(String username,String password) throws IOException{
		try {
			
			jsExecute("clickLogin");
			new WebLoginPageView(this.driver).login(username, password);
			
			
			return new WebUpperMenuPageView(driver);
			
		} catch (Exception e) {
			/*
			String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
			*/
			return null;
		}
		
	
	}
	
	/**
	 * Gets the welcome message after Login
	 *
	 * @return the welcome message
	 */
	public String getWelcomeMessage(){
		
		try {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			WebDriverWait wait = new WebDriverWait(driver, 10);
			wait.until(ExpectedConditions.presenceOfElementLocated(welcomeMessage));
			String returnValue = driver.findElement(welcomeMessage).getText();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			return returnValue;
		} catch (Exception e) {
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			return "";
		}
	}
	
	/**
	 * Search item.
	 *
	 * @param text the text to search for in search bar
	 * @return the web search results page view
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WebSearchResultsPageView searchItem(String text) throws IOException{
		
		try {
			
			jsExecute("clickSearchButton");
			
			WebElement element = this.driver.findElement(searchAreaInput);
			//element.clear();
			element.sendKeys(text);
			this.driver.getKeyboard().pressKey(Keys.ENTER);
			PerfectoUtils.sleep(2000);
						
			return new WebSearchResultsPageView(this.driver);
			
		} catch (Exception e) {
			
			return null;
		}
		
	}
	
	/**
	 * Js execute.
	 *
	 * @param action the action
	 */
	private void jsExecute(String action){
		
		
		//define the findElementByXpath(path) function:
		String jsScript="function findElementsByXpath(path){"
				+ "elements = document.evaluate(path, document, null, XPathResult.FIRST_ORDERED_NODE_TYPE, null).singleNodeValue;"
				+ "return elements;}";
		
		
		switch (action) {
		case "clickLogin":
				/*//path="primary-header-button primary-button";
				jsScript = jsScript+ " elements = document.getElementsByClassName('"+loginButtonText+"');"
						+ "elements[0].click();";*/
			jsScript=jsScript+ " elements = findElementsByXpath(\"" + loginButton + "\");"
					+ "elements.click();";
			break;
		
		case "clickLogout":
				jsScript=jsScript+ " elements = findElementsByXpath(\"" + logoutButton + "\");"
					+ "elements.click();";
		break;
		
		case "clickSearchButton":
			
			jsScript=jsScript+" elements = findElementsByXpath(\"" + searchButton1 + "\");"
					+ "elements.click();";
			jsScript=jsScript +" elements = findElementsByXpath(\"" + searchButton2 + "\");"
					+ "elements.click();";
		break;

		default:
			break;
		}

		
	     
		if (driver instanceof JavascriptExecutor) {
	        ((JavascriptExecutor)driver).executeScript(jsScript);
		}
	}
	
}
