package test_word;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


public class WordUtil {
    
	public static void createWord(String path,String fileName){
         File file=new File(path);
         if(!file.exists()) file.mkdirs(); 
         
         XWPFDocument document = initDocx();
         OutputStream stream=null;
         try {
             stream = new FileOutputStream(new File(file, fileName));
             document.write(stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(stream != null);
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	public static XWPFDocument initDocx() {
		XWPFDocument doc = new XWPFDocument();
//		XWPFHeaderFooterPolicy hfPolicy = doc.getHeaderFooterPolicy();
//		if (hfPolicy == null) {
//			try {
//				hfPolicy = new XWPFHeaderFooterPolicy(doc);
//			} catch (IOException | XmlException e) {
//				e.printStackTrace();
//			}
//		}
		Date now = new Date();
		SimpleDateFormat nowformat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		String nowstr = nowformat.format(now).toString();
		new ParagraphUtil(doc, true, false, 14, 0, 0, ParagraphAlignment.CENTER, "导出结果", "黑体").onHandleParagraph(1);
		new ParagraphUtil(doc, false, false, 11, 0, 0, ParagraphAlignment.LEFT, "导出时间：" + nowstr, "黑体").onHandleParagraph(1);
		//new ParagraphUtil(doc, false, false, 11, 0, 0, ParagraphAlignment.LEFT, "导出内容：", "黑体").onHandleParagraph(1);
		return doc;	
	}
    //向word中写入数据  

    public static void writeDataDocx(String path,List<VocabularyBean> datas) throws IOException{
        InputStream istream_init = null;
        OutputStream ostream_init = null;
        OPCPackage pack = null;
        XWPFDocument document = null;
        Integer i = 0;
        
        try {
			istream_init = new FileInputStream(path);
			pack = POIXMLDocument.openPackage(path);
			document = new XWPFDocument(pack);			
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();	
		} 
        
        
        for(VocabularyBean data : datas){
	        try {
	        	i++;
	        	System.out.println("正在导出"+i+"条记录...");
	        	XWPFDocument doc = new ParagraphUtil(document,data).handleParagraphs();        	
	            ostream_init = new FileOutputStream("temp.docx");
	            doc.write(ostream_init);            
	            handleWriteFile(path);
	            System.out.println("写入word成功！");           
	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally{
	            if(istream_init!=null){
	                try {
	                    istream_init.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if(ostream_init!=null){
	                try {
	                    ostream_init.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
        }
        System.out.println("共导出"+i+"条记录。");
    }

    public static void handleWriteFile(String path){
    	XWPFDocument document = null;
    	InputStream iStream = null;
    	OutputStream oStream = null;
    	File file = null;
    	FileInputStream fis = null;
		try {
			file = new File("temp.docx");
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	try {
			iStream = fis;
			oStream = new FileOutputStream(path);
			document = new XWPFDocument(iStream);
			document.write(oStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				iStream.close();
				oStream.close();
				file.delete();			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}   	
    }

    //读取数据 docx  
    public static String readDataDocx(String filePath) {
        String content="";
        InputStream istream=null;
        try {
            istream = new FileInputStream(filePath);

            XWPFDocument document = new XWPFDocument(istream);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            content = extractor.getText();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(istream != null){
                
            }
        }
        return content;
    }
}