package com.Plugdisk.tests;


import java.io.File;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

public abstract class BaseTestLogic {
	protected WebDriver driver;
	protected File fConf;
	protected File fReport;
	TestReportLisenter Lisenter;
	
	public BaseTestLogic(WebDriver webDriver, File fileConf, File fileReport) {
		this.driver = webDriver;
		this.fConf = fileConf;
		this.fReport = fileReport;
		Lisenter = null;
	}
	
	
	public boolean isElementPresent(By by) {
	    boolean flag = true;
	    driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	    if (!(driver.findElements(by).size() > 0)) {
	        flag = false;
	    } 
	    driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
	    return flag;
	}
	
	public boolean isElementVisible(By by){
		return driver.findElement(by).isDisplayed();
	}
	
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    }   // try 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }   // catch 
	}
	
	abstract void startTest() throws InterruptedException;
	
	public interface TestReportLisenter {
		public void onReportLisenter(String report);
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public File getfConf() {
		return fConf;
	}

	public void setfConf(File fConf) {
		this.fConf = fConf;
	}

	public File getfReport() {
		return fReport;
	}

	public void setfReport(File fReport) {
		this.fReport = fReport;
	}

	public TestReportLisenter getLisenter() {
		return Lisenter;
	}

	public void setLisenter(TestReportLisenter lisenter) {
		Lisenter = lisenter;
	}
}
