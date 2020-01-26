package com.Plugdisk.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;

public class D005_1 extends BaseTestLogic {
	
	private static String System_Out = "", Num_of_dept = "", deptName = "", deptID = ""; 
	private static String [] dept_list;
	
	public D005_1(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                       Create Department                       *");
		System.out.println("*****************************************************************");
		System.out.println("Test D005_1 - Department - Create Department");
		System.out.println("Can it create Department properly? \n" +
							"부서를 제대로 만들 수 있습니까? \n"+
							"--------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=35; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 32){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("no_of_department")){
		        		Num_of_dept = tokens[1];
		        	}else if (tokens[0].equals("department_name")){
		        		deptName = tokens[1];
		        	}else if (tokens[0].equals("department_id")){
		        		deptID = tokens[1];
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
	    
	    int Num_of_dept1 = Integer.parseInt(Num_of_dept);
		dept_list = new String[Num_of_dept1];
		
		driver.findElement(By.id("depart")).click();	//go to user menu
		Thread.sleep(1000);
		
		for (int i=1; i<=Num_of_dept1; i++){
			System_Out = deptName + i + "  created successfully.";
			
			driver.findElement(By.xpath("//li[contains(text(), '부서생성')]")).click();	//click Add department button
			Thread.sleep(2000);
			//switch to user creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
			//Enter Dept informations (Name, ID)
			driver.findElement(By.id("departName")).clear();
			driver.findElement(By.id("departName")).sendKeys(deptName + i);
			driver.findElement(By.id("departId")).clear();
			driver.findElement(By.id("departId")).sendKeys(deptID + i);
			
			//Click OK--> 
			driver.findElement(By.id("okButton")).click();
			Thread.sleep(1000);
			
			if (isAlertPresent()){		//while Alert msg appears
				System_Out = driver.switchTo().alert().getText();
				System_Out = "(" + deptName + i + ") " + System_Out;
				driver.switchTo().alert().accept();		//Click OK from popup alert
			}else{
				
				driver.switchTo().defaultContent();
				
				if (isElementPresent(By.id("boxIframe"))){
					driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
					
					if (isElementVisible(By.id("departName_errMsg"))){
						System_Out = driver.findElement(By.id("departName_errMsg")).getText();
						System_Out = "(" + deptName + i + ") " + System_Out;
					}else if (isElementVisible(By.id("departId_errMsg"))){
						System_Out = driver.findElement(By.id("departId_errMsg")).getText();
						System_Out = "(" + deptName + i + ") " + System_Out;
					}
					//click close button
					driver.findElement(By.id("closeButton")).click();
					System_Out = System_Out + "\r\nTest FAIL!" ;
				}
			}
			
			driver.switchTo().defaultContent();
			
			dept_list[i-1] = System_Out;
			System.out.println(dept_list[i-1]);
			Thread.sleep(2000);
		}

		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                       Create Department                       *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D005_1 - Department - Create Department\r\n");
	        builder.append("Can it create Department properly? \r\n" +
						   "부서를 제대로 만들 수 있습니까?\r\n");
	        builder.append("=================================================================\r\n");
	        for (int i=1; i<=Num_of_dept1; i++){
	        	if (dept_list[i-1] != null)			//to avoid null print to report file
	        		builder.append(dept_list[i-1] + "\r\n");
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
