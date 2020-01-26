package com.Plugdisk1.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class F003 extends BaseTestLogic {
	
	private static String System_Out = "", shareName = "";
	int i, j, k, totl_user = 0;
	private static String [] user_list, Success;
	Boolean sharePresent = false;
	
	public F003(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                      Share Permission Setting                 *");
		System.out.println("*****************************************************************");
		System.out.println("Test F003 - Share - Permission Setting");
		System.out.println("Share permission setting can be done for top share? \n" +
							"공유 디스크 접근 권한 설정? \n"+
							"-----------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=40; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	//System.out.println(line);
	        	if (i > 39){
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("share_name")){
		        		shareName = tokens[1];
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
	    
		//go to Share menu
  		driver.findElement(By.id("share")).click();
  		Thread.sleep(1000);
  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
  		Thread.sleep(1000);
		
  		System_Out = "";
  		
  		//if at least one share present
  		if (isElementPresent(By.xpath("//div[@id='share-list']/table/tbody/tr/td[2]/span"))){
  			Select viewAll = new Select(driver.findElement(By.id("page_list_size")));
  			viewAll.selectByVisibleText("전체");
			Thread.sleep(2000);
			
			//select share which we want to edit
			WebElement sharelist = driver.findElement(By.id("share-list"));
			
			List<WebElement> allshare = sharelist.findElements(By.className("icon_share"));
			i = 0;
			for (WebElement share : allshare) {
				//System.out.println(share.getText());
				i = i + 1;
				if (share.getText().equals(shareName)){
					driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr[" + i + "]/td/input")).click();
					sharePresent = true;
					break;
				}
			}
  			
			//if the share which we want to set permission is present
			if (sharePresent){
				driver.findElement(By.xpath("//li[contains(text(), '권한 설정')]")).click();	//click set permission button
				Thread.sleep(1000);
				//switch to user creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				
				driver.findElement(By.id("user_info")).click();			//click user tab
				Thread.sleep(1000);
				driver.findElement(By.id("searchButton")).click();			//click user search button
				Thread.sleep(2000);
				
				//select all existing users and allow permission
				WebElement list_of_users = driver.findElement(By.id("userLists"));
				List<WebElement> all_users = list_of_users.findElements(By.className("icon_user"));	
				//Success = "Can delete share successfully.";
				
				for (WebElement user : all_users) {
					j = j + 1;
					//System.out.println(user.getText());
					driver.findElement(By.xpath("//div[@id='userLists']/table/tbody/tr[" + j + "]/td/span")).click();	//select user
					driver.findElement(By.id("shareMemAddBtn")).click();			//click > button
				}
				System.out.println("Total number of users permited to access share is: " + j);
				driver.findElement(By.id("okButton")).click();		//check OK 
				Thread.sleep(2000);
				driver.switchTo().defaultContent();
				
				//go to User menu and get list of users
		  		driver.findElement(By.id("user")).click();
		  		Thread.sleep(1000);
		  		
		  		Select user_viewAll = new Select(driver.findElement(By.id("page_list_size")));
		  		user_viewAll.selectByVisibleText("전체");
				Thread.sleep(2000);
		  		
				
				user_list = new String[j];
				Success = new String[j];
		  		//get user list
				WebElement user_List_Content = driver.findElement(By.id("userListContent"));
				List<WebElement> users_list = user_List_Content.findElements(By.className("ui_view"));
				for (WebElement user_element : users_list) {
					user_list[k] = user_element.getText();
					k = k + 1;
				}
				totl_user = k;
				System.out.println("Total number of users found in system is: " + totl_user);
				
				driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();			//click logout button
				
				String currentURL = driver.getCurrentUrl();
				
				String[] tokens = currentURL.split("/");
				//System.out.println("1111111111: " + tokens[1] + " 2222222: " + tokens[2] + " 33333333333333: " + tokens[3]);
				//Go to general user login page 
				driver.get(tokens[2] + "/plugdisk/login.php");
				Thread.sleep(1000);
				
				for (i=0; i<totl_user; i++) {
					//System.out.println(user_element.getText());
					
					driver.findElement(By.id("userid")).clear();
					driver.findElement(By.id("userid")).sendKeys(user_list[i]);
					driver.findElement(By.id("passwd")).clear();
					driver.findElement(By.id("passwd")).sendKeys(user_list[i]);
					driver.findElement(By.id("login_button")).click();
					Thread.sleep(1000);
					
					//get share list for the user
					WebElement user_sharelist = driver.findElement(By.className("share-info-layer"));
					
					List<WebElement> user_allshare = user_sharelist.findElements(By.className("sharename"));
					Success[i] = "User (" + user_list[i] + ") is not granted permission to share " + shareName + " (FAIL).";
					for (WebElement user_share : user_allshare) {
						//System.out.println(user_list[i] + "-----------" + user_share.getText());
					
						if (user_share.getText().equals(shareName)){
							Success[i] = "User (" + user_list[i] + ") has been granted permission to share " + shareName + " successfully.";
							break;
						}
							
					}
					System.out.println(Success[i]);
					driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();			//click logout button
					
				}
				
			}else{
				System_Out = "The share you want to set permission is not available.";
				driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();			//click logout button
			}
  		}else{
  			System_Out = "No share present to set permission.";
  			driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();			//click logout button
  		}
  		
			System.out.println(System_Out);
			
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                      Share Permission Setting                 *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test F003 - Share - Permission Setting\r\n");
	        builder.append("Share permission setting can be done for top share? \r\n" +
						   "공유 디스크 접근 권한 설정?\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(System_Out + "\r\n");
	        builder.append("Total number of users permited to access share is: " + j + "\r\n");
	        builder.append("Total number of users found in system is: " + totl_user + "\r\n");
	        
	        for (int i=1; i<=totl_user; i++){
	        	if (Success[i-1] != null)			//to avoid null print to report file
	        		builder.append(Success[i-1] + "\r\n");
	        }
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}