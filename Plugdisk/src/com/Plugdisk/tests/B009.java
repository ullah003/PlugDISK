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

public class B009 extends BaseTestLogic {
	
	private static String[][] Users = new String[5][2];
	
	public B009(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                         Users & Visitors                      *");
		System.out.println("*****************************************************************");
		System.out.println("Test B009 - Admin Login - Users & Visitors");
		System.out.println("Can it show User and Visitor status? \n" +
							"사용자 현황 및 접속자 현황을 보여 준다. 단, 현재 접속자는 전용접속기 클라이언트 접속자수만 보이도록 한다. \n"+
							"--------------------------------------------------------------------------------------------------------------------");
		
		
		for (int i =1; i<=5; i++){
			if (!isElementPresent(By.xpath("//body/div[4]/div[2]/div[3]/table"))){
				String tmp = "FAIL";
				break;
			}else{
				//get Users information
				Users [i-1] [0] = driver.findElement(By.xpath("//body/div[4]/div[2]/div[3]/table/tbody/tr[" + i + "]/td")).getText();
				Users [i-1] [1] = driver.findElement(By.xpath("//body/div[4]/div[2]/div[3]/table/tbody/tr[" + i + "]/td[2]")).getText();
				System.out.println(Users [i-1] [0] + "\t\t\t" + Users [i-1] [1]);
			}
		}
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                         Users & Visitors                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test B009 - Admin Login - Users & Visitors\r\n");
	        builder.append("Can it show User and Visitor status? \r\n" +
						   "사용자 현황 및 접속자 현황을 보여 준다. 단, 현재 접속자는 전용접속기 클라이언트 접속자수만 보이도록 한다.\r\n");
	        builder.append("===================================================================================\r\n");
	        for (int i =1; i<=5; i++){
	        	builder.append(Users [i-1] [0] + "\r\t\r\t\r\t" + Users [i-1] [1] +  "\r\n");
	        }
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
