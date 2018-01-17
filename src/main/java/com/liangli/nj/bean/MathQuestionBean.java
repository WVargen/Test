package com.liangli.nj.bean;

import java.math.BigDecimal;
import java.util.List;

public class MathQuestionBean{
	List<BigDecimal> parameter;
	List<BigDecimal> answerNum;
	String question;
	String answer;
	
	public MathQuestionBean() {
		super();
	}	

	public MathQuestionBean(List<BigDecimal> parameter, List<BigDecimal> answerNum, String question, String answer) {
		super();
		this.parameter = parameter;
		this.answerNum = answerNum;
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

	public List<BigDecimal> getParameter() {
		return parameter;
	}

	public void setParameter(List<BigDecimal> parameter) {
		this.parameter = parameter;
	}

	public List<BigDecimal> getAnswerNum() {
		return answerNum;
	}

	public void setAnswerNum(List<BigDecimal> answerNum) {
		this.answerNum = answerNum;
	}
	
}