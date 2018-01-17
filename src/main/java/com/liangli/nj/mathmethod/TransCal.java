package com.liangli.nj.mathmethod;

import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class TransCal {
	
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> transform = gBean.getTransform();
		List<String> unit = gBean.getUnit();
		List<Integer> signs = gBean.getSigns();
		int parameter1 = 0, parameter2 = 0, parameter3 = 0;
		int parameter1Tmp = 0, parameter2Tmp = 0;
		
		parameter1Tmp = parameter1 = MathUtils.generateIntegerRandomNum(Miniparams.get(0), Params.get(0));
		parameter2Tmp = parameter2 = MathUtils.generateIntegerRandomNum(Miniparams.get(1), Params.get(1));
		
		int param1UnitNum = MathUtils.generateIntegerRandomNum(0, 2);
		int param2UnitNum = MathUtils.generateIntegerRandomNum(0, 2);

		String parameter1Unit = unit.get(param1UnitNum);
		String parameter2Unit = unit.get(param2UnitNum);
		String parameter3Unit = unit.get(param2UnitNum);

		if (param1UnitNum > param2UnitNum) {
			if (param2UnitNum > 0) {
				parameter1Tmp = parameter1 * transform.get(param1UnitNum - 1) / transform.get(param2UnitNum - 1);
				parameter3Unit = parameter2Unit;
			}else {
				parameter1Tmp = parameter1 * transform.get(param1UnitNum - 1);
				parameter3Unit = parameter2Unit;
			}
			
		}else if (param1UnitNum < param2UnitNum) {
			if (param1UnitNum > 0) {
				parameter2Tmp = parameter2 * transform.get(param2UnitNum - 1) / transform.get(param1UnitNum - 1);
				parameter3Unit = parameter1Unit;
			}else {
				parameter2Tmp = parameter2 * transform.get(param2UnitNum - 1);
				parameter3Unit = parameter1Unit;
			}
		}

		String plusQuestionStr, plusAnswerStr;
		int sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));
		
		if (sign == 0) {
			plusQuestionStr = parameter1 + parameter1Unit + "+" + parameter2 + parameter2Unit + "=";	
			parameter3 = parameter1Tmp + parameter2Tmp;
		}else {
			plusQuestionStr = parameter1 + parameter1Unit + "-" + parameter2 + parameter2Unit + "=";
			parameter3 = parameter1Tmp - parameter2Tmp;
		}
		
		plusAnswerStr = parameter3 + parameter3Unit;

		if (parameter3 >= Miniparams.get(2) && parameter3 <= Params.get(2)) {	
			mathQuestionBean.setQuestion(plusQuestionStr);
			mathQuestionBean.setAnswer(plusAnswerStr);
			return mathQuestionBean;
		}
		return generator(gBean);
				
	}
	
}
