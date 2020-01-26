package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;

public class E002 extends BaseTestLogic {
	
	private static String Success = "", Menus = "";
	int i = 0;
	
	public E002(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                             Share List                        *");
		System.out.println("*****************************************************************");
		System.out.println("Test E002 - Share - List view");
		System.out.println("Can it show Share listing as list view?\n" +
							"리스트형태로 공유정보를 제공하여 권한 및 정보 수정을 할 수 있는 인터페이스를 제공하는지 확인 \n"+
							"메인공유와 서브공유가 정상적으로 표시되는지 확인 \n"+
							"----------------------------------------------------------------------------------------------------");
		
		//go to Share menu
		driver.findElement(By.id("share")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li[2]/div/span")).click();
		Thread.sleep(1000);
		
		
		if (isElementPresent(By.xpath("//li[contains(text(), '정보 수정')]"))){		//If share Edit option available
			Menus = "Share Edit (정보 수정) option present.";
		}else
			Menus = "Share Edit (정보 수정) option not found.";
		
		if (isElementPresent(By.xpath("//li[contains(text(), '권한 설정')]"))){		//If share Permission option available
			Menus = Menus + "\nShare Permission (권한 설정) option present.";
		}else
			Menus = Menus + "\nShare Permission (권한 설정) option not found.";
		
		if (isElementPresent(By.xpath("//li[contains(text(), '삭제')]"))){		//If share delete option available
			Menus = Menus + "\nShare delete (삭제) option present.";
		}else
			Menus = Menus + "\nShare delete (삭제) option not found.";
		
		
		if (isElementPresent(By.id("share-search-list"))){		//If menuTree present
			Success = "It can show share listing as list view successfully.";
		}else
			Success = "Can't show share menu as list (FAIL).";
		
		//select show all
		Select viewAll = new Select(driver.findElement(By.id("page_list_size")));
		viewAll.selectByVisibleText("전체");
		Thread.sleep(2000);
		
		WebElement subshare_list = driver.findElement(By.id("share-search-list"));
		
		List<WebElement> allsubshare = subshare_list.findElements(By.className("icon_sub"));	
		for (WebElement subshare : allsubshare) {
			System.out.println(subshare.getText());
			i = i + 1;
		}
		
		System.out.println(Menus + "\n" + Success);
		System.out.println("Total number of sub-share present: " + i );
		
		
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
			builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                             Share list                        *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E002 - Share - List view\r\n");
	        builder.append("Can it show Share listing as list view? \r\n" +
						   "리스트형태로 공유정보를 제공하여 권한 및 정보 수정을 할 수 있는 인터페이스를 제공하는지 확인" + 
						   "메인공유와 서브공유가 정상적으로 표시되는지 확인\r\n");
	        builder.append("========================================================================\r\n");
	        builder.append(Menus + "\r\n" + Success + "\r\n");
	        builder.append("Total number of sub-share present: " + i + "\r\n");
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}