package com.liangli.nj.utils;

import java.util.Random;

public class CountNumberOfRegistrations {
	
	static final int hourMills = 3600000;
	static final int minMills = 60000;
	static final int minsPerHoue = 60;
	
	/**
	 * 
	 * @param startTime 开始计数的时间，单位：ms
	 * @param baseNumOfRegistrations 注册数的基数
	 * @param numOfIncreaseByHours 每小时增长的注册数量
	 * @return
	 */
	
	public static int CountRegistrations(long startTime , Integer baseNumOfRegistrations, Integer numOfIncreaseByHours) {
		
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
				numberOfRegistrations += generateRandom(i, numOfIncreaseByHours) / minsPerHoue;
			}
			//numberOfRegistrations += generateRandom((int) timeDiffPerHour) * timeDiffPerHour;
		}
		
		return numberOfRegistrations;
	}
	
	private static int generateRandom(int seed, Integer numOfIncreaseByHours) {
		int ret = 0;
		Random random = new Random(seed);
		double randomNum = random.nextGaussian();
		/*
		 * randomNum的绝对值小于1时，其与一的和乘增长率算得随机数
		 * 其余情况返回0
		 */
		if (Math.abs(randomNum) <= 1) {
			ret = (int) ((1 + randomNum) * numOfIncreaseByHours);
		}
		//System.out.println(ret);
		return ret;
	}

}
