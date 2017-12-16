package testRec;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.liangli.nj.bean.SentenceBean;

public class testXlsMatch{
	public static final int sentence_uuid = 6;
	public static final int sentence_content = 8;
	
	static String pattern_sen_content = "^([\u0391-\uFFE5]+[。]*\\s*\\n*[\u0391-\uFFE5]*\\s*\\n*)";
	static String pattern_num = "([0-9]\\s*\\.\\s*).*?";
	
	public static List<String[]> matchxls(String[][] inputxls) {
		
		List<String> SentenceContents = new ArrayList<>();
		for (String[] inputstrings : inputxls) {
			SentenceContents.add(inputstrings[sentence_content]);
		}
		
		Map<String, String> SentenceContent = new LinkedHashMap<>();
		Map<String, SentenceBean> ValueContent = new LinkedHashMap<>();
		Integer uuid = 0;
		
		for (String string : SentenceContents) {
			Map<String, String> SentenceContentTemp = new LinkedHashMap<>();
			SentenceContentTemp = testWordMatch.getPattern(string, pattern_sen_content);
			Set<String> keyset = SentenceContentTemp.keySet();
			
			for (String key : keyset) {
				String value = SentenceContentTemp.get(key);
				String keytemp = uuid.toString()+ "." + key;
				
				Map<String, String> ValueContentTemp = new LinkedHashMap<>();
				ValueContentTemp = testWordMatch.getPattern(value, pattern_num);
				
				for (String ValueString : ValueContentTemp.keySet()) {
					Integer ValueStringTemp = uuid + Integer.parseInt(ValueString.trim().substring(0, 1));
					System.out.println(ValueStringTemp);
					//System.out.println(ValueContentTemp.get(ValueString));
				}
				
				SentenceContentTemp.clear();
				SentenceContentTemp.put(keytemp, value);
				uuid++;
				
			}
			SentenceContent.putAll(SentenceContentTemp);
		}
		//System.out.println(SentenceContent);
		return null;
		
	}
}