package com.liangli.nj.mathmethod;

import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class MultipleCharacteristics {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();
		List<Integer> sign = new ArrayList<>();
		sign.add(signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1)));
		
		int parameter = MathUtils.generateIntegerRandomNum(Miniparams.get(0), Params.get(0));

		boolean isAnswerOk = true;
		String questionStr = "", answerStr = "";
		
		questionStr = parameter + "是否是";
		for (int i = 0; i < sign.size(); i++) {
			questionStr += sign.get(i)+ "和";
			float answer = (float) (1.0 * parameter / sign.get(i));
			if (isAnswerOk(answer) == false) {
				isAnswerOk = false;
			}
		}
		questionStr = questionStr.substring(0, questionStr.length() - 1) + "的倍数：";
		
		if (isAnswerOk) {
			answerStr = "是.";
		}else {
			answerStr = "否.";
		}
		
		mathQuestionBean.setAnswer(answerStr);
		mathQuestionBean.setQuestion(questionStr);
		return mathQuestionBean;
	}
	
	private static boolean isAnswerOk(float answerFloat) {
		Integer answer = (int) answerFloat;
		if (answerFloat % answer != 0) {
			return false;
		}
		return true;
	}
}
