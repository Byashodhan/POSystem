package com.kirwa.main;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kirwa.utils.Reporter;

import org.joda.time.DateTime;

public class DriverScript {
	static FileInputStream  file;// = new FileInputStream(new File("TestSuite.xlsx"));
	static XSSFWorkbook workbook;// = new XSSFWorkbook(file);
	public static Logger LOGGER = Logger.getLogger(DriverScript.class);
	public static void main(String[] args) throws Exception{
		file = new FileInputStream(new File("TestSuite.xlsx"));
		workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheet("Main");
		Iterator<Row> MySuites = sheet.rowIterator();
		MySuites.next();
		
		while(MySuites.hasNext()){
			Row suite = MySuites.next();
			
			if(suite.getCell(1).getBooleanCellValue())
			{	Reporter.startReport(suite.getCell(0).getStringCellValue());
				LOGGER.debug("Executing Test Suite" + suite.getCell(0).getStringCellValue());
				ExecuteTestSuite(suite.getCell(0).getStringCellValue());
				Reporter.endReport();
			}
			
		}
		MyDriver.driver.quit();
		file.close();
	}

	private static void ExecuteTestSuite(String SuiteName) throws Exception {
		XSSFSheet tcSheet = workbook.getSheet(SuiteName);
		Iterator<Row> steps = tcSheet.iterator();
		steps.next();
		String testCaseId="";
		String temtestcaseid = "";
		int stepcount = 0;
        int counter=1;
        DateTime startTime= new DateTime();
        DateTime EndTime ;
        boolean tcresult = true;
		while(steps.hasNext()){
			Row step = steps.next();
			if(stepcount==0){
				testCaseId= step.getCell(0).getStringCellValue();
				LOGGER.debug("TestCase Started : " + step.getCell(1).getStringCellValue());
                tcresult=true;
				Reporter.startTC(Integer.toString(counter++),step.getCell(1).getStringCellValue());
                startTime = new DateTime();
			}
			else{
				if(!step.getCell(0).getStringCellValue().equals(temtestcaseid)){
					testCaseId= step.getCell(0).getStringCellValue();
					MyDriver.driver.quit();
                    Reporter.endTC(startTime,new DateTime(),tcresult?"Pass":"Fail","");
                    LOGGER.info("TestCase Ended with " + tcresult);
                    LOGGER.debug("TestCase Started : " + step.getCell(1).getStringCellValue());
                    tcresult=true;

                    Reporter.startTC(Integer.toString(counter++), step.getCell(1).getStringCellValue());
                    startTime = new DateTime();;
				}

			}
			HashMap<String,String> inputList = new HashMap<String,String>();
			try{
				String[] inputs = step.getCell(4).getStringCellValue().split(",");
				
				for(int i = 8;i<8+inputs.length;i++){
					inputList.put(inputs[i-8],step.getCell(i).getStringCellValue());
				}
			}catch(Exception e){LOGGER.debug("Keyword not need inputs ");}
			tcresult &= ExecuteKeyWord(step.getCell(3).getStringCellValue(),inputList);
			temtestcaseid = testCaseId;
			stepcount++;

		}
        LOGGER.info("TestCase Ended with " + tcresult);
        Reporter.endTC(startTime,new DateTime(),tcresult?"Pass":"Fail","");
	}

	private static boolean ExecuteKeyWord(String KeyWord,HashMap<String,String> inputList) throws Exception {
		XSSFSheet kwSheet = workbook.getSheet("Keywords");
		Iterator<Row> keywords = kwSheet.iterator();
		keywords.next();
		while(keywords.hasNext()){
			Row kwrow = keywords.next();
			
			if(kwrow.getCell(0).getStringCellValue().equals(KeyWord)){
                LOGGER.debug("Started Keyword" + KeyWord);
                DateTime kwst = new DateTime();
                Reporter.startKW();
				Class<?> cl = Class.forName(kwrow.getCell(1).getStringCellValue());
				Method m = cl.getMethod(kwrow.getCell(0).getStringCellValue(),inputList.getClass());
				Object x = m.invoke(m, inputList);
                LOGGER.info("Keyword Ended with " + x.toString());
                Reporter.endKW(KeyWord,kwst,new DateTime(),(Boolean) x);
                return (Boolean) x;
			}
		}
        LOGGER.debug("Keyword not found" + KeyWord);
       // LOGGER.info("Keyword Ended with " + false);
		return false;
	}
	

}
