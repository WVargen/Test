package com.liangli.nj.log;

public class FatalLog {

	public static void log(Throwable e)
	{
		//todo 打印e
		e.printStackTrace();
	}
	
	public static void log(String log)
	{
		System.out.println("[fatal] - " + log);
	}
}
