package testRec;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordMatch {
	public static String getPattern(String str, String pattern)
	    {
	    	Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);
	
			while(m.find())
			{
				try
				{
					return m.group(1);
				}
				catch (Exception e)
				{
					
				}
			}
			
			return null;
	    }
		
		 public static String readStringFromFile(String path)
	     {
	         byte[] bytes = readFromFile(path);
	
	         try
	         {
	             return new String(bytes, "utf-8");
	         }
	         catch (Exception e)
	         {
	
	         }
	
	         return null;
	     }
	
	     public static byte[] readFromFile(String path)
	     {
	         try
	         {
	             FileInputStream fis = new FileInputStream(new File(path));
	             ByteArrayOutputStream bos = new ByteArrayOutputStream();
	             byte[] buffer = new byte[1024 * 32];
	             int k = 0;
	             while ((k = fis.read(buffer)) > 0)
	             {
	                 bos.write(buffer, 0, k);
	                 bos.flush();
	             }
	
	             fis.close();
	             bos.close();
	
	             return bos.toByteArray();
	
	         }
	         catch (Exception e)
	         {
	             e.printStackTrace();
	         }
	
	         return null;
	     }
	     
	     public static void test()
	     {
	 		String str = readStringFromFile("src/main/java/testRec/test.txt");
	 		
	 		for (String s : str.split("\\n"))
	 		{
	 			System.out.println("frank 分行:" + s);
	 		}
	 		
	 		System.out.println("frank 正则切割" + getPattern(str, "(\\n一[、]{1}[\\s\\S]*?)\\n"));
	 		System.out.println("frank 正则切割" + getPattern(str, "(\\n1[、]{1}[\\s\\S]*?)\\n"));
	 		System.out.println("frank 正则切割" + getPattern(str, "(\\n4[、]{1}[\\s\\S]*?)\\nA[、]{1}"));


	     }
}

