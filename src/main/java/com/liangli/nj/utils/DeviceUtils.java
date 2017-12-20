package com.liangli.nj.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * Created by franktang on 15-3-5.
 */
public class DeviceUtils
{
    public static class Weak
    {
        public static boolean isValidWeak(WeakReference<?> weak)
        {
            if (weak != null && weak.get() != null)
            {
                return true;
            }

            return false;
        }

        public static <T> T get(WeakReference<T> weak)
        {
            T object = null;

            if (weak != null && (object = weak.get()) != null)
            {
                return object;
            }

            return null;
        }
    }

    public static class string
    {
		public static boolean isJianti(String str) 
		{
			try 
			{
				if (!str.equals(new String(str.getBytes("gb2312"), "gb2312"))) 
				{
//					System.out.println("是繁体");
					return false;
				}
				else
				{
//					System.out.println("是简体");
					
					return true;
				}
			} catch (Exception exception3) {

			}

			return false;
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
    	
		public static String toHexString(byte[] b) 
		{
			StringBuilder sb = new StringBuilder();
			
			for (int i = 0; i < b.length; i++) {
				String hex = Integer.toHexString(b[i] & 0xFF);
				if (hex.length() == 1) {
					hex = '0' + hex;
				}
				sb.append(hex.toUpperCase() + " ");
			}
			
			return sb.toString();
		}
    	 
        public static String getCharacter(int[] codes)
        {
            StringBuilder sb = new StringBuilder();

            for (int alphabet : codes)
            {
                sb.append(Character.toString((char)alphabet));
            }

            return sb.toString();
        }
        
        public static String getString(Object obj)
        {
            if (obj == null)
            {
                return null;
            }

            return String.valueOf(obj);
        }

        public static String getThrowableDescription(Throwable throwable)
        {
            if (throwable == null)
            {
                return null;
            }

            StackTraceElement[] elements = throwable.getStackTrace();

            if (elements == null)
            {
                return null;
            }

            StringBuilder sb = new StringBuilder();

            for (StackTraceElement element : elements)
            {
                sb.append(element.toString() + "\n");
            }

            return sb.toString();
        }

        public static boolean isChinese(char c)
        {
            int min = 0x4E00;
            int max = 0x9FBF;
            int target = c;
            return (target <= max && target >= min) ? true : false;
        }

        public static boolean isEmpty(String target)
        {
            return target == null || target.length() == 0;
        }

        public static boolean getBoolean(String s)
        {
            boolean k = false;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                if (s.equals("1"))
                {
                    return true;
                }

                k = Boolean.parseBoolean(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static int getInteger(String s)
        {
            int k = -1;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Integer.parseInt(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static int getIntegerDefaultZero(String s)
        {
            int k = 0;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Integer.parseInt(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static long getLong(String s)
        {
            long k = -1;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Long.parseLong(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static long getLong(String s, long def)
        {
            long k = Long.valueOf(def);

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Long.parseLong(s);
            }
            catch (Exception e)
            {

            }

            return k;
        }

        public static double getDouble(String s)
        {
            double k = -1.0;

            if (isEmpty(s))
            {
                return k;
            }

            try
            {
                k = Double.parseDouble(s);
            }
            catch (Exception e)
            {

            }
            return k;
        }

        public static String getNotNullString(String s)
        {
            if (isEmpty(s))
            {
                return "";
            }

            return s;
        }
    }

    public static class file
    {
    	public static boolean compress(String basePath, String outputFilePath) {
            ZipOutputStream zos = null;

            try {
                String[] rels = new File(basePath).list();
                
                if (rels == null)
                {
                	return false;
                }
                
            	checkAndCreateDirectory(outputFilePath);
                zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outputFilePath)));

                for (String rel : rels)
                {
                	zipPath(basePath, "/" + rel, zos);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (zos != null) {
                        zos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            
            return true;
        }

        public static void zipPath(String basePath, String relativePath, ZipOutputStream zos) throws IOException {

            String fullPath = basePath + relativePath;
            File file = new File(fullPath);

            if (file.isDirectory()) {
                zipDirectory(basePath, relativePath, zos);
            } else {
                zipFile(basePath, relativePath, zos);
            }
        }


        /**
         * Method that get input file path, zip it and return File based on passed outputFilePath.
         *
         * @return Compressed file.
         */
        public static void zipFile(String basePath, String relativePath, ZipOutputStream zos) throws IOException {

            String fullPath = basePath + relativePath;
            
            if (!isExist(fullPath))
            {
            	return;
            }
            
//            System.out.println("frank relative path:" + relativePath);
            ZipEntry entry = new ZipEntry(relativePath);
            zos.putNextEntry(entry);
            FileInputStream in = new FileInputStream(fullPath);

            int len;
            byte[] buffer = new byte[1024];
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }
            zos.closeEntry();
            in.close();
        }

        /**
         * Method that get input directory path, zip it and return File based on passed outputFilePath.
         *
         * @return Compressed file.
         */
        public static void zipDirectory(String basePath, String relativePath, ZipOutputStream zos) throws IOException {

            String fullPath = basePath + relativePath;
            
            if (!isExist(fullPath))
            {
            	return;
            }
            
            String[] list = new File(fullPath).list();

            for (String filename : list) {
                zipPath(basePath, relativePath + "/" + filename, zos);
            }
        }
    	    
        public static boolean isExist(String path)
        {
            try
            {
                if (path == null)
                {
                    return false;
                }

                return new File(path).exists();
            }
            catch (Exception e)
            {

            }

            return false;
        }

        public static String getFilename(String path)
        {
            if (path == null)
            {
                return path;
            }

            int index = path.lastIndexOf("/");

            if (index != -1)
            {
                return path.substring(index + 1);
            }

            return path;
        }

        public static long getFileSize(String path)
        {
            long size = -1;
            try
            {
                String name = path.substring(path.lastIndexOf("/") + 1, path.length());
                path = path.substring(0, path.lastIndexOf("/"));
                File file = new File(path, name);
                if (file.exists())
                {
                    size = file.length();
                }
            }
            catch (Exception e)
            {
                System.out.println("file error:" + e.getMessage());
            }

            return size;
        }

        /*
         * sample:
         * AvqUtils.file.extractZipFile(CacheManager.get().getDebugLogPath
         * ("test.zip"), CacheManager.get().getZipFolderPath());
         */
        public static Map<String, String> extractZipFile(String source, String desBase)
        {
            if (source == null || desBase == null)
            {
                return null;
            }

            File file = new File(source);

            if (file.exists() == false)
            {
                return null;
            }

            String endString = desBase.substring(desBase.length() - 1, desBase.length());
            String base = String.valueOf(desBase);
            if (endString.equals("/") == false)
            {
                base = base + "/";
            }

            try
            {
                ZipEntry entry;
                ZipFile zipInStream = new ZipFile(source);
                Enumeration<? extends ZipEntry> e = zipInStream.entries();

                FileOutputStream fos = null;
                InputStream is = null;
                byte[] buffer = new byte[1024 * 32];
                int k = 0;
                Map<String, String> map = new HashMap<String, String>();

                while (e.hasMoreElements())
                {
                    entry = (ZipEntry) e.nextElement();
                    String entryName = entry.getName();
                    String outPath = base + entryName;
                    if (entry.isDirectory())
                    {
                        continue;
                    }

//                    System.out.println("zip:" + entryName);

                    checkAndCreateDirectory(outPath);

                    fos = new FileOutputStream(new File(outPath));
                    is = zipInStream.getInputStream(entry);

                    while ((k = is.read(buffer, 0, buffer.length)) > 0)
                    {
                        fos.write(buffer, 0, k);
                        fos.flush();
                    }

                    fos.close();
                    is.close();
                    map.put(entryName, outPath);
                }

                zipInStream.close();

                return map;
            }
            catch (Exception e)
            {
                System.out.println("zip error:" + e.getMessage());
            }

            return null;
        }

        public static boolean deleteFile(String path)
        {
            boolean result = false;

            try
            {
                if (path != null)
                {
                    File file =  new File(path);
                    result = file.delete();
                }
            }
            catch (Exception e)
            {

            }

            return result;
        }


        public static boolean deleteDirectory(File dir)
        {
            if (dir == null)
            {
                return true;
            }

            if (!dir.exists() || !dir.isDirectory())
            {
                return false;
            }

            String[] files = dir.list();

            for (int i = 0, len = files.length; i < len; i++)
            {
                File f = new File(dir, files[i]);

                if (f.isDirectory())
                {
                    deleteDirectory(f);
                }
                else
                {
                    f.delete();
                }
            }

            return dir.delete();
        }

        public static void copyFile(String from, String to)
        {
            if (from == null || to == null)
            {
                return;
            }
            try
            {
                File fromFile = new File(from);
                if (fromFile.exists() == false)
                {
                    return;
                }
                checkAndCreateDirectory(to);
                File toFile = new File(to);
                FileInputStream fis = new FileInputStream(fromFile);
                FileOutputStream fos = new FileOutputStream(toFile);

                byte[] buffer = new byte[32 * 1024];
                int k;
                while ((k = fis.read(buffer)) > 0)
                {
                    fos.write(buffer, 0, k);
                    fos.flush();
                }
                fis.close();
                fos.close();
            }
            catch (Exception e)
            {

            }
        }

        public static void printLogToFile(String filename, String text, boolean append)
        {
            if (filename == null || text == null)
            {
                return;
            }

            try
            {
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String nowString = df.format(new Date());
                PrintWriter pw = new PrintWriter(new FileWriter(filename, append));
                pw.println();
                pw.println(nowString + "\r\n" + text);
                pw.close();
            }
            catch (Exception e)
            {

            }
        }

        public static boolean rename(String from, String to)
        {
        	checkAndCreateDirectory(to);
            File source = new File(from);
            return source.renameTo(new File(to));
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

        public static byte[] readFromInputStream(InputStream is)
        {
            if (is == null)
            {
                return new byte[0];
            }

            try
            {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 32];
                int k = 0;
                while ((k = is.read(buffer)) > 0)
                {
                    bos.write(buffer, 0, k);
                    bos.flush();
                }

                is.close();
                bos.close();

                return bos.toByteArray();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return new byte[0];
        }

        public static void writeToFile(String path, byte[] buffer)
        {
            if (buffer == null || path == null)
            {
                return;
            }

            checkAndCreateDirectory(path);

            try
            {
                FileOutputStream fos = new FileOutputStream(new File(path));
                fos.write(buffer);
                fos.flush();
                fos.close();

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // String baseUrl="/data/data/"+this.getPackageName()+"/";
        public static void scanFolder(String baseUrl, Callback<String> callback)
        {

            if (baseUrl == null)
            {
                return;
            }
            
            baseUrl = checkPathWithSlash(baseUrl);

            if (new File(baseUrl).exists() == false)
            {
                return;
            }

            File file = new File(baseUrl);
            String[] list = file.list();

            if (list == null)
            {
                return;
            }

            for (String s : list)
            {
                File subFile = new File(baseUrl + s);
                if (subFile.isDirectory())
                {
                    scanFolder(baseUrl + s + "/");
                }
                else
                {
                    String path = baseUrl + s;
                    
                    if (callback != null)
                    {
                    	callback.execute(path);
                    }
                }

            }
        }
        
        public static void scanFolder(String baseUrl)
        {
        	scanFolder(baseUrl, new Callback<String>() {
				
				@Override
				public void execute(String path) {
                    
                    System.out.println(path + "\nsize: " + new File(path).length());
				}
			});
        }

        public static void newCopyFoler(String baseUrl, String outputUrl)
        {
            baseUrl = checkPathWithSlash(baseUrl);
            outputUrl = checkPathWithSlash(outputUrl);

            File file = new File(baseUrl);
            
            if (!file.exists())
            {
            	return;
            }
            
            String[] list = file.list();

            for (String s : list)
            {
                File subFile = new File(baseUrl + s);

                if (subFile.isDirectory())
                {
                    File outFile = new File(outputUrl + s);
                    outFile.mkdirs();
                    newCopyFoler(baseUrl + s, outputUrl + s);
                }
                else
                {
                    try
                    {
                        Stream.CopyFile(baseUrl + s, outputUrl + s);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }

                }
            }
        }

        public static String checkPathWithSlash(String path)
        {
            if (Strings.notNullOrEmpty(path)
                    && "/".equals(path.substring(path.length() - 1)) == false)
            {
                return path + "/";
            }

            return path;
        }

        public static String checkPathWithoutSlash(String path)
        {
            if (Strings.notNullOrEmpty(path)
                    && "/".equals(path.substring(path.length() - 1)))
            {
                return path.substring(0, path.length() - 1);
            }

            return path;
        }

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
    }

    public static class Stream
    {
        public static byte[] getBytes(InputStream is)
        {
            return getBytes(is, true);
        }

        public static byte[] getBytes(InputStream is, boolean isCloseStream)
        {
            if (is == null)
            {
                return null;
            }

            try
            {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024 * 32];
                int k = 0;
                while ((k = is.read(buffer)) > 0)
                {
                    baos.write(buffer, 0, k);
                    baos.flush();
                }

                if (isCloseStream)
                {
                    is.close();
                }

                baos.close();

                return baos.toByteArray();
            }
            catch (Exception e)
            {

            }

            return null;
        }

        public static void CopyFile(String scrPath, String desPath) throws IOException
        {
            file.checkAndCreateDirectory(desPath);
            FileInputStream fis = new FileInputStream(new File(scrPath));
            FileOutputStream fos = new FileOutputStream(new File(desPath));
            byte[] buffer = new byte[1024];
            int l;
            while ((l = fis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, l);
                fos.flush();
            }

            fis.close();
            fos.close();
        }

        public static void CopyFile(String srcPath, OutputStream fos) throws IOException
        {
            FileInputStream fis = new FileInputStream(new File(srcPath));
            byte[] buffer = new byte[1024];
            int l;
            while ((l = fis.read(buffer)) > 0)
            {
                fos.write(buffer, 0, l);
                fos.flush();
            }

            fis.close();
        }
    }

    public static class encode
    {
        private static final String ALGORITHM = "MD5";

        private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c',
                'd', 'e', 'f' };

        /**
         * encode string
         *
         * @param algorithm
         * @param str
         * @return String
         */
        public static String encodeByAlgorithm(String algorithm, String str)
        {
            if (str == null)
            {
                return null;
            }
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }

        /**
         * encode By MD5
         *
         * @param str
         * @return String
         */
        public static String encodeByMD5(String str)
        {
            if (str == null)
            {
                return null;
            }
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                messageDigest.update(str.getBytes());
                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }

        /**
         * encode By MD5
         *
         * @return String
         */
        public static String endcodeByMD5(InputStream is)
        {
            if (is == null)
            {
                return null;
            }

            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                byte[] buffer = new byte[1024 * 32];
                int k = 0;

                while ((k = is.read(buffer)) > 0)
                {
                    messageDigest.update(buffer, 0, k);
                }

                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }
        }

        public static String encodeByMD5(byte[] bytes)
        {
            if (bytes == null)
            {
                return null;
            }
            try
            {
                MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM);
                messageDigest.update(bytes);
                return getFormattedText(messageDigest.digest());
            }
            catch (Exception e)
            {
                throw new RuntimeException(e);
            }

        }

        /**
         * Takes the raw bytes from the digest and formats them correct.
         *
         * @param bytes
         *            the raw bytes from the digest.
         * @return the formatted bytes.
         */
        private static String getFormattedText(byte[] bytes)
        {
            int len = bytes.length;
            StringBuilder buf = new StringBuilder(len * 2);
            for (int j = 0; j < len; j++)
            {
                buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
                buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
            }
            return buf.toString();
        }

        // public static void main(String[] args) {
        // System.out.println("111111 MD5  :"
        // + EncoderHandler.encodeByMD5("111111"));
        // System.out.println("111111 MD5  :"
        // + EncoderHandler.encode("MD5", "111111"));
        // System.out.println("111111 SHA1 :"
        // + EncoderHandler.encode("SHA1", "111111"));
        // }
    }

    public static class date
    {
        public static boolean isYesterday(long time)
        {
            long yesterday = System.currentTimeMillis() - 24 * 60 * 60 * 1000;

            if (getYearFromTimeSince1970(time) == getYearFromTimeSince1970(yesterday)
                    && getMonthFromTimeSince1970(time) == getMonthFromTimeSince1970(yesterday)
                    && getDayFromTimeSince1970(time) == getDayFromTimeSince1970(yesterday))
            {
                return true;
            }

            return false;
        }

        public static boolean isToday(long time)
        {
            long today = System.currentTimeMillis();

            if (getYearFromTimeSince1970(time) == getYearFromTimeSince1970(today)
                    && getMonthFromTimeSince1970(time) == getMonthFromTimeSince1970(today)
                    && getDayFromTimeSince1970(time) == getDayFromTimeSince1970(today))
            {
                return true;
            }

            return false;
        }

        public static int getYearFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            int year = d.get(Calendar.YEAR);
            return year;
        }

        public static int getMonthFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            int month = d.get(Calendar.MONTH) + 1;
            return month;
        }

        public static int getDayFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            int day = d.get(Calendar.DAY_OF_MONTH);
            return day;
        }

        public static int getHourFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            return d.get(Calendar.HOUR_OF_DAY);
        }

        public static int getMinuteFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            return d.get(Calendar.MINUTE);
        }

        public static int getSecondFromTimeSince1970(long time)
        {
            Calendar d = Calendar.getInstance();
            Date myDate = new Date(time);
            d.setTime(myDate);
            return d.get(Calendar.SECOND);
        }

        /**
         * format:yyyy年 MM月 dd日 HH时 mm分 ss秒
         */
        public static long stringToTimeStamp(String time, String format) {
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
    }

    public static class math
    {
    	public static long caculateTotalReturnLong(Map<Integer, Integer> map)
        {
    		return caculateTotal(map);
        }
    	
    	public static int caculateTotal(Map<Integer, Integer> map)
        {
            int total = 0;

            if (map != null) {

                for (Object key : map.keySet()) {
                    if (map.get(key) != null) {
                        total += map.get(key);
                    }
                }
            }

            return total;
        }
    	
        public static int random(double size)
        {
            return (int)(Math.random() * Math.pow(10, size));
        }

        public static int randomWithin(int min, int max)
        {
            int number = randomWithin(max);

            if (number < min)
            {
                return randomWithin(min, max);
            }

            return number;
        }
        
        //包括number
        public static int randomWithin(int number)
        {
            double random = Math.random();

            if (random == 1)
            {
                return randomWithin(number);
            }

            int n = 0;

            if (number >= 0)
            {
                n = number + 1;
            }
            else
            {
                n = number - 1;
            }

            int ns = 1;

            //一半概率是负数
            if (Math.random() > 0.5 && number < 0)
            {
                ns = -1;
            }

            return (int)(random * n) * ns;
        }

    }

    public static class calendar
    {
    	 // 获得本日第一天0点时间
        public static long getTimesTodaymorning() {
        	Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            
            return  cal.getTime().getTime();
        }
        
        // 获得本月第一天0点时间
        public static Date getTimesMonthmorning() {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            return  cal.getTime();
        }

        // 获得本月最后一天24点时间
        public static Date getTimesMonthnight() {
            Calendar cal = Calendar.getInstance();
            cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.set(Calendar.HOUR_OF_DAY, 24);
            return cal.getTime();
        }

        // 获得本月第一天0点时间
        public static Date getTimesMonthmorning(int year, int month) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, 1, 0, 0, 0);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
            return  cal.getTime();
        }

