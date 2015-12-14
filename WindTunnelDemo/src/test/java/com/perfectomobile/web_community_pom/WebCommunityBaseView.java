package com.perfectomobile.web_community_pom;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.RemoteWebDriver;


// TODO: Auto-generated Javadoc
/***
 * The Class WebCommunityBaseView.
 */
public class WebCommunityBaseView {
	
	/** The driver. */
	protected RemoteWebDriver driver;
	
	/** The url of the community. */
	private String url = "http://community.perfectomobile.com/";
	
	/** The menu panel at the top of the page. */
	protected WebUpperMenuPageView menuPanel;
	
	/** The device properties. */
	private HashMap<String, String> deviceProperties;
	
	 /**********************************************************************
 	 * 		Constructor
 	 * ********************************************************************.
 	 *
 	 * @param driver the driver
 	 */
	public WebCommunityBaseView(RemoteWebDriver driver){
		this.driver = driver;
	}
	
	/**
	 * Instantiates a new web community base view.
	 *
	 * @param driver the driver
	 * @param deviceProperties the device properties
	 */
	public WebCommunityBaseView(RemoteWebDriver driver, HashMap<String,String> deviceProperties){
		this.driver = driver;
		this.deviceProperties = deviceProperties;
	}
	
		
	
	/**
	 *  
	 * 		init: initializes the driver.
	 * 		Navigates to community url and validates page is logged out.
	 * 		in case its not, logs out to reach the log in screen.
	 *
	 * @return the web community base view
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WebCommunityBaseView init() throws IOException{
				
		//set timeouts
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		//open url
		driver.get(url);
		
		//validate page opened	
		try{
			//PerfectoUtils.fluentWait(communityLogo, driver, 20);
			menuPanel = new WebUpperMenuPageView(this.driver);
			if (menuPanel.isLoggedIn()){
				menuPanel = menuPanel.logOut();
			}
		}
		catch(Exception e){
			/*
			String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
			throw new IllegalStateException();
			*/
		}	
		return this;
	}
	
	
	/**********************************************************************
	 * 	searchItem	
	 * 		This method searches a given text in the search bar of the community site.
	 * 		
	 * 		@param text	the text to search for
	 * 		@return	new instance of the view to the searched results page
	 * @throws IOException 
	 **********************************************************************/
	public WebSearchResultsPageView searchItem(String text) throws IOException{
		return menuPanel.searchItem(text);
	}
	
	
	/**
	 * ***
	 * login to the Community
	 * 	gets a username and password and logins to the community.
	 *
	 * @param username username
	 * @param password password
	 * @return the same page only this time the upper menu is logged in
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WebCommunityBaseView login(String username,String password) throws IOException{
		
		this.menuPanel= menuPanel.login(username, password);
		return this;
	}
	
	/**
	 * 
	 * logout
	 * performs a logout from the system.
	 *
	 * @return same page only this time the upper menu is logged out
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WebCommunityBaseView logOut() throws IOException{
		try {
			this.menuPanel = menuPanel.logOut();
			return this;
		} catch (Exception e) {

			throw new IllegalStateException();
		}
		
		
	}
	
	/**
	 * 
	 * getWelcomeMessage
	 * gets the welcome message after login.
	 *
	 * @return the welcome message after login
	 */
	public String getWelcomeMessage(){
		
		return menuPanel.getWelcomeMessage();
	}
	
	/**
	 * Gets the device property.
	 *
	 * @param key the key
	 * @return the device property
	 */
	public String getDeviceProperty(String key){
		return deviceProperties.get(key);
	}
	
}
