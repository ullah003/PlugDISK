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

public class E018 extends BaseTestLogic {
	
	private static String System_Out = "", Success = "", shareName = "";
	Boolean sharePresent = false;
	
	public E018(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                           Delete Share                        *");
		System.out.println("*****************************************************************");
		System.out.println("Test E018 - Share - Delete Share");
		System.out.println("Can it delete top share successfully? \n" +
							"메인공유를 삭제한다. \n"+
							"-----------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=38; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	//System.out.println(line);
	        	if (i > 25){
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
		
  		Success = "";
  		
  		//if at least one share present
  		if (isElementPresent(By.xpath("//div[@id='share-list']/table/tbody/tr/td[2]/span"))){
  			Select viewAll = new Select(driver.findElement(By.id("page_list_size")));
  			viewAll.selectByVisibleText("전체");
			Thread.sleep(2000);
			
			//select share which we want to edit
			WebElement sharelist = driver.findElement(By.id("share-list"));
			
			List<WebElement> allshare = sharelist.findElements(By.className("icon_share"));
			int i = 0;
			for (WebElement share : allshare) {
				//System.out.println(share.getText());
				i = i + 1;
				if (share.getText().equals(shareName)){
					driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr[" + i + "]/td/input")).click();
					sharePresent = true;
					break;
				}
			}
  			
			//if the share which we want to delete is present
			if (sharePresent){
				driver.findElement(By.xpath("//li[contains(text(), '삭제')]")).click();	//click Delete button
				Thread.sleep(1000);
				//switch to user creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				
				
				if (isElementPresent(By.id("pop-content"))){
					driver.findElement(By.id("delAgree")).click();		//check Agree 
					driver.findElement(By.id("delButton")).click();		//click delete button
					Thread.sleep(2000);
					
					//check if share is deleted successfully
					WebElement list_of_share = driver.findElement(By.id("dirTree"));
					List<WebElement> all_shares = list_of_share.findElements(By.className("share"));	
					Success = "Can delete share successfully.";
					for (WebElement share_element : all_shares) {
						//System.out.println(share_element.getText());
						if (share_element.getText().equals(shareName)){
							Success = "Can't delete share (FAIL).";
							break;
						}
					}
					driver.switchTo().defaultContent();
				}else
					Success = "Can't shows warning message about deleting sub-department while deleting a department (FAIL)";
			}else
				Success = "The share you want to delete is not available.";
  		}else
  			Success = "No share present to delete.";
  		
			System.out.println(Success);
			
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                           Delete Share                        *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E018 - Share - Delete Share\r\n");
	        builder.append("Can it delete top share successfully? \r\n" +
						   "메인공유를 삭제한다.\r\n");
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