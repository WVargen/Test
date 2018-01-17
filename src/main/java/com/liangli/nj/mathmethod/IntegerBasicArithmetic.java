package com.liangli.nj.mathmethod;

import java.math.BigDecimal;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class IntegerBasicArithmetic {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();

		int sign = signs.get(0);
		if (signs.size() > 1) {
			sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));
		}
		
		int decimalDigits1 = MathUtils.generateIntegerRandomNum(2, 3);
		int decimalDigits2 = MathUtils.generateIntegerRandomNum(2, 3);
		
		BigDecimal parameter1,parameter2;
		float answer = 0;
		
		parameter1 = MathUtils.generateDecimalRandomNum(Miniparams.get(0), Params.get(0), decimalDigits1);
		parameter2 = MathUtils.generateDecimalRandomNum(Miniparams.get(1), Params.get(1), decimalDigits2);
		
		String questionStr = "", answerStr = "";
		
		int param1Int = parameter1.intValue();
		int param2Int = parameter2.intValue();
		if (sign == 0) {
			answer = param1Int + param2Int;
			questionStr = param1Int + "+" + param2Int + "=";
		}else if (sign == 1) {
			answer = param1Int - param2Int;
			questionStr = param1Int + "-" + param2Int + "=";
		}else if (sign == 2) {
			answer = param1Int * param2Int;
			questionStr = param1Int + "x" + param2Int + "=";
		}else if (sign == 3) {
			answer = (float) (1.0 * param1Int / param2Int);
			questionStr = param1Int + "÷" + param2Int + "=";
		}
			
		answerStr = (int) answer + "";
		
		if (answer >= Miniparams.get(2) && answer <= Params.get(2)&& 
			isAnswerOk(answer)) {
			mathQuestionBean.setQuestion(questionStr);
			mathQuestionBean.setAnswer(answerStr);
			return mathQuestionBean;
		}
		
		return generator(gBean);
		
	}
	
	private static boolean isAnswerOk(float answerFloat) {
		Integer answer = (int) answerFloat;
		if (answerFloat % answer != 0) {
			return false;
		}
		return true;
	}

}
