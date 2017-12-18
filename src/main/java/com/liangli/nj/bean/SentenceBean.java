package com.liangli.nj.bean;

public class SentenceBean {
	String queuuid;
	String questions;
	String answer;
	
	public SentenceBean() {
		super();
	}
	
	public SentenceBean(String queuuid, String questions, String answer) {
		super();
		this.queuuid = queuuid;
		this.questions = questions;
		this.answer = answer;
	}
	
	public String getQueuuid() {
		return queuuid;
	}
	public void setQueuuid(String queuuid) {
		this.queuuid = queuuid;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
}

