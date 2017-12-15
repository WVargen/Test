package testRec;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	
	public static List<String[]> matchWord(String inputdoc) {
		Map<String, String> datas = new LinkedHashMap<>();
    	
		String read_doc = inputdoc;//.replace("\n", "");
		datas = getPattern(read_doc, "\\n([一二三四五六七八九][、.]{1}[\\s\\S]*?\\n)");
		
		List<String []> output = new ArrayList<>(); 
		for (String key : datas.keySet()) {
			String cells [] = new String[10];
			List<String> temp = new ArrayList<>();
			if(key.contains("单项选择")){
			    String value = datas.get(key);
			    String que_pattern = "\\n([0-9]{1,2}[、.]{1}[\\s\\S]*?)\\n+\\s*A[、]{1}";
			    String opt_pattern = "\\s+[A-Za-z]{1}[、.]{1}\\s*";
			    Map<String, String> questions = getPattern(value, que_pattern);
			    for(String question:questions.keySet()){
					cells = null;
					temp.clear();
			    	temp.add(question.substring(0, question.indexOf("、")));
			    	temp.add("1");
			    	temp.add(question.substring(question.indexOf("、")+1, question.length()-1));
			    	String options = questions.get(question).substring(4);
			    	temp.addAll(Arrays.asList(options.split(opt_pattern)));
				    cells = temp.toArray(new String[temp.size()]);
				    output.add(cells);
			    }

			}
			else if (key.contains("完形填空")) {
				String value = datas.get(key);
			    String que_pattern = "\\n([0-9]{1,2}[、.]{1}[\\s\\S]*?)\\n+\\s*1[、.]{1}";
			    String opts_pattern = "\\s+[1-9]{1}[、.]{1}\\s*";
			    String opt_pattern = "\\s+[0-9]{0,2}[、.]{0,1}[A-Za-z]+[、.]{1}\\s+";
			    Map<String, String> questions = getPattern(value, que_pattern);
			    for(String question:questions.keySet()){
					cells = null;
					temp.clear();
					String article = question.substring(question.indexOf("、")+1, question.length()-1);
					temp.add(question.substring(0, question.indexOf("、")));
					temp.add("3");
					temp.add(article);
					
					String [] options = questions.get(question).substring(4).split(opts_pattern);
					String [] option = options[0].split(opt_pattern);
					for (int i = 0; i < option.length; i++) {
						if(option[i] != null && !option[i].isEmpty())temp.add(option[i]);
						System.out.println(option[i]);
					}
					cells = temp.toArray(new String[temp.size()]);
				    output.add(cells);
				    
			    	for (int i = 1; i < options.length; i++) {
			    		temp.clear();
			    		temp.add(question.substring(0, question.indexOf("、")));
			    		temp.add("3");
			    		temp.add("");
				    	String [] que_options = options[i].split(opt_pattern);
						for (int opts = 0; opts < que_options.length; opts++) {
							if(que_options[opts] != null && !que_options[opts].isEmpty())temp.add(que_options[opts]);
							System.out.println(que_options[opts]);
						}
						cells = temp.toArray(new String[temp.size()]);
					    output.add(cells);
					}
			    	
				    cells = temp.toArray(new String[temp.size()]);
				    output.add(cells);
			    }
			}

		    //System.out.println(data);
		}

		return output;
		
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