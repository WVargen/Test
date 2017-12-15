package com.liangli.nj.bean;

public class QuestionBean {

	String answer;
	String explain;
	
	
	public QuestionBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public QuestionBean(String answer, String explain) {
		super();
		this.answer = answer;
		this.explain = explain;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	
}
