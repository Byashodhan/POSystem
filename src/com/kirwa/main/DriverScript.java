package com.kirwa.main;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.apache.log4j.Logger;
import com.kirwa.utils.ExcelReader;
import com.kirwa.utils.Reporter;
import org.joda.time.DateTime;
/*********
 * 
 * @author Kirtesh Wani
 *
 ************/
public class DriverScript {
	//static FileInputStream  file;// = new FileInputStream(new File("TestSuite.xlsx"));
	static ExcelReader workbook;// = new XSSFWorkbook(file);
	public static Logger LOGGER = Logger.getLogger(DriverScript.class);
	public static boolean tcError = false;
	public static void main(String[] args) throws Exception{
		workbook = new ExcelReader("TestSuite.xlsx");
		for(int i=1;i<workbook.getRowCount("Main");i++){
			if(workbook.getCellStringData("Main", i, 1).toLowerCase().equals("true")){	
				Reporter.startReport(workbook.getCellStringData("Main",i,0));
				LOGGER.debug("Executing Test Suite" + workbook.getCellStringData("Main",i,0));
				ExecuteTestSuite(workbook.getCellStringData("Main",i,0));
				Reporter.endReport();
			}
		}
		if (MyDriver.driver!=null)
		MyDriver.driver.quit();
		workbook.close();
	}

	private static void ExecuteTestSuite(String SuiteName) throws Exception {
		String testCaseId="";
		String temtestcaseid = "";
		boolean executetest=true;
		int stepcount = 0;
        int counter=1;
        DateTime startTime= new DateTime();
        boolean tcresult = true;
		for(int i =1;i<workbook.getRowCount(SuiteName);i++){
			if(stepcount==0){
				testCaseId= workbook.getCellStringData(SuiteName, i, 0);
				executetest = workbook.getCellStringData(SuiteName, i, 1).toUpperCase().equals("Y")?true:false;
				if(!executetest)
                	LOGGER.warn("TestCase Marked for Skip " + workbook.getCellStringData(SuiteName, i, 1));
                else
                	LOGGER.debug("TestCase Started : " +  workbook.getCellStringData(SuiteName, i, 1));
				tcresult=true;
                tcError=false;
				Reporter.startTC(Integer.toString(counter++), workbook.getCellStringData(SuiteName, i, 1));
                startTime = new DateTime();
			}
			else{
				if(! workbook.getCellStringData(SuiteName, i, 0).equals(temtestcaseid)){
					testCaseId=  workbook.getCellStringData(SuiteName, i, 0);
					if (MyDriver.driver!=null)
					MyDriver.driver.quit();
                    Reporter.endTC(startTime,new DateTime(),tcresult?"Pass":"Fail",tcError);
                    LOGGER.info("TestCase Ended with " + tcresult);
                    executetest = workbook.getCellStringData(SuiteName, i, 1).toUpperCase().equals("Y")?true:false;
                    if(!executetest)
                    	LOGGER.warn("TestCase Marked for Skip " + workbook.getCellStringData(SuiteName, i, 1));
                    else
                    	LOGGER.debug("TestCase Started : " +  workbook.getCellStringData(SuiteName, i, 1));
                    tcresult=true;
                    tcError =false;
                    Reporter.startTC(Integer.toString(counter++),  workbook.getCellStringData(SuiteName, i, 1));
                    startTime = new DateTime();;
				}
			}
			HashMap<String,String> inputList = new HashMap<String,String>();
			try{
				String[] inputs =  workbook.getCellStringData(SuiteName, i, 4).split(",");
				for(int i1 = 8;i1<8+inputs.length;i1++){
					inputList.put(inputs[i1-8], workbook.getCellStringData(SuiteName, i, i1));
				}
			}catch(Exception e){LOGGER.debug("Keyword not need inputs ");}
			if(executetest)
				tcresult &= ExecuteKeyWord(workbook.getCellStringData(SuiteName, i, 3),inputList,Boolean.getBoolean(workbook.getCellStringData(SuiteName, i, 5)));
			else
				LOGGER.warn("Testcase marked for Skip so Skipping keyword \"" + workbook.getCellStringData(SuiteName, i, 3) +"\"");
			temtestcaseid = testCaseId;
			stepcount++;
		}
        LOGGER.info("TestCase Ended with " + tcresult);
        Reporter.endTC(startTime,new DateTime(),tcresult?"Pass":"Fail",tcError);
	}

	private static boolean ExecuteKeyWord(String KeyWord,HashMap<String,String> inputList,boolean Expected) throws Exception {
		for(int i=1;i<workbook.getRowCount("Keywords");i++){
			if(workbook.getCellStringData("Keywords", i, 0).equals(KeyWord)){
                Reporter.isError=false;
				LOGGER.debug("Started Keyword: " + KeyWord);
                DateTime kwst = new DateTime();
                Reporter.startKW();
				Class<?> cl = Class.forName(workbook.getCellStringData("Keywords", i,1));
				Method m = cl.getMethod( workbook.getCellStringData("Keywords", i, 0),inputList.getClass());
				Object x = m.invoke(m, inputList);
                LOGGER.info("Keyword Ended with " + x.toString());
                Reporter.endKW(KeyWord,kwst,new DateTime(),(Boolean) x,Reporter.isError?"Script Failure":"");
                tcError |= Expected;
                return (Boolean) x == Expected;
			}
		}
        LOGGER.debug("Keyword not found" + KeyWord);
		return false;
	}
	

}
