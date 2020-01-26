package com.Plugdisk.tests;

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


		A001 Intro_image = new A001(driver, fConf, fReport);
		Intro_image.setLisenter(lisenter);
		Intro_image.startTest();
		
		Login();
		
		A003 admin_login = new A003(driver, fConf, fReport);
		admin_login.setLisenter(lisenter);
		admin_login.startTest();
		
		A004 admin_change_pass = new A004(driver, fConf, fReport);
		admin_change_pass.setLisenter(lisenter);
		admin_change_pass.startTest();
		
		A005 gotoSysAdmin = new A005(driver, fConf, fReport);
		gotoSysAdmin.setLisenter(lisenter);
		gotoSysAdmin.startTest();
		
		A006 rememberID = new A006(driver, fConf, fReport);
		rememberID.setLisenter(lisenter);
		rememberID.startTest();
		
		//Login again
		driver.findElement(By.id("userid")).clear();
		driver.findElement(By.id("userid")).sendKeys(userID);
		driver.findElement(By.id("passwd")).clear();
		driver.findElement(By.id("passwd")).sendKeys(userPass);
		driver.findElement(By.id("login_button")).click();
		
		B001 Default_logo = new B001(driver, fConf, fReport);
		Default_logo.setLisenter(lisenter);
		Default_logo.startTest();
		
		B003 Server_Version = new B003(driver, fConf, fReport);
		Server_Version.setLisenter(lisenter);
		Server_Version.startTest();
		
		B004 Tree_Menu = new B004(driver, fConf, fReport);
		Tree_Menu.setLisenter(lisenter);
		Tree_Menu.startTest();
		
		B007 Logical_Physical_Vol = new B007(driver, fConf, fReport);
		Logical_Physical_Vol.setLisenter(lisenter);
		Logical_Physical_Vol.startTest();
		
		B009 Users_list = new B009(driver, fConf, fReport);
		Users_list.setLisenter(lisenter);
		Users_list.startTest();
		
		C001 create_user = new C001(driver, fConf, fReport);
		create_user.setLisenter(lisenter);
		create_user.startTest();
		
		C008 modify_multiUser = new C008(driver, fConf, fReport);
		modify_multiUser.setLisenter(lisenter);
		modify_multiUser.startTest();
		
		C008_1 modify_user = new C008_1(driver, fConf, fReport);
		modify_user.setLisenter(lisenter);
		modify_user.startTest();
		
		C009 init_passwd = new C009(driver, fConf, fReport);
		init_passwd.setLisenter(lisenter);
		init_passwd.startTest();
		
		//pageRefresh();
		Admin_Login();
		
		C013 delete_active_user = new C013(driver, fConf, fReport);
		delete_active_user.setLisenter(lisenter);
		delete_active_user.startTest();
		
		C017 delete_user = new C017(driver, fConf, fReport);
		delete_user.setLisenter(lisenter);
		delete_user.startTest();
		
		C019 deactivate_user = new C019(driver, fConf, fReport);
		deactivate_user.setLisenter(lisenter);
		deactivate_user.startTest();
		
		Admin_Login();

		
		D001 department_tree = new D001(driver, fConf, fReport);
		department_tree.setLisenter(lisenter);
		department_tree.startTest();
		
		D002 Sub_department_user = new D002(driver, fConf, fReport);
		Sub_department_user.setLisenter(lisenter);
		Sub_department_user.startTest();
		
		D003 Duplicate_sub_department = new D003(driver, fConf, fReport);
		Duplicate_sub_department.setLisenter(lisenter);
		Duplicate_sub_department.startTest();
		
		
		D004 Duplicate_department_ID = new D004(driver, fConf, fReport);
		Duplicate_department_ID.setLisenter(lisenter);
		Duplicate_department_ID.startTest();


		D005 department_share = new D005(driver, fConf, fReport);
		department_share.setLisenter(lisenter);
		department_share.startTest();
		
		D005_1 create_department = new D005_1(driver, fConf, fReport);
		create_department.setLisenter(lisenter);
		create_department.startTest();
		
		
		D007 create_multi_department = new D007(driver, fConf, fReport);
		create_multi_department.setLisenter(lisenter);
		create_multi_department.startTest();
		
		
   		D008 modify_department = new D008(driver, fConf, fReport);
		modify_department.setLisenter(lisenter);
		modify_department.startTest();
		
		D009 AddUser_department = new D009(driver, fConf, fReport);
		AddUser_department.setLisenter(lisenter);
		AddUser_department.startTest();
		
		D013 Move_department = new D013(driver, fConf, fReport);
		Move_department.setLisenter(lisenter);
		Move_department.startTest();

		D017 delete_department = new D017(driver, fConf, fReport);
		delete_department.setLisenter(lisenter);
		delete_department.startTest();

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
