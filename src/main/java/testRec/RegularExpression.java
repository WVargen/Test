/**
 * 
 */
/**
 * @author vargen
 *
 */
package testRec;


import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression
{
    public static void main( String[] args ){
    	String inputpath_xls = "C:/Users/vargen/Desktop/test/句型_小韦.xlsx";
    	String outputpath_xls = "C:/Users/vargen/Desktop/test/1.xlsx";
    	File xlsfile = new File(outputpath_xls);
    	
    	String inputpath_doc = "C:/Users/vargen/Desktop/test/2017年广东中考英语.docx";
    	String outputpath_doc = "C:/Users/vargen/Desktop/test/2.xlsx";
    	File docfile = new File(outputpath_doc);
    	
    	//word文档部分
    	//List<String[]> dox_data = WordMatch.matchWord(inputpath_doc);
    	//testJDBC.ExcelUtils.WriteToFile(docfile, dox_data);
    	WordMatch.test();
    	
    	//句型部分
    	String read_xls[][] = null;
    	read_xls = testJDBC.ExcelUtils.ReadFromFile(inputpath_xls);
		Pattern p = Pattern.compile("([0-9]\\s*\\.).*?([0-9]\\s*\\.\\s*)");
		List<String[]> xls_data = new ArrayList<>();
    	for (int i = 0; i < read_xls.length; i++) {
			String string = read_xls[i][6].replace("\n", "").toString();
			//System.out.print(i+"------"+string);
			String line[] = new String[10];
			Matcher m = p.matcher(string);
			int start = 0;
			int index = 0;
			while(m.find(start))
			{
				String first = m.group(1);
				String second = m.group(2);

				//System.out.println("frank group:" + first + "/" + second);

				int sIndex = m.start() + first.length();
				int eIndex = m.end() - second.length();
				start = eIndex;
				line[index] = string.substring(sIndex, eIndex);
				//System.out.println("frank :" + string.substring(sIndex, eIndex));
				index ++;
			}
			xls_data.add(line);
		}
    	testJDBC.ExcelUtils.WriteToFile(xlsfile, xls_data);
    	
    	System.out.println("complete！");
    }
}