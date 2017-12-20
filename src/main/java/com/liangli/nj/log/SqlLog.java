package com.liangli.nj.log;

public class SqlLog {

	public static void logWarning(Throwable e)
	{
		//TODO 打印e
		e.printStackTrace();
	}
	
	public static void printConsol(String log)
	{
		System.out.println("[sql] - " + log);
	}
}
