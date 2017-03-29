package com.kohmiho.spm.vaadin.component;

import com.vaadin.ui.Table;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

@SuppressWarnings("serial")
public class SucceededListerner implements SucceededListener {

	private Table table;

	public SucceededListerner(Table table) {
		this.table = table;
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		table.refreshRowCache();
	}

}
