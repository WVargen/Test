package com.liangli.nj.utils;

public class FileUtils {

	public static boolean checkFileFormat(String filePath, String format)
	{
		if (filePath == null || format == null)
		{
			return false;
		}
		
		int index = filePath.lastIndexOf(format);
		
		if (index >= 0 && (index + format.length()) == filePath.length()) {
			return true;
		}
		
		return false;
	}
	
	public static String cutFileFormat(String filename, String format)
	{
		boolean check = checkFileFormat(filename, format);
		
		if (check)
		{
			return filename.substring(0, filename.length() - format.length());
		}
		
		return filename;
	}
}
