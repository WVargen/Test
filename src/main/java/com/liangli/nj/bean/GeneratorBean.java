package com.liangli.nj.bean;

import java.util.List;

public class GeneratorBean {
	String generator;
	List<Long> params;
	List<Long> minparams; 
	List<Integer> signs;
	List<Integer> transform;
	List<String> unit;
	
	public GeneratorBean() {
		super();
	}


	public GeneratorBean(String generator, List<Long> params, List<Long> minparams, List<Integer> signs,
			List<Integer> transform, List<String> unit) {
		super();
		this.generator = generator;
		this.params = params;
		this.minparams = minparams;
		this.signs = signs;
		this.transform = transform;
		this.unit = unit;
	}


	public String getGenerator() {
		return generator;
	}

	public void setGenerator(String generator) {
		this.generator = generator;
	}

	public List<Long> getParams() {
		return params;
	}

	public void setParams(List<Long> params) {
		this.params = params;
	}

	public List<Long> getMinparams() {
		return minparams;
	}

	public void setMinparams(List<Long> minparams) {
		this.minparams = minparams;
	}

	public List<Integer> getSigns() {
		return signs;
	}

	public void setSigns(List<Integer> signs) {
		this.signs = signs;
	}

	public List<Integer> getTransform() {
		return transform;
	}

	public void setTransform(List<Integer> transform) {
		this.transform = transform;
	}

	public List<String> getUnit() {
		return unit;
	}

	public void setUnit(List<String> unit) {
		this.unit = unit;
	}
	
	
}
