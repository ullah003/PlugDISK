package com.Plugdisk.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public class D002 extends BaseTestLogic {
	
	private static String successMsg = "", user_item = "", user_element = "";
	
	public D002(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                    Sub-department & User list                 *");
		System.out.println("*****************************************************************");
		System.out.println("Test D001 - Department - Tree/subtree & User list");
		System.out.println("Can it show sub-department and user listing of department?\n" +
							"부서에 포함된 하위 부서 및 사용자를 리스팅 하지는 확인.\n"+
							"--------------------------------------------------------------------");
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
			builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                    Sub-department & User list                 *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D002 - Department - Tree/subtree & User list\r\n");
	        builder.append("Can it show sub-department and user listing of department? \r\n" +
						   "부서에 포함된 하위 부서 및 사용자를 리스팅 하지는 확인.\r\n");
	        builder.append("=================================================================\r\n");
	        
		//go to department menu
		driver.findElement(By.id("depart")).click();
		Thread.sleep(1000);
		
		//if department root present		
		if (isElementPresent(By.id("departTree"))){
			//System.out.println("11111111111111111111111");
			//department list
			WebElement list = driver.findElement(By.id("departTree"));
			
			//get all Departments
			List<WebElement> allitems = list.findElements(By.className("open"));
			for (WebElement division : allitems) {
				
				System.out.println("\n부서: " + division.getText());
				builder.append("\r\n부서: " + division.getText());
				System.out.println("-------------------------------------");
				builder.append("\r\n-------------------------------------");
				System.out.println("Name\tID\tPosition\tStatus");
				System.out.println("----\t--\t--------\t------");
				builder.append("\r\nName\tID\tPosition\tStatus");
				builder.append("\r\n----\t--\t--------\t------\r\n");
				
				if (isElementPresent(By.id("depart-list"))){
					WebElement userlist = driver.findElement(By.id("depart-list"));
					
						List<WebElement> alluser = userlist.findElements(By.className("wordBreak"));			
						for (WebElement user : alluser) {
							//System.out.println(user.getText());
							//builder.append(user.getText());	
							//builder.append("\r\n");
							
							//new line wise tokenization 
							StringTokenizer str = new StringTokenizer(user.getText(),"\n");
							while(str.hasMoreTokens()){
								user_item = str.nextToken();
								//Space wise tokenization
								StringTokenizer str1 = new StringTokenizer(user_item," ");
								while(str1.hasMoreTokens()){
									user_element = str1.nextToken();
									System.out.print(user_element + "\t");
									builder.append(user_element + "\t");
								}
								System.out.println();
								builder.append("\r\n");
							}
						}
				}else
					successMsg = "No user list found for this department.";
					
				if (isElementPresent(By.id("1st_root"))){
					//sub-department list
					WebElement sub_list = driver.findElement(By.id("1st_root"));	
					//get all Sub-Departments
					List<WebElement> sub_dept = sub_list.findElements(By.className("icon_division"));
					
					for (WebElement sub_division : sub_dept) {
						
						System.out.println("\n부서: " + sub_division.getText());
						builder.append("\r\n부서: " + sub_division.getText());
						System.out.println("-------------------------------------");
						builder.append("\r\n-------------------------------------");
						System.out.println("Name\tID\tPosition\tStatus");
						System.out.println("----\t--\t--------\t------");
						builder.append("\r\nName\tID\tPosition\tStatus");
						builder.append("\r\n----\t--\t--------\t------\r\n");
						sub_division.click();
						
						if (isElementPresent(By.id("depart-list"))){
							WebElement userlist1 = driver.findElement(By.id("depart-list"));
							
								List<WebElement> alluser1 = userlist1.findElements(By.className("wordBreak"));			
								for (WebElement user1 : alluser1) {
									//System.out.println(user1.getText());
									
									//new line wise tokenization 
									StringTokenizer str = new StringTokenizer(user1.getText(),"\n");
									while(str.hasMoreTokens()){
										user_item = str.nextToken();
										//Space wise tokenization
										StringTokenizer str1 = new StringTokenizer(user_item," ");
										while(str1.hasMoreTokens()){
											user_element = str1.nextToken();
											System.out.print(user_element + "\t");
											builder.append(user_element + "\t");
										}
										System.out.println();
										builder.append("\r\n");
									}
								}
						}else
							successMsg = "No user list found for this department.";
					}
				}else
					successMsg = "Sub-Department list doesn't exists!";
			}
		}else
			successMsg = "Department list doesn't exists to test!";
		
		
		
//		
//		if (isElementPresent(By.id("departTree"))){		//If menuTree present
//			Success = "It can show department listing as tree view successfully.";
//		}else
//			Success = "Can't show department menu as Tree (FAIL).";
//		
//		System.out.println(Success);
		
		
		
		
	        builder.append(successMsg + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}