package com.Plugdisk.tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.*;


public class D009 extends BaseTestLogic {
	
	private static String System_Out = "", Adduser_dept = "", total_page = "", tmp = "";
	Boolean isPresent = false;
	
	public D009(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                    Add user to  Department                    *");
		System.out.println("*****************************************************************");
		System.out.println("Test D009 - Department - Add user to Department");
		System.out.println("When you select a department and click Add user, can it show add user window \n" +
							"search current users list and can add users properly? \n" +
							"사용자 추가 버튼을 클릭하면 사용자 검색뷰가 보이고 현재위치의 부서에 사용자를 추가한다. \n" +
							"사용자 아이디 또는 이름으로 검색하여 선택된 부서에 사용자를 추가할 수 있다. \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=41; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 40){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("Adduser_dept")){
		        		Adduser_dept = tokens[1];
		        	}
	        	}
	        }
	    } finally {
	        try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	  //go to department menu
	    driver.findElement(By.id("depart")).click();
	  	Thread.sleep(1000);
	    
	    
	    //if at least 1 department present
	    if (isElementPresent(By.xpath("//div[@id='depart-list']/table/tbody/tr/td[2]/span"))){
	    	
	    	WebElement list_of_dept = driver.findElement(By.id("departTree"));
			List<WebElement> deptments = list_of_dept.findElements(By.className("icon_division"));	
			for (WebElement deprt : deptments) {
				//tmp = "Can delete deparment successfully.";
				//System.out.println(deprt.getText());
				if (deprt.getText().equals(Adduser_dept)){
					isPresent = true;
					deprt.click();
					break;
				}
			}
			
			if (isPresent){
				driver.findElement(By.xpath("//li[contains(text(), '사용자 추가')]")).click();		//click Add User
				Thread.sleep(2000);
				//switch to department creation frame
		    	driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
		    	
		    	driver.findElement(By.id("searchButton")).click();		//click search
		    	driver.findElement(By.id("selectAll")).click();			//select All
		    	driver.findElement(By.id("okButton")).click();			//select OK button
		    	
		    	if (isAlertPresent()){		//while Alert msg appears
					System_Out = driver.switchTo().alert().getText();
					System_Out = System_Out + "\r\nTest Fail!";
					driver.switchTo().alert().accept();		//Click OK from popup alert
				}else{
					System_Out = "Users added to department (" + Adduser_dept + ") successfully.";
				}
				
				//click cancel and go back to default content
				if (isElementPresent(By.id("closeButton")))
					driver.findElement(By.id("closeButton")).click();
				driver.switchTo().defaultContent();
				
			}else
				System_Out = "Department (" + Adduser_dept + ") not found to add user.";
	    	
		}else
			System_Out = "No department present to delete.";
		
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                     Add user to Department                    *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D009 - Department - Add user to Department\r\n");
	        builder.append("When you select a department and click Add user, can it show add user window \r\n" +
	        				"search current users list and can add users properly? \r\n" +
						   "사용자 추가 버튼을 클릭하면 사용자 검색뷰가 보이고 현재위치의 부서에 사용자를 추가한다. \r\n" +
						   "사용자 아이디 또는 이름으로 검색하여 선택된 부서에 사용자를 추가할 수 있다. \r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(System_Out + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
