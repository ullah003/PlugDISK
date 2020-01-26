package com.Plugdisk1.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class TestWorking {
	protected static WebDriver driver;
	private static String baseUrl, userID, userPass;
	private BaseTestLogic.TestReportLisenter lisenter;
	
	File fConf, fReport;
	
	public TestWorking(File fileConf, File fileReport) {
		// TODO Auto-generated constructor stub
		fConf = fileConf;
		fReport = fileReport;
		lisenter = null;
	}
	
	public void Login() throws IOException, InterruptedException {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		BufferedReader br = new BufferedReader(new FileReader(fConf));
	    try {
	        for (int i=0; i<=2; i++){
	        	String line = br.readLine();
	        	//System.out.println(line);
	        	String[] tokens = line.split("=");
            	if (tokens[0].equals("url")){
	        		baseUrl = tokens[1];
	        	}else if (tokens[0].equals("ID")){
	        		userID = tokens[1];
	        	}else if (tokens[0].equals("passwd")){
	        		userPass = tokens[1];
	        	}
	        }
	    } finally {
	        br.close();
	    }
		
		driver.get(baseUrl);
		
		driver.findElement(By.id("userid")).clear();
		driver.findElement(By.id("userid")).sendKeys(userID);
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys(userPass);
		//driver.findElement(By.cssSelector("input.btn_login")).click();
		driver.findElement(By.id("login_button")).click();
	}
	
	public void Admin_Login() throws IOException, InterruptedException {
		driver.get(baseUrl);
		Thread.sleep(3000);
		driver.findElement(By.id("userid")).clear();
		driver.findElement(By.id("userid")).sendKeys(userID);
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys(userPass);
		driver.findElement(By.id("login_button")).click();
	}

	public void startAutoTest() throws Exception {
		Login();


		E001 share_tree = new E001(driver, fConf, fReport);
		share_tree.setLisenter(lisenter);
		share_tree.startTest();
		
		pageRefresh();
		E002 share_list = new E002(driver, fConf, fReport);
		share_list.setLisenter(lisenter);
		share_list.startTest();
		
		pageRefresh();
		E005 share_disk_usage = new E005(driver, fConf, fReport);
		share_disk_usage.setLisenter(lisenter);
		share_disk_usage.startTest();
		
		pageRefresh();
		E003 quota_top_share = new E003(driver, fConf, fReport);
		quota_top_share.setLisenter(lisenter);
		quota_top_share.startTest();
		
		pageRefresh();
		E006 create_share = new E006(driver, fConf, fReport);
		create_share.setLisenter(lisenter);
		create_share.startTest();
		
		pageRefresh();
		E007 create_sub_share = new E007(driver, fConf, fReport);
		create_sub_share.setLisenter(lisenter);
		create_sub_share.startTest();
		
		pageRefresh();
		E008 search_share_admin = new E008(driver, fConf, fReport);
		search_share_admin.setLisenter(lisenter);
		search_share_admin.startTest();
		
		pageRefresh();
		E009 share_duplication_check = new E009(driver, fConf, fReport);
		share_duplication_check.setLisenter(lisenter);
		share_duplication_check.startTest();
		
		pageRefresh();
		E014 edit_share = new E014(driver, fConf, fReport);
		edit_share.setLisenter(lisenter);
		edit_share.startTest();
		
		pageRefresh();
		E016 delete_warning = new E016(driver, fConf, fReport);
		delete_warning.setLisenter(lisenter);
		delete_warning.startTest();
		
		pageRefresh();
		E018 delete_share = new E018(driver, fConf, fReport);
		delete_share.setLisenter(lisenter);
		delete_share.startTest();
		
		pageRefresh();
		E020 delete_subshare = new E020(driver, fConf, fReport);
		delete_subshare.setLisenter(lisenter);
		delete_subshare.startTest();
		
		E013 share_setting = new E013(driver, fConf, fReport);
		share_setting.setLisenter(lisenter);
		share_setting.startTest();
		
		pageRefresh();
		F002 edit_subShare = new F002(driver, fConf, fReport);
		edit_subShare.setLisenter(lisenter);
		edit_subShare.startTest();
		
		pageRefresh();
		F003 permission_setting = new F003(driver, fConf, fReport);
		permission_setting.setLisenter(lisenter);
		permission_setting.startTest();
		
		Admin_Login();
		F004 subShare_permission_setting = new F004(driver, fConf, fReport);
		subShare_permission_setting.setLisenter(lisenter);
		subShare_permission_setting.startTest();


	}

	public void setLisenter(BaseTestLogic.TestReportLisenter lisenter) {
		this.lisenter = lisenter;
	}
	
	private static void pageRefresh() {
		//Refresh
		try{
			Thread.sleep(2000);
		} catch(Exception e) {}
		String url = driver.getCurrentUrl();  
		driver.navigate().to(url);  
		try{
			Thread.sleep(2000);
		} catch(Exception e) {}
	}
		
}
