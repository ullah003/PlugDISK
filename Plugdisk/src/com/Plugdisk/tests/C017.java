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

public class C017 extends BaseTestLogic {
	
	private static String System_Out = "", userID = "", userName = "", userEmail = "", 
			userPosition = "", userPh = "", userStatus = "", userQuota = "", quotaUnit = "";
	
	public C017(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Delete User                          *");
		System.out.println("*****************************************************************");
		System.out.println("Test C017 - User - Delete User");
		System.out.println("Can it delete user account from system? \n" +
							"시스템 사용자 정보가 삭제 되는지 확인 \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=26; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 25){
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
					//driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();	//select check box
					
					System.out.println("Current user status: " + driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText());
			    	if (driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText().equals("정상")){	//if status is Active (정상) 
			    		//select user and change status to Inactive (서비스 중지)
			    		driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();
			    		Thread.sleep(1000);
			    		driver.findElement(By.xpath("//body/div[4]/div[2]/form/div/ul/li[5]")).click();
			    	}
			    	
			    	//try to delete user
			    	Thread.sleep(2000);
		    		driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();		//select 1st user which is active
		    		driver.findElement(By.xpath("//body/div[4]/div[2]/form/div/ul/li[3]")).click();				//click delete button
		    		
		    		if (isAlertPresent()){		//while Alert msg appears
						System_Out = driver.switchTo().alert().getText();
						//if (System_Out.equals("서비스 중지 사용자만 삭제할 수 있습니다."))
							//System_Out = System_Out + "\r\nCan show warning while trying to delete Active user successfully.";
						driver.switchTo().alert().accept();		//Click OK from popup alert
					}else
						System_Out = "No warning message while deleting user.";
		    		
		    		//check if user has been deleted
		    		driver.findElement(By.id("search_txt")).clear();
					driver.findElement(By.id("search_txt")).sendKeys(userID);
					
					driver.findElement(By.xpath("//body/div[4]/div[2]/form/div[2]/div/div[2]/input[2]")).click();		//click Search button
					Thread.sleep(1000);
					if (isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]"))){
						String tmp1_userID = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]")).getText();
						if (tmp1_userID.equals(userID)){
							System_Out = "User " + userID + " delete FAIL.";
						}else
							System_Out = "User " + userID + " has been deleted successfully.";
					}else{
						System_Out = "User " + userID + " has been deleted successfully.";
					}
				}else
					System_Out = "The user you want to delete is not available.";
			}else
				System_Out = "The user you want to delete is not available.";
		}else
			System_Out = "No user present to delete.";
		
	    
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
			
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Delete User                          *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test C017 - User - Delete User\r\n");
	        builder.append("Can it delete user account from system? \r\n" +
						   "시스템 사용자 정보가 삭제 되는지 확인 \r\n");
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
