package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;


public class E009 extends BaseTestLogic {
	
	private static String Success = " ", shareName = "";
	
	public E009(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                     Share duplication check                   *");
		System.out.println("*****************************************************************");
		System.out.println("Test E009 - Create Share - Duplication check");
		System.out.println("Duplicaiton checking possible during creating a share? \n" +
							"공유디스크 이름은 중복 될 수 없다. \n"+
							"--------------------------------------------------------------------");

		Success = "";
		
	    //go to Share menu
  		driver.findElement(By.id("share")).click();
  		Thread.sleep(1000);
  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
  		Thread.sleep(1000);
		
  		//if at least 1 share present
  		if (isElementPresent(By.xpath("//div[@id='share-list']/table/tbody/tr/td[2]/span"))){
  			shareName = driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr/td[2]/span")).getText();
  			//System.out.println("--------------------Share Name:" + shareName);
  			driver.findElement(By.xpath("//li[contains(text(), '공유 생성')]")).click();	//click Add share button
			Thread.sleep(1000);
			//switch to user creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
			driver.findElement(By.id("shareName")).clear();
			driver.findElement(By.id("shareName")).sendKeys(shareName + "\t");
			Thread.sleep(1000);
			
			if (isElementVisible(By.id("shareName_errMsg"))){
				Success = driver.findElement(By.id("shareName_errMsg")).getText();
				Success = Success + "\nDuplication check during share creation successful.";
			}else
				Success = "Duplication check during share creation FAIL.";
			
			driver.findElement(By.id("closeButton")).click();
  		}else
  			Success = "No share present for duplicaiton test.";
	
  		System.out.println(Success);
			
	

		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                       Search duplication check                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E009 - Create Share - Duplication check\r\n");
	        builder.append("Duplicaiton checking possible during creating a share? \r\n" +
						   "공유디스크 이름은 중복 될 수 없다.\r\n");
	        builder.append("=================================================================\r\n");
	        
	        builder.append(Success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}