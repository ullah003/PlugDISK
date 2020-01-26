package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class E005 extends BaseTestLogic {
	
	private static String System_Out = "", success = "", shareName = "", quota = "", usage = ""; 
	private static String [] share_list;
	
	public E005(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                        Share disk usage                       *");
		System.out.println("*****************************************************************");
		System.out.println("Test E005 - Share - Disk usage");
		System.out.println("Can it show disk usage only when quota setting done for share?\n" +
							"쿼터가 설정되어 있지 않은 경우 웹에서는 사용량 체크가 불가능하다. \n" +
							"남은 용량은 확인이 가능하게 할 수 있으나. \n" +
							"--------------------------------------------------------------------");
		
	    
	    share_list = new String[2];
		success = "";
	    //go to Share menu
  		driver.findElement(By.id("share")).click();
  		Thread.sleep(1000);
  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
  		Thread.sleep(1000);
		
  		//create 2 test department
		for (int i=1; i<=2; i++){
			System_Out = shareName + i + "  created successfully.";
			
			driver.findElement(By.xpath("//li[contains(text(), '공유 생성')]")).click();	//click Add user button
			Thread.sleep(1000);
			//switch to user creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
			//Enter share informations (Name, desc, share admin, )
			driver.findElement(By.id("shareName")).sendKeys("AAAAA" + i + "\t");
			driver.findElement(By.id("desc")).sendKeys("AAAAA desc" + i + "\t");
			
			if (i == 2){
				Select setQuota = new Select(driver.findElement(By.id("setQuota")));
				setQuota.selectByVisibleText("용량할당");
				Thread.sleep(1000);
				
				driver.findElement(By.id("quota")).sendKeys("2");			//quota value
	
				//quota_unit
				Select quota_unit = new Select(driver.findElement(By.id("quota_unit")));
				quota_unit.selectByVisibleText("MB");
			}
			Thread.sleep(1000);
			
			//Click OK--> 
			driver.findElement(By.id("okButton")).click();
			Thread.sleep(1000);
			
			if (isAlertPresent()){		//while Alert msg appears
				System_Out = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();		//Click OK from popup alert
				
				driver.findElement(By.id("closeButton")).click();
				//System_Out = System_Out + "\nTest FAIL!" ;
			}else{
				driver.switchTo().defaultContent();
				if (isElementPresent(By.id("boxIframe"))){
					driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
					
					if (isElementVisible(By.id("shareName_errMsg"))){
						System_Out = driver.findElement(By.id("shareName_errMsg")).getText();
						System_Out = "(" + shareName + i + ") " + System_Out;
					}else if (isElementVisible(By.id("share_admin_errMsg"))){
						System_Out = driver.findElement(By.id("share_admin_errMsg")).getText();
						System_Out = "(" + shareName + i + ") " + System_Out;
					}else if (isElementVisible(By.id("quota_errMsg"))){
						System_Out = driver.findElement(By.id("quota_errMsg")).getText();
						System_Out = "(" + shareName + i + ") " + System_Out;
					}
					//click cancel button
					driver.findElement(By.id("closeButton")).click();
					//System_Out = System_Out + "\nTest FAIL!" ;
				}
			}
			
			share_list[i-1] = System_Out;
			System.out.println(share_list[i-1]);
			
		}
		
		//check if disk usage available
		WebElement sharelist = driver.findElement(By.id("share-list"));
		
		List<WebElement> allshare = sharelist.findElements(By.className("icon_share"));	
		int i = 0, j = 0;
		for (WebElement share : allshare) {
			if (j == 2)
				break;
			i = i + 1;
			System.out.println(share.getText());
			if (share.getText().equals("AAAAA1") || share.getText().equals("AAAAA2")){
				j = j+1;
				quota = driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr[" + i +"]/td[4]")).getText();
				usage = driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr[" + i +"]/td[5]")).getText();
				System.out.println(share.getText() + "\t" + quota +  "\t" + usage);
				if (quota.equals("할당 없음") && usage.equals("")){
					success = success + "\nWhen quota setting not done, it shows blank usage successfully.";
				}else if (!quota.equals("할당 없음") && !usage.equals("")){
					success = success + "\nWhen quota setting done, it shows usage successfully.";
				}else
					success = success + "\nShow disk usage only when quota setting done for share test FAIL.";
			}
		}
		
		System.out.println(success);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                        Share disk usage                       *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E005 - Share - Disk usage\r\n");
	        builder.append("Can it show disk usage only when quota setting done for share? \r\n" +
						   "쿼터가 설정되어 있지 않은 경우 웹에서는 사용량 체크가 불가능하다. \r\n" +
	        				"남은 용량은 확인이 가능하게 할 수 있으나. \r\n");
	        builder.append("=================================================================\r\n");
	        for (int k=1; k<=2; k++){
	        	if (share_list[k-1] != null)			//to avoid null print to report file
	        		builder.append(share_list[k-1] + "\r\n");
	        }
	        builder.append(success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}