package com.liangli.nj.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Strings
{
    /**
     * Compares 2 strings. AAA&lt;AAa&lt;aAA&lt;Bbb&lt;bBB&lt;CCC&lt;CCCc
     * 
     * @return negative if s1 &lt; s2, 0 if s1 == s2, positive if s1 &gt; s2
     */
    public static int compare(String s1, String s2)
    {
        int len1 = s1.length();
        int len2 = s2.length();
        int len = Math.min(len1, len2);
        for (int i = 0; i < len; ++i)
        {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            char l1 = Character.toLowerCase(c1);
            char l2 = Character.toLowerCase(c2);
            if (l1 != l2)
            {
                return l1 < l2 ? -1 : 1;
            }

            if (c1 != c2)
            {
                return c1 < c2 ? -1 : 1;
            }
        }
        return len1 - len2;
    }

    /**
     * Compare protocol commands, why we don't use
     * string.equals/string.equalsIgnoreCase is because we need to make it be
     * easy maintained. Like, if we want to make the command comparison be case
     * insensitive, we only need to change this method.
     * 
     * @param cmd
     *            the first command
     * @param another
     *            another command
     * 
     * @return true for command equality, false for otherwise
     */
    public static boolean equalCommands(String cmd, String another)
    {
        return equalsIgnoreCase(cmd, another);
    }

    /**
     * Compare user names, why we don't use
     * string.equals/string.equalsIgnoreCase is because we need to make it be
     * easy maintained. Like, if we want to make the name comparison be case
     * insensitive, we only need to change this method.
     * 
     * @param name
     *            the first name
     * @param another
     *            another name
     * @return true for name equality, false for otherwise
     */
    public static boolean equalNames(String name, String another)
    {
        return equals(name, another);
    }

    /**
     * Checks if two strings are equals. What this method differs from
     * String.equals(s) is that this method allows the strings to be null.
     * 
     * @param s1
     *            the first input string
     * @param s2
     *            the second input string
     * @return true when two not null strings are equal, false for otherwise.
     */
    public static boolean equals(String s1, String s2)
    {
        if (s1 == null || s2 == null)
        {
            return false;
        }
        return s1.equals(s2);
    }

    /**
     * Checks if two strings are equal case insensitively. What this method
     * differs from String.equals(s) is that this method allows the strings to
     * be null.
     * 
     * @param s1
     *            the first input string
     * @param s2
     *            the second input string
     * @return true when two not null strings are equal case insensitively,
     *         false for otherwise.
     */
    public static boolean equalsIgnoreCase(String s1, String s2)
    {
        if (s1 == null || s2 == null)
        {
            return false;
        }
        return s1.equalsIgnoreCase(s2);
    }

    /**
     * Checks if all the chars in the string are digits.
     * 
     * @param str
     *            the input string
     * @return true for all digits, false for otherwise.
     */
    public static boolean isDigits(String str)
    {
        if (nullOrEmpty(str))
        {
            return false;
        }

        Pattern pattern = Pattern.compile("-?[0-9]+");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches())
        {
            return false;
        }
        return true;
    }

    /**
     * Joins multiple numbers to a string with specified separator.
     * 
     * @param numbers
     *            The set of numbers
     * @param separator
     *            The specified separator
     * @return The joined string.
     */
    public static String joinIntegerSet(final Set<Integer> numbers, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final int number : numbers)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(number);
            first = false;
        }
        return builder.toString();
    }

    /**
     * Joins multiple numbers to a string with specified separator.
     * 
     * @param numbers
     *            The set of numbers
     * @param separator
     *            The specified separator
     * @return The joined string.
     */
    public static String joinNumbers(final Set<Long> numbers, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Long number : numbers)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(number);
            first = false;
        }
        return builder.toString();
    }

    /**
     * Joins multiple strings to a string with specified separator.
     * 
     * @param strings
     *            The set of strings;
     * @param separator
     *            The specified separator
     * @return The joined string.
     */
    public static String joinStrings(final Collection<String> strings, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final String str : strings)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(str);
            first = false;
        }
        return builder.toString();
    }

    /**
     * Checks if the string is not null or empty.
     * 
     * @param str
     *            the input string
     * @return true when the string is not null or empty, false for otherwise.
     */
    public static boolean notNullOrEmpty(String str)
    {
        return str != null && str.length() > 0 && !equals(str, "null");
    }

    /**
     * Checks if the string is null or empty.
     * 
     * @param str
     *            the input string
     * @return true when the string is either null or empty, false for
     *         otherwise.
     */
    public static boolean nullOrEmpty(String str)
    {
        return str == null || str.length() == 0;
    }

    /**
     * Returns a literal pattern <code>String</code> for the specified
     * <code>String</code>.
     * 
     * <p>
     * This method produces a <code>String</code> that can be used to create a
     * <code>Pattern</code> that would match the string <code>s</code> as if it
     * were a literal pattern.
     * </p>
     * Metacharacters or escape sequences in the input sequence will be given no
     * special meaning.
     * 
     * @param s
     *            The string to be literalized
     * @return A literal string replacement
     */
    public static String quote(String s)
    {
        int slashEIndex = s.indexOf("\\E");
        if (slashEIndex == -1)
            return "\\Q" + s + "\\E";

        String sb = "\\Q";

        slashEIndex = 0;
        int current = 0;
        while ((slashEIndex = s.indexOf("\\E", current)) != -1)
        {
            sb += s.substring(current, slashEIndex);
            current = slashEIndex + 2;
            sb += "\\E\\\\E\\Q";
        }
        sb += s.substring(current, s.length());
        sb += "\\E";
        return sb;
    }

    /**
     * Checks if the string starts with the given char.
     * 
     * @param s
     *            the string
     * @param ch
     *            the char
     * @return true if the string starts with the char, false for otherwise.
     */
    public static boolean startsWith(String s, char ch)
    {
        return !s.isEmpty() && s.charAt(0) == ch;
    }

    public static String joinLong(final Collection<Long> numbers, final char separator)
    {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (final long number : numbers)
        {
            if (!first)
            {
                builder.append(separator);
            }
            builder.append(number);
            first = false;
        }
        return builder.toString();
    }

    public static int parseInt(String s, int defaultValue)
    {
        if (nullOrEmpty(s))
        {
            return defaultValue;
        }

        try
        {
            return Integer.parseInt(s);
        }
        catch (Exception e)
        {

        }

        return defaultValue;
    }

    public static long parseLong(String s, long defaultValue)
    {
        if (nullOrEmpty(s))
        {
            return defaultValue;
        }

        try
        {
            return Long.parseLong(s);
        }
        catch (Exception e)
        {

        }

        return defaultValue;
    }

    public static double parseDouble(String s, double defaultValue)
    {
        if (nullOrEmpty(s))
        {
            return defaultValue;
        }

        try
        {
            return Double.parseDouble(s);
        }
        catch (Exception e)
        {

        }
        return defaultValue;
    }
    
    public static double parseFloat(String s, float defaultValue)
    {
        if (nullOrEmpty(s))
        {
            return defaultValue;
        }

        try
        {
            return Float.parseFloat(s);
        }
        catch (Exception e)
        {

        }
        return defaultValue;
    }

    public static boolean parseBoolean(String s, boolean defaultValue)
    {
        if (nullOrEmpty(s))
        {
            return defaultValue;
        }

        try
        {
            if (s.equals("1"))
            {
                return true;
            }

            return Boolean.parseBoolean(s);
        }
        catch (Exception e)
        {

        }

        return defaultValue;
    }

    public static String getNotNullString(String s)
    {
        if (s == null)
        {
            return "";
        }

        return s;
    }

    public static double string2Double(String str) {
        if (str == null || "".equals(str)) {
            return 0.00;
        } else {
            try {
                double d = Double.parseDouble(str);
                return d;
            } catch (Exception e) {
                e.printStackTrace();
                return 0.00;
            }
        }
    }

    public static String double2String(double d) {
        if (d == 0) {
            return "0.00";
        }
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(d);
    }

    /**
     * 判断对象是否为空
     *
     * @param obj
     * @return boolean
     * @method isEmpty
     */
    public static boolean isEmpty(Object obj) {
        if (obj instanceof String) {
            return nullOrEmpty((String) obj);
        }
        if (obj instanceof List<?>) {
            List<?> list = (List<?>) obj;
            return list == null || list.size() == 0;
        }
        
        if (obj instanceof Set<?>) {
        	Set<?> set = (Set<?>) obj;
            return set == null || set.size() == 0;
        }
        
        if (obj instanceof Map<?, ?>) {
            Map<?, ?> map = (Map<?, ?>) obj;
            return map == null || map.size() == 0;
        }
        if (obj instanceof int[]) {
            int[] array = (int[]) obj;
            return array == null || array.length == 0;
        }
        if (obj instanceof float[]) {
            float[] array = (float[]) obj;
            return array == null || array.length == 0;
        }
        if (obj instanceof long[]) {
            long[] array = (long[]) obj;
            return array == null || array.length == 0;
        }
        if (obj instanceof double[]) {
            double[] array = (double[]) obj;
            return array == null || array.length == 0;
        }
        if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            return array == null || array.length == 0;
        }
        if (obj instanceof Object) {
            return obj == null;
        }
        return true;
    }

    public static ArrayList<String> split(String target, String... params)
    {
        ArrayList<String> array = new ArrayList<>();

        if (target == null)
        {
            return array;
        }

        array.add(target);

        for (String param : params)
        {
            array = split(array, param);
        }

        return array;
    }

    private static ArrayList<String> split(ArrayList<String> targets, String split)
    {
        ArrayList<String> array = new ArrayList<>();

        for (String target : targets)
        {
            try
            {
                if (".".equals(split))
                {
                    split = "\\.";
                }

                String[] ss = target.split(split);

                for (String s : ss)
                {
                    array.add(s);
                }
            }
            catch (Exception e)
            {

            }
        }

        return array;
    }
    
    public static boolean isChinese(String s)
    {
        if (s == null)
        {
            return true;
        }

        for (int i = 0; i < s.length(); i ++)
        {
            if (!isChinese(s.charAt(i)))
            {
                return false;
            }
        }

        return true;
    }

    public static boolean isChinese(char c)
    {
        int min = 0x4E00;
        int max = 0x9FBF;
        int target = c;
        return (target <= max && target >= min) ? true : false;
    }

    public static long stringToTimeStamp(String time, String format)
    {
        SimpleDateFormat fmt = new SimpleDateFormat(format);
        try {
            Date date = fmt.parse(time);
            return date.getTime();
        } catch (ParseException e) {
            return 0;
        }
    }

    public static String timestampToString(long time)
    {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String nowString = null;

        try
        {
            nowString = df.format(new Date(time));
        }
        catch(Exception e)
        {

        }

        return nowString;
    }

    public static String timestampToString(long time, String format)
    {
        DateFormat df = new SimpleDateFormat(format);
        String nowString = null;

        try
        {
            nowString = df.format(new Date(time));
        }
        catch(Exception e)
        {

        }

        return nowString;
    }

    /**
     * bytes转换成十六进制字符串
     * @param b byte数组
     * @return String 每个Byte值之间空格分隔
     */
    public static String byte2HexStr(byte[] b)
    {
        String stmp="";
        StringBuilder sb = new StringBuilder("");
        for (int n=0;n<b.length;n++)
        {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length()==1)? "0"+stmp : stmp);
            sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }

    /**
     * Count how many marks existed in string.
     *
     * @param string
     *            The source sentence.
     * @param mark
     *            The specific substring to count.
     * @return The number of marks existed in string.
     */
    public static int count(String string, String mark) {
        if (!isEmpty(string) && !isEmpty(mark)) {
            int count = 0;
            int index = string.indexOf(mark);
            while (index != -1) {
                count++;
                string = string.substring(index + mark.length());
                index = string.indexOf(mark);
            }
            return count;
        }
        return 0;
    }
    
    public static double getDiscountPrice(double f)
	{
		BigDecimal b = new BigDecimal(f);  
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		
		return f1;
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
    
    public static <K, T> Map<K, T> listToMap(List<T> list, KeyGenerator<K, T> generator)
    {
        Map<K, T> map = new HashMap<>();

        if (list == null)
        {
            return map;
        }

        for (T t : list)
        {
            map.put(generator.getKey(t), t);
        }

        return map;
    }
    
    public static interface KeyGenerator<K, T> {

        K getKey(T instance);
    }
    
    public static String cutEndTrim(String str)
    {
        if (Strings.notNullOrEmpty(str))
        {
            int index = str.lastIndexOf("\n");

            if (index >= 0 && index == str.length() - 1)
            {
                return cutEndTrim(str.substring(0, index));
            }

            index = str.lastIndexOf(" ");

            if (index >= 0 && index == str.length() - 1)
            {
                return cutEndTrim(str.substring(0, index));
            }
            
            index = str.lastIndexOf(String.valueOf((char)160));

            if (index >= 0 && index == str.length() - 1)
            {
                return cutEndTrim(str.substring(0, index));
            }
            
            index = str.lastIndexOf(String.valueOf((char)12288));

            if (index >= 0 && index == str.length() - 1)
            {
                return cutEndTrim(str.substring(0, index));
            }
          
        }

        return str;
    }

    public static String cutHeadTrim(String str)
    {
        if (Strings.notNullOrEmpty(str))
        {
            int index = str.indexOf("\n");

            if (index == 0)
            {
                return cutHeadTrim(str.substring(1));
            }

            index = str.indexOf(" ");

            if (index == 0)
            {
                return cutHeadTrim(str.substring(1));
            }
            
            index = str.indexOf(String.valueOf((char)160));

            if (index == 0)
            {
                return cutHeadTrim(str.substring(1));
            }
            
            index = str.indexOf(String.valueOf((char)12288));

            if (index == 0)
            {
                return cutHeadTrim(str.substring(1));
            }
        }

        return str;
    }
    
    public static String cutHead(String str, String prefix)
    {
        if (str != null && prefix != null)
        {
            if (str.startsWith(prefix))
            {
                return cutHead(str.substring(prefix.length()), prefix);
            }
        }

        return str;
    }
    
    public static String trimBaiduLogo(String s)
    {
    	if (s != null)
    	{
    		return s.replaceAll("_豆丁网", "")
    				.replaceAll("_百度百科", "")
    				.replaceAll("_百度文库", "")
    				.replaceAll("_百度知道", "")
    				.replaceAll("_百度", "")
    				.replaceAll("_爱问知识人", "")
    				.replaceAll("迅雷看看", "")
    				.replaceAll("丁丁问吧", "");
    	}
    	
    	return s;
    }
    
    public static String toChinese(int intInput) {  
        String si = String.valueOf(intInput);  
        String sd = "";  
        if (si.length() == 1) // 個  
        {  
            sd += GetCH(intInput);  
            return sd;  
        } else if (si.length() == 2)// 十  
        {  
            if (si.substring(0, 1).equals("1"))  
                sd += "十";  
            else  
                sd += (GetCH(intInput / 10) + "十");  
            sd += toChinese(intInput % 10);  
        } else if (si.length() == 3)// 百  
        {  
            sd += (GetCH(intInput / 100) + "百");  
            if (String.valueOf(intInput % 100).length() < 2)  
                sd += "零";  
            sd += toChinese(intInput % 100);  
        } else if (si.length() == 4)// 千  
        {  
            sd += (GetCH(intInput / 1000) + "千");  
            if (String.valueOf(intInput % 1000).length() < 3)  
                sd += "零";  
            sd += toChinese(intInput % 1000);  
        } else if (si.length() == 5)// 萬  
        {  
            sd += (GetCH(intInput / 10000) + "万");  
            if (String.valueOf(intInput % 10000).length() < 4)  
                sd += "零";  
            sd += toChinese(intInput % 10000);  
        }  
  
        return sd;  
    }  
  
    private static String GetCH(int input) {  
        String sd = "";  
        switch (input) {  
        case 1:  
            sd = "一";  
            break;  
        case 2:  
            sd = "二";  
            break;  
        case 3:  
            sd = "三";  
            break;  
        case 4:  
            sd = "四";  
            break;  
        case 5:  
            sd = "五";  
            break;  
        case 6:  
            sd = "六";  
            break;  
        case 7:  
            sd = "七";  
            break;  
        case 8:  
            sd = "八";  
            break;  
        case 9:  
            sd = "九";  
            break;  
        default:  
            break;  
        }  
        return sd;  
    }  
    
    public static int countPattern(String str, String pattern)
    {
		int count = 0;

		if (str != null && pattern != null) {
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(str);

			while (m.find()) {
				count++;
			}
		}

		return count;
    }
    
    public static String getPattern(String str, String pattern)
    {
    	Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(str);

		while(m.find())
		{
			try
			{
				return m.group(1);
			}
			catch (Exception e)
			{
				
			}
		}
		
		return null;
    }
    
    public static boolean isEqual(String str, String[] compares)
    {
    	if (compares != null)
    	{
    		for (String s : compares)
    		{
    			if (s.equals(str))
    			{
    				return true;
    			}
    		}
    	}
    	
    	return false;
    }
    
    public static <T> List<T> toList(T[] tt)
    {
        List<T> list = new ArrayList<>();

        for (T t : tt)
        {
            list.add(t);
        }

        return list;
    }
    
    public static String getContextPath()
    {
    	String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    	
    	return path.substring(0,  path.length() - 1);
    }
    
    public static Properties readProperties(String path)
    {
        Properties p = new Properties();
        try {
            p.load(new InputStreamReader(new FileInputStream(path), "utf-8"));

            return p;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Properties();
    }
}
