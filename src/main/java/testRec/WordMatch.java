package testRec;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WordMatch {
	static Pattern pattern_content = Pattern.compile("([一二三四五六七八九]\\s*\\、).*?");
	static Pattern pattern_num = Pattern.compile("([0-9]\\s*\\.*\\、\\s*).*?([0-9]\\s*\\.*\\、\\s*)");
	
	public static List<String[]> matchWord(String inputpath) {
		List<String []> datas = new ArrayList<>();
		String[] data = null;
    	
		String read_doc = WordUtil.readDataDocx(inputpath);//.replace("\n", "");
    	Matcher matcher_content = pattern_content.matcher(read_doc);
    	String rdoc_content = matchBegin(matcher_content, read_doc);
    	
    	String regex_title = "[一二三四五六七八九]\\s*\\、\\s*[\u0391-\uFFE5]{2,6}\n+";
    	String regex_num = "[0-9]\\s*\\、+\\s*";
    	String[] arr = rdoc_content.split(regex_title);
    	for (String s : arr) {	
    		data = s.split(regex_num);
    		datas.add(data);
    		
//    		Matcher matcher_num = pattern_num.matcher(s);
//    		datas.add(matchBeginAndEnd(matcher_num,s));
//          System.out.println("============"+s);
        }
        for(String d:data){
        	System.out.println("============"+d);
        }
//    	Matcher matcher_1 = pattern_1.matcher(read_doc);
//		datas = match(matcher_1, read_doc);
//		for (int i = 0; i < datas.size(); i++) {
//			Matcher matcher_2 = pattern_2.matcher(datas.get(i));
//			data = match(matcher_2, datas.get(i));
//			System.out.println(i+"===="+"\n"+data);
//		}
		
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