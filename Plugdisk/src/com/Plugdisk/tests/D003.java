package com.Plugdisk.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;

public class D003 extends BaseTestLogic {
	
	private static String sub_dept= "", Success = "";
	
	public D003(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                      Duplicate sub-department                 *");
		System.out.println("*****************************************************************");
		System.out.println("Test D003 - Department - Duplicate sub-department");
		System.out.println("Duplication checking while creating sub-department under same department.\n" +
							"같은 부모 및에는 같은 이름의 부서를 생성 할 수 없음을 확인. \n"+
							"--------------------------------------------------------------------");
		
		//go to department menu
		driver.findElement(By.id("depart")).click();
		Thread.sleep(1000);
		
		if (isElementPresent(By.xpath("//ul[@id='departTree']/li/div/span"))){		//If at least 1 dept present
			if (isElementPresent(By.xpath("//ul[@id='departTree']/li/ul/li/div/a/span"))){		//If at least 1 sub-dept present
				sub_dept = driver.findElement(By.xpath("//ul[@id='departTree']/li/ul/li/div/a/span")).getText();
				//System.out.println("--------------------> " + sub_dept);
				driver.findElement(By.xpath("//li[contains(text(), '부서생성')]")).click();	//click Add department button
				Thread.sleep(2000);
				//switch to department creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				//enter existing dept name
				driver.findElement(By.id("departName")).sendKeys(sub_dept);
				//click OK
				driver.findElement(By.id("okButton")).click();
				
				if (isAlertPresent()){		//while Alert msg appears
					Success = driver.switchTo().alert().getText();
					if (Success.equals("중복된 부서명 입니다.")){
						Success = Success + "\r\nDuplication checking while creating sub-department under same department successfull.";
					}else{
						Success = Success + "\r\nDuplication checking while creating sub-department under same department FAIL.";
					}
					driver.switchTo().alert().accept();		//Click OK from popup alert
				}else if (isElementVisible(By.id("departName_errMsg"))){		//if duplication error present
					Success = driver.findElement(By.id("departName_errMsg")).getText();
					if (Success.equals("중복된 부서명 입니다.")){
						Success = Success + "\r\nDuplication checking while creating sub-department under same department successfull.";
					}else{
						Success = Success + "\r\nDuplication checking while creating sub-department under same department FAIL.";
					}
				}else{
					Success = "Duplication checking while creating sub-department under same department FAIL.";
				}
				
				//click cancel and go back to default content
				if (isElementPresent(By.id("closeButton")))
					driver.findElement(By.id("closeButton")).click();
				driver.switchTo().defaultContent();
				
			}else{
				//no sub-dept, create both
				driver.findElement(By.xpath("//li[contains(text(), '부서생성')]")).click();	//click Add department button
				Thread.sleep(2000);
				//switch to department creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				//enter existing dept name
				driver.findElement(By.id("departName")).sendKeys("ETC");
				//click OK
				driver.findElement(By.id("okButton")).click();
				driver.switchTo().defaultContent();				//switch back to default content
				
				//try to create same sub-dept again
				driver.findElement(By.xpath("//li[contains(text(), '부서생성')]")).click();	//click Add department button
				Thread.sleep(2000);
				//switch to department creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				//enter existing dept name
				driver.findElement(By.id("departName")).sendKeys("ETC");
				//click OK
				driver.findElement(By.id("okButton")).click();
				
				if (isAlertPresent()){		//while Alert msg appears
					Success = driver.switchTo().alert().getText();
					if (Success.equals("중복된 부서명 입니다.")){
						Success = Success + "\r\nDuplication checking while creating sub-department under same department successfull.";
					}else{
						Success = Success + "\r\nDuplication checking while creating sub-department under same department FAIL.";
					}
					driver.switchTo().alert().accept();		//Click OK from popup alert
				}else if (isElementVisible(By.id("departName_errMsg"))){		//if duplication error present
					Success = driver.findElement(By.id("departName_errMsg")).getText();
					if (Success.equals("중복된 부서명 입니다.")){
						Success = Success + "\r\nDuplication checking while creating sub-department under same department successfull.";
					}else{
						Success = Success + "\r\nDuplication checking while creating sub-department under same department FAIL.";
					}
				}else{
					Success = "Duplication checking while creating sub-department under same department FAIL.";
				}
				
				//click cancel and go back to default content
				if (isElementPresent(By.id("closeButton")))
					driver.findElement(By.id("closeButton")).click();
				driver.switchTo().defaultContent();
			}	
		}else
			Success = "No department present to test (FAIL).";
		
		System.out.println(Success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
			builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                      Duplicate sub-department                 *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D003 - Department - Duplicate sub-department\r\n");
	        builder.append("Duplication checking while creating sub-department under same department. \r\n" +
						   "같은 부모 및에는 같은 이름의 부서를 생성 할 수 없음을 확인.\r\n");
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