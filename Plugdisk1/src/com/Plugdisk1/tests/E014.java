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

public class E014 extends BaseTestLogic {
	
	private static String System_Out = "", Success = "", modify_shareName = "", share_diskName = "", new_shareDesc = "", new_shareAdmin = "",  
			new_shareQuota = "", quotaUnit = "", new_permission = "", IP1 = "", IP2 = "", net_log = "", recycle = "";
	//private static String [] share_list;
	Boolean sharePresent = false;
	
	public E014(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Modify Share                         *");
		System.out.println("*****************************************************************");
		System.out.println("Test E014 - Share - Modify Share");
		System.out.println("Can it modify selected share's information properly? \n" +
							"공유디스크명, 설명, 용량, 공유관리자 설정 정보를 변경한다. \n"+
							"--------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=36; i++){
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
		        	if (tokens[0].equals("modify_share_name")){
		        		modify_shareName = tokens[1];
		        	}else if (tokens[0].equals("share_disk_name")){
		        		share_diskName = tokens[1];
		        	}else if (tokens[0].equals("new_desc")){
		        		new_shareDesc = tokens[1];
		        	}else if (tokens[0].equals("new_admin")){
		        		new_shareAdmin = tokens[1];
		        	}else if (tokens[0].equals("new_quota")){
		        		new_shareQuota = tokens[1];
		        	}else if (tokens[0].equals("quota_unit")){
		        		quotaUnit = tokens[1];
		        	}else if (tokens[0].equals("new_premission")){
		        		new_permission = tokens[1];
		        	}else if (tokens[0].equals("IP1")){
		        		IP1 = tokens[1];
		        	}else if (tokens[0].equals("IP2")){
		        		IP2 = tokens[1];
		        	}else if (tokens[0].equals("network_log")){
		        		net_log = tokens[1];
		        	}else if (tokens[0].equals("recycle")){
		        		recycle = tokens[1];
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
  		Success = "";
  		
  		if (isElementPresent(By.xpath("//div[@id='share-list']/table/tbody/tr/td[2]/span"))){
  			//select All view option from the bottom
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
				if (share.getText().equals(modify_shareName)){
					driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr[" + i + "]/td/input")).click();
					sharePresent = true;
					break;
				}
			}
  			
			//if the share which we want to modify is present
			if (sharePresent){
				driver.findElement(By.xpath("//li[contains(text(), '정보 수정')]")).click();	//click Edit button
				Thread.sleep(1000);
				//switch to user creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
  								
				//Enter share informations (Name, desc, share admin etc )
				driver.findElement(By.id("shareName")).clear();
				driver.findElement(By.id("shareName")).sendKeys(share_diskName + "\t");
				driver.findElement(By.id("desc")).clear();
				driver.findElement(By.id("desc")).sendKeys(new_shareDesc + "\t");
				
				//share dept  "search_list"
				driver.findElement(By.id("share_admin")).clear();
				driver.findElement(By.id("share_admin")).sendKeys(new_shareAdmin + "\t");
				
				Select setQuota = new Select(driver.findElement(By.id("setQuota")));
				setQuota.selectByVisibleText("용량할당");
				Thread.sleep(1000);
				
				driver.findElement(By.id("quota")).clear();
				driver.findElement(By.id("quota")).sendKeys(new_shareQuota);			//quota value

				//quota_unit
				Select quota_unit = new Select(driver.findElement(By.id("quota_unit")));
				//select.deselectAll();
				if (quotaUnit.equals("MB"))
					quota_unit.selectByVisibleText("MB");
				else if (quotaUnit.equals("GB"))
					quota_unit.selectByVisibleText("GB");
				else if (quotaUnit.equals("TB"))
					quota_unit.selectByVisibleText("TB");
				else{
					System_Out = "Wrong entry for Share Quota!"; 
				}
				Thread.sleep(1000);
				
				//select Permission
				Select priority = new Select(driver.findElement(By.id("priority")));
				if (new_permission.equals("R"))
					priority.selectByVisibleText("읽기");
				else if (new_permission.equals("RW"))
					priority.selectByVisibleText("읽기/쓰기");
				else{
					System_Out = "Wrong entry for sub-share permission! Default value selected."; 
				}
				Thread.sleep(1000);
				
				if (net_log.equals("Yes") || net_log.equals("yes")){
					if (!driver.findElement(By.id("shareShareLogUse")).isSelected())
						driver.findElement(By.id("shareShareLogUse")).click();
				}else if (net_log.equals("No") || net_log.equals("no")){
					if (driver.findElement(By.id("shareShareLogUse")).isSelected())
						driver.findElement(By.id("shareShareLogUse")).click();
				}else{
					System_Out = System_Out + "\nWrong entry for sub-share network log setting, accepts only (Yes/yes, No/no). Default value selected." ;
				}
				
				if (recycle.equals("Yes") || recycle.equals("yes")){
					if (!driver.findElement(By.id("shareRecyleUse")).isSelected())
						driver.findElement(By.id("shareRecyleUse")).click();
				}else if (recycle.equals("No") || recycle.equals("no")){
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
							System_Out = modify_shareName + ": " + System_Out;
						}else if (isElementVisible(By.id("share_admin_errMsg"))){
							System_Out = driver.findElement(By.id("share_admin_errMsg")).getText();
							System_Out = modify_shareName + ": " + System_Out;
						}else if (isElementVisible(By.id("quota_errMsg"))){
							System_Out = driver.findElement(By.id("quota_errMsg")).getText();
							System_Out = modify_shareName + ": " + System_Out;
						}else if (isElementVisible(By.id("ip_errMsg"))){
							System_Out = driver.findElement(By.id("ip_errMsg")).getText();
							System_Out = modify_shareName + ": " + System_Out;
						}
						//click cancel button
						driver.findElement(By.id("closeButton")).click();
						System_Out = System_Out + "\nTest FAIL!" ;
					}else{
						//check if sub share created successfully
//						Select page = new Select(driver.findElement(By.id("page_list_size")));
//						page.selectByVisibleText("전체");
//						Thread.sleep(2000);

						//Search Parent share ---------------------------------
				  		WebElement share_list = driver.findElement(By.id("share-list"));
						
						List<WebElement> all_share = share_list.findElements(By.className("u_view"));	
						Success = "Can't edit share (FAIL).";
						for (WebElement shares : all_share) {
							//System.out.println(shares.getText());
							if (shares.getText().equals(share_diskName)){
								//System.out.println("Share edited ----------------------");
								Success = "Can edit share successfully.";
								break;
							}
						}
						
					}
				}
				
			}else
				Success = "The share you want to edit is not present.";
  		}else
  			Success = "No share present to edit.";
  		
			System.out.println(System_Out + "\n" + Success);
			
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Modify Share                         *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E014 - Share - Modify Share\r\n");
	        builder.append("Can it modify selected share's information properly? \r\n" +
						   "공유디스크명, 설명, 용량, 공유관리자 설정 정보를 변경한다.\r\n");
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