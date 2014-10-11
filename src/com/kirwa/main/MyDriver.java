package com.kirwa.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;


public class MyDriver {
	public static WebDriver driver;
	public static ResourceBundle conf = ResourceBundle.getBundle("com.kirwa.config.config");
	
	private static String outPutfolder;
	public static void init()
	{
		
		Date currentDate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("dd_MM_yy_hh_mm_ss");
		outPutfolder = df.format(currentDate);
		String strBrowserType = conf.getString("BrowserType");
		if(strBrowserType.equals("InternetExplorer"))
		{
			System.setProperty("webdriver.ie.driver","D:\\bin\\drivers\\IEDriver.exe");	
			driver=new InternetExplorerDriver();
		}
		else if(strBrowserType.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver","D:\\bin\\drivers\\chromedriver.exe");	
			driver=new ChromeDriver();	
		}else{
			driver = new FirefoxDriver();
		}
	}
	public static void takeScreenShot()
	{
		try{
		File screenshot =((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		new File("D:/" + outPutfolder).mkdir();
		FileUtils.copyFile(screenshot, new File("d:/testScreens/"+outPutfolder+"/test.png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

}
