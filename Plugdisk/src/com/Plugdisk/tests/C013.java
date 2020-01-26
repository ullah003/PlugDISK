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

public class C013 extends BaseTestLogic {
	
	private static String System_Out = "", userID = "", userStatus = "";
	
	public C013(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Delete User                          *");
		System.out.println("*****************************************************************");
		System.out.println("Test C013 - User - Delete User");
		System.out.println("If user status is Active and you try to delete it, does it shows warning message? \n" +
							"서비스 중지 상태가 아닌 사용자를 삭제 할 때 삭제 할 수 없다는 경고 메시지가 출력되는지 확인 \n" +
							"----------------------------------------------------------------------------------------------");
		
		//go to user menu
	    driver.findElement(By.id("user")).click();	//go to user menu
		Thread.sleep(1000);
	    
		//if at least one user present with its current status
	    if (isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]"))){
	    	
	    	System.out.println("Current user status: " + driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText());
	    	if (!driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[7]")).getText().equals("정상")){	//if status is not Active (정상) 
	    		//select 1st user and change status to Active (정상)
	    		driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();
	    		Thread.sleep(1000);
	    		driver.findElement(By.xpath("//body/div[4]/div[2]/form/div/ul/li[4]")).click();
	    	}
	    	
	    	//try to delete active user
	    	Thread.sleep(2000);
    		driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td/input")).click();		//select 1st user which is active
    		driver.findElement(By.xpath("//body/div[4]/div[2]/form/div/ul/li[3]")).click();				//click delete button
    		
    		if (isAlertPresent()){		//while Alert msg appears
				System_Out = driver.switchTo().alert().getText();
				if (System_Out.equals("서비스 중지 사용자만 삭제할 수 있습니다."))
					System_Out = System_Out + "\r\nCan show warning while trying to delete Active user successfully.";
					driver.switchTo().alert().accept();		//Click OK from popup alert
			}else
				System_Out = "No warning message while deleting active user (FAIL).";
	    	
	    }else
	    	System_Out = "No user found to test.";
		
	    
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Delete User                          *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test C013 - User - Delete User\r\n");
	        builder.append("If user status is Active and you try to delete it, does it shows warning message? \r\n" +
						   "서비스 중지 상태가 아닌 사용자를 삭제 할 때 삭제 할 수 없다는 경고 메시지가 출력되는지 확인\r\n");
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
