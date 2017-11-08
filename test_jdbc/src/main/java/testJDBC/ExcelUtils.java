package testJDBC;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelUtils {

	public static void WriteToFile(String outputFilePath, List<chinese_unit_test> datas)
	{
		ArrayList<chinese_unit_test> bean = new ArrayList<chinese_unit_test>();
		bean.addAll(datas);
		
		HSSFWorkbook wb = new HSSFWorkbook();
    	HSSFSheet sheet = wb.createSheet("Test");
    	
    	HSSFFont font = wb.createFont();
    	font.setFontName("Verdana");
    	
    	HSSFCellStyle style = wb.createCellStyle();
    	style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
    	
    	style.setFont(font);

		for(int i = 0;i < datas.size();i++)
    	{
			HSSFRow rowscore = sheet.createRow(i);
			HSSFCell  cellscore = null;

			for(int j = 0;j < 17;j++)
			{
				cellscore = rowscore.createCell(j);
				cellscore.setCellStyle(style);
				String cellvalue = bean.get(i).getChinese_unit_list().get(j);
				try {
					//if (cellvalue != null){
					//	System.out.println(cellvalue);
					cellscore.setCellValue(cellvalue);
					//}
				} catch (Exception e) {
					System.out.println(e);
				}
				
			}

		}
					
		FileOutputStream out = null;
    	
    	try {
    		out = new FileOutputStream(outputFilePath);
    		wb.write(out);
			
		} catch (Exception e) {
			System.out.println(e.toString());
		}finally {
			try {
				wb.close();
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
	}

	}
}
