package com.liangli.nj.utils;

import java.util.Random;

public class CountNumberOfRegistrations {
	
	static int numOfIncreaseByHours = 1000;
	static final int hourMills = 3600000;
	static final int minMills = 60000;
	static final int minsPerHoue = 60;
	
	public static int CountRegistrations(long startTime , Integer baseNumOfRegistrations) {
		
		long nowTime = System.currentTimeMillis();
		int numberOfRegistrations = baseNumOfRegistrations;
		
		if (startTime >= nowTime) {
			numberOfRegistrations = 0;
		}else {
			long timeDiff = nowTime - startTime;
			//float timeDiffPerHour = (float) (1.0 * timeDiff / hourMills);
			float timeDiffPerMin = (float) (1.0 * timeDiff / minMills);
			for (int i = 0; i < timeDiffPerMin; i++) {
				/*
				 * 统计数据每分钟更新一次
				 */
				numberOfRegistrations += generateRandom(i) / minsPerHoue;
			}
			//numberOfRegistrations += generateRandom((int) timeDiffPerHour) * timeDiffPerHour;
		}
		
		return numberOfRegistrations;
	}
	
	private static int generateRandom(int seed) {
		int ret = numOfIncreaseByHours;
		Random random = new Random(seed);
		ret = (int) (Math.sqrt(50000) * random.nextGaussian() + numOfIncreaseByHours);
		return ret;
	}

}
