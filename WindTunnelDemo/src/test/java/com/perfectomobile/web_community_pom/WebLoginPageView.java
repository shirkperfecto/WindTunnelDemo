package com.perfectomobile.web_community_pom;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import com.perfectomobile.utils.PerfectoUtils;

/**
 * The Class WebLoginPageView.
 */
public class WebLoginPageView extends WebCommunityBaseView {
	
	/** The username. */
	String username;
	
	/** The password. */
	String password;
	
	/** The header panel. */
	//page elements:
	private By headerPanel = By.xpath("//*[@class='page__wrapper']");
	
	/** The username text field. */
	private By usernameTextField = By.xpath("//input[@id='user_email']");
	
	/** The password text field. */
	private By passwordTextField = By.xpath("//input[@id='user_password']");
	
	/** The submit button. */
	private By submitButton = By.xpath("//input[@type='submit']");
	
		
	/**
	 *		Constructor.
	 *
	 * @param driver the driver
	 * @throws IOException 
	 */
	public WebLoginPageView(RemoteWebDriver driver) throws IOException{
        super(driver);
        this.username="";
        this.password="";
        
        //validate page loaded successfully before proceeding
        try{
        	PerfectoUtils.fluentWait(headerPanel, driver, 30);
        }
        catch(Exception e){
        	throw new IllegalStateException();
        }
        
       
    }
	

	/**
	 * login
	 * 	gets a username and password and logins to the community.
	 *
	 * @param username username
	 * @param password password
	 * @return the same page only this time the upper menu is logged in
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public WebLoginPageView login(String username, String password) throws IOException{
		try {
			WebElement element = this.driver.findElement(this.usernameTextField);
			//element.clear();
			element.sendKeys(username);
			element = this.driver.findElement(passwordTextField);
			//element.clear();
			element.sendKeys(password);
			
			this.driver.findElement(this.submitButton).click();
			this.username = username;
			this.password = password;
			
			return this;
			//check for errors:
								
		} catch (Exception e) {
			return null;
		}
		
	}
	
	
}