        // 获得本月最后一天24点时间
        public static Date getTimesMonthnight(int year, int month) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
            cal.set(Calendar.HOUR_OF_DAY, 24);
            return cal.getTime();
        }

        // 获得本月第一天0点时间
        public static Date getTimesMonthmorning(int year, int month, int day) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, 1, 0, 0, 0);
            cal.set(Calendar.DAY_OF_MONTH, day);
            return  cal.getTime();
        }

        // 获得本月最后一天24点时间
        public static Date getTimesMonthnight(int year, int month, int day) {
            Calendar cal = Calendar.getInstance();
            cal.set(year, month, cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
            cal.set(Calendar.DAY_OF_MONTH, day);
            cal.set(Calendar.HOUR_OF_DAY, 24);
            return cal.getTime();
        }
    }

    public static class list {
    	
    	public static interface KeyGenerator<K, T> {

            K getKey(T instance);
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
    }
    
    public static class http {
    	
    	public static void downloadToPath(String url, String path)
        {
            try {
            	file.checkAndCreateDirectory(path);
                URL uri = new URL(url);
                URLConnection conn = uri.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                
                InputStream is = conn.getInputStream();

                FileOutputStream fis = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int len;
                while((len = is.read(buffer)) > 0)
                {
                    fis.write(buffer, 0, len);
                    fis.flush();
                }

                is.close();
                fis.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    	
    	public static void downloadMp3ToPath(String url, String path)
        {
            try {
            	file.checkAndCreateDirectory(path);
                URL uri = new URL(url);
                HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                String contentType = conn.getHeaderField("Content-Type");

                if (contentType == null
                		|| !contentType.contains("audio"))
                {
                	System.out.println("frank not mp3");
                	return;
                }
                
                InputStream is = conn.getInputStream();

                FileOutputStream fis = new FileOutputStream(path);
                byte[] buffer = new byte[1024];
                int len;
                while((len = is.read(buffer)) > 0)
                {
                    fis.write(buffer, 0, len);
                    fis.flush();
                }

                is.close();
                fis.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    	
    	public static String downloadToString(String url)
        {
            try {
                URL uri = new URL(url);
                URLConnection conn = uri.openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                
                InputStream is = conn.getInputStream();

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while((len = is.read(buffer)) > 0)
                {
                	baos.write(buffer, 0, len);
                }
                
                String result = new String(baos.toByteArray());
                
                is.close();
                baos.close();
                
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            return null;
        }
    }
    
    
}
