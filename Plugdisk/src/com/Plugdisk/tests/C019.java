package com.Plugdisk.tests;

//import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class C019 extends BaseTestLogic {
	
	private static String System_Out = "", ID = "", Passwd = "", userID = "", userPass = "", userStatus = "", adminURL = "";
	
	public C019(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                   Activate/Deactivate User                    *");
		System.out.println("*****************************************************************");
		System.out.println("Test C019 - User - Activate/Deactivate User");
		System.out.println("If you deactivate user status can it show from web and restricts user access, \n" +
							"again when you activate user status can it show from web and allows user access? \n" +
							"사용자 서비스를 중지 하였을 때 웹 & 접속기에서 접속이 제한되는지 확인 \n" +
							"다시 서비스를 시작하였을 때 정상적으로 접속이 가능한지 확인 \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=29; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (i >= 2 & i <= 3 || i >= 28 & i <= 29){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("user_ID")){
		        		userID = tokens[1];
		        	}else if (tokens[0].equals("pass")){
		        		userPass = tokens[1];
		        	}else if (tokens[0].equals("ID")){
		        		ID = tokens[1];
		        	}else if (tokens[0].equals("passwd")){
		        		Passwd = tokens[1];
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
					
					userStatus = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText();
			    	System.out.println("Current user & status: " + userID + " : " + userStatus);
			    	//Deactivate the user if Activated currently
			    	if (userStatus.equals("정상")){		//if status is Active (정상) 
			    		//select 1st user and change status to Active (서비스 중지)
			    		driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();
			    		Thread.sleep(1000);
			    		driver.findElement(By.xpath("//body/div[4]/div[2]/form/div/ul/li[5]")).click();
			    	}
			    	Thread.sleep(1000);
			    	userStatus = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText();
			    	System.out.println("Status after service stop: " + userStatus);
			    	//User login after service stop
					driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();		//click logout
					Thread.sleep(1000);
					adminURL = driver.getCurrentUrl();
					
					String[] tokens = adminURL.split("/");
					//System.out.println("1111111111: " + tokens[1] + " 2222222: " + tokens[2] + " 33333333333333: " + tokens[3]);
					//Go to general user login page 
					driver.get(tokens[2] + "/plugdisk/login.php");
					Thread.sleep(1000);
					//login using default password
					driver.findElement(By.id("userid")).clear();
					driver.findElement(By.id("userid")).sendKeys(userID);
					driver.findElement(By.id("passwd")).clear();
					driver.findElement(By.id("passwd")).sendKeys(userPass);
					driver.findElement(By.id("login_button")).click();
					Thread.sleep(1000);
					
					
		    		if (isAlertPresent()){		//while Alert msg appears
						System_Out = driver.switchTo().alert().getText();
						if (System_Out.equals("사용중지중인 사용자 입니다.")){
							System_Out = System_Out + "\r\nCan deactivate user successfully.";
							driver.switchTo().alert().accept();		//Click OK from popup alert
						}else{
							System_Out = System_Out + "\r\nWrong ID or password (FAIL).";
							driver.switchTo().alert().accept();		//Click OK from popup alert
						}
					}else
						System_Out = System_Out + "\r\nCan't deactivate user (FAIL).";
					
	    
	    
				    //---------------------------------------------------------------
				    //Admin login to activate and test again
				    
				    driver.get(adminURL);
				    Thread.sleep(1000);
				    driver.findElement(By.id("userid")).clear();
					driver.findElement(By.id("userid")).sendKeys(ID);
					driver.findElement(By.id("passwd")).clear();
					driver.findElement(By.id("passwd")).sendKeys(Passwd);
					driver.findElement(By.id("login_button")).click();
					
					//go to user menu
				    driver.findElement(By.id("user")).click();	//go to user menu
					Thread.sleep(1000);
					
					select = new Select(driver.findElement(By.xpath("//select[@id='search_type']")));
					select.selectByVisibleText("아이디");
					Thread.sleep(1000);
					
			    	driver.findElement(By.id("search_txt")).clear();
					driver.findElement(By.id("search_txt")).sendKeys(userID);
					
					//driver.findElement(By.xpath("//*[@id='search_button']")).click();
					driver.findElement(By.xpath("//body/div[4]/div[2]/form/div[2]/div/div[2]/input[2]")).click();		//click Search button
					
					Thread.sleep(2000);
					//if search result available
//					if (isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]"))){
//						String tmp_userID = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]")).getText();
//						if (tmp_userID.equals(userID)){
							//driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();	//select check box
							
							userStatus = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText();
					    	System.out.println("Current user & status: " + userID + " : " + userStatus);
					    	//Deactivate the user if Activated currently
					    	if (!userStatus.equals("정상")){		//if status is Active (정상) 
					    		//select 1st user and change status to Active (서비스 중지)
					    		driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();
					    		Thread.sleep(1000);
					    		driver.findElement(By.xpath("//body/div[4]/div[2]/form/div/ul/li[4]")).click();
					    	}
					    	
					    	Thread.sleep(2000);
					    	userStatus = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText();
					    	System.out.println("Status after service start: " + userStatus);
					    	//User login after service stop
							driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();		//click logout
							Thread.sleep(1000);
							//String currentURL = driver.getCurrentUrl();
							
//							String[] tokens = adminURL.split("/");
			
							//Go to general user login page 
							driver.get(tokens[2] + "/plugdisk/login.php");
							Thread.sleep(1000);
							//login using default password
							driver.findElement(By.id("userid")).clear();
							driver.findElement(By.id("userid")).sendKeys(userID);
							driver.findElement(By.id("passwd")).clear();
							driver.findElement(By.id("passwd")).sendKeys(userPass);
							driver.findElement(By.id("login_button")).click();
							Thread.sleep(1000);
							
							if (isAlertPresent()){		//while Alert msg appears
								System_Out = System_Out + "\r\n" + driver.switchTo().alert().getText() + "\r\n Wrong ID or Password (FAIL).";
								driver.switchTo().alert().accept();		//Click OK from popup alert
							}else if (isElementPresent(By.xpath("//li[contains(text(), '로그아웃')]"))){		//if general user login successful
								System_Out = System_Out + "\r\nCan activate user successfully.";
								driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();		//click logout
							}else
								System_Out = System_Out + "\r\nCan't activate user again (FAIL).";
							
//						}else
//							System_Out = "The user you want to activate is not available.";
//					}else
//						System_Out = "The user you want to activate is not available.";
			
				}else
					System_Out = "The user you want to deactivate is not available.";
			}else
				System_Out = "The user you want to deactivate is not available.";
	    }else
			System_Out = "No user present to test.";	
	    
	    
	    //-----------------------------------------------------------------
		
	    
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                   Activate/Deactivate User                    *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test C019 - User - Activate/Deactivate User\r\n");
	        builder.append("If you deactivate user status can it show from web and restricts user access, \r\n" +
	        				"again when you activate user status can it show from web and allows user access? \r\n" +
						   "사용자 서비스를 중지 하였을 때 웹 & 접속기에서 접속이 제한되는지 확인\r\n" +
						   "다시 서비스를 시작하였을 때 정상적으로 접속이 가능한지 확인 \r\n" );
	        builder.append("=====================================================================\r\n");
	        builder.append(System_Out + "\r\n");
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
