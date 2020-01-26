package com.Plugdisk.tests;

import static org.junit.Assert.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.*;

public class A003 extends BaseTestLogic {
	
	private static String success = "";
	
	public A003(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException  {
		System.out.println("*****************************************************************");
		System.out.println("*                             Admin Login                       *");
		System.out.println("*****************************************************************");
		System.out.println("Test A003 - Admin Login");
		System.out.println("After initial setting the device is accessible using default password?\n" +
							"초기 설치 후 기본 패스워드로 접근이 되는지 확인 \n"+
							"----------------------------------------------------------");

		if (isElementPresent(By.id("admin"))){		//If Admin menu is present
			success = "Admin login using default password successful.";
		}else
			success = "Admin login using default password FAIL.";
		
		System.out.println(success);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                           Admin Login                         *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test A003 - Admin Login\r\n");
	        builder.append("After initial setting the device is accessible using default password? \r\n" +
						   "초기 설치 후 기본 패스워드로 접근이 되는지 확인\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
