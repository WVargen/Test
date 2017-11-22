package testRec;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testWordMatch {
	static Pattern pattern_content = Pattern.compile("([一二三四五六七八九]\\s*\\、).*?");
	static Pattern pattern_num = Pattern.compile("([0-9]\\s*\\.*\\、\\s*).*?([0-9]\\s*\\.*\\、\\s*)");
	
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
	
	
	public static List<String[]> matchWord(String inputpath) {
		List<String []> datas = new ArrayList<>();
    	
		String read_doc = WordUtil.readDataDocx(inputpath);//.replace("\n", "");
    	Matcher matcher_content = pattern_content.matcher(read_doc);
    	String rdoc_content = matchBegin(matcher_content, read_doc);
    	
    	String regex_title = "[一二三四五六七八九]\\s*\\、\\s*[\u0391-\uFFE5]{1,6}\n*";
    	String regex_num = "[0-9]+?\\s*\\、+\\s*\n*";
    	String[] arr = rdoc_content.split(regex_title);
    	for (String s : arr) {
    		if (s.isEmpty())continue;
    		String[] data = s.split(regex_num);
        	List<String> dList = new ArrayList<>();
        	for (String d:data) {
				if (d.isEmpty())continue;
				dList.add(d);
			}
        	datas.add(dList.toArray(new String [dList.size()])); 
            for(String d:data){
            	System.out.println("============"+d);
            }
        }		
		return datas;
		
	}
	
	public static String matchBegin(Matcher matcher,String string) {
		int start = 0;
		String ret = "";
		if(matcher.find(start))
		{
			String first = matcher.group(1);			
			int sIndex = matcher.start();//+ first.length();
			ret = string.substring(sIndex);				
			//System.out.println("frank :=============" + string.substring(sIndex));
		}
		return ret;		
		
	}
	public static Object[] matchBeginAndEnd(Matcher matcher,String string) {
		int start = 0;
		List<String> r = new ArrayList<>();
		String lines = "";
		while(matcher.find(start))
		{
			String first = matcher.group(1);
			String second = matcher.group(2);
			
			int sIndex = matcher.start()+ first.length();
			int eIndex = matcher.end() - second.length();
			start = eIndex;
			lines = string.substring(sIndex, eIndex);		
			r.add(lines);			
			System.out.println("frank :=============" + string.substring(sIndex, eIndex));
		}
		Object[] ret = r.toArray();
		return ret;		
	}
	
}