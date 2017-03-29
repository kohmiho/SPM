package com.kohmiho.spm.vaadin.page;

import com.kohmiho.spm.vaadin.component.AProjectTable;
import com.vaadin.data.util.sqlcontainer.SQLContainer;

public class SurveyAssignmentTable extends AProjectTable {

	private static final long serialVersionUID = 1L;

	private static final Object[] VISIBLE_COLUMNS = { "SPM_ID", "SUPPLIER_NAME", "ROLE_NAME", "APPLY", "SUBMIT" };
	private static final String[] COLUMN_HEADERS = { "Id", "Supplier", "Role", "Applicable", "Submitted" };

	public SurveyAssignmentTable(final SQLContainer projectContainer) {
		super(projectContainer);
	}

	@Override
	public Object[] getVisibleColumns() {
		return VISIBLE_COLUMNS;
	}

	@Override
	public String[] getColumnHeaders() {
		return COLUMN_HEADERS;
	}

}
