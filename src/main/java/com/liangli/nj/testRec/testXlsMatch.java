package com.liangli.nj.testRec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import com.liangli.nj.bean.SentenceBean;

public class testXlsMatch{
	private static final int sentence_uuid = 6;
	private static final int sentence_content = 8;
	private static final int sentence_answer = 13;
	
	static String pattern_sen_content = "^([\u0391-\uFFE5]+[。]*\\s*\\n*[\u0391-\uFFE5]*\\s*\\n*)";
	static String pattern_num = "([0-9]\\s*\\.\\s*).*?";
	
	static List<String> SentenceContents = new ArrayList<>();
	static List<String> SentenceAnswers = new ArrayList<>();
	static List<String> SentenceTitles = new ArrayList<>();
	
	static Map<String, SentenceBean> SentenceMap = new LinkedHashMap<>();
		
	public static List<String[]> matchxls(String[][] inputxls) {
		List<String[]> output = new ArrayList<>();
		for (String[] inputstrings : inputxls) {
			SentenceContents.add(inputstrings[sentence_content]);
			SentenceAnswers.add(inputstrings[sentence_answer]);
		}
		Map<String, String> hc = handleContent();
		Map<String, String> ha = handleAnswer();
		
		handleQueAndAns(hc, ha);
		String[] input = null;
		for (String uuid : SentenceMap.keySet()){
			SentenceBean SenBean = SentenceMap.get(uuid);
			String Queuuid = SenBean.getQueuuid();
			
			input = inputxls[Integer.parseInt(Queuuid)];
			List<String> sentenceOutput = new ArrayList<>();
			for (int i = 0; i < input.length; i++) {
				switch (i) {
				case sentence_uuid:
					sentenceOutput.add(uuid);
					break;
				case sentence_content:
					sentenceOutput.add(SenBean.getQuestions());
					break;
				case sentence_answer:
					sentenceOutput.add(SenBean.getAnswer());
					break;
				default:
					sentenceOutput.add(i, input[i]);
				}					
			}	
			output.add(sentenceOutput.toArray(new String[sentenceOutput.size()]));					
		}
		return output;
		
	}
	private static Map<String, String> handleContent() {
		Map<String, String> SentenceContent = new LinkedHashMap<>();
		//大题目编号
		Integer Que_uuid = 1;
		//小题整体编号
		for (String sc : SentenceContents) {
			Map<String, String> SentenceContentTemp = new LinkedHashMap<>();
			SentenceContentTemp = testWordMatch.getPattern(sc, pattern_sen_content);
			Set<String> keyset = SentenceContentTemp.keySet();
			
			for (String key : keyset) {
				String value = SentenceContentTemp.get(key);
				String keytemp = Que_uuid.toString()+ "." + key;
				SentenceTitles.add(keytemp);
				
				SentenceContentTemp.clear();
				SentenceContentTemp.put(keytemp, value);
				Que_uuid++;				
			}
			SentenceContent.putAll(SentenceContentTemp);
		}
		return SentenceContent;
	}
	
	private static Map<String, String> handleAnswer() {
		Map<String, String> SentenceAnswer = new LinkedHashMap<>();
		Integer Que_uuid = 1;
		for(String key:SentenceTitles){
			SentenceAnswer.put(key, SentenceAnswers.get(Que_uuid));
			Que_uuid++;
		}
		return SentenceAnswer;
	}
	
	private static void handleQueAndAns(Map<String, String> content, Map<String, String> answer) {
		Map<String, String> ContentTemp = new LinkedHashMap<>();
		Integer uuid = 1;
		for (String st : SentenceTitles) {
			ContentTemp = testWordMatch.getPattern(content.get(st), pattern_num);
			
			int i = 0;
			List<String> answerTemp = Arrays.asList(answer.get(st).split("\\|"));
			
			for (String ct : ContentTemp.keySet()) {
				SentenceBean sentenceBean = new SentenceBean();
				sentenceBean.setQueuuid(st.substring(0,st.indexOf(".")));
				sentenceBean.setQuestions(ContentTemp.get(ct));
				sentenceBean.setAnswer(answerTemp.get(i));
				SentenceMap.put(uuid.toString(), sentenceBean);
				uuid++;
				i++;
			}
		}
	}
}