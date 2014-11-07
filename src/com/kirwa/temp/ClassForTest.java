package com.kirwa.temp;

import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Period;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByXPath;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Created by Kirtesh on 08-10-2014.
 */
public class ClassForTest {
    private static Logger LOGGER = Logger.getLogger(ClassForTest.class);
    public static void main(String[] args) throws Exception{
    	String strExcelDate ="01-11-2013";
    	SimpleDateFormat excelDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    	SimpleDateFormat uiDateFormat = new SimpleDateFormat("MMMM yyyy");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://jqueryui.com/datepicker/");
        driver.switchTo().frame(driver.findElement(By.className("demo-frame")));
        driver.findElement(By.id("datepicker")).click();
        String strUIDate = driver.findElement(By.className("ui-datepicker-title")).getText();
        //System.out.println(uiDateFormat.parse(strUIDate));
        Months month = Months.monthsBetween(new DateTime(uiDateFormat.parse(strUIDate)),new DateTime(excelDateFormat.parse(strExcelDate)));
       // Period diffPeriod = new Period(new DateTime(uiDateFormat.parse(strUIDate)),new DateTime(excelDateFormat.parse(strExcelDate)));
        System.out.println(month.getMonths());
        
        for(int i=1;i<=Math.abs(month.getMonths());i++){
        	driver.findElement(By.className("ui-datepicker-"+(month.getMonths()>0?"next":"prev"))).click();
        }
        driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//a[text()='"+ new DateTime(excelDateFormat.parse(strExcelDate)).getDayOfMonth() +"']")).click();
      Thread.sleep(10000);
      driver.quit();
    }
}
