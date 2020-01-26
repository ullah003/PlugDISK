package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.*;


public class E008 extends BaseTestLogic {
	
	private static String System_Out = " ", Success = " ", userName = "";
	
	public E008(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                       Search Share Admin                      *");
		System.out.println("*****************************************************************");
		System.out.println("Test E008 - Share - Search Share Admin");
		System.out.println("Can it search share admin (user) while creating/editing share? \n" +
							"관리자 지정시 정상적으로 검색되어 표기 되는지 확인한다? \n"+
							"--------------------------------------------------------------------");
		
		//get user name to search while creating/editing share
		driver.findElement(By.id("user")).click();
		Thread.sleep(1000);
		if (isElementPresent(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]")))				//if user present
			userName = driver.findElement(By.xpath("//div[@id='userListContent']/table/tbody/tr/td[3]")).getText();
		//System.out.println(userName);
		
		Success = "";
		if (!userName.equals("")){
		    //go to Share menu
	  		driver.findElement(By.id("share")).click();
	  		Thread.sleep(1000);
	  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
	  		Thread.sleep(1000);
			
	  		//if at least 1 share present, try to edit it
//	  		if (isElementPresent(By.xpath("//div[@id='share-list']/table/tbody/tr/td/input"))){
//	  			driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr/td/input")).click();
	  			
	  			driver.findElement(By.xpath("//li[contains(text(), '공유 생성')]")).click();	//click Add share button
				Thread.sleep(1000);
				//switch to user creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				
				driver.findElement(By.id("share_admin")).clear();
				driver.findElement(By.id("share_admin")).sendKeys(userName);
	
				Success = "Can't search share admin (FAIL).";
				WebElement user_search_list = driver.findElement(By.id("search_list"));
				
				List<WebElement> alluser = user_search_list.findElements(By.className("search_list"));	
				for (WebElement user : alluser) {
					System.out.println(user.getText());
					if (user.getText().equals(userName)){
						Success = "Can search share admin successfully.";
						break;
					}
				}
				driver.findElement(By.id("closeButton")).click();
//	  		}
		}else
			Success = "No user present to test.";
  		
  		System.out.println(Success);
			
	

		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                       Search Share Admin                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E008 - Share - Search Share Admin\r\n");
	        builder.append("Can it search share admin (user) while creating/editing share? \r\n" +
						   "관리자 지정시 정상적으로 검색되어 표기 되는지 확인한다?\r\n");
	        builder.append("=================================================================\r\n");
	        
	        builder.append(System_Out + "\r\n" + Success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}