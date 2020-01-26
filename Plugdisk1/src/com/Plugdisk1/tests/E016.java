package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;

public class E016 extends BaseTestLogic {
	
	private static String System_Out = "";
	Boolean sharePresent = false;
	
	public E016(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                       Delete Share Warning                    *");
		System.out.println("*****************************************************************");
		System.out.println("Test E016 - Share - Delete Share Warning");
		System.out.println("Can it show sub-share and data deletion warning message shile deleting a share? \n" +
							"하위에 모든 데이터가 삭제 되는지 경고메시지를 출력하는지 확인한다. \n"+
							"--------------------------------------------------------------------");
		
		//go to Share menu
  		driver.findElement(By.id("share")).click();
  		Thread.sleep(1000);
  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
  		Thread.sleep(1000);
		
  		System_Out = "";
  		
  		//if at least one share present
  		if (isElementPresent(By.xpath("//div[@id='share-list']/table/tbody/tr/td[2]/span"))){
  			driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr/td/input")).click();
  			driver.findElement(By.xpath("//li[contains(text(), '삭제')]")).click();	//click Delete button
			Thread.sleep(1000);
  			
			Thread.sleep(1000);
			//switch to user creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
			if (isElementPresent(By.id("pop-content"))){
				System_Out = driver.findElement(By.id("pop-content")).getText();
				System_Out = System_Out + "\nCan show warning message about deleting sub-department while deleting a department successfully";
				driver.findElement(By.id("closeButton")).click();
			}else
				System_Out = "Can't show warning message about deleting sub-department while deleting a department (FAIL)";
  		}else
  			System_Out = "No share present to delete.";
  		
			System.out.println(System_Out);
			
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                       Delete Share Warning                    *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E016 - Share - Delete Share Warning\r\n");
	        builder.append("Can it show sub-share and data deletion warning message shile deleting a share? \r\n" +
						   "하위에 모든 데이터가 삭제 되는지 경고메시지를 출력하는지 확인한다.\r\n");
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