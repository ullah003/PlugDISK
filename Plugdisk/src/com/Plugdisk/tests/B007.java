package com.Plugdisk.tests;

import static org.junit.Assert.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.*;

public class B007 extends BaseTestLogic {
	
	private static String Logical_Vol = "", Physical_Vol = "";
	
	public B007(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                            Disk Status                        *");
		System.out.println("*****************************************************************");
		System.out.println("Test B007 & B008 - Admin Login - Disk Pie Graph");
		System.out.println("Logical and Physical disk status appears as Pie Graph?\n" +
							"디스크의 논리적, 물리적 할당현황을 원형 그래프로 표시한다. \n"+
							"--------------------------------------------------------------------");
		
		if (isElementPresent(By.xpath("//body/div[4]/div[2]/div/div[2]/img"))){		//If logical disk Pie present
			Logical_Vol = "Logical disk status appears as Pie Graph successfully.";
		}else
			Logical_Vol = "Logical disk status doesn't appears as Pie Graph (FAIL).";
		
		if (isElementPresent(By.xpath("//body/div[4]/div[2]/div[2]/div[2]/img"))){		//If physical disk Pie present
			Physical_Vol = "Physical disk status appears as Pie Graph successfully.";
		}else
			Physical_Vol = "Physical disk status doesn't appears as Pie Graph (FAIL).";
		
		System.out.println(Logical_Vol + "\n" + Physical_Vol);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                            Disk Status                        *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test B007 & B008 - Admin Login - Disk Pie Graph\r\n");
	        builder.append("Logical and Physical disk status appears as Pie Graph? \r\n" +
						   "디스크의 논리적, 물리적 할당현황을 원형 그래프로 표시한다.\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(Logical_Vol + "\r\n" +  Physical_Vol +  "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
