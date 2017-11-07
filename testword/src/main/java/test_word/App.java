package test_word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
    	
  
    	String path = "C:\\Users\\vargen\\Desktop\\";
        String fileName = "testword.docx";
        String filePath = path + fileName;
        //创建word  
        WordUtil.createWord(path,fileName);
        //写入数据  
        List<VocabularyBean> datas = new ArrayList<>();
        String data1 = "1.本文是以poi3.8读写2010word的文件;\r\n"
        		+ "2.输入字符串时，可以使用来\\r\\n换行;\r\n"
        		+ "3.这是一条测试功能用的字符串.";
        String data2 = "1.本文是以poi3.8读写2010word的文件;\r\n"
        		+ "2.输入字符串时，可以使用来\\r\\n换行;\r\n"
        		+ "3.这是又一条不同的测试功能用的字符串;";

        String data3 = "1.本文是以poi3.8读写2010word的文件;\r\n"
        		+ "2.输入字符串时，可以使用来\\r\\n换行;\r\n"
        		+ "3.这是又又一条测试功能用的字符串.";
        String data4 = "1.本文是以poi3.8读写2010word的文件;\r\n"
        		+ "2.输入字符串时，可以使用来\\r\\n换行;\r\n"
        		+ "3.这是又又又一条不同的测试功能用的字符串;";
        
        VocabularyBean data01 = new VocabularyBean("Perfer to","|ww|",data1,data2);
        
        VocabularyBean data02 = new VocabularyBean("be fond of","|ww|",data3,data4);

        datas.add(data01);
        datas.add(data02);
        WordUtil.writeDataDocx(filePath,datas);

        //String contentWord = WordUtil.readDataDocx(filePath);
        //System.out.println("word的内容为:\n"+contentWord);

        System.out.println( "complete!" );
    }
}
