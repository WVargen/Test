package com.liangli.nj.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.liangli.nj.utils.DeviceUtils.file;

import com.liangli.nj.utils.Strings;

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
	
	public static void scanFilePath(File file, Map<String, File> fileMap) 
	{
		File[] files = file.listFiles();
		for(int i = 0; i < files.length; i++)  
        {  
            if(files[i].isDirectory())
            {
            
            	scanFilePath(files[i], fileMap); 
            }
			else  
            { 
            	fileMap.put(files[i].getName().substring(0, files[i].getName().indexOf(".")), files[i]);
            	
            }
            
        } 	
	}
	
	public static List<String[]> scanFilePathCatalog(String filePath) 
	{
		int filePathLength = filePath.length();
		File file = new File(filePath);
		List<String []> output = new ArrayList<>();
		Map<String, File> fileMap = new LinkedHashMap<String, File>();
		
		scanFilePath(file, fileMap);
		
		int maxlevel = 0;
		for (File files : fileMap.values())
		{
			String[] filePathList = files.toString().split("\\\\");
			maxlevel = filePathList.length - file.toString().split("\\\\").length - 1;
		}
		
		List<String> row = new ArrayList<>();
		
		for (int i = 1; i <= maxlevel; i++) {
			row.add("category" + i);
		}
		row.add("word");
		output.add(row.toArray(new String[maxlevel]));
		
		for (String filename : fileMap.keySet())
		{
			row.clear();

			String filePathStr = fileMap.get(filename).toString().substring(filePathLength + 1);
			row = Strings.split(filePathStr, "\\\\", "/");
			row.remove(row.size() - 1);
			if (row.size() < maxlevel) {
				for (int i = row.size(); i < maxlevel; i++) {
					row.add("");
				}
			}
			
			row.add(filename);
			output.add(row.toArray(new String [row.size()]));
		}
		return output;
	}
}
