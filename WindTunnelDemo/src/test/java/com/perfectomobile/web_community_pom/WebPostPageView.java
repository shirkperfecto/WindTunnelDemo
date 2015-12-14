package com.perfectomobile.web_community_pom;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Reporter;

import com.perfectomobile.utils.PerfectoUtils;

/**
 * The Class WebPostPageView.
 */
public class WebPostPageView extends WebCommunityBaseView {
	
	/** The post page. */
	private By postPage = By.xpath("//div[@class='post-head-main']");
	
	/** The post title. */
	private By postTitle = By.xpath("//h2[@class='title']");
	
	/** The post description. */
	private By postDescription = By.xpath("//h4[starts-with(@class,'description')]");
	
	/** The post update date. */
	private By postUpdateDate = By.xpath("//div[starts-with(@class,'updated-at')]");
	
		
	/**
	 * 		Constructor.
	 *
	 * @param driver the driver
	 * @throws IOException 
	 */
	public WebPostPageView(RemoteWebDriver driver) throws IOException{
        super(driver);
        
        //validate page loaded successfully before proceeding
        try{
        	PerfectoUtils.fluentWait(postPage, driver, 15);
        }
        catch(Exception e){
        	String errorFile = PerfectoUtils.takeScreenshot(driver);
			Reporter.log("Error screenshot saved in file: " + errorFile);
        	throw new IllegalStateException();
        }
    }
	
	/**
	 * Gets the post title.
	 *
	 * @return the post title
	 */
	public String getPostTitle(){
		try {
			//return this.driver.findElement(postTitle).getText();
			return PerfectoUtils.fluentWait(postTitle, this.driver, 20).getText();
		} catch (Exception e) {
			return "ERROR";
		}
	}
	
	/**
	 * Gets the post description.
	 *
	 * @return the post description
	 */
	public String getPostDescription(){
		try {
			return this.driver.findElement(postDescription).getText();
		} catch (Exception e) {
			return "ERROR";
		}
	}
	
	/**
	 * Gets the post published date.
	 *
	 * @return the post published date
	 */
	public String getPostPublishedDate(){
		String text;
		int start;
		try {
			text =  this.driver.findElement(postUpdateDate).getText();
			start = text.indexOf("Published")+9;
			return text.substring(start);
			
		} catch (Exception e) {
			return "ERROR";
		}
	}
	

}
