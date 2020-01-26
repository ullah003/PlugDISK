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

public class A006 extends BaseTestLogic {
	
	private static String success = "", baseUrl = "", userID = "", userPass = "";
	
	public A006(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Remember Admin ID                    *");
		System.out.println("*****************************************************************");
		System.out.println("Test A006 - Admin Login-Remember Admin ID");
		System.out.println("If Remember ID checked, can it remember ID?\n" +
							"체크 박스를 클릭했을 때 이이디를 기억하는지 확인 \n"+
							"--------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=3; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	//System.out.println(line);
	        	String[] tokens = line.split("=");
	        	if (tokens[0].equals("url")){
	        		baseUrl = tokens[1];
	        	}else if (tokens[0].equals("ID")){
	        		userID = tokens[1];
	        	}else if (tokens[0].equals("passwd")){
	        		userPass = tokens[1];
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
	    
		driver.get(baseUrl);
		
	    
		if (isElementPresent(By.id("remID"))){		//If remember ID check box present
			if (!driver.findElement(By.id("remID")).isSelected())
				driver.findElement(By.id("remID")).click();
			
			driver.findElement(By.id("userid")).clear();
			driver.findElement(By.id("userid")).sendKeys(userID);
			driver.findElement(By.id("passwd")).clear();
			driver.findElement(By.id("passwd")).sendKeys(userPass);
			
			driver.findElement(By.id("login_button")).click();
			
			Thread.sleep(1000);
			
			if (isElementPresent(By.xpath("//li[contains(text(), '로그아웃')]"))){		//If Logout menu is present
				driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();
				Thread.sleep(5000);
				
				String AdminID = driver.findElement(By.id("userid")).getAttribute("value");
				//System.out.println("------------------------>" + AdminID);
				if (driver.findElement(By.id("remID")).isSelected() & AdminID.equals("wsadmin")){
					success = "Can remember Admin ID successfully.";
				}else
					success = "Can't remember Admin ID (FAIL).";
				
			}else
				success = "Logout FAIL.";
		}else
			success = "Remember ID checkbox is not available.";
		
		System.out.println(success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Remember Admin ID                    *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test A006 - Admin Login-Remember Admin ID\r\n");
	        builder.append("If Remember ID checked, can it remember ID? \r\n" +
						   "체크 박스를 클릭했을 때 이이디를 기억하는지 확인\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
