package com.Plugdisk.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


import org.openqa.selenium.*;

public class D005 extends BaseTestLogic {
	
	private static String sub_dept_ID= "", Success = "", departname_name = "";
	
	public D005(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                     Department share creation                 *");
		System.out.println("*****************************************************************");
		System.out.println("Test D005 - Department - Create Share Folder");
		System.out.println("Can it create share folder while creating department?\n" +
							"부서를 생성 할 때 공유디스크를 자동 생성 할 수 있는지 확인. \n"+
							"--------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=31; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i > 30){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("dept_name")){
		        		departname_name = tokens[1];
		        	}
	        	}
	        }
	    } finally {
	        try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    
		//go to department menu
		driver.findElement(By.id("depart")).click();
		Thread.sleep(1000);
		
//		if (isElementPresent(By.xpath("//ul[@id='departTree']/li/div/span"))){		//If at least 1 dept present

			//sub_dept_ID = driver.findElement(By.xpath("//div[@id='depart-list']/table/tbody/tr/td[3]")).getText();
			
			driver.findElement(By.xpath("//li[contains(text(), '부서생성')]")).click();		//click Add department button
			Thread.sleep(2000);
			if (isElementPresent(By.id("boxIframe"))){
				//switch to department creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				
				driver.findElement(By.id("departName")).sendKeys(departname_name);	//Enter any department name	
	//			driver.findElement(By.id("departId")).clear();
	//			driver.findElement(By.id("departId")).sendKeys("");		//Enter existing department ID
				driver.findElement(By.id("createShare")).click();		//check create share checkbox
				Thread.sleep(1000);
				//click OK
				driver.findElement(By.id("okButton")).click();
				
				
				Thread.sleep(1000);
				//go back to default content
				driver.switchTo().defaultContent();
				
				if (isElementPresent(By.id("boxIframe"))){
					driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
					
					if (isElementVisible(By.id("departName_errMsg"))){						//userName_errMsg
						Success = driver.findElement(By.id("departName_errMsg")).getText();
					}else if (isElementVisible(By.id("departId_errMsg"))){							//email_errMsg
						Success = driver.findElement(By.id("departId_errMsg")).getText();
					}
					//click cancel button
					driver.findElement(By.id("closeButton")).click();
					Success = Success + "\r\nTest FAIL!" ;
				}else {
					//check if share folder created while creating department
					driver.findElement(By.id("share")).click();
					Thread.sleep(2000);
					
					driver.findElement(By.xpath("//ul[@id='menuTree']/li[4]/ul/li/div/span")).click();	//go to share folder list
					Thread.sleep(2000);
					
					//get share list and compare
					if (isElementPresent(By.id("share-list"))){
						WebElement sharelist = driver.findElement(By.id("share-list"));
		
							List<WebElement> allshare = sharelist.findElements(By.className("icon_share"));			
							for (WebElement share : allshare) {
								//System.out.println(share.getText());
								if (share.getText().equals(departname_name)){
									Success = "It can create share folder while creating department successfully";
									break;
								}else
									Success = "It can't create share folder while creating department (FAIL).";
							}
					}else
						Success = "No share list found, test FAIL.";
					
					
//					driver.findElement(By.id("closeButton")).click();	
//					driver.switchTo().defaultContent();
				}
				driver.switchTo().defaultContent();
			}else
				Success = "Department creation popup is not available!.";
			
				
			
//		}else
//			Success = "No department present to check ID duplication (FAIL).";
		
		System.out.println(Success);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
			builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                     Department share creation                 *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D005 - Department - Create Share Folder\r\n");
	        builder.append("Can it create share folder while creating department? \r\n" +
						   "부서를 생성 할 때 공유디스크를 자동 생성 할 수 있는지 확인.\r\n");
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