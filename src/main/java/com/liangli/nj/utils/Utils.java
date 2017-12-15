package com.liangli.nj.utils;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Utils { 
	public static void checkAndCreateDirectory(String path)
    {
         if (path == null)
         {
               return;
         }

         File file = new File(path);
         File parent = file.getParentFile();

         if (parent != null && !parent.exists())
         {
             parent.mkdirs();
         }
    }
	
	public static File createFile(String Path,String Name){
		File f = new File(Path);
		if(!f.exists()){
			f.mkdirs();
		} 
		File file = new File(f,Name);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return file;
	}
	
	public static String readStringFromFile(String path)
    {
        byte[] bytes = readFromFile(path);

        try
        {
            return new String(bytes, "utf-8");
        }
        catch (Exception e)
        {

        }

        return null;
    }

    public static byte[] readFromFile(String path)
    {
        try
        {
            FileInputStream fis = new FileInputStream(new File(path));
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024 * 32];
            int k = 0;
            while ((k = fis.read(buffer)) > 0)
            {
                bos.write(buffer, 0, k);
                bos.flush();
            }

            fis.close();
            bos.close();

            return bos.toByteArray();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
	   
	public static String ParseJson(String jsonString) {
	        JSONObject jsonObject = JSONObject.parseObject(jsonString);
	        StringBuffer stringBuffer = new StringBuffer();
	        JSONArray jsonArray = null;
	        if(jsonObject.get("r") != null){
	        	jsonArray = jsonObject.getJSONArray("r");
	        	//System.out.println(jsonArray);
	        	for (int i = 0; i < jsonArray.size(); i++) {
	        		JSONObject jObject = jsonArray.getJSONObject(i);
	        		stringBuffer.append(jObject.getString("n"));
	        		stringBuffer.append(" ");
	        	}
        		//System.out.println(stringBuffer);
	        }
			return stringBuffer.toString();
	    }	
}