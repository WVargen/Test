package com.liangli.nj.mathmethod;

import java.math.BigDecimal;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class DecimalApproximateValue {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();//近似位数
		
		int sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));

		String questionStr = "", answerStr = "";
		BigDecimal parameter = null;

		int digitalNum = MathUtils.generateIntegerRandomNum(4, 7);
		parameter = MathUtils.generateDecimalRandomNum(Miniparams.get(0), Params.get(0), digitalNum);
		
		questionStr = parameter + "保留" + sign + "位小数，结果为：";
		
		if (sign <digitalNum) {
			BigDecimal answer = parameter.setScale(sign, BigDecimal.ROUND_HALF_UP);
			answerStr = answer + ".";
			mathQuestionBean.setQuestion(questionStr);
			mathQuestionBean.setAnswer(answerStr);
			return mathQuestionBean;
		}
			
		return generator(gBean);
	}
}
