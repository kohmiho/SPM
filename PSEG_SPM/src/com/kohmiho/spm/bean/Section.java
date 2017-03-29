package com.kohmiho.spm.bean;

import java.util.ArrayList;
import java.util.List;

public class Section extends Survey {

	private List<Survey> list = new ArrayList<>();

	@Override
	public Survey add(Survey survey) {
		list.add(survey);
		return this;
	}

	public List<Survey> getChildren() {
		return list;
	}
}