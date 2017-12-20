package com.liangli.nj.table;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class NewWordTable{
	public static String handleWordTable(HashMap<String, Integer> worldtable, String inputstring) {
		List<String> strings = Arrays.asList(inputstring.replace(" ", "").split(""));
		StringBuilder result = new StringBuilder();
		HashMap<String, Integer> tm = worldtable;
	    for(int i = 0 ; i<strings.size() ; i++){
	        String string = strings.get(i);
	        if(tm.containsKey(string)){
	            int conut = tm.get(string);
	            tm.put(string,conut+1);
	        }
	        else{
	            tm.put(string, 1);
	            result.append(string + " ");
	        }
	    }
	    String ret = "";
     	Set<Entry<String, Integer>> set = tm.entrySet();
     	Iterator<Entry<String, Integer>> iter = set.iterator();
     	while(iter.hasNext()){
     		Entry<String, Integer> map = iter.next();
     		String k = map.getKey();
     		ret += k + " ";
     		int v = map.getValue();
     		//System.out.println(k+"("+v+")  ");
     	}
		return result.toString();
	}
}