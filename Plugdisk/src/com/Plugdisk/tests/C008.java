package com.Plugdisk.tests;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class C008 extends BaseTestLogic {
	
	private static String System_Out = "", Num_of_users = "", userID = "", userPass = "", 
			userName = "", userEmail = "", userPosition = "", userPh = "", userQuota = "", quotaUnit = "";
	private static String [] user_list;
	
	public C008(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Modify User                          *");
		System.out.println("*****************************************************************");
		System.out.println("Test C008 - User - Modify User");
		System.out.println("If more than 1 user selected, can it show warning message? \n" +
							"2명 이상의 사용자를 선택 했을 때 한명의 사용자만 선택하라는 경고창이 출력 되는지 확인 \n" +
							"----------------------------------------------------------------------------------------------");
		
		//go to user menu
	    driver.findElement(By.id("user")).click();	//go to user menu
		Thread.sleep(1000);
	    
		//if 2 user selected
	    if (isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]")) &
	    		isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr[2]/td[3]"))){
	    	
	    	driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();
	    	driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr[2]/td/input")).click();
	    	
	    	driver.findElement(By.xpath("//li[contains(text(), '정보 수정')]")).click();	//click Add user button
			Thread.sleep(2000);
			
			if (isAlertPresent()){		//while Alert msg appears
				System_Out = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();		//Click OK from popup alert
			}else
				System_Out = "No warning message while more than 2 users selected (FAIL)";
	    }else
	    	System_Out = "2 user not present to select.";
	    
	    
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Modify User                          *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test C008 - User - Modify User\r\n");
	        builder.append("If more than 1 user selected, can it show warning message? \r\n" +
						   "2명 이상의 사용자를 선택 했을 때 한명의 사용자만 선택하라는 경고창이 출력 되는지 확인\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(System_Out + "\r\n");
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
