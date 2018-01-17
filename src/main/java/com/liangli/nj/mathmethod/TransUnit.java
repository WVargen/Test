package com.liangli.nj.mathmethod;

import java.math.BigDecimal;
import java.util.List;

import javax.lang.model.type.UnionType;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class TransUnit {
	
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> transform = gBean.getTransform();
		List<String> unit = gBean.getUnit();

		Long parameter = Params.get(0);
		Long miniParameter = Miniparams.get(0);
		
		int number = MathUtils.generateIntegerRandomNum(miniParameter, parameter);
		
		int questionUnitNum = MathUtils.generateIntegerRandomNum(0, unit.size()-1);
		String questionUnit = unit.get(questionUnitNum);
		
		int answerUnitNum = MathUtils.generateIntegerRandomNum(0,unit.size()-1);
		while (answerUnitNum == questionUnitNum) {
			answerUnitNum = MathUtils.generateIntegerRandomNum(0, unit.size()-1);
		}
		String answerUint = unit.get(answerUnitNum);
		
		String questionStr, answerStr;
	
		questionStr = number + questionUnit + "=";
		float answer = (float) (number * (1.0 * transform.get(questionUnitNum) / transform.get(answerUnitNum)));
		answerStr = answer + answerUint;
		
		mathQuestionBean.setQuestion(questionStr);
		mathQuestionBean.setAnswer(answerStr);

		return mathQuestionBean;
				
	}

}
