package com.liangli.nj.bean;

public class SentenceBean {
	Integer uuid;
	String questions;
	String answer;
	
	public SentenceBean() {
		super();
	}
	
	public SentenceBean(Integer uuid, String questions, String answer) {
		super();
		this.uuid = uuid;
		this.questions = questions;
		this.answer = answer;
	}
	
	public Integer getUuid() {
		return uuid;
	}
	public void setUuid(Integer uuid) {
		this.uuid = uuid;
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

