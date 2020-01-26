package com.Plugdisk.tests;

import java.io.IOException;
//import java.util.concurrent.TimeUnit;
//import org.openqa.selenium.By;
//import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import com.Plugdisk.tests.MainViewer;


public class plugdisk_All {
	
	protected static WebDriver driver;

	public static void main(String[] args) throws IOException, InterruptedException{
		MainViewer mainView = new MainViewer();
		mainView.setVisible(true);					
	}
	
//	public static boolean isElementPresent(By by) {
//	    boolean flag = true;
//	    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//	    if (!(driver.findElements(by).size() > 0)) {
//	        flag = false;
//	    } 
//	    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
//	    return flag;
//	}
//	
//	public static boolean isElementVisible(By by){
//		return driver.findElement(by).isDisplayed();
//	}
//	
//	public static boolean isAlertPresent() 
//	{ 
//	    try 
//	    { 
//	        driver.switchTo().alert(); 
//	        return true; 
//	    }   // try 
//	    catch (NoAlertPresentException Ex) 
//	    { 
//	        return false; 
//	    }   // catch 
//	}
}
