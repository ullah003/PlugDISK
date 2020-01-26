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


public class D008 extends BaseTestLogic {
	
	private static String System_Out = "", modified_dept_name = "";
	
	public D008(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                        Modify Department                      *");
		System.out.println("*****************************************************************");
		System.out.println("Test D008 - Department - Modify Department");
		System.out.println("Department modificaiton popup appears when you click 'Modify user' button and can it modify department info? \n" +
							"우측 리스트에서 선택하여 '정보수정' 버튼으로 정보 수정 창이 출력 되는지 확인 \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=39; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 38){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("dept_newname")){
		        		modified_dept_name = tokens[1];
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
	    	//select first item check box to modify
	    	driver.findElement(By.xpath("//div[@id='depart-list']/table/tbody/tr/td/input")).click();
	    	
	    	driver.findElement(By.xpath("//li[contains(text(), '정보 수정')]")).click();	//click edit button
	    	Thread.sleep(2000);
			//switch to department creation frame
	    	driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
	    	
	    	driver.findElement(By.id("newdepartName")).clear();
			driver.findElement(By.id("newdepartName")).sendKeys(modified_dept_name + "\t");		//insert new dept name
			Thread.sleep(3000);
			if (isElementVisible(By.id("departName_errMsg"))){		//if  error present
				//System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				System_Out = driver.findElement(By.id("departName_errMsg")).getText() + "\r\nTest FAIL";
				driver.findElement(By.id("closeButton")).click();
				driver.switchTo().defaultContent();
			}else{
				driver.findElement(By.id("modifyButton")).click();			//click OK button
				driver.switchTo().defaultContent();
				
				if (isElementPresent(By.id("boxIframe"))){
					//System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzzz");
					System_Out = "Something went wrong!\r\nTest FAIL";
					driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
					driver.findElement(By.id("closeButton")).click();
					driver.switchTo().defaultContent();
				}else{
					//System.out.println("1111111111111111111111111111111111");
					System_Out = "It can't modify department name (FAIL).";
					//driver.switchTo().defaultContent();
			    	WebElement deptlist = driver.findElement(By.id("depart-list"));
					
					List<WebElement> alldept = deptlist.findElements(By.className("icon_division"));			
					for (WebElement dept : alldept) {
						//System.out.println(dept.getText());
						if (dept.getText().equals(modified_dept_name)){
							System_Out = "It can modify department name successfully";
							break;
						}
					}
				}
			}
		}else
			System_Out = "No department present to modify.";
		
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        								
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                        Modify Department                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D008 - Department - Modify Department\r\n");
	        builder.append("Department modificaiton popup appears when you click 'Modify user' button and can it modify department info? \r\n" +
						   "우측 리스트에서 선택하여 '정보수정' 버튼으로 정보 수정 창이 출력 되는지 확인 \r\n");
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
