package com.liangli.nj.testRec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liangli.nj.bean.QuestionBean;

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
		//标题
		String [] title = {"uuid","type","question","a","b","c","d","answer","explain"};
		List<String> titles = new ArrayList<>();
		titles.addAll(Arrays.asList(title));

		List<String> choices = new ArrayList<>();	
		//遍历匹配出的key
		for (String key : datas.keySet()) {
			//cells用于输出，temp为临时记录变量
				
			if(key.contains("单项选择")|| key.contains("选择题")){
				
				//System.out.println(key);
				String value = datas.get(key);
			    titles.add(value);
			    choices.add(value);
			}
		}	
		String cells [] = new String[10];
		List<String> temp = new ArrayList<>();
			
		String que_pattern = "\\n([0-9]{1,2}[、.]{1}[\\s\\S]*?)\\n+\\s*A[、]{1}";
	    String opt_pattern = "\\s+[A-Za-z]{1}[、.]{1}\\s*";
	    String ans_pattern = "\\n([0-9]{1,2}[、.]{1}\\s*)(【答案】)[\\s\\S]*?\\n+\\s*";
	    
	    Map<String, String> questionsMap = new HashMap<>();
	    Map<String, QuestionBean> answerMap = new HashMap<>();
	    
		for (String choice_ans:choices){
				
			Map<String, String> answers = getPattern(choice_ans, ans_pattern);
			for (String answer : answers.keySet()) {	
				String uuid = answer.substring(0, answer.indexOf("、"));	
				String answerStr_pattern = "(【*答案】*)[\\s\\S]*?\\n+\\s*(【考点】)+";
			    Map<String, String> answerStrMap = getPattern(answers.get(answer), answerStr_pattern);
			    QuestionBean qBean = new QuestionBean();
			    String answerStr = answerStrMap.get("【答案】").substring(0, answerStrMap.get("【答案】").indexOf("【")).replace(" ", "");
			    qBean.setAnswer(answerStr);
			    	
			    String explainStr_pattern = "(【*解析】*)[\\s\\S]*?\\n+\\s*(【点评】)*";
			    Map<String, String> explainStrMap = getPattern(answers.get(answer), explainStr_pattern);

			    String explainStr = explainStrMap.get("【解析】");
			    qBean.setExplain(explainStr);
			    
			    answerMap.put(uuid, qBean);
			    	
			}
		}
			
		for (String choice_que:choices){ 
			Map<String, String> questions = getPattern(choice_que, que_pattern);
			for(String question:questions.keySet()){
				cells = null;
				temp.clear();
					
				String uuid = question.substring(0, question.indexOf("、"));
				String type = "1";
				String questionStr = question.substring(question.indexOf("、")+1, question.length()-1);
				questionStr = questionStr.replaceAll("    ", "____");
					
				questionsMap.put(uuid, questionStr);
					
			    temp.add(uuid);
			    temp.add(type);
			    temp.add(questionStr); 
			    String options = questions.get(question).substring(4);
			    
			    temp.addAll(Arrays.asList(options.split(opt_pattern)));
			    
			    for(String id:answerMap.keySet()){
			    	if (uuid.equalsIgnoreCase(id)) {
						temp.add(answerMap.get(id).getAnswer());
						temp.add(answerMap.get(id).getExplain());
					}
			    }
			    cells = temp.toArray(new String[temp.size()]);
				output.add(cells);
			 }
			      
		}
//			else if (key.contains("完形填空")) {
//				String value = datas.get(key);
//			    String que_pattern = "\\n([0-9]{1,2}[、.]{1}[\\s\\S]*?)\\n+\\s*1[、.]{1}";
//			    String opts_pattern = "\\s+[1-9]{1}[、.]{1}\\s*";
//			    String opt_pattern = "\\s+[0-9]{0,2}[、.]{0,1}[A-Za-z]+[、.]{1}\\s+";
//			    Map<String, String> questions = getPattern(value, que_pattern);
//			    for(String question:questions.keySet()){
//					cells = null;
//					temp.clear();
//					String article = question.substring(question.indexOf("、")+1, question.length()-1);
//					temp.add(question.substring(0, question.indexOf("、")));
//					temp.add("3");
//					temp.add(article);
//					
//					String [] options = questions.get(question).substring(4).split(opts_pattern);
//					String [] option = options[0].split(opt_pattern);
//					for (int i = 0; i < option.length; i++) {
//						if(option[i] != null && !option[i].isEmpty())temp.add(option[i]);
//						System.out.println(option[i]);
//					}
//					cells = temp.toArray(new String[temp.size()]);
//				    output.add(cells);
//				    
//			    	for (int i = 1; i < options.length; i++) {
//			    		temp.clear();
//			    		temp.add(question.substring(0, question.indexOf("、")));
//			    		temp.add("3");
//			    		temp.add("");
//				    	String [] que_options = options[i].split(opt_pattern);
//						for (int opts = 0; opts < que_options.length; opts++) {
//							if(que_options[opts] != null && !que_options[opts].isEmpty())temp.add(que_options[opts]);
//							System.out.println(que_options[opts]);
//						}
//						cells = temp.toArray(new String[temp.size()]);
//					    output.add(cells);
//					}
//			    	
//				    cells = temp.toArray(new String[temp.size()]);
//				    output.add(cells);
//			    }
//			}
//
//		    //System.out.println(data);		
		output.add(0,titles.toArray(new String[titles.size()]));
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