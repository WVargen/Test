package com.liangli.nj.utils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes.Name;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpUtils {
	public static boolean ReadUrlAndDownload(String urlPath, String outputPath, String type) {
		File fileDir = new File(outputPath);
		fileDir.mkdirs();
		
		boolean successFlag = false;
		try {
			URL url = new URL(urlPath);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			//httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			httpURLConnection.setConnectTimeout(30000);
			httpURLConnection.setReadTimeout(30000);
				
			int responseCode = httpURLConnection.getResponseCode();
				
			if (responseCode == HttpURLConnection.HTTP_OK) {	
				DataInputStream in = new DataInputStream(httpURLConnection.getInputStream()); 
				File file = new File(outputPath);
				FileOutputStream out = new FileOutputStream(file, false);
				byte[] buffer = new byte[2048];
				int count = 0;
				while ((count = in.read(buffer)) > 0) {
					out.write(buffer, 0, count);
					out.flush();
				}
				out.close();
				in.close();
				successFlag = true;
			}else {
				System.out.println("失败" + responseCode + "路径：" + url);
			}
				
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return successFlag;
		
	}
	
	public static boolean ReadUrl2Path(String urlPath, String outputPath, String name) {
		File fileDir = new File(outputPath);
		fileDir.mkdirs();
		
		boolean successFlag = false;
		try {	
			URL url = new URL(urlPath);
			URLConnection urlConnection = url.openConnection();
			HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
			//httpURLConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			httpURLConnection.setConnectTimeout(30000);
			httpURLConnection.setReadTimeout(30000);
				
			int responseCode = httpURLConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) {
				//System.out.println("成功读取路径：" + url);
				successFlag = true;
				DataInputStream in = new DataInputStream(httpURLConnection.getInputStream());
				File file = new File(outputPath + name);
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
				System.out.println("失败" + responseCode + "路径：" + url);
			}
				
		} catch (MalformedURLException e) {
				e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return successFlag;		
	}
}
