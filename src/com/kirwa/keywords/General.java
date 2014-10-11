package com.kirwa.keywords;

import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import com.kirwa.main.MyDriver;

public class General {
	
	public static boolean LaunchApplication(HashMap<String,String> args)
	{
		try {
			MyDriver.init();
			MyDriver.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			//MyDriver.driver.get("http://google.com");
			MyDriver.driver.get(args.get("URL"));
			MyDriver.driver.manage().window().maximize();
			//ToDo : Check if Application Launched
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//System.out.println("Launch Application using URL" + args.get("URL"));
	public static ResourceBundle Genrepo = ResourceBundle.getBundle("com.kirwa.repo.Generalrepo");
	public static boolean LoginApplication(HashMap<String,String> args)
	{
		try {
			MyDriver.driver.findElement(By.xpath(Genrepo.getString("LogintxtUname"))).sendKeys(args.get("UserName"));
			MyDriver.driver.findElement(By.xpath(Genrepo.getString("LogintxtPwd"))).sendKeys(args.get("Password"));
			MyDriver.driver.findElement(By.xpath(Genrepo.getString("LoginbtnSignin"))).click();
//MyDriver.driver.manage().window().setSize(new Dimension(100, 100));
			if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("ValidationDashboard"))).size()>0)
			{
				return true;
			}
			else
			{
			//MyDriver.takeScreenShot();
			return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    }

	//public static ResourceBundle LogoutRepo= ResourceBundle.getBundle("com.kirwa.repo.logoutrepo");
	public static boolean LogoutApplication(HashMap<String,String> args){
		try {
			MyDriver.driver.findElement(By.xpath(Genrepo.getString("logout"))).click();
			
			if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("Validationlogout"))).size()>0)
			{
				return true;	
			}
			else
			{

				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
}
		//System.out.println("Logging out the Application");
	//public static ResourceBundle NavigatetoMenus =ResourceBundle.getBundle("com.kirwa.repo.menunavigationrepo");	
	public static boolean NavigatetoDashboard(HashMap<String,String> args){
			
			
				try {
					
					MyDriver.driver.findElement(By.xpath(Genrepo.getString("Linkdashboard"))).click();
					if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("ValidationDashboard"))).size()>0)
					{
					return true;
					}
					else
					{
					MyDriver.takeScreenShot();
					return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			    
			}
			public static boolean NavigatetoNewPO(HashMap<String,String> args) {
				try {
					MyDriver.driver.findElement(By.xpath(Genrepo.getString("LinkNewPO"))).click();
					if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("ValidationDashboard"))).size()>0)
					{
					return true;
					}
					else
					{
					MyDriver.takeScreenShot();
					return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			    
			}
			public static boolean NavigatetoHistory(HashMap<String,String> args) {
				try {
					MyDriver.driver.findElement(By.xpath(Genrepo.getString("LinkHistroy"))).click();
					if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("validationHistory"))).size()>0)
					{
					return true;
					}
					else
					{
					MyDriver.takeScreenShot();
					return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			    
			}
			public static boolean NavigatetoWithMe(HashMap<String,String> args) {
				try {
					MyDriver.driver.findElement(By.xpath(Genrepo.getString("LinkWithMe"))).click();
					if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("ValidationWithMe"))).size()>0)
					{
					return true;
					}
					else
					{
					MyDriver.takeScreenShot();
					return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			    
			}
			
			public static boolean NavigatetoReports(HashMap<String,String> args) {
				try {
					MyDriver.driver.findElement(By.xpath(Genrepo.getString("LinkReports"))).click();
					if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("POSystemReports"))).size()>0)
					{
					return true;
					}
					else
					{
					MyDriver.takeScreenShot();
					return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			    
			}

			public static boolean NavigatetoSetting(HashMap<String,String> args) {
				try {
					MyDriver.driver.findElement(By.xpath(Genrepo.getString("LinkSetting"))).click();
					if(MyDriver.driver.findElements(By.xpath(Genrepo.getString("ValidationSetting"))).size()>0)
					{
					return true;
					}
					else
					{
					MyDriver.takeScreenShot();
					return false;
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
			    
			}
			
		}
	






