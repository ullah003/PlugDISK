package com.Plugdisk1.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class E006 extends BaseTestLogic {
	
	private static String System_Out = "", Num_of_shares = "", shareName = "", shareDesc = "", 
			shareAdmin = "", shareQuota = "", quotaUnit = "";
	private static String [] share_list;
	
	public E006(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Create Share                         *");
		System.out.println("*****************************************************************");
		System.out.println("Test E006 - Share - Create Share");
		System.out.println("Can it create shares properly? \n" +
							"공유를 제대로 만들 수 있습니까? \n"+
							"--------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=10; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	//System.out.println(line);
	        	if (i > 4){
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("no_of_share")){
		        		Num_of_shares = tokens[1];
		        	}else if (tokens[0].equals("share_name")){
		        		shareName = tokens[1];
		        	}else if (tokens[0].equals("share_desc")){
		        		shareDesc = tokens[1];
		        	}else if (tokens[0].equals("share_admin")){
		        		shareAdmin = tokens[1];
		        	}else if (tokens[0].equals("share_quota")){
		        		shareQuota = tokens[1];
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
	    
	    int Num_of_share1 = Integer.parseInt(Num_of_shares);
	    share_list = new String[Num_of_share1];
		
	    //go to Share menu
  		driver.findElement(By.id("share")).click();
  		Thread.sleep(1000);
  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
  		Thread.sleep(1000);
		
		for (int i=1; i<=Num_of_share1; i++){
			System_Out = shareName + i + "  created successfully.";
			
			driver.findElement(By.xpath("//li[contains(text(), '공유 생성')]")).click();	//click Add user button
			Thread.sleep(1000);
			//switch to user creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
/*
			//check if Max share exceed 
			//if (isElementPresent(By.xpath("//body/div[28]/div[2]/div/div/div/div/div/div[2]"))){
			if (isElementPresent(By.xpath("//div[@class='ext-mb-content']"))){	
				System_Out = driver.findElement(By.xpath("//div[@class='ext-mb-content']")).getText();
				if (System_Out.equals("Can not create User account.\nMaximum number of User accounts has been exceeded.\n(Max number of user accounts: 200)")){
					//share_list[i-1] = user_name + i + ": " + System_Out;
					System.out.println(share_list[i-1] + "\n");
					//System.out.println("Exceed----> " + user_name + i + ": " + System_Out);
					driver.findElement(By.xpath("//button[contains(text(), 'OK')]")).click();
				break;
				}
			}
*/			
			//Enter share informations (Name, desc, share admin, )
			//driver.findElement(By.xpath("//*[@id='userid']")).sendKeys(userID + i);
			driver.findElement(By.id("shareName")).sendKeys(shareName + i + "\t");
			driver.findElement(By.id("desc")).sendKeys(shareDesc + i + "\t");
			
			//share dept  "search_list"
			driver.findElement(By.id("share_admin")).sendKeys(shareAdmin + i + "\t");
			
			Select setQuota = new Select(driver.findElement(By.id("setQuota")));
			setQuota.selectByVisibleText("용량할당");
			Thread.sleep(1000);
			
			driver.findElement(By.id("quota")).sendKeys(shareQuota);			//quota value

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
				break;
			}
			Thread.sleep(1000);
			
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
					System_Out = System_Out + "\nTest FAIL!" ;
				}
			}
			
			share_list[i-1] = System_Out;
			System.out.println(share_list[i-1]);
			
		}	

		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Create Share                         *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E006 - Share - Create Share\r\n");
	        builder.append("Can it create shares properly? \r\n" +
						   "공유를 제대로 만들 수 있습니까?\r\n");
	        builder.append("=================================================================\r\n");
	        for (int i=1; i<=Num_of_share1; i++){
	        	if (share_list[i-1] != null)			//to avoid null print to report file
	        		builder.append(share_list[i-1] + "\r\n");
	        }
	        //builder.append(System_Out + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}