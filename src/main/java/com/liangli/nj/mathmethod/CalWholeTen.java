package com.liangli.nj.mathmethod;


import java.util.List;
import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class CalWholeTen {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> transform = gBean.getTransform();
		List<Integer> signs = gBean.getSigns();
		int sign = signs.get(0);
		if (signs.size() > 1) {
			sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));
		}
		int parameter1 = 0, parameter2 = 0;
		float answer = 0;
		
		parameter1 = MathUtils.generateIntegerRandomNum(Miniparams.get(0) / transform.get(0), Params.get(0) / transform.get(0)) * transform.get(0);
		parameter2 = MathUtils.generateIntegerRandomNum(Miniparams.get(1) / transform.get(1), Params.get(1) / transform.get(1)) * transform.get(1);
		
		String questionStr = null, answerStr = null;
		
		if (sign == 0) {
			answer = parameter1 + parameter2;
			questionStr = parameter1 + "+" + parameter2 + "=";
		}else if (sign == 1) {
			answer = parameter1 - parameter2;
			questionStr = parameter1 + "-" + parameter2 + "=";
		}else if (sign == 2) {
			answer = parameter1 * parameter2;
			questionStr = parameter1 + "x" + parameter2 + "=";
		}else if (sign == 3) {
			answer = (float) (1.0 * parameter1 / parameter2);
			questionStr = parameter1 + "÷" + parameter2 + "=";
		}
		
		if (answer >= Miniparams.get(2) && answer <= Params.get(2) && isAnswerOk(answer)) {
			answerStr = (int) answer + "";
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
