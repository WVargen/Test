package com.liangli.nj.testRec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.liangli.nj.utils.Definition;
import com.liangli.nj.utils.ExcelUtils;

public class KnowledgePointRecognition {
	final static String knowledgePointPath = Definition.getClassPath() + "/1-6年级英语知识点.xlsx";
	final static int grammarIdColumn = 1;
	
	public static void handleExerciseProblems(String inputFilePath) {
		String[][] exerciseFile = ExcelUtils.ReadFromFile(inputFilePath);
		int questionIndex = 0;
		List<Integer> answerIndexList = new ArrayList<>();
		for (int i = 0; i < exerciseFile[0].length; i++) {
			String cell = exerciseFile[0][i];
			if (cell.equals("question")) {			
				questionIndex = i;
			}else if (cell.equals("a")||cell.equals("b")||
					  cell.equals("c")||cell.equals("d")) {
				answerIndexList.add(i);
			}
		}
		
		for (int row = 1; row < exerciseFile.length; row++) {
			
			List<String> answerGrammarIDList = new ArrayList<>();
			String questionStr = exerciseFile[row][questionIndex];
			for (int i = 0; i < answerIndexList.size(); i++) {
				String answer[] = exerciseFile[row][answerIndexList.get(i)].split(",");
				List<String> grammarIDList = new ArrayList<>();
				for (int answerIndex = 0; answerIndex < answer.length; answerIndex++) {
					String grammarID = matchGrammarIdRow(questionStr, answer[answerIndex]);
					if (!grammarIDList.contains(grammarID)) {
						grammarIDList.add(grammarID);
					}	
				}
				String grammarIdStr = "";
				for (String str : grammarIDList) {
					if (!str.isEmpty()) {
						grammarIdStr += str + "|";
					}
				}
				
				while (grammarIdStr.contains("||")) {
					grammarIdStr = grammarIdStr.replace("||", "");
				}
				answerGrammarIDList.add(grammarIdStr);
			}
			System.out.println(answerGrammarIDList);
		}
		System.out.println("complete!");
	}
	
	private static String matchGrammarIdRow(String question, String answer) {
		String[][] fileContent = ExcelUtils.ReadFromFile(knowledgePointPath);
		
		String grammarID = "";
		List<Integer> grammarRowList = new ArrayList<>();
		Map<Integer, JSONArray> answerMatchConditions = getConditionsByName("abcd任一符合条件");
		Map<Integer, JSONArray> answerExcludeConditions = getConditionsByName("abcd排除条件");
		Map<Integer, JSONArray> questionConditions = getConditionsByName("题目符合条件");
				
		String questionStr = question.toLowerCase();
		String answerStr = answer.toLowerCase();
		boolean questionMatch = false;
		for (Integer questionConditionIndex : questionConditions.keySet()){
			String questionCondition[] = questionConditions.get(questionConditionIndex).getString(0)
										.trim().replace("(","").replace(")", "").split("\\|");
			
			for (String condition : questionCondition) {
				if (questionStr.indexOf(condition) != -1) {
					grammarRowList.add(questionConditionIndex);
					questionMatch = true;
				}
			}
			//System.out.println(grammarRowList);
		}
		for (Integer answerConditionIndex : answerMatchConditions.keySet()) {
			JSONArray answerMatchCondition = answerMatchConditions.get(answerConditionIndex);
			JSONArray answerExcludeCondition = null;
			if (answerExcludeConditions.containsKey(answerConditionIndex)) {
				answerExcludeCondition = answerExcludeConditions.get(answerConditionIndex);

				for (int index = 0; index < answerExcludeCondition.size(); index++) {
					Pattern answerExcludePattern = Pattern.compile(answerExcludeCondition.get(index).toString());
					Matcher answerExclude = answerExcludePattern.matcher(answerStr);
					if (answerExclude.find()) {
						return "";
					}
				}	
			}
			
			for (int index = 0; index < answerMatchCondition.size(); index++) {
				Pattern answerMatchPattern = Pattern.compile(answerMatchCondition.get(index).toString());
				Matcher answerMatch = answerMatchPattern.matcher(answerStr);
				
				while (answerMatch.find()) {
					if (questionMatch && grammarRowList.contains(answerConditionIndex)) {
						grammarID += fileContent[answerConditionIndex][grammarIdColumn] + "|";
					}else if (!questionMatch) {
						grammarID += fileContent[answerConditionIndex][grammarIdColumn] + "|";
					}
				}
			}		
		}
		return grammarID;
	}
	
	private static Map<Integer, JSONArray> getConditionsByName(String name) {		
		Map<Integer, String> conditions = getContentByName(knowledgePointPath, name);

		Map<Integer, JSONArray> map = new LinkedHashMap<>();
		for (Integer rowIndex : conditions.keySet()) {
			String condition = conditions.get(rowIndex);
			JSONArray answerConditionList = new JSONArray();
			if (!(condition == null || condition.isEmpty())) {
				answerConditionList = JSON.parseArray(condition);
				map.put(rowIndex, answerConditionList);
			}
		}	
		return map;
	}
	
	private static Map<Integer, String> getContentByName(String filePath, String columnName) {
		Map<Integer, String> output = new LinkedHashMap();
		String[][] fileContent = ExcelUtils.ReadFromFile(filePath);
		
		int index = 0;		
		for (int i = 0; i < fileContent[0].length; i++) {
			String cell = fileContent[0][i];
			if (cell.equals(columnName)) {			
				index = i;
			}
		}
		
		for (int row = 1; row < fileContent.length; row++) {
			output.put(row, fileContent[row][index]);
		}
		return output;
	}
}
