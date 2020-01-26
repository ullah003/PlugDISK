package com.Plugdisk1.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.openqa.selenium.*;


public class E013 extends BaseTestLogic {
	
	private static String System_Out = "", Success = "", Folder_Name = "";
	Boolean folderPresent = false, sub_sharePresent = false;
	
	public E013(WebDriver webDriver, File fileConf, File fileReport) {
		super(webDriver, fileConf, fileReport);
		// TODO Auto-generated constructor stub
	}

	void startTest() throws InterruptedException {
		System.out.println("*****************************************************************");
		System.out.println("*                          Share Setting                        *");
		System.out.println("*****************************************************************");
		System.out.println("Test E013 - Share - Share setting");
		System.out.println("Share setting can be done for normal folder which is not shared? \n" +
							"웹탐색기형 UI에서 공유설정되어 있지 않은 폴더를 선택하여 공유를 생성한다? \n"+
							"-----------------------------------------------------------------");
		
		//go to Share menu
//  		driver.findElement(By.id("share")).click();
//  		Thread.sleep(1000);
//  		driver.findElement(By.xpath("//div[@id='tree_view']/ul/li[4]/ul/li/div/span")).click();
//  		Thread.sleep(1000);
		
  		Success = "";
  		System_Out = "";
  		
  		//Search Parent share ---------------------------------
  		WebElement folderlist = driver.findElement(By.id("share-list"));
		
		List<WebElement> allfolder = folderlist.findElements(By.className("icon_folder"));	
		int i = 0;
		for (WebElement folder : allfolder) {
			//System.out.println(share.getText());
			i = i + 1;
			Folder_Name = folder.getText();
			if (!Folder_Name.equals("")){
				//System.out.println(Folder_Name);
				driver.findElement(By.xpath("//div[@id='share-list']/table/tbody/tr[" + i + "]/td/input")).click();
				folderPresent = true;
				break;
			}
		}
  		
		
		if (folderPresent){
			driver.findElement(By.xpath("//li[contains(text(), '공유 설정')]")).click();	//click Delete button
			Thread.sleep(1000);
			
			if (isAlertPresent()){		//while Alert msg appears
				System_Out = driver.switchTo().alert().getText();
				driver.switchTo().alert().accept();		//Click OK from popup alert
				
				//driver.findElement(By.id("closeButton")).click();
				System_Out = System_Out + "\nTest FAIL!" ;
			}else{
			
				//switch to user creation frame
				driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
				
				
				//Click OK--> 
				driver.findElement(By.id("okButton")).click();
				Thread.sleep(1000);
				
				if (isAlertPresent()){		//while Alert msg appears
					System_Out = driver.switchTo().alert().getText();
					driver.switchTo().alert().accept();		//Click OK from popup alert
					
					driver.findElement(By.id("closeButton")).click();
					System_Out = System_Out + "\nTest FAIL!" ;
				}else{
				
					driver.switchTo().defaultContent();
					
					if (isElementPresent(By.id("boxIframe"))){
						driver.switchTo().frame(driver.findElement(By.id("boxIframe")));
						
						if (isElementVisible(By.id("shareName_errMsg"))){
							System_Out = driver.findElement(By.id("shareName_errMsg")).getText();
							System_Out = Folder_Name + ": " + System_Out;
						}else if (isElementVisible(By.id("ip_errMsg"))){
							System_Out = driver.findElement(By.id("ip_errMsg")).getText();
							System_Out = Folder_Name + ": " + System_Out;
						}
						//click cancel button
						driver.findElement(By.id("closeButton")).click();
						System_Out = System_Out + "\nTest FAIL!" ;
					}else{
						//check if share setting done successfully
				  		WebElement share_list = driver.findElement(By.id("share-list"));
						
						List<WebElement> all_share = share_list.findElements(By.className("icon_share"));	
						Success = "Share setting FAIL.";
						for (WebElement shares : all_share) {
							//System.out.println(shares.getText());
							if (shares.getText().equals(Folder_Name)){
								Success = "Share setting successfull.";
								break;
							}
						}
						
					}
				}
			}
			driver.switchTo().defaultContent();
			
  		}else
  			Success = "Folder not found for share setting.";
  		
			System.out.println(System_Out + "\n" + Success);
			
		try {
			StringBuilder builder = new StringBuilder();
	        BufferedWriter out = new BufferedWriter(new FileWriter(fReport, true));
	        
	        builder.append("\r\n*****************************************************************\r\n");
	        builder.append("*                         Delete Sub-Share                      *\r\n");
	        builder.append("*****************************************************************\r\n");
	        builder.append("Test E020 - Share - Delete Sub-Share\r\n");
	        builder.append("Can it delete sub-share successfully? \r\n" +
						   "서브공유디스크 삭제는 공유 정보만 삭제 되는지 확인.\r\n");
	        builder.append("=================================================================\r\n");
	        builder.append(System_Out + "\r\n" + Success + "\r\n");
	        
	        out.write(builder.toString());

			if(Lisenter != null){
				Lisenter.onReportLisenter(builder.toString());
			}
            out.close();
	        } catch (IOException e) {}
	}
}