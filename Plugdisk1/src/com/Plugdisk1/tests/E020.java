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

public class E020 extends BaseTestLogic {
	
	private static String Success = "", Parent_Share = "", Sub_Share = "";
	Boolean parentPresent = false, sub_sharePresent = false;
	
	public E020(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                         Delete Sub-Share                      *");
		System.out.println("*****************************************************************");
		System.out.println("Test E020 - Share - Delete Sub-Share");
		System.out.println("Can it delete sub-share successfully? \n" +
							"서브공유디스크 삭제는 공유 정보만 삭제 되는지 확인. \n"+
							"-----------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=18; i++){
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
  		
		
		if (parentPresent){
			Select viewAll = new Select(driver.findElement(By.id("page_list_size")));
  			viewAll.selectByVisibleText("전체");
			Thread.sleep(2000);
			
			//select share which we want to edit
			WebElement sub_sharelist = driver.findElement(By.id("share-list"));
			
			List<WebElement> all_subshare = sub_sharelist.findElements(By.className("icon_share"));
			int i = 0;
			for (WebElement sub_share : all_subshare) {
				//System.out.println(sub_share.getText());
				i = i + 1;
				if (sub_share.getText().equals(Sub_Share)){
					driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr[" + i + "]/td/input")).click();
					sub_sharePresent = true;
					break;
				}
			}
  			
			//if the sub-share which we want to delete is present
			if (sub_sharePresent){
				driver.findElement(By.xpath("//li[contains(text(), '삭제')]")).click();	//click Delete button
				Thread.sleep(1000);
				//switch to user creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				
				
				if (isElementPresent(By.id("pop-content"))){
					driver.findElement(By.id("delAgree")).click();		//check Agree 
					driver.findElement(By.id("delButton")).click();		//click delete button
					Thread.sleep(2000);
					
					//check if share is deleted successfully
					WebElement list_of_subshare = driver.findElement(By.id("share-list"));
					List<WebElement> all_subshares = list_of_subshare.findElements(By.className("icon_share"));	
					Success = "Can delete sub-share successfully.";
					for (WebElement subshare_element : all_subshares) {
						System.out.println(subshare_element.getText());
						if (subshare_element.getText().equals(Sub_Share)){
							Success = "Can't delete sub-share (FAIL).";
							break;
						}
					}
					driver.switchTo().defaultContent();
				}else
					Success = "Can't shows warning message about deleting sub-department while deleting a department (FAIL)";
			}else
				Success = "The sub-share you want to delete is not available.";
  		}else
  			Success = "Parent share not present to delete sub-share.";
  		
			System.out.println(Success);
			
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                         Delete Sub-Share                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E020 - Share - Delete Sub-Share\r\n");
	        builder.append("Can it delete sub-share successfully? \r\n" +
						   "서브공유디스크 삭제는 공유 정보만 삭제 되는지 확인.\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(Success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}