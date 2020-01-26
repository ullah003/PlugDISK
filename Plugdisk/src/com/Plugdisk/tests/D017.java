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
import org.openqa.selenium.support.ui.Select;


public class D017 extends BaseTestLogic {
	
	private static String System_Out = "", del_dept_name = "", total_page = "", tmp = "";
	Boolean isSelected = false;
	
	public D017(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                        Delete Department                      *");
		System.out.println("*****************************************************************");
		System.out.println("Test D017 - Department - Delete Department");
		System.out.println("Does it shows warning message about deleting sub-department while deleting a department? \n" +
							"Can it delete selected department and all its info successfully? \n" +
							"부서정보를 삭제할 때 하위 부서도 삭제한다는 메시지를 출력하는지 확인 \n" +
							"하위부서가 있는 부서를 삭제할 경우 하위부서의 정보와 시스템 그룹정보도 삭제 하는지 확인 \n" +
							"----------------------------------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=46; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 45){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("del_deptname")){
		        		del_dept_name = tokens[1];
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
	    	
	    	//get total page number
//	    	total_page = driver.findElement(By.id("total_pages")).getText();
//			int totl_page = Integer.parseInt(total_page);
//			for (int j=1; j<=totl_page; j++){
//				if (j>1)		//go to next page if exists
//					driver.findElement(By.id("IDgoNextPage")).click();
				
				//System.out.println("Page------------------> :" + j);
				//select the dept you want to delete
				
				Select viewAll = new Select(driver.findElement(By.id("page_list_size")));
	  			viewAll.selectByVisibleText("전체");
				Thread.sleep(2000);
				
		    	WebElement deptlist = driver.findElement(By.id("depart-list"));
				
				List<WebElement> alldept = deptlist.findElements(By.className("icon_division"));	
				int i = 0;
				for (WebElement dept : alldept) {
					i = i + 1;
					//System.out.println(dept.getText());
					if (dept.getText().equals(del_dept_name)){
						isSelected = true;
						driver.findElement(By.xpath("//div[@id='depart-list']/table/tbody/tr[" + i +"]/td/input")).click();
						//j = totl_page +1;		//break outer loop 
						break;
					}
				}
			//}
	    	
			if (isSelected){
				//System.out.println("Success.............................");
				driver.findElement(By.xpath("//li[contains(text(), '삭제')]")).click();		//click delete option
				Thread.sleep(2000);
				//switch to department creation frame
		    	driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				if (isElementPresent(By.id("pop-content"))){
					System_Out = "Can show warning message about deleting sub-department while deleting a department successfully";
					
					driver.findElement(By.id("delAgree")).click();		//check Agree 
					driver.findElement(By.id("delButton")).click();		//click delete button
					Thread.sleep(2000);
					
					//check if it is deleted successfully
					WebElement list_of_dept = driver.findElement(By.id("departTree"));
					List<WebElement> deptments = list_of_dept.findElements(By.className("icon_division"));	
					for (WebElement deprt : deptments) {
						tmp = "Can delete deparment successfully.";
						//System.out.println(deprt.getText());
						if (deprt.getText().equals(del_dept_name)){
							tmp = "Can't delete deparment (FAIL).";
							break;
						}
					}
					System_Out = System_Out + "\r\n" + tmp; 
					driver.switchTo().defaultContent();
				}else{
					System_Out = "Can't shows warning message about deleting sub-department while deleting a department (FAIL)";
				}
			}else
				System_Out = "Department not found, can't delete.";
		}else
			System_Out = "No department present to delete.";
		
		System.out.println(System_Out);
		
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                        Delete Department                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D017 - Department - Delete Department\r\n");
	        builder.append("Does it shows warning message about deleting sub-department while deleting a department? \r\n" +
	        				"Can it delete selected department and all its info successfully? \r\n" +
						   "부서정보를 삭제할 때 하위 부서도 삭제한다는 메시지를 출력하는지 확인 \r\n" +
						   "하위부서가 있는 부서를 삭제할 경우 하위부서의 정보와 시스템 그룹정보도 삭제 하는지 확인 \r\n");
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
