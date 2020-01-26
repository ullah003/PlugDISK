package com.Plugdisk.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.*;


public class D013 extends BaseTestLogic {
	
	private static String System_Out = "", source_dept = "", target_dept = "", total_page = "";
	Boolean sourcePresent = false, targetPresent = false;
	
	public D013(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                        Move Department                        *");
		System.out.println("*****************************************************************");
		System.out.println("Test D013 - Department - Add user to Department");
		System.out.println("Can it move selected department to target department? \n" +
							"선택된 부서를 이동 한다. \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=44; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 42){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("source_dept")){
		        		source_dept = tokens[1];
		        	}else if (tokens[0].equals("target_dept")){
		        		target_dept = tokens[1];
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
	    
	  //go to department menu
	    driver.findElement(By.id("depart")).click();
	  	Thread.sleep(1000);
	    
	    
	    //if at least 1 department present
	    if (isElementPresent(By.xpath("//div[@id='depart-list']/table/tbody/tr/td[2]/span"))){
	    	
//	    	WebElement list_of_dept = driver.findElement(By.id("departTree"));
//			List<WebElement> deptments = list_of_dept.findElements(By.className("icon_division"));	
//			for (WebElement deprt : deptments) {
//				//tmp = "Can delete deparment successfully.";
//				System.out.println(deprt.getText());
//				if (deprt.getText().equals(source_dept)){
//					System.out.println("source----------------------");
//					sourcePresent = true;
//				}else if (deprt.getText().equals(target_dept)){
//					System.out.println("target----------------------");
//					targetPresent = true;
//				}
//				if (sourcePresent & targetPresent)
//					break;
//			}
			
//			if (sourcePresent & targetPresent)
				//get total page number
		    	total_page = driver.findElement(By.id("total_pages")).getText();
				int totl_page = Integer.parseInt(total_page);
				//select source department
				for (int j=1; j<=totl_page; j++){
					if (j>1)		//go to next page if exists
						driver.findElement(By.id("IDgoNextPage")).click();
					Thread.sleep(1000);
					
					//System.out.println("Page------------------> :" + j);
					
			    	WebElement deptlist = driver.findElement(By.id("depart-list"));
					
					List<WebElement> alldept = deptlist.findElements(By.className("icon_division"));	
					int i = 0;
					for (WebElement dept : alldept) {
						i = i + 1;
						//System.out.println(dept.getText());
						if (dept.getText().equals(source_dept)){
							//System.out.println("Source----------------------");
							sourcePresent = true;
							driver.findElement(By.xpath("//div[@id='depart-list']/table/tbody/tr[" + i +"]/td/input")).click();
							j = totl_page +1;		//break outer loop 
							break;
						}
					}
				}
				
				if (sourcePresent){
					driver.findElement(By.xpath("//li[contains(text(), '이동')]")).click();		//click move button
					Thread.sleep(2000);
					//switch to department creation frame
			    	driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			    	
			    	//select target department
			    	WebElement list_of_dept = driver.findElement(By.id("departTree"));
					List<WebElement> deptments = list_of_dept.findElements(By.className("icon_division"));	
					int i = 0;
					for (WebElement deprt : deptments) {
						i = i + 1;
						//System.out.println(deprt.getText());
						if (deprt.getText().equals(target_dept)){
							//System.out.println("target----------------------");
							targetPresent = true;
							//driver.findElement(By.xpath("//ul[@id='1st_root']/li[" + i + "]/div/a/span")).click();
							driver.findElement(By.xpath("//ul[@id='1st_root']/li[" + i + "]/a/span")).click();
							Thread.sleep(3000);
							break;
						}
					}
					
					if (targetPresent){
						driver.findElement(By.id("okButton")).click();			//select OK button
						if (isAlertPresent()){		//while Alert msg appears
							System_Out = driver.switchTo().alert().getText();
							System_Out = System_Out + "\r\nTest Fail!";
							driver.switchTo().alert().accept();		//Click OK from popup alert
							
							//click cancel and go back to default content
							if (isElementPresent(By.id("closeButton")))
								driver.findElement(By.id("closeButton")).click();
							driver.switchTo().defaultContent();
							
						}else{
							System_Out = "Moved to department successfully.";
						}
						//==============================================================
						//targetPresent = false;
						sourcePresent = false;
						//check if successfully moved
						WebElement list_of_dept1 = driver.findElement(By.id("departTree"));
						List<WebElement> deptments1 = list_of_dept1.findElements(By.className("icon_division"));	
						for (WebElement deprt1 : deptments1) {
							//System.out.println(deprt1.getText());
							if (deprt1.getText().equals(target_dept)){
								//System.out.println("AAAAA target----------------------");
								targetPresent = true;
								deprt1.click();
								break;
							}
						}
						Thread.sleep(1000);
						if (targetPresent){
							total_page = driver.findElement(By.id("total_pages")).getText();
							totl_page = Integer.parseInt(total_page);
							//check if source department present inside target
							for (int j=1; j<=totl_page; j++){
								if (j>1)		//go to next page if exists
									driver.findElement(By.id("IDgoNextPage")).click();
								Thread.sleep(1000);
								
								//System.out.println("Page------------------> :" + j);
								
						    	WebElement deptlist = driver.findElement(By.id("depart-list"));
								
								List<WebElement> alldept = deptlist.findElements(By.className("icon_division"));	
								//int i = 0;
								for (WebElement dept : alldept) {
									//i = i + 1;
									//System.out.println(dept.getText());
									if (dept.getText().equals(source_dept)){
										//System.out.println("Source----------------------");
										sourcePresent = true;
										//driver.findElement(By.xpath("//div[@id='depart-list']/table/tbody/tr[" + i +"]/td/input")).click();
										j = totl_page +1;		//break outer loop 
										break;
									}
								}
							}
							if (sourcePresent)
								System_Out = System_Out + "\r\nCan move source department to target department successfully.";
							else
								System_Out = System_Out + "\r\nCan't move department (FAIL).";
							
						}
						
						
						
						//==============================================================
						
					}else{
						System_Out = "Target department not present. Test Fail!";
						//click cancel and go back to default content
						if (isElementPresent(By.id("closeButton")))
							driver.findElement(By.id("closeButton")).click();
						driver.switchTo().defaultContent();
					}
				}else
					System_Out = "Source department not present. Test Fail!";
		}else
			System_Out = "No department present to delete.";
		
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
			
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                        Move Department                        *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D013 - Department - Add user to Department\r\n");
	        builder.append("Can it move selected department to target department? \r\n" +
						   "선택된 부서를 이동 한다. \r\n");
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
