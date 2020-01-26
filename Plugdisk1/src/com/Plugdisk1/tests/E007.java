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

public class E007 extends BaseTestLogic {
	
	private static String System_Out = " ", Success = " ", Parent_Share = "", Sub_Share = "", Sub_Desc = "", 
			Sub_Permission = "", Sub_IP1 = "", Sub_IP2 = "", Sub_Net_Log = "", Sub_Recycle = "";
	//private static String [] share_list;
	Boolean parentPresent = false;
	
	public E007(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Create Sub-Share                          *");
		System.out.println("*****************************************************************");
		System.out.println("Test E007 - Share - Create Sub-Share");
		System.out.println("Can it create sub-share properly? \n" +
							"서브공유가 정상적으로 생성되는지 확인한다? \n"+
							"--------------------------------------------------------------------");
		
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
	        	//System.out.println(line);
	        	if (i > 16){
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("parent_share")){
		        		Parent_Share = tokens[1];
		        	}else if (tokens[0].equals("sub_share")){
		        		Sub_Share = tokens[1];
		        	}else if (tokens[0].equals("sub_desc")){
		        		Sub_Desc = tokens[1];
		        	}else if (tokens[0].equals("permission")){
		        		Sub_Permission = tokens[1];
		        	}else if (tokens[0].equals("sub_IP1")){
		        		Sub_IP1 = tokens[1];
		        	}else if (tokens[0].equals("sub_IP2")){
		        		Sub_IP2 = tokens[1];
		        	}else if (tokens[0].equals("sub_network_log")){
		        		Sub_Net_Log = tokens[1];
		        	}else if (tokens[0].equals("sub_recycle")){
		        		Sub_Recycle = tokens[1];
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
		
  		//Search Parent share ---------------------------------
  		WebElement sharelist = driver.findElement(By.id("dir-tree"));
		
		List<WebElement> allshare = sharelist.findElements(By.className("share"));	
		for (WebElement share : allshare) {
			//System.out.println(share.getText());
			if (share.getText().equals(Parent_Share)){
				parentPresent = true;
				share.click();
				break;
			}
		}
		Success = " ";
		System_Out = " ";
  		if (parentPresent){
			driver.findElement(By.xpath("//li[contains(text(), '공유 생성')]")).click();	//click Add share button
			Thread.sleep(1000);
			//switch to user creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
			//Enter share informations (Name, desc, share admin, )
			driver.findElement(By.id("shareName")).sendKeys(Sub_Share + "\t");
			driver.findElement(By.id("desc")).sendKeys(Sub_Desc + "\t");
			
			//select Priority
			Select priority = new Select(driver.findElement(By.id("priority")));
			if (Sub_Permission.equals("R"))
				priority.selectByVisibleText("읽기");
			else if (Sub_Permission.equals("RW"))
				priority.selectByVisibleText("읽기/쓰기");
			else{
				System_Out = "Wrong entry for sub-share permission! Default value selected."; 
			}
			Thread.sleep(1000);
			
			if (Sub_Net_Log.equals("Yes") || Sub_Net_Log.equals("yes")){
				if (!driver.findElement(By.id("shareShareLogUse")).isSelected())
					driver.findElement(By.id("shareShareLogUse")).click();
			}else if (Sub_Net_Log.equals("No") || Sub_Net_Log.equals("no")){
				if (driver.findElement(By.id("shareShareLogUse")).isSelected())
					driver.findElement(By.id("shareShareLogUse")).click();
			}else{
				System_Out = System_Out + "\nWrong entry for sub-share network log setting, accepts only (Yes/yes, No/no). Default value selected." ;
			}
			
			if (Sub_Recycle.equals("Yes") || Sub_Recycle.equals("yes")){
				if (!driver.findElement(By.id("shareRecyleUse")).isSelected())
					driver.findElement(By.id("shareRecyleUse")).click();
			}else if (Sub_Recycle.equals("No") || Sub_Recycle.equals("no")){
				if (driver.findElement(By.id("shareRecyleUse")).isSelected())
					driver.findElement(By.id("shareRecyleUse")).click();
			}else{
				System_Out = System_Out + "\nWrong entry for sub-share Recycle Bin setting, accepts only (Yes/yes, No/no). Default value selected." ;
			}
			
			
			//Click OK--> 
			driver.findElement(By.id("okButton")).click();
			Thread.sleep(1000);
			
			if (isAlertPresent()){		//while Alert msg appears
				System_Out = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();		//Click OK from popup alert
				
				driver.findElement(By.id("closeButton")).click();
				System_Out = System_Out + "\nTest FAIL!" ;
			}else{
			
				driver.switchTo().defaultContent();
				
				if (isElementPresent(By.id("boxIframe"))){
					driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
					
					if (isElementVisible(By.id("shareName_errMsg"))){
						System_Out = driver.findElement(By.id("shareName_errMsg")).getText();
						System_Out = "(" + Sub_Share + ") " + System_Out;
					}else if (isElementVisible(By.id("ip_errMsg"))){
						System_Out = driver.findElement(By.id("ip_errMsg")).getText();
						System_Out = "(" + Sub_Share + ") " + System_Out;
					}
					//click cancel button
					driver.findElement(By.id("closeButton")).click();
					System_Out = System_Out + "\nTest FAIL!" ;
				}else{
					//check if sub share created successfully
					Select page = new Select(driver.findElement(By.id("page_list_size")));
					page.selectByVisibleText("전체");
					
					Thread.sleep(2000);

					//Search Parent share ---------------------------------
			  		WebElement sub_sharelist = driver.findElement(By.id("share-list"));
					
					List<WebElement> allsubshare = sub_sharelist.findElements(By.className("icon_share"));	
					Success = "Can't crate sub share under parent share (FAIL).";
					for (WebElement sub_share : allsubshare) {
						System.out.println(sub_share.getText());
						if (sub_share.getText().equals(Sub_Share)){
							//System.out.println("Sub Share----------------------");
							Success = "Can crate sub share under parent share successfully.";
							break;
						}
					}
					
				}
			}
  		}else
  			System_Out = "Parent share not present to create sub-share!";
  		
  		
			System.out.println(System_Out + "\n" + Success);

		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Create Sub-Share                          *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E007 - Share - Create Sub-Share\r\n");
	        builder.append("Can it create sub-share properly? \r\n" +
						   "서브공유가 정상적으로 생성되는지 확인한다?\r\n");
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