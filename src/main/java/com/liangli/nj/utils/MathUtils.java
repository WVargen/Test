package com.liangli.nj.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MathUtils {

	public static int generateIntegerRandomNum(long min, long max) {
		return (int) (Math.random() * (max - min + 1) + min);
	}

	public static BigDecimal generateDecimalRandomNum(long min, long max, int digitalNum) {
		BigDecimal number = new BigDecimal (Math.random() * (max - min + 1) + min)
							.setScale(digitalNum, BigDecimal.ROUND_HALF_UP);
		return number;
	}
	
//	public static List<BigDecimal> generateParameters(List<BigDecimal> params) {
//		List<BigDecimal> parameters = new ArrayList<>();
//		return parameters;
//	}
	
}
