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


public class D007 extends BaseTestLogic {
	
	private static String System_Out = "", path_to_file = "", successCount = "", failCount = ""; 
	private static String [] error_list;
	int totl_error = 0;
	
	public D007(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                   Create Multiple Department                  *");
		System.out.println("*****************************************************************");
		System.out.println("Test D007 - Department - Create Multiple Department");
		System.out.println("Can it create Multiple Department properly? \n" +
							"양식 파일 불러오기 / 정합성 체크 - 요류 사항 뷰 / 등록 - 일괄 등록 \n"+
							"--------------------------------------------------------------------");
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(fConf));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    try {
	        for (int i=1; i<=37; i++){
	        	String line = null;
				try {
					line = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	if (i > 36){
	        		//System.out.println(line);
		        	String[] tokens = line.split("=");
		        	if (tokens[0].equals("multi_dept_file_path")){
		        		path_to_file = tokens[1];
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
	    
		driver.findElement(By.id("depart")).click();	//go to user menu
		Thread.sleep(1000);
		
		
			
			
			driver.findElement(By.xpath("//li[contains(text(), '부서생성')]")).click();	//click Add department button
			Thread.sleep(2000);
			//switch to dept creation frame
			driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
			
			//select multi registration
			driver.findElement(By.id("allAdd")).click();
			Thread.sleep(1000);
			
			driver.findElement(By.xpath("//input[@type='FILE']")).sendKeys(path_to_file);
			
			
			//Click OK--> 
			driver.findElement(By.id("okButton")).click();
			Thread.sleep(5000);
			
			
			successCount = driver.findElement(By.id("successCnt")).getText();
			failCount = driver.findElement(By.id("failCnt")).getText(); 
			System.out.println("Total department registered successfully = " + successCount);
			System.out.println("Total department register FAIL = " + failCount);
			
			//total number of department
			if (!successCount.equals("") && !failCount.equals("")){
				int Num_of_dept_succ = Integer.parseInt(successCount);
				int Num_of_dept_fail = Integer.parseInt(failCount);
			
				totl_error = Num_of_dept_succ + Num_of_dept_fail;
			}
			error_list = new String[totl_error];
			
			WebElement deptlist = driver.findElement(By.id("errList"));
			List<WebElement> alldept = deptlist.findElements(By.className("un_view"));			
			int i = 0;
			for (WebElement dept : alldept) {
				i = i + 1;
				
				String deptName = driver.findElement(By.xpath("//div[@id='errList']/table/tbody/tr[" + i + "]/td[2]")).getText();
				String err_cause = driver.findElement(By.xpath("//div[@id='errList']/table/tbody/tr[" + i + "]/td[3]")).getText();
				String remarks = driver.findElement(By.xpath("//div[@id='errList']/table/tbody/tr[" + i + "]/td[4]")).getText();
				System.out.println(dept.getText() + "\t\t" + deptName + "\t\t" + err_cause + "\t\t" + remarks);
				error_list[i-1] = dept.getText() + "\t\t" + deptName + "\t\t" + err_cause + "\t\t" + remarks;
			}
			
			
			
			driver.findElement(By.id("closeButton")).click();
			
			
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                   Create Multiple Department                  *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test D007 - Department - Create Multiple Department\r\n");
	        builder.append("Can it create Multiple Department properly? \r\n" +
						   "양식 파일 불러오기 / 정합성 체크 - 요류 사항 뷰 / 등록 - 일괄 등록\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append("Total department registered successfully = " + successCount + "\r\n");
	        builder.append("Total department register FAIL = " + failCount + "\r\n");
	        for (int j=1; j<=totl_error; j++){
	        	if (error_list[j-1] != null)			//to avoid null print to report file
	        		builder.append(error_list[j-1] + "\r\n");
	        }
	        //builder.append(System_Out + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}
