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
import com.liangli.nj.bean.QuestionBean;
import com.liangli.nj.utils.Definition;
import com.liangli.nj.utils.ExcelUtils;
import com.liangli.nj.utils.Strings;
import com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor;

public class KnowledgePointRecognition {
	final static String knowledgePointPath = Definition.getClassPath() + "/1-6年级英语知识点修改.xlsx";
	
	final static int grammarIdColumn         = 1;
	final static int questionConditionColume = 11;
	final static int answerConditionColume   = 12;
	final static int answerExcludeColume     = 13;
	final static int conditionIsAnswer       = 14;
	
	final static int questionColumn = 1;
	final static int AColumn 		= 2;
	final static int BColumn 		= 3;
	final static int CColumn 		= 4;
	final static int DColumn 		= 5;
	final static int AnswerColumn 	= 6;
	
	public static void handleExerciseProblems(String inputFilePath) {
		String[][] exerciseFile = ExcelUtils.ReadFromFile(inputFilePath);
		List<QuestionBean> exerciseProblems = new ArrayList<>();

		for (int row = 1; row < exerciseFile.length; row++) {
			QuestionBean exerciseProblem = new QuestionBean();
			List<String> answerList = new ArrayList<>();
			for (int column = 0; column < exerciseFile[0].length; column++) {
				String cell = exerciseFile[row][column];
				switch (column) {
				case questionColumn:
					exerciseProblem.setQuestion(cell);
					break;
				case AColumn:
				case BColumn:
				case CColumn:
				case DColumn:
					answerList.add(cell);
					exerciseProblem.setAnswerList(answerList);
					break;
				case AnswerColumn:
					exerciseProblem.setAnswer(cell);
					break;
				default:
					break;
				}	
			}
			System.out.println(matchGrammarIdRow(exerciseProblem));
			exerciseProblems.add(exerciseProblem);
		}
	}
	
	private static List<String> matchGrammarIdRow(QuestionBean exerciseProblems) {
		List<String> grammarId = new ArrayList<>();
		
		String answerCondition = "", answerExcludeCondition = "", questionCondition = "", conditionIsTheAnswer = "";
		String[][] knowledgePointExcel = ExcelUtils.ReadFromFile(knowledgePointPath);
		List<String> answerStrList = exerciseProblems.getAnswerList();
		String questionStr = exerciseProblems.getQuestion();
		String answerStr   = exerciseProblems.getAnswer();
		
		for (int row = 1; row < knowledgePointExcel.length; row++) {
			answerCondition        = knowledgePointExcel[row][answerConditionColume];
			answerExcludeCondition = knowledgePointExcel[row][answerExcludeColume];
			questionCondition      = knowledgePointExcel[row][questionConditionColume];
			conditionIsTheAnswer   = knowledgePointExcel[row][conditionIsAnswer];
			
			boolean matchAnswerInclude = false, matchAnswerExclude = false, 
					matchQuestion = false, matchAnswerCondition = false;
			JSONArray answerConditionArray = JSON.parseArray(answerCondition);
			JSONArray excludeConditionArray = JSON.parseArray(answerExcludeCondition);
			
			for (int answerNum = 0; answerNum < answerStrList.size(); answerNum ++) {
				matchAnswerInclude = matchAnswerPattern(answerStrList.get(answerNum), answerConditionArray);
				matchAnswerExclude = matchAnswerPattern(answerStrList.get(answerNum), excludeConditionArray);
				matchQuestion      = matchQuestionPattern(questionStr, questionCondition);
				if (conditionIsTheAnswer.equalsIgnoreCase("yes") && 
					answerStrList.equals(answerStr)) {
					matchAnswerCondition = true;
				}else if (conditionIsTheAnswer.isEmpty() || conditionIsTheAnswer == null) {
					matchAnswerCondition = true;
				}
				if (matchAnswerInclude && !matchAnswerExclude &&
					matchQuestion && matchAnswerCondition) 
					grammarId.add(knowledgePointExcel[row][grammarIdColumn]);
			}	
		}
		return grammarId;
	}
	
	private static boolean matchAnswerPattern(String answerStr, JSONArray conditions) {
		boolean matchPattern = false;
		if (conditions != null && !conditions.isEmpty()) {
			for (Object condition : conditions) {
				String match = Strings.getPattern(answerStr, condition.toString());
				if (match != null && !match.isEmpty()) matchPattern = true;
			}
		}
		return matchPattern;
	}
	
	private static boolean matchQuestionPattern(String question, String questionCondition) {
		if (questionCondition != null && !questionCondition.isEmpty()) {
			questionCondition = questionCondition
							   .substring(questionCondition.indexOf("(") + 1, questionCondition.indexOf(")"));
			String words[] = questionCondition.split("\\|");
			for (String word : words) {
				boolean match = Strings.getPattern(question, word) != null && 
								!Strings.getPattern(question, word).isEmpty();
				if (match) return true;
			}
		}else 
			return true;
		return false;
	}
}
