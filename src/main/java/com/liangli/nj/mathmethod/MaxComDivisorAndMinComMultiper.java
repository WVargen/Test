package com.liangli.nj.mathmethod;


import java.util.ArrayList;
import java.util.List;

import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class MaxComDivisorAndMinComMultiper {

	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
	
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		int sign = gBean.getSigns().get(0);
	
		List<Integer> parameter = new ArrayList<>();
		for (int i = 0; i < Params.size(); i++) {
			parameter.add(MathUtils.generateIntegerRandomNum(Miniparams.get(i), Params.get(i)));
		}
		String questionStr, answerStr = "";
		questionStr = parameter.get(0) + "和" + parameter.get(1);

		if (sign == 0) {//最大公约数
			answerStr = MaxComDivisor(parameter.get(0), parameter.get(1)) + "";
			questionStr += "最大公约数为："; 
		}else if (sign == 1) {//最小公倍数
			answerStr = MinComMultiper(parameter.get(0), parameter.get(1)) + "";
			questionStr += "最小公倍数为：";
		}
		mathQuestionBean.setQuestion(questionStr);
		mathQuestionBean.setAnswer(answerStr);
	
		return mathQuestionBean;
			
}

    private static int MaxComDivisor(int param1,int param2){//最大公约数
        if(param1 > param2){
            int tmp = param1 ;
            param1  = param2;
            param2 = tmp;
        }
        while(param1 != 0){
            int temp = param2 % param1;
            param2 = param1;
            param1 = temp;
        }
        return param2;
    }
    
    private static int MinComMultiper(int param1,int param2){//最小公倍数
        int a = param1, b = param2;
        int g = MaxComDivisor(a,b);
        return param1*param2/g;
    }
}
