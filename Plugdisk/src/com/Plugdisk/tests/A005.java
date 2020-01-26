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

public class A005 extends BaseTestLogic {
	
	private static String success = "", PlugdiskURL = "", sysAdminURL = "";
	
	public A005(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException  {
		System.out.println("*****************************************************************");
		System.out.println("*                    Go to System Admin (AnyStore)              *");
		System.out.println("*****************************************************************");
		System.out.println("Test A005 - Admin Login-Changed Password");
		System.out.println("Can it redirect to Anystor admin login page when you click 시스템 관리자 바로가기\n" +
							"Anystor 관리자 로그인 화면으로 이동 (will redirect to 'IP/admin.html') \n"+
							"-------------------------------------------------------------------------------------");
		
	    
		if (isElementPresent(By.xpath("//li[contains(text(), '로그아웃')]"))){		//If Admin menu is present
			driver.findElement(By.xpath("//li[contains(text(), '로그아웃')]")).click();
			Thread.sleep(2000);
			PlugdiskURL = driver.getCurrentUrl();
			
			String[] tokens = PlugdiskURL.split("/");
			
//			System.out.println("token0: " + tokens[0] + "\n" + "token1: " + tokens[1] + "\n" + "token2: " + tokens[2] + 
//					"\n" + "token3: " + tokens[3] + "\n" + "token4: " + tokens[4]);
			
			String tmp = tokens[0] + "//" + tokens[2] + "/" + "admin.html" ;
			System.out.println(tmp);
			driver.findElement(By.id("btn_sysadmin")).click();
			Thread.sleep(2000);
			sysAdminURL = driver.getCurrentUrl();
			System.out.println(sysAdminURL);
			
			if (sysAdminURL.equals(tmp))
				success = "Can redirect to System Admin (AnyStore) page successfully.";
			else
				success = "Can't redirect to System Admin (AnyStore) page (FAIL).";
		}else
			success = "Logout option is not present.";
		
		System.out.println(success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                    Go to System Admin (AnyStore)              *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test A005 - Admin Login-Changed Password\r\n");
	        builder.append("Can it redirect to Anystor admin login page when you click 시스템 관리자 바로가기 \r\n" +
						   "Anystor 관리자 로그인 화면으로 이동 (will redirect to 'IP/admin.html')\r\n");
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
