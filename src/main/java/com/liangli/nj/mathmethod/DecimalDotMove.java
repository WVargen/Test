package com.liangli.nj.mathmethod;

import java.math.BigDecimal;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class DecimalDotMove {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();
		
		//0为写左移，1为读右移
		int sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));

		String questionStr = "", answerStr = "";
		BigDecimal parameter = null;

	
		int digitalNum = MathUtils.generateIntegerRandomNum(2, 6);
		parameter = MathUtils.generateDecimalRandomNum(Miniparams.get(0), Params.get(0), digitalNum);
		BigDecimal answer = new BigDecimal(0);
		
		int moveNum = MathUtils.generateIntegerRandomNum(1, parameter.toString().length());
		
		if (sign == 0) {
			questionStr = parameter + "小数点向左移动" + moveNum + "，结果为：";
			answer = generateAnswer(parameter, 0, moveNum);	
						
		}else if (sign == 1) {
			questionStr = parameter + "小数点向右移动" + moveNum + "，结果为：";
			answer = generateAnswer(parameter, 1, moveNum);
		}
			
		if (answer.compareTo(new BigDecimal(Miniparams.get(Miniparams.size() - 1))) == 1 && 
			answer.compareTo(new BigDecimal(Params.get(Params.size() - 1))) == - 1) {
			answerStr = answer + ".";
			mathQuestionBean.setQuestion(questionStr);
			mathQuestionBean.setAnswer(answerStr);
			return mathQuestionBean;
		}
			
		return generator(gBean);
	}
	
	private static BigDecimal generateAnswer(BigDecimal parameter, int direction, int moveNum) {
		BigDecimal answer = new BigDecimal(0);
		int decimalLenth = parameter.subtract(new BigDecimal(parameter.intValue())).toString().length() - 2;
		if (direction == 0) {
			answer = parameter.divide(new BigDecimal(Math.pow(10, moveNum)))
					.setScale(decimalLenth + moveNum, BigDecimal.ROUND_HALF_UP);
		}else if (direction == 1) {
			answer = parameter.multiply(new BigDecimal(Math.pow(10, moveNum)))
					.setScale(decimalLenth - moveNum, BigDecimal.ROUND_HALF_UP);
		}
		
		return answer;
	}
}
