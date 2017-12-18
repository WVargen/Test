package com.liangli.nj.bean;

public class SimilarWord {

	String n;
	Float similar;
	
	public SimilarWord(String n, float similar) {
		super();
		this.n = n;
		this.similar = similar;
	}
	public SimilarWord() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getN() {
		return n;
	}
	public void setN(String n) {
		this.n = n;
	}
	public Float getSimilar() {
		return similar;
	}
	public void setSimilar(Float similar) {
		this.similar = similar;
	}

	
	
	
}
