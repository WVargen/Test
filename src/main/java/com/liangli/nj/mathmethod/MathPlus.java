package com.liangli.nj.mathmethod;

import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.bean.MathQuestionBean;

public class MathPlus{
	
	static int[] augendRange = {1, 10};
	static int[] addendRange = {1, 10};
	static int[] sumRange    = {1, 10};
	
	List<int[]> parameters = new ArrayList<>();
	
	public MathPlus() {
		this.parameters.add(MathPlus.addendRange);
		this.parameters.add(MathPlus.sumRange);
		this.parameters.add(MathPlus.addendRange);
	}

	public List<int[]> getPlusParameters() {
		return parameters;
	}

	public static MathQuestionBean mathPlus() {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		int augend = 0, addend = 0, sum = 0;
		
		augend = generateRandomNum(addendRange[0], addendRange[1]);
		addend = generateRandomNum(sumRange[0], sumRange[1]);
		sum = augend + addend;
		if (sum < addendRange[0] || sum > addendRange[1]) {
			mathPlus();
		}else {
			String plusQuestionStr = augend + "+" + addend + "=";
			String plusAnswerStr = sum + "";
			mathQuestionBean.setQuestion(plusQuestionStr);
			mathQuestionBean.setAnswer(plusAnswerStr);
			System.out.println(mathQuestionBean.getQuestion() + mathQuestionBean.getAnswer());
		}
		return mathQuestionBean;
		
	}
	private static int generateRandomNum(int min, int max) {
		return (int)(Math.random() * (max - min) + min);
	}
}