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

public class B004 extends BaseTestLogic {
	
	private static String Success = "";
	
	public B004(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                             Tree Menu                         *");
		System.out.println("*****************************************************************");
		System.out.println("Test B004 - Admin Login - Tree Menu");
		System.out.println("Admin menu appears as tree menu?\n" +
							"관리자 메뉴가 트리형태로 나타낸다. \n"+
							"--------------------------------------------------------------------");
		
		if (isElementPresent(By.id("menuTree"))){		//If menuTree present
			Success = "Admin menu appears as tree menu successfully.";
		}else
			Success = "Can't show Admin menu as Tree (FAIL).";
		
		System.out.println(Success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                             Tree Menu                         *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test B004 - Admin Login - Tree Menu\r\n");
	        builder.append("Admin menu appears as tree menu? \r\n" +
						   "관리자 메뉴가 트리형태로 나타낸다.\r\n");
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
