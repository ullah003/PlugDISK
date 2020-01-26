package com.Plugdisk.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;

public class D004 extends BaseTestLogic {
	
	private static String sub_dept_ID= "", Success = "";
	
	public D004(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Duplicate dept ID                    *");
		System.out.println("*****************************************************************");
		System.out.println("Test D004 - Department - Duplicate dept ID");
		System.out.println("Duplication checking for Dept ID while creating a department.\n" +
							"유니크한 부서 아이디를 유지 하는지 확인(다른 부서명을 입력하고 중복된 ID를 입력 시 예외처리 하는지 확인. \n"+
							"--------------------------------------------------------------------");
		
		//go to department menu
		driver.findElement(By.id("depart")).click();
		Thread.sleep(1000);
		
		if (isElementPresent(By.xpath("//ul[@id='departTree']/li/div/span"))){		//If at least 1 dept present
			if (isElementPresent(By.xpath("//div[@id='depart-list']/table/tbody/tr/td[3]"))){		//get ID if at least 1 sub-dept present
				sub_dept_ID = driver.findElement(By.xpath("//div[@id='depart-list']/table/tbody/tr/td[3]")).getText();
				//System.out.println("--------------------> " + sub_dept_ID);
				driver.findElement(By.xpath("//li[contains(text(), '부서생성')]")).click();		//click Add department button
				Thread.sleep(2000);
				//switch to department creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				
				driver.findElement(By.id("departName")).sendKeys("abc");	//Enter any department name	
				driver.findElement(By.id("departId")).clear();
				driver.findElement(By.id("departId")).sendKeys(sub_dept_ID);		//Enter existing department ID
				//Thread.sleep(3000);
				//click OK
				driver.findElement(By.id("okButton")).click();
				//Thread.sleep(3000);
				if (isAlertPresent()){		//while Alert msg appears
					Success = driver.switchTo().alert().getText();
					if (Success.equals("중복된 아이디 입니다.")){
						Success = Success + "\r\nDuplication checking for Dept ID while creating sub-department successfull.";
					}else{
						Success = Success + "\r\nDuplication checking for Dept ID while creating sub-department FAIL.";
					}
					driver.switchTo().alert().accept();		//Click OK from popup alert
				}else if (isElementVisible(By.id("departId_errMsg"))){		//if duplication error present
					Success = driver.findElement(By.id("departId_errMsg")).getText();
					if (Success.equals("중복된 아이디 입니다.")){
						Success = Success + "\r\nDuplication checking for Dept ID while creating sub-department successfull.";
					}else{
						Success = Success + "\r\nDuplication checking for Dept ID while creating sub-department FAIL.";
					}
				}else{
					Success = "Duplication checking for Dept ID while creating sub-department FAIL.";
				}
				
				//click cancel and go back to default content
				if (isElementPresent(By.id("closeButton")))
					driver.findElement(By.id("closeButton")).click();
				driver.switchTo().defaultContent();
			}else
				Success = "No sub-department present to check ID duplication (FAIL).";
		}else
			Success = "No department present to check ID duplication (FAIL).";
		
		System.out.println(Success);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
			builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Duplicate dept ID                    *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D004 - Department - Duplicate dept ID\r\n");
	        builder.append("Duplication checking for Dept ID while creating a department. \r\n" +
						   "유니크한 부서 아이디를 유지 하는지 확인(다른 부서명을 입력하고 중복된 ID를 입력 시 예외처리 하는지 확인.\r\n");
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