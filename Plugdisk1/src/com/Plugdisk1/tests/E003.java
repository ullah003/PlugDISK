package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;

public class E003 extends BaseTestLogic {
	
	private static String success_topShare = "", success_subShare = ""; 
	//private static String [] share_list;
	
	public E003(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                     Quota option checking                     *");
		System.out.println("*****************************************************************");
		System.out.println("Test E003 - Share - Quota option checking");
		System.out.println("It shows quota and share admin setting option only while creating top share?\n" +
							"최상위 공유디스크 생성시에만 쿼터 설정, 공유디스크 관리자 선택 UI가 나타난다. \n" +
							"--------------------------------------------------------------------");
		
	    
	    
		success_topShare = ""; success_subShare = "";
		//go to Share menu
  		driver.findElement(By.id("share")).click();
  		Thread.sleep(1000);
  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
  		Thread.sleep(1000);
	    	
		driver.findElement(By.xpath("//li[contains(text(), '공유 생성')]")).click();	//click Add user button
		Thread.sleep(1000);
		//switch to user creation frame
		driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
		
		//check if quota and share admin option available
		if (isElementPresent(By.id("setQuota")) && isElementPresent(By.id("share_admin")))
			success_topShare = "It shows quota and share admin setting while creating top share(success).";
		else
			success_topShare = "It can't show quota and share admin setting while creating top share(FAIl).";
		
		Thread.sleep(1000);
		driver.findElement(By.id("closeButton")).click();
		driver.switchTo().defaultContent();
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		
		//now check quata and share admin option for sub-share
		if (isElementPresent(By.xpath("//div[@id='dir-tree']/ul/li/ul/li/div/a"))){
			driver.findElement(By.xpath("//div[@id='dir-tree']/ul/li/ul/li/div/a")).click();
			Thread.sleep(1000);
			driver.findElement(By.xpath("//li[contains(text(), '공유 생성')]")).click();	//click Add user button
			Thread.sleep(1000);
			
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
			//check if quota and share admin option available
			if (isElementPresent(By.id("setQuota")) && isElementPresent(By.id("share_admin")))
				success_subShare = "It shows quota and share admin setting while creating sub-share (FAIL).";
			else
				success_subShare = "It can't show quota and share admin setting while creating sub-share(success).";
		}else
			success_subShare = "Top share not found to test sub-share quota and share admin option.";
		
		Thread.sleep(1000);
		driver.findElement(By.id("closeButton")).click();
		
		
		System.out.println(success_topShare + "\n" + success_subShare);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                     Quota option checking                     *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E003 - Share - Quota option checking\r\n");
	        builder.append("It shows quota and share admin setting option only while creating top share? \r\n" +
						   "최상위 공유디스크 생성시에만 쿼터 설정, 공유디스크 관리자 선택 UI가 나타난다.\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(success_topShare + "\r\n" + success_subShare + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}