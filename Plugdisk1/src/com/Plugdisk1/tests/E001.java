package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.openqa.selenium.*;

public class E001 extends BaseTestLogic {
	
	private static String Success = "", Menus = "";
	
	public E001(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                             Share Tree                        *");
		System.out.println("*****************************************************************");
		System.out.println("Test E001 - Share - Tree view");
		System.out.println("Can it show Share listing as tree view?\n" +
							"탐색기 형태로 공유디스크와 하위공유 디스크를 생성 할 수 있는 인터페이스를 제공하는지 확인? \n"+
							"----------------------------------------------------------------------------------------------------");
		
		//go to Share menu
		driver.findElement(By.id("share")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
		Thread.sleep(1000);
		
		
		if (isElementPresent(By.xpath("//li[contains(text(), '공유 생성')]"))){		//If create share option available
			Menus = "Create Share (공유 생성) option present.";
		}else
			Menus = "Create Share (공유 생성) option not found.";
		
		if (isElementPresent(By.xpath("//li[contains(text(), '공유 설정')]"))){		//If share setting option available
			Menus = Menus + "\nShare setting (공유 설정) option present.";
		}else
			Menus = Menus + "\nShare setting (공유 설정) option not found.";
		
		if (isElementPresent(By.xpath("//li[contains(text(), '정보 수정')]"))){		//If share Edit option available
			Menus = Menus + "\nShare Edit (정보 수정) option present.";
		}else
			Menus = Menus + "\nShare Edit (정보 수정) option not found.";
		
		if (isElementPresent(By.xpath("//li[contains(text(), '권한 설정')]"))){		//If share Permission option available
			Menus = Menus + "\nShare Permission (권한 설정) option present.";
		}else
			Menus = Menus + "\nShare Permission (권한 설정) option not found.";
		
		if (isElementPresent(By.xpath("//li[contains(text(), '삭제')]"))){		//If share delete option available
			Menus = Menus + "\nShare delete (삭제) option present.";
		}else
			Menus = Menus + "\nShare delete (삭제) option not found.";
		
		
		if (isElementPresent(By.id("dir-tree"))){		//If menuTree present
			Success = "It can show share listing as tree view successfully.";
		}else
			Success = "Can't show share menu as Tree (FAIL).";
		
		System.out.println(Menus + "\n" + Success);
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport));
	        
			builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                             Share Tree                        *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E001 - Share - Tree view\r\n");
	        builder.append("Can it show Share listing as tree view? \r\n" +
						   "탐색기 형태로 공유디스크와 하위공유 디스크를 생성 할 수 있는 인터페이스를 제공하는지 확인?\r\n");
	        builder.append("========================================================================\r\n");
	        builder.append(Menus + "\r\n" + Success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}