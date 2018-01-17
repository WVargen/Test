package com.liangli.nj.mathmethod;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.bean.MathQuestionBean;
import com.liangli.nj.utils.MathUtils;

public class NumberReadandWrite {
	public static MathQuestionBean generator(GeneratorBean gBean) {
		MathQuestionBean mathQuestionBean = new MathQuestionBean();
		
		List<Long> Params = gBean.getParams(), Miniparams = gBean.getMinparams();
		List<Integer> signs = gBean.getSigns();
		String type = gBean.getUnit().get(0);
		//0为写转读，1为读转写
		int sign = signs.get(MathUtils.generateIntegerRandomNum(0, signs.size() - 1));
		//产生2-4位小数
		int digitalNum = MathUtils.generateIntegerRandomNum(2, 5);
		
		String questionStr = "", answerStr = "";
		BigDecimal parameter = null;
		if (type.equals("整数")) {
			int digitalCount = MathUtils.generateIntegerRandomNum(Miniparams.get(0).toString().length(), Params.get(0).toString().length());
			parameter = MathUtils.generateDecimalRandomNum(Miniparams.get(0), (long) Math.pow(10, digitalCount - 1), 0);	
		}else if (type.equals("小数")) {
			parameter = MathUtils.generateDecimalRandomNum(Miniparams.get(0), Params.get(0), digitalNum);
			
		}
		
		if (sign == 0) {
			questionStr = parameter + "读作：";
			answerStr = generateDecimalReading(parameter)+ ".";		
		}else if (sign == 1) {
			questionStr = generateDecimalReading(parameter) + "写作：";
			answerStr = parameter + ".";
		}
		
		mathQuestionBean.setAnswer(answerStr);
		mathQuestionBean.setQuestion(questionStr);
		return mathQuestionBean;
	}
	
	private static String generateDecimalReading(BigDecimal decimalNum) {
		String decimalReaing = "";
		long integerPart = decimalNum.longValue();
		float decimalPart = decimalNum.subtract(new BigDecimal(integerPart)).floatValue();
		
		decimalReaing = generateIntegerNumReading((long)integerPart);
		if (decimalPart != 0) {
			decimalReaing += "点" + generateDecimalNumReading(decimalPart);
		}	
		return decimalReaing;
	}
	
	private static String generateIntegerNumReading(Long number) {
		 String units[] = { "", "万", "亿"};// 单位
		 List<String> numStrList = new ArrayList<>();
		 String numReading = "";
		 
		 long numberDivTmp = number, numberTmp = 0;
		 while (numberDivTmp > 0) {
			numberTmp = numberDivTmp % 10000;
			numStrList.add(generateIntegerNumReading((int)numberTmp));
			numberDivTmp = numberDivTmp / 10000;
		 }
		 for (int i = numStrList.size() - 1; i >= 0; i--) {
			 if (!numStrList.get(i).equals("零")) {
				numReading += numStrList.get(i);
				numReading += units[i];
				if (i > 1 && !numStrList.get(i - 1).contains("千")) numReading += "零";
			 }else {
				numReading += numStrList.get(i);
			 }
		 }
		 while (numReading.contains("零零")) numReading = numReading.replace("零零", "零");
		 
		 if (numReading.length() > 1 && numReading.substring(numReading.length() - 1).equals("零")) {
			 numReading = numReading.substring(0, numReading.length() - 1);
		 }

		 if (numReading.length() > 1 && numReading.substring(0, 2).equals("一十"))
				numReading = numReading.substring(1);
		 
		 return numReading;
	}
	
	private static String generateIntegerNumReading(Integer number) {
		//System.out.println(number);
		
		String numbers[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		String units[] = { "", "十", "百", "千"};// 单位
		StringBuffer readingBuffer = new StringBuffer();
		String numReading = "";
		
		String inputNumber[] = new String[number.toString().length()];
		for (int i = 0; i < inputNumber.length; i++) inputNumber[i] = String.valueOf(number.toString().charAt(i));
		
		int unitNum = 0;
		if (inputNumber.length < 5) {
			for (int i = 0; i < inputNumber.length; i++) {
				if (!inputNumber[i].equals("0")) {
					unitNum = inputNumber.length - i - 1;
		            readingBuffer.append(numbers[Integer.parseInt(inputNumber[i])]);
		            readingBuffer.append(units[unitNum]);
				}else {
					readingBuffer.append(numbers[Integer.parseInt(inputNumber[i])]);
				}
			}
			
			numReading = readingBuffer.toString();
			while (numReading.contains("零零")) {
				numReading = numReading.replace("零零", "零");
			}

			if (numReading.length() > 1 && numReading.substring(numReading.length() - 1).equals("零")) {
				numReading = numReading.substring(0, numReading.length() - 1);
			}

		}else {
			return "长度大于4";
		}
		return numReading;
	}
	
	private static String generateDecimalNumReading(Float number) {
		String numReading = "";
		String numbers[] = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };
		
		String inputNumber[] = new String[number.toString().length()];
		for (int i = 0; i < inputNumber.length; i++) inputNumber[i] = String.valueOf(number.toString().charAt(i));
		
		for (int i = 2; i < number.toString().length(); i++) 
			numReading += numbers[Integer.parseInt(inputNumber[i])];	
		return numReading;
	}

}
