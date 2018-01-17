package com.liangli.nj.mathmethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class FourArithmeticOperation {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		/*
		 * 获取参数
		 */
		List<Long> params = gBean.getParams(), miniparams = gBean.getMinparams();
		int sign = gBean.getSigns().get(0);
		List<String> type = gBean.getUnit();//有无括号	
		/*
		 * 产生操作数列表和操作符列表
		 */
		List<Integer> operatorNumList = new ArrayList<>();
		List<Float> ParamsList = new ArrayList<>();
		
		int paramCount = MathUtils.generateIntegerRandomNum(3, params.size() - 1);//操作数个数，最少3个
		
		for (int i = 0; i < paramCount; i++) {
			float param = 0;
			if (type.contains("整数")) { //如果需要产生整数
				param = MathUtils.generateIntegerRandomNum(miniparams.get(i), params.get(i));
			}else if (type.contains("小数")) { // 如果需要产生小数
				int decimalDigitsNum = MathUtils.generateIntegerRandomNum(2, 3);//小数长度
				param = MathUtils.generateDecimalRandomNum(miniparams.get(i), params.get(i), decimalDigitsNum).floatValue();
			}
			ParamsList.add(param);
			operatorNumList.add(MathUtils.generateIntegerRandomNum(0, 1));
		}
		/*
		 * 根据sign产生表达式,0为计算，1为简易方程	
		 */
		String questionStr = generateExpression(ParamsList, operatorNumList, type);
		operatorNumList.remove(operatorNumList.size() - 1);
		/*
		 * 计算答案
		 */
		float answer = generateAnswer(questionStr);
		String answerStr = (int)answer + "";
		
		if (!checkAnswerOK(answer, params, miniparams, type)) {
			return generator(gBean);
		}else {
			if (sign == 1) {
				List<String> parameters = new ArrayList<String>(Arrays.asList(questionStr
						.replace("(", "").replace(")", "").split("(?:\\+|-|x|÷|=)")));
				int paramNum = MathUtils.generateIntegerRandomNum(0, parameters.size() - 1);
				String parameter = parameters.get(paramNum);
				questionStr = questionStr.replace(parameter, "X") + (int)answer;
				answerStr = "答：X=" + parameter;
				mathQuestionBean.setAnswer(answerStr);
				mathQuestionBean.setQuestion(questionStr);
			}else {
				mathQuestionBean.setAnswer(answerStr);
				mathQuestionBean.setQuestion(questionStr);
			}
			return mathQuestionBean;
	
		}		
	}
	/*
	 * 检查answer
	 */
	private static boolean checkAnswerOK(float answer,List<Long> params, List<Long> miniparams,List<String> type) {
		boolean isAnswerOk = false;
		if (type.contains("整数")) { //整数
			if (answer >= miniparams.get(miniparams.size() - 1) && 
				answer <= params.get(params.size() - 1) && 
				checkAnswerInteger(answer)) {
				isAnswerOk = true;
			}
		}else if (type.contains("小数")) { //小数
			if (answer >= miniparams.get(miniparams.size() - 1) && 
					answer <= params.get(params.size() - 1)) {
				isAnswerOk = true;
			}
		}
		return isAnswerOk;
	}
	/*
	 * 判断答案是否为正整数
	 */
	private static boolean checkAnswerInteger(float answerFloat) {
		Integer answer = (int) answerFloat;
		if (answerFloat % answer != 0) {
			return false;
		}
		return true;
	}
	/*
	 * 根据type生成表达式
	 */
	private static String generateExpression(List<Float> ParamsList, List<Integer> operatorNumList,List<String> type) {
		int paramCount = ParamsList.size();
		int leftBrackets = 0;
		int rightBrackets = paramCount - 1;
		while (leftBrackets == 0 && rightBrackets == paramCount - 1) {
			leftBrackets = MathUtils.generateIntegerRandomNum(0, paramCount - 2);
			rightBrackets = MathUtils.generateIntegerRandomNum(leftBrackets + 1, paramCount - 1);
		}
		
		boolean brackets = false;
		if (type.contains("无括号")) brackets = false;
		else if (type.contains("有括号")) brackets = true;
		String questionStr = "";
		/*
		 * 生成表达式
		 */
		for (int i = 0; i < paramCount; i++) {
			float parameter = ParamsList.get(i);
			int operator = operatorNumList.get(i);
			if (brackets && i == leftBrackets) {
				questionStr += "(";
			}
			if (type.contains("整数")) questionStr += (int)parameter + "";
			else if (type.contains("小数")) questionStr += parameter + "";
			
			if (brackets && i == rightBrackets) {
				questionStr += ")";
			}
			
			questionStr = generateBasicExpression(questionStr, operator, brackets);
		}
		return questionStr = questionStr.substring(0, questionStr.length() - 1) + "=";
	}
	/*
	 * 生成表达式
	 */
	private static String generateBasicExpression(String expression, int sign, boolean brackets) {
		String generatedExpression = expression;
		
		if (sign == 0) {
			generatedExpression += "+"; 
		}else if (sign == 1) {
			generatedExpression += "-";
		}else if (sign == 2) {
			generatedExpression += "x";
		}else if (sign == 3) {
			generatedExpression += "÷";
		}
		
		return generatedExpression;	
	}
	
	/*
	 * 计算根据Type产生表达式
	 */
	private static float generateAnswer(String questionStr) {
		float generatedAnswer = 0;
		List<String> operations = new ArrayList<>();
		questionStr = questionStr.substring(0, questionStr.indexOf("="));
		if (questionStr.indexOf("(") == -1) {
			return generateAnswerWithoutBrackets(questionStr);			
		}else {
			operations.add(questionStr.substring(0, questionStr.indexOf("(")));
			operations.add(questionStr.substring(questionStr.indexOf("(") + 1, questionStr.indexOf(")")));
			operations.add(questionStr.substring(questionStr.indexOf(")") + 1));

			float answerWithBrackets = generateAnswerWithoutBrackets(operations.get(1));
			if (checkAnswerInteger(answerWithBrackets) && answerWithBrackets >= 0) {
				String generatedQuestionStr = operations.get(0) + (int)answerWithBrackets + operations.get(2) + "=";		
				generatedAnswer = generateAnswerWithoutBrackets(generatedQuestionStr);
				return generatedAnswer;
			}else {
				return 0;
			}
		}	
	}
	
	/*
	 * 计算表达式
	 */
	private static float generateAnswerWithoutBrackets(String questionStr) {
		List<String> parameters, operators;
		float generatedAnswer = 0;

		parameters = new ArrayList<String>(Arrays.asList(questionStr.split("(?:\\+|-|x|÷|=)")));
		operators = new ArrayList<String>(Arrays.asList(questionStr.split("[0-9\\.=]+")));
		operators.remove(0);

		int opSize = operators.size();
		for (int i = 0; i < opSize;) {
			if (operators.get(i).equals("x")||
				operators.get(i).equals("÷")) {

				BigDecimal parameter1 = new BigDecimal(parameters.get(i));
				BigDecimal parameter2 = new BigDecimal(parameters.get(i+1));
				String operator = operators.get(i);
				
				generatedAnswer = calculation(parameter1, parameter2, operator);
				String calculatedParam = generatedAnswer + "";				
				parameters.remove(i);
				parameters.remove(i);
				parameters.add(i, calculatedParam);				
				operators.remove(i);				
				opSize--;
			}else {
				i++;
			}
		}
		
		while (!operators.isEmpty()) {
			String operator = operators.get(0);
			BigDecimal parameter1 = new BigDecimal(parameters.get(0));
			BigDecimal parameter2 = new BigDecimal(parameters.get(1));
			parameters.remove(0);
			parameters.remove(0);
				
			generatedAnswer = calculation(parameter1, parameter2, operator);
			String calculatedParam = generatedAnswer + "";
			
			parameters.add(0, calculatedParam);
			operators.remove(0);
			
		}

		return generatedAnswer;	
	}
	
	private static float calculation(BigDecimal parameter1, BigDecimal parameter2, String sign) {
		BigDecimal answer = new BigDecimal(0);
		switch (sign) {
		case "+":
			answer = parameter1.add(parameter2);
			break;
		case "-":
			answer = parameter1.subtract(parameter2);
			break;
		case "x":
			answer = parameter1.multiply(parameter2);
			break;
		case "÷":
			answer = parameter1.subtract(parameter2);
			break;
		default:
			break;
		}
		return answer.floatValue();
	}
	
}
