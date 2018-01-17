package com.liangli.nj.mathmethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class DecimalBasicArithmetic {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> params = gBean.getParams(), miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();
		List<String> unit = gBean.getUnit();

		int sign = signs.get(0);
		if (signs.size() > 1) {
			sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));
		}
			
		BigDecimal parameter1,parameter2;		
		boolean isParametersOk = false, isAnswerOk = false;
		List<BigDecimal> parameters = null;
		for (String type : unit) {
			while (!isParametersOk) {
				parameters = generateParameters(params, miniparams, params.size() - 1);
				isParametersOk = checkParametersOk(parameters, type);			
			}
		}
		
		parameter1 = parameters.get(0);
		parameter2 = parameters.get(1);
		
		String questionStr = "";
		BigDecimal answerDec = new BigDecimal(0);
		
		if (sign == 0) {
			answerDec = parameter1.add(parameter2);
			questionStr =  parameter1 + "+" + parameter2 ;
		}else if (sign == 1) {
			answerDec = parameter1.subtract(parameter2);
			questionStr = parameter1 + "-" + parameter2 ;
		}else if (sign == 2) {
			answerDec = parameter1.multiply(parameter2);
			questionStr = parameter1 + "x" + parameter2 ;
		}else if (sign == 3) {
			try {
				answerDec = parameter1.divide(parameter2);
			} catch (Exception e) {
				return generator(gBean);
			}				
			questionStr = parameter1 + "÷" + parameter2 ;
		}
		
		isAnswerOk = checkAnswerOk(answerDec, params.get(params.size() - 1), miniparams.get(miniparams.size() - 1));		
		if (isAnswerOk) {
			mathQuestionBean = handleAnswerAccordingToType(answerDec, questionStr, unit);
			return mathQuestionBean;
		}
		
		return generator(gBean);
		
	}
	
	private static List<BigDecimal> generateParameters(List<Long> params, List<Long> miniparams, int count) {
		List<BigDecimal> parameters = new ArrayList<>();
		for (int i = 0; i < count; i++) {
			int decimalDigits = MathUtils.generateIntegerRandomNum(2, 3);
			BigDecimal parameter = MathUtils.generateDecimalRandomNum(miniparams.get(i), params.get(i), decimalDigits);
			parameters.add(parameter);
		}	
		return parameters;
	}
	
	private static boolean checkParametersOk(List<BigDecimal> parameters, String type){
		boolean isParametersOk = false;
		float param1Decimal = parameters.get(0).subtract(new BigDecimal(parameters.get(0).intValue())).floatValue(); 
		float param2Decimal = parameters.get(1).subtract(new BigDecimal(parameters.get(1).intValue())).floatValue(); 
		if (type.equals("退位")) {
			if (param1Decimal < param2Decimal) {
				isParametersOk = true;
			}
		}else if (type.equals("不退位")) {
			if (param1Decimal > param2Decimal) {
				isParametersOk = true;
			}
		}else if (type.equals("任意小数")) {
			isParametersOk = true;
		}else {
			isParametersOk = true;
		}
		
		return isParametersOk;
	}
	
	private static MathQuestionBean handleAnswerAccordingToType(BigDecimal answer, String question, List<String> types) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		String answerStr = "",questionStr = question;
		List<BigDecimal> answers = new ArrayList<>();
		for (String type : types) {
			if (type.contains("近似")) {
				BigDecimal answer1 = answer.setScale(1, BigDecimal.ROUND_HALF_UP);
				BigDecimal answer2 = answer.setScale(2, BigDecimal.ROUND_HALF_UP);
				answerStr = "保留一位小数的结果为：" + answer1 + 
							"保留两位小数的结果为：" + answer2;
				answers.add(answer1);
				answers.add(answer2);
				questionStr = "（近似计算）" + questionStr + "= ";
			}else {
				questionStr += "=";
				answerStr = answer + "";
			}
		}
		mathQuestionBean.setQuestion(questionStr);
		mathQuestionBean.setAnswerNum(answers);
		mathQuestionBean.setAnswer(answerStr);
		return mathQuestionBean;
	}
	private static boolean checkAnswerOk(BigDecimal answer, long max, long min) {
		boolean isAnswerOk = false;
		if (answer.compareTo(new BigDecimal(max)) == -1 &&
			answer.compareTo(new BigDecimal(min)) == 1) {
			isAnswerOk = true;
		}
		return isAnswerOk;
	}
}
