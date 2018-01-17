package com.liangli.nj.mathmethod;

import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class CalPerimeterAndArea {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		int sign = gBean.getSigns().get(0);
		List<String> unit = gBean.getUnit();
		List<Integer> parameter = new ArrayList<>();
		for (int i = 0; i < Params.size(); i++) {
			parameter.add(MathUtils.generateIntegerRandomNum(Miniparams.get(i), Params.get(i)));
		}
		
		int squOrRec = MathUtils.generateIntegerRandomNum(0, unit.size() - 1);
		String type = unit.get(squOrRec);
		
		int answer = 0;
		
		if (sign == 0) {//周长
			mathQuestionBean = calPerimeter(type, parameter);
			answer = Integer.valueOf(mathQuestionBean.getAnswer().substring(0, mathQuestionBean.getAnswer().indexOf("c")));	
		}else if (sign == 1) {//面积
			mathQuestionBean = calArea(type, parameter);
			answer = Integer.valueOf(mathQuestionBean.getAnswer().substring(0, mathQuestionBean.getAnswer().indexOf("平")));	
		}
		
		if (answer >= Miniparams.get(parameter.size() - 1) && answer <= Params.get(parameter.size() - 1)) {			
			return mathQuestionBean;
		}
		return generator(gBean);
		
	}
	
	private static MathQuestionBean calArea(String type, List<Integer> parameter) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		int answer = 0;
		String answerStr = "", questionStr = "";
		
		if (type.equals("长方形")) {
			answer = (parameter.get(0) * parameter.get(1)) * 2;
			questionStr = "长方形的长为" + parameter.get(0) + "cm，宽为" + parameter.get(1) + "cm，它的面积为：";
			answerStr = answer + "平方厘米.";
		}else if (type.equals("正方形")) {
			answer = parameter.get(0) * parameter.get(0);
			questionStr = "正方形的边长为" + parameter.get(0) + "cm，它的面积为：";
			answerStr = answer + "平方厘米.";
		}else if (type.equals("平行四边形")) {
			answer = (parameter.get(0) * parameter.get(1)) * 2;
			questionStr = "平行四边形的长为" + parameter.get(0) + "cm，高为" + parameter.get(1) + "cm，它的面积为：";
			answerStr = answer + "平方厘米.";
		}else if (type.equals("三角形")) {
			answer = (parameter.get(0) * parameter.get(1)) / 2;
			questionStr = "三角的底为" + parameter.get(0) + "cm，高为" + parameter.get(1) + "cm，它的面积为：";
			answerStr = answer + "平方厘米.";
		}else if (type.equals("梯形")) {
			answer = (parameter.get(0) + parameter.get(1)) * parameter.get(2) / 2;
			questionStr = "梯形的上底为" + parameter.get(0) + "cm，下底为" + parameter.get(1) + "cm，高为" + parameter.get(2) + "cm，它的面积为：";
			answerStr = answer + "平方厘米.";
		}
		
		mathQuestionBean.setAnswer(answerStr);
		mathQuestionBean.setQuestion(questionStr);
		return mathQuestionBean;
	}
	
	private static MathQuestionBean calPerimeter(String type, List<Integer> parameter) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		int answer = 0;
		String answerStr = "", questionStr = "";
		
		if (type.equals("长方形")) {
			answer = (parameter.get(0) + parameter.get(1)) * 2;
			questionStr = "长方形的长为" + parameter.get(0) + "cm，宽为" + parameter.get(1) + "cm，它的周长为：";
			answerStr = answer + "cm.";
		}else if (type.equals("正方形")) {
			answer = parameter.get(0) * 4;
			questionStr = "正方形的边长为" + parameter.get(0) + "cm，它的周长为：";
			answerStr = answer + "cm.";
		}else if (type.equals("四边形")) {
			answer = parameter.get(0) + parameter.get(1) + parameter.get(2) + parameter.get(3);
			questionStr = "四边形的边长为" + parameter.get(0) + "cm、" + parameter.get(1) + "cm、" + parameter.get(2) + "cm、" + parameter.get(3) + "cm，它的周长为：";
			answerStr = answer + "cm.";
		}
		
		mathQuestionBean.setAnswer(answerStr);
		mathQuestionBean.setQuestion(questionStr);
		return mathQuestionBean;
	}

}
