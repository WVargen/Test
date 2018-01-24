package com.liangli.nj.bean;

import java.util.List;

public class QuestionBean {

	String question;
	String answer;
	List<String> answerList;
	String explain;
	
	
	public QuestionBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	public QuestionBean(String question, String answer, List<String> answerList, String explain) {
		super();
		this.question = question;
		this.answer = answer;
		this.answerList = answerList;
		this.explain = explain;
	}


	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public List<String> getAnswerList() {
		return answerList;
	}


	public void setAnswerList(List<String> answerList) {
		this.answerList = answerList;
	}


	public String getExplain() {
		return explain;
	}
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	
}
