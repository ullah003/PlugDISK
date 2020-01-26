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

public class B003 extends BaseTestLogic {
	
	private static String Server = "", Version = "";
	
	public B003(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Server & Version                     *");
		System.out.println("*****************************************************************");
		System.out.println("Test B003 - Admin Login - Server & Version");
		System.out.println("It shows server and  version properly?\n" +
							"서버, 버전, 서버상태를 나타낸다. \n"+
							"--------------------------------------------------------------------");
		
		
	    //html/body/div[3]/div[2]/div
		if (isElementPresent(By.xpath("//body/div[3]/div[2]/div"))){		//If Server information present
			Server = driver.findElement(By.xpath("//body/div[3]/div[2]/div")).getText();
		}else
			Server = "Server informaiton not found!";
		
		if (isElementPresent(By.xpath("//body/div[3]/div[2]/div[2]"))){		//If Version information present
			Version = driver.findElement(By.xpath("//body/div[3]/div[2]/div[2]")).getText();
		}else
			Version = "Version informaiton not found!";
		
		System.out.println(Server + "\n" + Version);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Server & Version                     *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test B003 - Admin Login - Server & Version\r\n");
	        builder.append("It shows server and  version properly? \r\n" +
						   "서버, 버전, 서버상태를 나타낸다.\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(Server + "\r\n" + Version + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
