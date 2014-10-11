package com.kirwa.utils;

import org.joda.time.DateTime;
import org.joda.time.Seconds;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Reporter {
	static String reportName="";
	static File reportFile = null;
	public static boolean isError=false;
	private static FileWriter fw=null;
	private static BufferedWriter bw=null;
	private static String tcstart ="",kwstart="";
	static DateFormat df = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
	public static void startReport(String TestSuiteName) throws IOException{
		reportName = "Reports/AutomationTestResult" + df.format(new Date())+".html";
		reportFile = new File(reportName);
		reportFile.createNewFile();
		fw = new FileWriter(reportFile.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		bw.write("<html><head>" +
                "<style>.tbltc td,.tbltc th{border:1px solid black}.tbltc{width:100%}th{background:blue}.Fail td{background:red}</style>" +
                "</head><body><h1>"+TestSuiteName+"<h1>" +
                "<table class='tbltc'><tr><th>ID</th><th>Testcase Name</th><th>Execution Time</th><th>Result</th><th>Fail Cause</th></tr>");
		
	}

    public static void startTC(String id,String name) throws IOException {
       // bw.write("<tr><td>"+id+"</td><td>"+name+"</td>");
    	tcstart="";
    	tcstart="<tr><td rowspan='2'>"+id+"</td><td>"+name+"</td>";
    	kwstart="<table>";
    }

    public static void endTC(DateTime startTime,DateTime EndTime,String result,boolean failcause) throws IOException {
        bw.write(tcstart + "<td>"+Seconds.secondsBetween(startTime,EndTime).getSeconds()+"</td><td>"+result+"</td><td>"+(failcause?"Script Failure":"")+"</td></tr><tr><td colspan='4'>"+kwstart+"</table></td></tr>");
    }
    
    public static void startKW(){
    	kwstart+="";
    }
    public static void endKW(String name,DateTime startTime, DateTime EndTime, Boolean x,String errorCause){
    	kwstart+="<tr class="+(x?"Pass":"Fail")+"><th>"+name+"</th><td>"+Seconds.secondsBetween(startTime,EndTime).getSeconds()+"</td><td>"+(x?"Pass":"Fail")+"</td><td>"+errorCause+"</td></tr>";
    }
    
	public static void endReport() throws IOException{
		bw.write("</table></body></html>");
		bw.close();
	}

}
