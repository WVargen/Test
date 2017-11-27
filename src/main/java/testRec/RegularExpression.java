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
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegularExpression
{
    public static void main( String[] args ){
    	String inputpath_doc = "C:/Users/hp/Desktop/test/2017年广东中考英语.docx";
    	String outputpath_doc = "C:/Users/hp/Desktop/test/2.xlsx";
    	File docfile = new File(outputpath_doc);
    	
    	//word文档部分
    	String doc_read = WordUtil.readDataDocx(inputpath_doc);
    	List<String> doc_data = testWordMatch.matchWord(doc_read);
    	//System.out.println(doc_data);
    	//testJDBC.ExcelUtils.WriteToFile(docfile, dox_data);
    	
    	
    	System.out.println("complete！");
    }
}