package com.liangli.nj.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
	public static String ReadUrlAndDownloadWithType(List<String> urlPathList, String outputPath, String type, boolean show) {
		File fileDir = new File(outputPath);
		fileDir.mkdirs();
		
		int successNum = 0;		
		for (int i = 0; i < urlPathList.size(); i++) {
			try {
				//System.out.println(urlPathList.get(i));
				URL url = new URL(urlPathList.get(i));
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
				//httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				httpURLConnection.setConnectTimeout(30000);
				httpURLConnection.setReadTimeout(30000);
				
				int responseCode = httpURLConnection.getResponseCode();
				
				if (responseCode == HttpURLConnection.HTTP_OK) {
					//System.out.println("成功读取路径：" + url);
					successNum++;
					
					DataInputStream in = new DataInputStream(httpURLConnection.getInputStream()); 
					File file = new File(outputPath);
					
					if (type.equalsIgnoreCase("mp3")) {
						file = new File(outputPath, (i+1) + ".mp3");
					}else if (type.equalsIgnoreCase("jpg") ) {
						file = new File(outputPath, (i+1) + ".jpg");
					}
					
					FileOutputStream out = new FileOutputStream(file, false);
					byte[] buffer = new byte[2048];
					int count = 0;
					while ((count = in.read(buffer)) > 0) {
						out.write(buffer, 0, count);
						out.flush();
					}
					out.close();
					in.close();
				}else {
					System.out.println("失败");
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		if (show) {
			System.out.println("\n>>>成功写入" + outputPath + "共：" + successNum + "个文件.");
		}	
		return null;
		
	}
	
	public static String ReadUrl2Path(List<String> urlPathList, String outputPath, List<String> name) {
		File fileDir = new File(outputPath);
		fileDir.mkdirs();
		int successNum = 0;
		for (int i = 0; i < urlPathList.size(); i++) {
			
			try {
				
				URL url = new URL(urlPathList.get(i));
				URLConnection urlConnection = url.openConnection();
				HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
				//httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
				httpURLConnection.setConnectTimeout(30000);
				httpURLConnection.setReadTimeout(30000);
				
				int responseCode = httpURLConnection.getResponseCode();
				
				if (responseCode == HttpURLConnection.HTTP_OK) {
					successNum ++;
					System.out.println("成功读取路径：" +successNum + ".");
					DataInputStream in = new DataInputStream(httpURLConnection.getInputStream());
					File file;
					if (name.isEmpty()) {
						file = new File(outputPath + i);
					}else {
						file = new File(outputPath + name.get(i));
					}
					FileOutputStream out = new FileOutputStream(file);
					byte[] buffer = new byte[2048];
					int count = 0;
					while ((count = in.read(buffer)) > 0) {
						out.write(buffer, 0, count);
						out.flush();
					}
					out.close();
					in.close();
				}else {
					System.out.println("失败路径：" + url);
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		System.out.println("成功读取路径：" + successNum + "个。");
		return null;		
	}
	
	public static List<String> generateUrl(List<String> words) {
		String generatedUrl = "https://image.baidu.com/search/index?"
				+ "tn=baiduimage&ipn=r&ct=201326592&cl=2&lm=-1&st=-1&"
				+ "fm=index&fr=&hs=0&xthttps=111111&sf=1&fmq=&pv=&ic=0"
				+ "&nc=1&z=&se=1&showtab=0&fb=0&width=&height=&face=0&"
				+ "istype=2&ie=utf-8&word=inputWord&oq=eye&rsp=-1";
		
		String generatedUrlTmp = generatedUrl;
		
		List<String> generatedUrlList = new ArrayList<>();
		
		Pattern pattern = Pattern.compile("(&word=inputWord&)");
		Matcher matcher = pattern.matcher(generatedUrl);
		matcher.find();
		int groupStart = matcher.start();
		int groupEnd = matcher.end() - 1;
		for (String word : words) {
			generatedUrl = generatedUrlTmp.substring(0, groupStart + "&word=".length())
					+ word + generatedUrlTmp.substring(groupEnd);
			generatedUrlList.add(generatedUrl);
		}
		return generatedUrlList;
	}
}
