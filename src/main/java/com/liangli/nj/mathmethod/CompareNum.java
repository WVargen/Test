package com.liangli.nj.mathmethod;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;

public class CompareNum {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		List<Long> params = gBean.getParams(), miniparams = gBean.getMinparams();
		Integer sign = gBean.getSigns().get(0);
		
		List<Float> floatParameters = new ArrayList<>();
		List<Integer> intParameters = new ArrayList<>();
		
		String questionStr = "对", answerStr = "";
		
		if (sign == 1) {//小数
			for (int i = 0; i < params.size(); i++) {
				floatParameters.add(generateRandomNum((float)miniparams.get(i), (float)params.get(i)));
				questionStr += floatParameters.get(i) + "、";
			}
		}else if (sign == 0) {//整数
			for (int i = 0; i < params.size(); i++) {
				intParameters.add(generateRandomNum(miniparams.get(i), params.get(i)));
				questionStr += intParameters.get(i) + "、";
			}
		}
		questionStr = questionStr.substring(0, questionStr.length() - 1) + "按由小到大的顺序进行排序:";
		Collections.sort(floatParameters);
		Collections.sort(intParameters);

		if (sign == 1) {
			for (int i = 0; i < floatParameters.size(); i++) {
				answerStr += floatParameters.get(i) + "<";
			}
		}else {
			for (int i = 0; i < intParameters.size(); i++) {
				answerStr += intParameters.get(i) + "<";
			}
		}
		
		answerStr = answerStr.substring(0, answerStr.length() - 1) + ".";
		
		mathQuestionBean.setQuestion(questionStr);
		mathQuestionBean.setAnswer(answerStr);
		return mathQuestionBean;
	}
	
	private static int generateRandomNum(long min, long max) {
		return (int)(Math.random() * (max - min + 1) + min);
	}
	
	private static float generateRandomNum(Float min, Float max) {
		return (float) (Math.random() * (max - min + 1) + min);
	}
}
