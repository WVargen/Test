package com.liangli.nj.bean;

public class MathQuestionBean{
	String question;
	String answer;
	
	public MathQuestionBean() {
		super();
	}

	public MathQuestionBean(String question, String answer) {
		super();
		this.question = question;
		this.answer = answer;
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
	
	
}