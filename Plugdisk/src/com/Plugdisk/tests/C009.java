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

public class C009 extends BaseTestLogic {
	
	private static String System_Out = "", userID = "", userName = "", userEmail = "", 
			userPosition = "", userPh = "", userStatus = "", userQuota = "", quotaUnit = "";
	
	public C009(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Modify User                          *");
		System.out.println("*****************************************************************");
		System.out.println("Test C009 - User - Initialize Password");
		System.out.println("Can it initialize password same as user name when 'Initialize' button clicked? \n" +
							"'사용자 초기화' 버튼을 클릭했을 때 아이디와 동일한 패스워드로 변경 되는지 확인? \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=17; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 16){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("user_ID")){
		        		userID = tokens[1];
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
	    
//	    String currentURL = driver.getCurrentUrl();
//		//http://192.168.2.131/plugdisk/admin/login.php
//		String[] tokens = currentURL.split("/");
//		System.out.println("1111111111: " + tokens[1] + "2222222222: " + tokens[2] + "33333333333333: " + tokens[3]);
		
	    //go to user menu
	    driver.findElement(By.id("user")).click();	//go to user menu
		Thread.sleep(1000);
	    
	    //if at least 1 user present
	    if (isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]"))){
	    	//search corresponding user
	    	
	    	Select select = new Select(driver.findElement(By.xpath("//select[@id='search_type']")));
			select.selectByVisibleText("아이디");
			Thread.sleep(1000);
			
	    	driver.findElement(By.id("search_txt")).clear();
			driver.findElement(By.id("search_txt")).sendKeys(userID);
			
			//driver.findElement(By.xpath("//*[@id='search_button']")).click();
			driver.findElement(By.xpath("//body/div[4]/div[2]/form/div[2]/div/div[2]/input[2]")).click();		//click Search button
			
			Thread.sleep(2000);
			//if search result available
			if (isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]"))){
				String tmp_userID = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]")).getText();
				if (tmp_userID.equals(userID)){
					driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();
					driver.findElement(By.xpath("//li[contains(text(), '정보 수정')]")).click();	//click Add user button
					Thread.sleep(2000);
					
					if (isElementPresent(By.id("boxIframe"))){
						//switch to user creation frame
						driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
						
						driver.findElement(By.id("initPwd")).click();					//initialize Password
						Thread.sleep(1000);
						if (isAlertPresent()){		//while Alert msg appears
							System_Out = driver.switchTo().alert().getText();
							System.out.println(System_Out);
							driver.switchTo().alert().accept();		//Click OK from popup alert
													
						}
						
						driver.switchTo().defaultContent();
						
						//User login using reseted password
						driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();		//click logout
						Thread.sleep(1000);
						String currentURL = driver.getCurrentUrl();
						
						String[] tokens = currentURL.split("/");
						//System.out.println("1111111111: " + tokens[1] + " 2222222: " + tokens[2] + " 33333333333333: " + tokens[3]);
						//Go to general user login page 
						driver.get(tokens[2] + "/plugdisk/login.php");
						Thread.sleep(1000);
						//login using default password
						driver.findElement(By.id("userid")).clear();
						driver.findElement(By.id("userid")).sendKeys(userID);
						driver.findElement(By.id("passwd")).clear();
						driver.findElement(By.id("passwd")).sendKeys(userID);
						driver.findElement(By.id("login_button")).click();
						Thread.sleep(1000);
						if (isElementPresent(By.xpath("//body/div/ul/li[2]"))){
							driver.findElement(By.xpath("//body/div/ul/li[2]")).click();
							String user_name = driver.findElement(By.xpath("//div[@id='diskname']/span")).getText();
							
							//System.out.println("User name displayed : " + user_name);
							if (user_name.equals(userID))
								System_Out = System_Out + "\nPassword initialization for "  + userID + " successful.";
							else
								System_Out = System_Out + "\nPassword initialization for "  + userID + " FAIL.";
							
							//logout from general user home page
							driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();	//click logout
						}
						
						//Alternate way to match user name
//						if (isElementPresent(By.xpath("//body/div[3]/div/div[2]/div"))){
//							String user_name = driver.findElement(By.xpath("//body/div[3]/div/div[2]/div")).getText();
//							String[] tokens1 = user_name.split("\\(");
//							System.out.println("00000000000: " + tokens1[0] + "1111111111: " + tokens1[1]);
//							tokens1 = tokens1[1].split("\\)");
//							if (tokens1[0].equals(userID)){
//								System_Out = System_Out + "\nPassword initialization for "  + userID + " successful.";
//							}else
//								System_Out = System_Out + "\nPassword initialization for "  + userID + " FAIL.";
//						}
							
						 
					}else
						System_Out = "User update popup not available!.";
				}else
					System_Out = "The user you want to modify is not available.";
			}else
				System_Out = "The user you want to modify is not available.";
		}else
			System_Out = "No user present to reset password.";
	    
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
			
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Modify User                          *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test C009 - User - Initialize Password\r\n");
	        builder.append("Can it initialize password same as user name when 'Initialize' button clicked? \r\n" +
						   "'사용자 초기화' 버튼을 클릭했을 때 아이디와 동일한 패스워드로 변경 되는지 확인?\r\n");
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
