package com.liangli.nj.utils;

import java.util.Properties;

public class Definition {
	
	public static String getClassPath()
	{
		return Strings.cutEnd(Thread.currentThread().getContextClassLoader().getResource("").getPath(), "/");
	}
	
	public static boolean printSqlInConsol = false;
	public static Properties dbCconfig = null;
	
	static 
	{
		if (DeviceUtils.file.isExist("/home/webdata/nw/db.properties"))		
		{
			dbCconfig = Strings.readProperties("/home/webdata/nw/db.properties");
		}
		else
		{
			dbCconfig = Strings.readProperties(getClassPath() + "/db.properties");
		}
	}

}
