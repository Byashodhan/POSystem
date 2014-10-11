package com.kirwa.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**********************************
 * 
 * @author Kirtesh Wani
 *
 ***********************************/

public class ExcelReader {
	private XSSFWorkbook workbook;
	private final String excelfilepath;
	
	public ExcelReader(String excelFilePath) throws Exception {
		workbook = new XSSFWorkbook(new FileInputStream(new File(excelFilePath)));
		excelfilepath = excelFilePath;
	}
	public void close(){
		workbook = null;
	}
	public String getCellStringData(String SheetName,int RowNum,int ColNum){
		try{
			XSSFSheet sheet = workbook.getSheet(SheetName);
			Cell cell = sheet.getRow(RowNum).getCell(ColNum);
			switch(sheet.getRow(RowNum).getCell(ColNum).getCellType()){
			case Cell.CELL_TYPE_STRING:
                return cell.getRichStringCellValue().getString();
            case Cell.CELL_TYPE_NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
			case Cell.CELL_TYPE_BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case Cell.CELL_TYPE_FORMULA:
                return cell.getCellFormula();
            case Cell.CELL_TYPE_BLANK:
            	return "";
            default:
            	 return cell.getStringCellValue();
			}
		}
		catch(Exception e){
			return "";
		}
	}
	public int getRowCount(String SheetName){
		XSSFSheet sheet;
		try{
		sheet = workbook.getSheet(SheetName);
		return sheet.getPhysicalNumberOfRows();
		}finally{
			sheet=null;
		}
	}
	public int getColumnCount(String SheetName,int RowNum){
		XSSFSheet sheet;
		try{
			sheet = workbook.getSheet(SheetName);
			return sheet.getRow(RowNum).getPhysicalNumberOfCells();
		}finally{
			sheet=null;
		}
	}
	public boolean createSheet(String SheetName){
		try{
			FileOutputStream output = new FileOutputStream(excelfilepath);
			workbook.createSheet(SheetName);
			workbook.write(output);
			output.close();
			ExcelReaderint();
			return true;
		}
		catch(Exception e){
			return false;
		}
	}
	public boolean deleteSheet(String SheetName){
		try{
			FileOutputStream output = new FileOutputStream(excelfilepath);
			workbook.removeSheetAt(workbook.getSheetIndex(SheetName));
			workbook.write(output);
			output.close();
			ExcelReaderint();
			return true;
		}
		catch(Exception e){
			return false;
		}
		
	}
	private void ExcelReaderint() throws FileNotFoundException, IOException {
		workbook = new XSSFWorkbook(new FileInputStream(new File(excelfilepath)));
	}
}
