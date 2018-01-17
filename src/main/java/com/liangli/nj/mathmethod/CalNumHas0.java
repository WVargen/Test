package com.liangli.nj.mathmethod;

import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class CalNumHas0 {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();

		int sign = signs.get(0);
		if (signs.size() > 1) {
			sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));
		}
		int parameter1 = 0, parameter2 = 0, answer = 0;
		
		parameter1 = generateRandomNumHas0(Miniparams.get(0), Params.get(0));
		parameter2 = MathUtils.generateIntegerRandomNum(Miniparams.get(1), Params.get(1));
		
		String questionStr = null, answerStr = null;
		
		if (sign == 0) {
			answer = parameter1 + parameter2;
			answerStr = parameter1 + "+" + parameter2 + "=";
			questionStr = answer + "";
		}else if (sign == 1) {
			answer = parameter1 - parameter2;
			questionStr = parameter1 + "-" + parameter2 + "=";
			answerStr = answer + "";
		}else if (sign == 2) {
			answer = parameter1 * parameter2;
			questionStr = parameter1 + "x" + parameter2 + "=";
			answerStr = answer + "";
		}
		
		if (answer >= Miniparams.get(2) && answer <= Params.get(2)) {
			
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

}
