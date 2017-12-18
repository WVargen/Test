package com.liangli.nj.bean;

import java.util.List;
import java.util.Map;

public class ChineseWord {

	String p;
	String n;
	String means;
	String zaoju;
	String history;
	List<String> cizu;
	List<String> fanyi;
	List<String> tongyi;
	Map<String, List<SimilarWord>> yintong;
	Map<String, List<SimilarWord>> xingtong;
	
	public String getP() {
		return p;
	}
	public void setP(String p) {
		this.p = p;
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public List<String> getCizu() {
		return cizu;
	}
	public void setCizu(List<String> cizu) {
		this.cizu = cizu;
	}
	public List<String> getFanyi() {
		return fanyi;
	}
	public void setFanyi(List<String> fanyi) {
		this.fanyi = fanyi;
	}
	public List<String> getTongyi() {
		return tongyi;
	}
	public void setTongyi(List<String> tongyi) {
		this.tongyi = tongyi;
	}
	public String getMeans() {
		return means;
	}
	public void setMeans(String means) {
		this.means = means;
	}
	public String getZaoju() {
		return zaoju;
	}
	public void setZaoju(String zaoju) {
		this.zaoju = zaoju;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public Map<String, List<SimilarWord>> getYintong() {
		return yintong;
	}
	public void setYintong(Map<String, List<SimilarWord>> yintong) {
		this.yintong = yintong;
	}
	public Map<String, List<SimilarWord>> getXingtong() {
		return xingtong;
	}
	public void setXingtong(Map<String, List<SimilarWord>> xingtong) {
		this.xingtong = xingtong;
	}
	
	
	
}
