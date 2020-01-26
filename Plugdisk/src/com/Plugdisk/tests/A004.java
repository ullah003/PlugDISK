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

public class A004 extends BaseTestLogic {
	
	private static String success = "", userID = "", userPass = "", newPass = "", output = "", output1 = "";
	
	public A004(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException  {
		System.out.println("*****************************************************************");
		System.out.println("*                        Change Admin Password                  *");
		System.out.println("*****************************************************************");
		System.out.println("Test A004 - Admin Login-Changed Password");
		System.out.println("After changind Admin password, if you try to login again can it login using new password?\n" +
							"관리자 설정에서 패스워드를 변경 후 변경이 되었는지 로그아웃 후 변경 된 정보로 로그인 하여 확인? \n"+
							"------------------------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=5; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 1){
		        	//System.out.println(line);
		        	String[] tokens = line.split("=");
	            	if (tokens[0].equals("passwd")){
	            		userPass = tokens[1];
		        	}else if (tokens[0].equals("passwd1")){
		        		newPass = tokens[1];
		        	}else if (tokens[0].equals("ID")){
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
	    
	    
		if (isElementPresent(By.id("admin"))){		//If Admin menu is present
			success = "Admin login successful.";
			driver.findElement(By.id("admin")).click();	// go to Admin menu
			//Change Admin password
			driver.findElement(By.id("passwd")).clear();
			driver.findElement(By.id("passwd")).sendKeys(userPass);
			driver.findElement(By.id("passwd1")).clear();
			driver.findElement(By.id("passwd1")).sendKeys(newPass);
			driver.findElement(By.id("passwd2")).clear();
			driver.findElement(By.id("passwd2")).sendKeys(newPass);
			driver.findElement(By.id("okButton")).click();
			output = driver.switchTo().alert().getText();
			output = "Changing password....\r\n" + output; 
			driver.switchTo().alert().accept();		//Click OK from popup alert
		}else
			success = "Admin login FAIL.";
		
		System.out.println(success + "\nSystem Output: " + output);
		
		//Logout to login again with new password
		driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();
		
		//try to login using new password
		driver.findElement(By.id("userid")).clear();
		driver.findElement(By.id("userid")).sendKeys(userID);
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys(newPass);
		driver.findElement(By.id("login_button")).click();
		
//		output = driver.switchTo().alert().getText();
//		driver.switchTo().alert().accept();		//Click OK from popup alert
		
		if (isElementPresent(By.id("admin"))){		//If Admin menu is present
			success = "Admin login using new password successful.";
			//reset password to previous one 
			driver.findElement(By.id("admin")).click();	// go to Admin menu
			//Change Admin password
			driver.findElement(By.id("passwd")).clear();
			driver.findElement(By.id("passwd")).sendKeys(newPass);
			driver.findElement(By.id("passwd1")).clear();
			driver.findElement(By.id("passwd1")).sendKeys(userPass);
			driver.findElement(By.id("passwd2")).clear();
			driver.findElement(By.id("passwd2")).sendKeys(userPass);
			driver.findElement(By.id("okButton")).click();
			output1 = driver.switchTo().alert().getText();
			output1 = "Reseting previous password....\r\n" + output1;
			driver.switchTo().alert().accept();		//Click OK from popup alert
			
		}else
			success = "Admin login using new password FAIL.";
		
		System.out.println(success + "\nSystem Output: " + output1);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                       Change Admin Password                       *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test A004 - Admin Login-Changed Password\r\n");
	        builder.append("After changind Admin password, if you try to login again can it login using new password? \r\n" +
						   "관리자 설정에서 패스워드를 변경 후 변경이 되었는지 로그아웃 후 변경 된 정보로 로그인 하여 확인?\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(success + "\r\n" + output + "\r\n" + output1);
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
