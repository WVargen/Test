package com.liangli.nj.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;


public class ExcelUtils {

	public static void WriteToFile(File file, String sheetname,List<String[]> datas)
	{
		ArrayList<String[]> bean = new ArrayList<String[]>();
		bean.addAll(datas);
		
		XSSFWorkbook wb = new XSSFWorkbook();
		XSSFSheet sheet = null;
		if (wb.getSheet(sheetname) == null) {
			 sheet = wb.createSheet(sheetname);
		}else{
			 sheet = wb.getSheet(sheetname);
		}
    	
    	XSSFFont font = wb.createFont();
    	font.setFontName("Verdana");
    	
    	XSSFCellStyle style = wb.createCellStyle();
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	
    	style.setFont(font);

		for(int i = 0;i < datas.size();i++)
    	{
			XSSFRow rowscore = sheet.createRow(i);
			XSSFCell  cellscore = null;

			for(int j = 0;j < bean.get(i).length;j++)
			{
				cellscore = rowscore.createCell(j);
				cellscore.setCellStyle(style);
				String cellvalue = bean.get(i)[j];
				try {
					cellscore.setCellValue(cellvalue);
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}

		}
					
		FileOutputStream out = null;
    	
    	try {
    		out = new FileOutputStream(file);
    		wb.write(out);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			try {
				//wb.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	
	public static String[][] ReadFromFile(String inputFilePath) 
	{
		FileInputStream iStream = null;
		try {
			iStream = new FileInputStream(inputFilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 String fileType = inputFilePath.substring(inputFilePath.lastIndexOf(".") + 1, inputFilePath.length());
		 Workbook wb = null;
		 if (fileType.equals("xls")) {
	            try {
					wb = new HSSFWorkbook(iStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        } else if (fileType.equals("xlsx")) {
	            try {
					wb = new XSSFWorkbook(iStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
	        } else {
	            try {
					throw new Exception("读取的不是excel文件");
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
		 Sheet sheet = wb.getSheetAt(0);
		 int i = sheet.getPhysicalNumberOfRows();
		 int j = sheet.getRow(0).getPhysicalNumberOfCells();
		 String[][] content = new String[i][j];
		 i = 0;
		 for (Iterator<Row> iter = sheet.rowIterator(); iter.hasNext();) {
			 j = 0;
			 Row row = iter.next(); 
			 for (Iterator<Cell> iter2 =row.cellIterator(); iter2.hasNext();) {
				 Cell cell = iter2.next();
                 content[i][j] = cell.getCellType() == Cell.CELL_TYPE_NUMERIC ? cell.getNumericCellValue()+"":cell.getStringCellValue();
                 j++;
			 }
			 i++;
		 }
		 //for (int k = 0; k < content.length; k++) {
			//System.out.println(content[k][6]);
		//}
		return content;
	}
}
