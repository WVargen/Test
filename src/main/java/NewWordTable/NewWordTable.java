package NewWordTable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class NewWordTable{
	public static String handleWordTable(String inputstring) {
		List<String> strings = Arrays.asList(inputstring.split(" "));
		TreeMap<String,Integer> tm = new TreeMap<String,Integer>();
	    for(int i = 0 ; i<strings.size() ; i++){
	        String string = strings.get(i);
	        if(tm.containsKey(string)){
	            int conut = tm.get(string);
	            tm.put(string,conut+1);
	        }
	        else{
	            tm.put(string, 1);
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
     		System.out.println(k+"("+v+")  ");
     	}
		return ret;
	}
}