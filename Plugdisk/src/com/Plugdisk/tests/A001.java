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

public class A001 extends BaseTestLogic {
	
	private static String Success = "";
	
	public A001(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                         Introduction Image                    *");
		System.out.println("*****************************************************************");
		System.out.println("Test A001 - Admin Login - Introduction Image");
		System.out.println("If not changed can it show default image?\n" +
							"변경을 하지 않았을 데 기본 이미지로 보이는지 확인 \n"+
							"-----------------------------------------------------------------");
		
		if (isElementPresent(By.xpath("//html/body/div/div"))){		//If logical disk Pie present
			Success = "Can show Introduction Image successfully.";
		}else
			Success = "Can't show Introduction Image (FAIL).";
		
		
		System.out.println(Success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                         Introduction Image                    *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test A001 - Admin Login - Introduction Image\r\n");
	        builder.append("If not changed can it show default image? \r\n" +
						   "변경을 하지 않았을 데 기본 이미지로 보이는지 확인.\r\n");
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
