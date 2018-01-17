package com.liangli.nj.mathmethod;

import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class CalAnsHas0 {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();

		int sign = signs.get(0);
		int parameter1 = 0, parameter2 = 0;
		float answer = 0;

		if (sign == 0) {
			parameter1 = generateRandomNumHas0(Miniparams.get(0), Params.get(0));
		}else if (sign == 1) {
			parameter1 = MathUtils.generateIntegerRandomNum(Miniparams.get(0), Params.get(0) / 10) * 10;
		}
		
		parameter2 = MathUtils.generateIntegerRandomNum(Miniparams.get(1), Params.get(1));
		
		String questionStr = null, answerStr = null;
		answer = (float) (1.0 * parameter1 / parameter2);
		questionStr = parameter1 + "÷" + parameter2 + "=";

		if (answer >= Miniparams.get(2) 
			&& answer <= Params.get(2)
			&& isAnswerOk(sign, answer)) {
			answerStr = (int) answer + "";
			mathQuestionBean.setQuestion(questionStr);
			mathQuestionBean.setAnswer(answerStr);
			return mathQuestionBean;
		}
		return generator(gBean);
		
	}
	
	private static int generateRandomNumHas0(long min, long max) {
		int ret = 0;
		Integer randomNum = MathUtils.generateIntegerRandomNum(min, max);
		if (randomNum.toString().contains("0")) {
			ret = randomNum;
		}else {
			return generateRandomNumHas0(min, max);
		}
		return ret;
	}
	
	private static boolean isAnswerOk(int sign, float answerFloat) {
		boolean ret = false;
		Integer answer = (int) answerFloat;
		if (answerFloat % answer != 0) {
			return false;
		}
		if (sign == 0) {
			String anString = answer.toString();
			if (anString.substring(0, answer.toString().length() - 1).contains("0")
				&& !anString.substring(answer.toString().length() - 1).equalsIgnoreCase("0")) {
				ret = true;
			}
		}else if (sign == 1) {
			String anString = answer.toString();
			if (anString.substring(answer.toString().length() - 1).equalsIgnoreCase("0")
				&& !anString.substring(0, answer.toString().length() - 1).contains("0")) {
				ret = true;
			}
		}
		return ret;
	}

}
