package com.liangli.nj.log;

public class Logger {
	
	final public static Logger instance = new Logger();
	
	public static Logger get()
	{
		return instance;
	}
	
	public static void error(String tag, Throwable e)
	{
		//打印e;
		e.printStackTrace();
	}

}
