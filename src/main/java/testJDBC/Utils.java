package testJDBC;

import java.io.File;
import java.io.IOException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class Utils {    
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
	   
	public static StringBuffer ParseJson(String jsonString) {
	        JSONObject jsonObject = JSONObject.parseObject(jsonString);
	        StringBuffer stringBuffer = new StringBuffer();
	        JSONArray jsonArray = null;
	        if(jsonObject.get("r") != null){
	        	jsonArray = jsonObject.getJSONArray("r");
	        	//System.out.println(jsonArray);
	        	for (int i = 0; i < jsonArray.size(); i++) {
	        		JSONObject jObject = jsonArray.getJSONObject(i);
	        		stringBuffer.append(jObject.getString("n"));
	        		stringBuffer.append("|");
	        	}
        		System.out.println(stringBuffer);
	        }
			return stringBuffer;
	    }	
}