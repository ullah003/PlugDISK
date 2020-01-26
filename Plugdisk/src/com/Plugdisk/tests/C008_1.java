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

public class C008_1 extends BaseTestLogic {
	
	private static String System_Out = "", userID = "", userName = "", userEmail = "", 
			userPosition = "", userPh = "", userStatus = "", userQuota = "", quotaUnit = "";
	
	public C008_1(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Modify User                          *");
		System.out.println("*****************************************************************");
		System.out.println("Test C008_1,C010 - User - Modify User");
		System.out.println("User modificaiton popup appears when you click 'Modify user' button and can it modify user info? \n" +
							"수정할 사용자를 선택 후 '정보수정' 버튼을 클릭하면 '사용자 수정' 창이 출력 되는지 확인 \n" +
							"이름, 이메일, 직급, 상태, 용량을 수정 할 수 있는지 확인 \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=24; i++){
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
		        	}else if (tokens[0].equals("name")){
		        		userName = tokens[1];
		        	}else if (tokens[0].equals("email")){
		        		userEmail = tokens[1];
		        	}else if (tokens[0].equals("position")){
		        		userPosition = tokens[1];
		        	}else if (tokens[0].equals("ph")){
		        		userPh = tokens[1];
		        	}else if (tokens[0].equals("status")){
		        		userStatus = tokens[1];
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
						
						
						//can't test all together
	//					driver.findElement(By.id("initPwd")).click();					//initialize Password
	//					Thread.sleep(1000);
	//					if (isAlertPresent()){		//while Alert msg appears
	//						//String mainWindowHandle = driver.getWindowHandles().iterator().next();
	//						
	//						System_Out = driver.switchTo().alert().getText();
	//						System.out.println(System_Out);
	//						driver.switchTo().alert().accept();		//Click OK from popup alert
	//						
	//						//driver.switchTo().window(mainWindowHandle);
	//					}
	//					Thread.sleep(1000);
						
						driver.findElement(By.id("username")).clear();
						driver.findElement(By.id("username")).sendKeys(userName);		//insert User Name
						driver.findElement(By.id("email")).clear();
						driver.findElement(By.id("email")).sendKeys(userEmail);			//insert User e-mail
						driver.findElement(By.id("position")).clear();
						driver.findElement(By.id("position")).sendKeys(userPosition);	//insert User position
						driver.findElement(By.id("tel")).clear();
						driver.findElement(By.id("tel")).sendKeys(userPh);				//insert User Phone
						
						Select status = new Select(driver.findElement(By.id("userstate")));		//Select user status
						if (userStatus.equals("Active"))
							status.selectByVisibleText("정상");
						else if (userStatus.equals("Inactive"))
							status.selectByVisibleText("서비스 중지");
						else{
							System_Out = System_Out + "\nWrong entry for user status! Accepts Active or Inactive only."; 
						}
						Thread.sleep(1000);
						driver.findElement(By.id("quota")).clear();
						driver.findElement(By.id("quota")).sendKeys(userQuota);			//insert User Quota
						
						Select selectUnit = new Select(driver.findElement(By.id("quota_unit")));		//Select quota unit
						if (quotaUnit.equals("MB"))
							selectUnit.selectByVisibleText("MB");
						else if (quotaUnit.equals("GB"))
							selectUnit.selectByVisibleText("GB");
						else if (quotaUnit.equals("TB"))
							selectUnit.selectByVisibleText("TB");
						else{
							System_Out = System_Out + "\nWrong entry for User Quota! Accepts MB/GB/TB only."; 
						}
						Thread.sleep(1000);
						
						//
						driver.findElement(By.id("modifyButton")).click();
						
						Thread.sleep(1000);
						//go back to default content
						driver.switchTo().defaultContent();
						
						if (isElementPresent(By.id("boxIframe"))){
							driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
							
							if (isElementVisible(By.id("userName_errMsg"))){						//userName_errMsg
								System_Out = System_Out + "\n" + driver.findElement(By.id("userName_errMsg")).getText();
							}else if (isElementVisible(By.id("email_errMsg"))){							//email_errMsg
								System_Out = System_Out + "\n" + driver.findElement(By.id("email_errMsg")).getText();
							}else if (isElementVisible(By.id("quota_errMsg"))){							//quota_errMsg
								System_Out = System_Out + "\n" + driver.findElement(By.id("quota_errMsg")).getText();
							}
							//click cancel button
							driver.findElement(By.id("closeButton")).click();
							System_Out = System_Out + "\nTest FAIL!" ;
						}else {
							//check if modified contents are correct	
							driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();
							driver.findElement(By.xpath("//li[contains(text(), '정보 수정')]")).click();	//click Add user button
							Thread.sleep(2000);
							
							//switch to user creation frame
							driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
							
							if (userName.equals(driver.findElement(By.id("username")).getAttribute("value")) &
									userEmail.equals(driver.findElement(By.id("email")).getAttribute("value")) &
									userPosition.equals(driver.findElement(By.id("position")).getAttribute("value")) & 
									userPh.equals(driver.findElement(By.id("tel")).getAttribute("value")) &
									userQuota.equals(driver.findElement(By.id("quota")).getAttribute("value"))){
							
								System_Out = "User information updated successfully.";
							}else
								System_Out = "User information update FAIL!.";
							
							driver.findElement(By.id("closeButton")).click();	
							driver.switchTo().defaultContent();
						}
					}else
						System_Out = "User update popup not available!.";
				}else
					System_Out = "The user you want to modify is not available.";
			}else
				System_Out = "The user you want to modify is not available.";
		}else
			System_Out = "No user present to modify.";
		
	    
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Modify User                          *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test C008_1,C010 - User - Modify User\r\n");
	        builder.append("User modificaiton popup appears when you click 'Modify user' button and can it modify user info? \r\n" +
						   "수정할 사용자를 선택 후 '정보수정' 버튼을 클릭하면 '사용자 수정' 창이 출력 되는지 확인\r\n" +
						   "이름, 이메일, 직급, 상태, 용량을 수정 할 수 있는지 확인 \r\n");
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
