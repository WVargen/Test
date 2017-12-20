package com.liangli.nj.database;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.liangli.nj.utils.Strings;

public class BaseTable {

	public final static int NOT_SET = Integer.MIN_VALUE;
	
	@Column(ignore = true)
	private String[] selectColumns;
	
	public BaseTable select(String... columns)
	{
		this.selectColumns = columns;
		
		return this;
	}
	
	public boolean isInSelectColumns(String name)
	{
		if (selectColumns == null || name == null)
		{
			return true;
		}
		
		for (String column : selectColumns)
		{
			if (name.equals(column))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public String tableName() {
		return "`" + getClass().getSimpleName() + "`";
	}
	
	public String column(String name)
	{
		return tableName() + ".`" + name + "`";
    }

	private Map<String, String> get_object_vars() {
		Map<String, String> result = new HashMap<>();

		Field[] fs = getClass().getDeclaredFields();

		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			field.setAccessible(true);

			try {
				Column annotation = field.getAnnotation(Column.class);

				if (annotation != null && annotation.ignore()) {
					continue;
				}

				Class<?> type = field.getType();
				String name = field.getName();
				Object val = field.get(this);			
				
				if (!isInSelectColumns(name))
				{
					continue;
				}

				if (type == String.class 
						|| type == int.class 
						|| type == long.class
						|| type == float.class
						|| type == double.class) {
					result.put(name, val == null ? null : String.valueOf(val));
				}
			} catch (Exception e) {

			}
		}

		return result;
	}
	
	private Map<String, Object> get_object_vars_object() {
		Map<String, Object> result = new HashMap<>();

		Field[] fs = getClass().getDeclaredFields();

		for (int i = 0; i < fs.length; i++) {
			Field field = fs[i];
			field.setAccessible(true);

			try {
				Column annotation = field.getAnnotation(Column.class);

				if (annotation != null && annotation.ignore()) {
					continue;
				}

				String name = field.getName();
				Object val = field.get(this);			
				
				if (!isInSelectColumns(name))
				{
					continue;
				}

				result.put(name, val);
				
			} catch (Exception e) {

			}
		}

		return result;
	}

	public String columns()
    {
        String result = "";
        String tablename = tableName();
        Map<String, String>vals = get_object_vars();

        Iterator<String> it = vals.keySet().iterator();

        while(it.hasNext())
        {
            String name = it.next();
            result += "," + tablename + ".`" + name + "`";
        }

        return cutHead(result, ",");
    }

	private String cutHead(String str, String prefix) {
		if (str != null && prefix != null) {
			if (str.startsWith(prefix)) {
				return str.substring(prefix.length());
			}
		}

		return str;
	}
	
	public String insertClause()
    {
        String sql = "";
        List<String> nameArray = new ArrayList<>();
        List<String> valueArray = new ArrayList<>();
        Map<String, Object> obj_vals = get_object_vars_object();

        Iterator<String> it = obj_vals.keySet().iterator();

        while(it.hasNext())
        {
            String name = it.next();
            Object value = obj_vals.get(name);
            
            if (value != null
            		&& (value.getClass() == int.class
            		|| value.getClass() == Integer.class
            		|| value.getClass() == long.class
            		|| value.getClass() == Long.class))
            {
            	if (Strings.parseInt(String.valueOf(value), 0) == NOT_SET)
            	{
            		continue;
            	}
            }

            nameArray.add(name);

            if (Strings.isEmpty(value))
            {
                valueArray.add("NULL");
            }
            else
            {
                valueArray.add("'" + escape(String.valueOf(value)) + "'");
            }
        }

        int index = 0;

        for (String name : nameArray)
        {
            if (index == 0)
            {
                sql += "`" + name + "`";
            }
            else
            {
                sql = sql + ",`" + name + "`";
            }

            index ++;
        }

        sql = sql + ") VALUES(";
        index = 0;

        for (String value : valueArray)
        {
            if (index == 0)
            {
                sql += value;
            }
            else
            {
                sql = sql + "," + value;
            }

            index ++;
        }

        return sql;
    }
	
	protected String escape(String keyWord)
    {
        if (keyWord != null)
        {
//            keyWord = keyWord.replace("/", "//");
            keyWord = keyWord.replace("'", "''");
//            keyWord = keyWord.replace("[", "/[");
//            keyWord = keyWord.replace("]", "/]");
//            keyWord = keyWord.replace("%", "/%");
//            keyWord = keyWord.replace("&", "/&");
//            keyWord = keyWord.replace("_", "/_");
//            keyWord = keyWord.replace("(", "/(");
//            keyWord = keyWord.replace(")", "/)");
        }

        return keyWord;
    }
	
	public static String unescape(String keyWord)
    {
        if (keyWord != null)
        {
            keyWord = keyWord.replace("//", "/");
            keyWord = keyWord.replace("/[", "[");
            keyWord = keyWord.replace("/]", "]");
            keyWord = keyWord.replace("/%", "%");
            keyWord = keyWord.replace("/&", "&");
            keyWord = keyWord.replace("/_", "_");
            keyWord = keyWord.replace("/(", "(");
            keyWord = keyWord.replace("/)", ")");
        }

        return keyWord;
    }
    
    public String updateClause()
    {
        Map<String, Object> obj_vals = get_object_vars_object();
        Iterator<String> it = obj_vals.keySet().iterator();

        String result = "";

        while(it.hasNext())
        {
            String name = it.next();
            Object value = obj_vals.get(name);

            if (value == null)
            {
            	result +="`" + name + "`= NULL,";
            }
            else if (value.getClass() == int.class 
            		|| value.getClass() == float.class
            		|| value.getClass() == long.class
            		|| value.getClass() == double.class)
            {
                result += "`" + name + "`=" + value + ",";
            }
			else 
			{
				result += "`" + name + "`='" + escape(String.valueOf(value)) + "',";

			}
		}

        return cutEnd(result, ",");
    }
    
    public static String cutEnd(String str, String prefix)
    {
        if (str != null && prefix != null)
        {
            int index = str.lastIndexOf(prefix);

            if (index > 0 && index + prefix.length() == str.length())
            {
                return str.substring(0, index);
            }
        }

        return str;
    }

}
