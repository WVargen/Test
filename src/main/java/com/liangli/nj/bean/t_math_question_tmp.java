package com.liangli.nj.bean;

import com.alibaba.fastjson.JSONObject;
import com.liangli.nj.bean.GeneratorBean;
import com.liangli.nj.database.BaseTable;

public class t_math_question_tmp extends BaseTable {
	String type;
	String pattern;
	String generator;
	GeneratorBean generatorBean;
	String name;
	
	public t_math_question_tmp() {
		super();
	}

	public t_math_question_tmp(String type, String pattern, String generator, GeneratorBean generatorBean,
			String name) {
		super();
		this.type = type;
		this.pattern = pattern;
		this.generator = generator;
		this.generatorBean = generatorBean;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public GeneratorBean getGeneratorBean() {
		GeneratorBean gBean = JSONObject.parseObject(generator, GeneratorBean.class);
		return gBean;
	}

	public void setGeneratorBean(GeneratorBean generatorBean) {
		this.generatorBean = generatorBean;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
