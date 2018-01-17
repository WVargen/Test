package com.liangli.nj.mathmethod;

import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class TimeTransform {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<String> units = gBean.getUnit();
		int unit1Num = MathUtils.generateIntegerRandomNum(0, 1);
		
		int parameter = MathUtils.generateIntegerRandomNum(Miniparams.get(0), Params.get(0));

		String questionStr = null, answerStr = null;
		if (unit1Num == 0 && units.get(unit1Num).equals("24时")) {
			questionStr = "24时计时法为" + parameter + "时，它的12时计时法为：";
			answerStr = "上午" + parameter + "时。";
			if (parameter == 0) {
				answerStr = "上午12时。";
			}else if (parameter >= 12) {
				answerStr = "下午" + (parameter - 12) + "时。";
			}
		}else if (unit1Num == 1 && units.get(unit1Num).equals("12时")) {
			int AMOrPM = MathUtils.generateIntegerRandomNum(0, 1);
			if (AMOrPM == 0) {
				questionStr = "12时计时法为上午" + parameter + "时，它的24时计时法为：";
				if (parameter == 12) {
					answerStr = "24时。";
				}else {
					answerStr = parameter + "时。";
				}
			}else {
				questionStr = "12时计时法为下午" + parameter + "时，它的24时计时法为：";
				answerStr = (parameter + 12) + "时。";
			}
		}

		mathQuestionBean.setQuestion(questionStr);
		mathQuestionBean.setAnswer(answerStr);
		return mathQuestionBean;			
	}
}
