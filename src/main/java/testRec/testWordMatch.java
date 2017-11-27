package testRec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testWordMatch {
	static Pattern pattern_content = Pattern.compile("([一二三四五六七八九]\\s*\\、).*?");
	static Pattern pattern_num = Pattern.compile("([0-9]\\s*\\.*\\、\\s*).*?([0-9]\\s*\\.*\\、\\s*)");
	
	public static Map<String, String> getPattern(String str, String pattern)
    {
		int start = 0;
		List<String> titles = new ArrayList<>();
		List<Integer> startwith = new ArrayList<>();
		Map<String, String> piece = new LinkedHashMap<>();
		
    	Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);

		while(m.find(start))
		{
			try
			{
				String title = m.group(1);
				startwith.add(m.start());
				start = m.end();
				//System.out.println("start with" + startwith.get(index));
				titles.add(title);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		startwith.add(str.length());
		for (int i = 0; i < titles.size(); i++) {
			String content = str.substring(startwith.get(i) + titles.get(i).length(), startwith.get(i+1));
			piece.put(titles.get(i), content);
		}
		//System.out.println(piece);
		return piece;
    }
	
	
	public static List<String> matchWord(String inputdoc) {
		Map<String, String> datas = new LinkedHashMap<>();
    	
		String read_doc = inputdoc;//.replace("\n", "");
		datas = getPattern(read_doc, "\\n([一二三四五六七八九][、.]{1}[\\s\\S]*?\\n)");
		
		Map<String, String> data = new LinkedHashMap<>();
		
		for (String key : datas.keySet()) {
		    String value = datas.get(key);
		    data = getPattern(value, "(\\n[0-9]{1,2}[、.]+)[\\s\\S]*?\\n");
		    System.out.println(data);
		}
//		datas = getPattern(read_doc, "\\n([一二三四五六七八九][、.]{1}[\\s\\S]*?)\\n+([一二三四五六七八九][、.]{1})");
//		System.out.println("frank 正则切割" + getPattern(read_doc, "\\n([一二三四五六七八九][、.]{1}[\\s\\S]*?)\\n"));
		
//    	
//    	String regex_title = "[一二三四五六七八九]\\s*\\、\\s*[\u0391-\uFFE5]{1,6}\n*";
//    	String regex_num = "[0-9]+?\\s*\\、+\\s*\n*";
		
		//		
//    	for (String s : arr) {
//    		if (s.isEmpty())continue;
//    		String[] data = s.split(regex_num);
//        	List<String> dList = new ArrayList<>();
//        	for (String d:data) {
//				if (d.isEmpty())continue;
//				dList.add(d);
//			}
//        	datas.add(dList.toArray(new String [dList.size()])); 
//            for(String d:data){
//            	System.out.println("============"+d);
//            }
//        }		
		return null;
		
	}
	
	public static String matchBegin(Matcher matcher,String string) {
		int start = 0;
		String ret = "";
		if(matcher.find(start))
		{
			//String first = matcher.group(1);			
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