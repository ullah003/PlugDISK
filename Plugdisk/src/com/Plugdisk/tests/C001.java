package com.Plugdisk.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class C001 extends BaseTestLogic {
	
	private static String System_Out = "", Num_of_users = "", userID = "", userPass = "", 
			userName = "", userEmail = "", userPosition = "", userPh = "", userQuota = "", quotaUnit = "";
	private static String [] user_list;
	
	public C001(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Create User                          *");
		System.out.println("*****************************************************************");
		System.out.println("Test C001~C007 - User - Create User");
		System.out.println("Can it create user properly? \n" +
							"사용자를 제대로 만들 수 있습니까? \n"+
							"--------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=15; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	//System.out.println(line);
	        	if (i > 6){
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("no_of_user")){
		        		Num_of_users = tokens[1];
		        	}else if (tokens[0].equals("ID")){
		        		userID = tokens[1];
		        	}else if (tokens[0].equals("pass")){
		        		userPass = tokens[1];
		        	}else if (tokens[0].equals("name")){
		        		userName = tokens[1];
		        	}else if (tokens[0].equals("email")){
		        		userEmail = tokens[1];
		        	}else if (tokens[0].equals("position")){
		        		userPosition = tokens[1];
		        	}else if (tokens[0].equals("Ph")){
		        		userPh = tokens[1];
		        	}else if (tokens[0].equals("disk_quota")){
		        		userQuota = tokens[1];
		        	}else if (tokens[0].equals("quota_unit")){
		        		quotaUnit = tokens[1];
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
	    
	    int Num_of_user1 = Integer.parseInt(Num_of_users);
		user_list = new String[Num_of_user1];
		
		driver.findElement(By.id("user")).click();	//go to user menu
		Thread.sleep(1000);
		
		for (int i=1; i<=Num_of_user1; i++){
			System_Out = userID + i + "  created successfully.";
			
			driver.findElement(By.xpath("//li[contains(text(), '사용자 생성')]")).click();	//click Add user button
			Thread.sleep(2000);
			//switch to user creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
/*
			//check if Max user exceed 
			//if (isElementPresent(By.xpath("//body/div[28]/div[2]/div/div/div/div/div/div[2]"))){
			if (isElementPresent(By.xpath("//div[@class='ext-mb-content']"))){	
				System_Out = driver.findElement(By.xpath("//div[@class='ext-mb-content']")).getText();
				if (System_Out.equals("Can not create User account.\nMaximum number of User accounts has been exceeded.\n(Max number of user accounts: 200)")){
					//user_list[i-1] = user_name + i + ": " + System_Out;
					System.out.println(user_list[i-1] + "\n");
					//System.out.println("Exceed----> " + user_name + i + ": " + System_Out);
					driver.findElement(By.xpath("//button[contains(text(), 'OK')]")).click();
				break;
				}
			}
*/			
			//Enter user informations (Name, desc, email, password)
			//driver.findElement(By.xpath("//*[@id='userid']")).sendKeys(userID + i);
			driver.findElement(By.id("userid")).sendKeys(userID + i);
			driver.findElement(By.id("passwd1")).sendKeys(userPass + i);
			driver.findElement(By.id("passwd2")).sendKeys(userPass + i);
			driver.findElement(By.id("username")).sendKeys(userName + i );
			driver.findElement(By.id("email")).sendKeys(userEmail);
			driver.findElement(By.id("position")).sendKeys(userPosition + i);
			driver.findElement(By.id("tel")).sendKeys(userPh);
			driver.findElement(By.id("quota")).sendKeys(userQuota);
			//quota_unit
			
			Select select = new Select(driver.findElement(By.xpath("//select")));
			//select.deselectAll();
			if (quotaUnit.equals("MB"))
				select.selectByVisibleText("MB");
			else if (quotaUnit.equals("GB"))
				select.selectByVisibleText("GB");
			else if (quotaUnit.equals("TB"))
				select.selectByVisibleText("TB");
			else{
				System_Out = "Wrong entry for User Quota!"; 
				break;
			}
			Thread.sleep(1000);
			
			//Click next--> 
			driver.findElement(By.id("userCreateButton")).click();
			Thread.sleep(1000);
			
			if (isAlertPresent()){		//while Alert msg appears
				System_Out = driver.switchTo().alert().getText();
				System_Out = "(" + userID + i + ") " + System_Out;
				driver.switchTo().alert().accept();		//Click OK from popup alert
			}else if (isElementPresent(By.id("departCreateButton"))){	//while goes to next step normally
				//select brifcase and add
				if (isElementPresent(By.xpath("//*[@id='departTree']/li/ul/li/a/span"))){
					driver.findElement(By.xpath("//*[@id='departTree']/li/ul/li/a/span")).click();
					//driver.findElement(By.xpath("//body/div/div/div[2]/div[2]/ul/li/ul/li/a/span")).click();
					driver.findElement(By.id("addBtn")).click();
				}else
					System_Out = "Department tree not available!";
					
				//click OK
				driver.findElement(By.id("departCreateButton")).click();
			}else if (isElementVisible(By.id("userID_errMsg"))){
				System_Out = driver.findElement(By.id("userID_errMsg")).getText();
				System_Out = "(" + userID + i + ") " + System_Out;
			}else if (isElementVisible(By.id("userName_errMsg"))){
				System_Out = driver.findElement(By.id("userName_errMsg")).getText();
				System_Out = "(" + userID + i + ") " + System_Out;
			}else if (isElementVisible(By.id("email_errMsg"))){
				System_Out = driver.findElement(By.id("email_errMsg")).getText();
				System_Out = "(" + userID + i + ") " + System_Out;
			}else if (isElementVisible(By.id("quota_errMsg"))){
				System_Out = driver.findElement(By.id("quota_errMsg")).getText();
				System_Out = "(" + userID + i + ") " + System_Out;
			} 
			
//			if (isElementPresent(By.id("departCreateButton"))){
//				//select brifcase and add
//				//driver.findElement(By.xpath("ul[@id='departTree']/li/ul/li/a/span")).click();
//				//driver.findElement(By.id("addBtn")).click();
//				
//				//click OK
//				driver.findElement(By.id("departCreateButton")).click();
//			}
			
			user_list[i-1] = System_Out;
			System.out.println(user_list[i-1]);
			
			//go back to default content
			driver.switchTo().defaultContent();
			
//			if (isElementPresent(By.xpath("//div[@class='ext-mb-content']"))){	
//				//System_Out = driver.findElement(By.xpath("//body/div[28]/div[2]/div/div/div/div/div/div[2]")).getText();
//				System_Out = driver.findElement(By.xpath("//div[@class='ext-mb-content']")).getText();
//				//System.out.println("Found----> " + user_name + i + ": " + System_Out);
//				Thread.sleep(1000);
//				driver.findElement(By.xpath("//button[contains(text(), 'OK')]")).click();
//			}
//			System.out.println(user_list[i-1]);
		}
		
		//System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Create User                          *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test C001~C007 - User - Create User\r\n");
	        builder.append("Can it create user properly? \r\n" +
						   "사용자를 제대로 만들 수 있습니까?\r\n");
	        builder.append("=================================================================\r\n");
	        for (int i=1; i<=Num_of_user1; i++){
	        	if (user_list[i-1] != null)			//to avoid null print to report file
	        		builder.append(user_list[i-1] + "\r\n");
	        }
	        //builder.append(System_Out + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
