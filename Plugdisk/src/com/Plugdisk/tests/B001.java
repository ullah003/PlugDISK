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

public class B001 extends BaseTestLogic {
	
	private static String Success = "";
	
	public B001(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Default Logo                     *");
		System.out.println("*****************************************************************");
		System.out.println("Test B001 - Admin Login - Default Logo");
		System.out.println("If not changed can it show default logo?\n" +
							"변경을 하지 않았을 데 기본 이미지로 보이는지 확인 \n"+
							"--------------------------------------------------------------------");
		
		if (isElementPresent(By.id("logoImg"))){		//If Logo present
			Success = "Can show default logo successfully.";
		}else
			Success = "Can't show default logo (FAIL).";
		
		System.out.println(Success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                          Default Logo                    *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test B001 - Admin Login - Default Logo\r\n");
	        builder.append("If not changed can it show default logo? \r\n" +
						   "변경을 하지 않았을 데 기본 이미지로 보이는지 확인\r\n");
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
