package com.liangli.nj.mathmethod;

import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class VolumeAndSurfaceArea {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();
		List<String> unit = gBean.getUnit();
		List<Integer> parameter = new ArrayList<>();
		for (int i = 0; i < Params.size(); i++) {
			parameter.add(MathUtils.generateIntegerRandomNum(Miniparams.get(i), Params.get(i)));
		}
		
		int volOrArea = MathUtils.generateIntegerRandomNum(0, unit.size() - 1);
		String type = unit.get(volOrArea);
		
		int sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));

			if (sign == 0) {//体积
				mathQuestionBean = calVolume(type, parameter);
			}else if (sign == 1) {//表面积
				mathQuestionBean = calSurfaceArea(type, parameter);
			}
		
		return mathQuestionBean;
		
	}
	
	private static MathQuestionBean calVolume(String type, List<Integer> parameter) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		int answer = 0;
		String answerStr = "", questionStr = "";
		
		if (type.equals("长方体")) {
			answer = parameter.get(0) * parameter.get(1) * parameter.get(2);
			questionStr = "长方体的长为" + parameter.get(0) + "cm，宽为" + parameter.get(1) + "cm，高为" + parameter.get(2) +"cm，它的体积为：";
			answerStr = answer + "立方厘米.";
		}else if (type.equals("正方体")) {
			answer = (int) Math.pow(parameter.get(0), 3);
			questionStr = "正方形的棱长为" + parameter.get(0) + "cm，它的体积为：";
			answerStr = answer + "立方厘米.";
		}
		
		mathQuestionBean.setAnswer(answerStr);
		mathQuestionBean.setQuestion(questionStr);
		return mathQuestionBean;
	}
	
	private static MathQuestionBean calSurfaceArea(String type, List<Integer> parameter) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		int answer = 0;
		String answerStr = "", questionStr = "";
		
		if (type.equals("长方体")) {
			answer = ((parameter.get(0) * parameter.get(1)) + (parameter.get(0) * parameter.get(2)) + (parameter.get(1) * parameter.get(2))) * 2;
			questionStr = "长方体的长为" + parameter.get(0) + "cm，宽为" + parameter.get(1) + "cm，高为" + parameter.get(2)+ "cm，它的表面积为：";
			answerStr = answer + "平方厘米.";
		}else if (type.equals("正方体")) {
			answer = (int) (Math.pow(parameter.get(0), 2) * 6);
			questionStr = "正方体的边长为" + parameter.get(0) + "cm，它的表面积为：";
			answerStr = answer + "平方厘米.";
		}
		
		mathQuestionBean.setAnswer(answerStr);
		mathQuestionBean.setQuestion(questionStr);
		return mathQuestionBean;
	}

}
