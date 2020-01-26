package com.Plugdisk.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;

public class D001 extends BaseTestLogic {
	
	private static String Success = "";
	
	public D001(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Department Tree                      *");
		System.out.println("*****************************************************************");
		System.out.println("Test D001 - Department - Tree Tree view");
		System.out.println("Can it show department listing as tree view?\n" +
							"트리구조의 부서를 표현할 수 있는지 확인 \n"+
							"--------------------------------------------------------------------");
		
		//go to department menu
		driver.findElement(By.id("depart")).click();
		Thread.sleep(1000);
		
		if (isElementPresent(By.id("departTree"))){		//If menuTree present
			Success = "It can show department listing as tree view successfully.";
		}else
			Success = "Can't show department menu as Tree (FAIL).";
		
		System.out.println(Success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
			builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Department Tree                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D001 - Department - Tree view\r\n");
	        builder.append("Can it show department listing as tree view? \r\n" +
						   "트리구조의 부서를 표현할 수 있는지 확인.\r\n");
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