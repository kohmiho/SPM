package com.kohmiho.spm.vaadin.page;

import com.kohmiho.spm.vaadin.page.component.ButtonLine;
import com.vaadin.data.Item;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public abstract class Page extends CustomComponent {

	protected int pageNumber;
	protected VerticalLayout mainLayout;
	// protected Label sectionTitle;
	protected VerticalLayout surveyLayout;

	protected SPMMain spmMain;
	private Page previousPage;
	private Page nextPage;
	protected ButtonLine buttonLine;
	protected Label supplierName;

	protected FieldGroup textAnswerFieldGroup;
	protected FieldGroup numberAnswerFieldGroup;
	protected FieldGroup cmtFieldGroup;

	public Page(SPMMain spmMain) {
		this.spmMain = spmMain;

		textAnswerFieldGroup = new FieldGroup();
		textAnswerFieldGroup.setBuffered(false);
		numberAnswerFieldGroup = new FieldGroup();
		numberAnswerFieldGroup.setBuffered(false);
		cmtFieldGroup = new FieldGroup();
		cmtFieldGroup.setBuffered(false);
	}

	private void setPreviousPage(Page page) {
		this.previousPage = page;
	}

	public void setNextPage(Page page) {
		this.nextPage = page;
		page.setPreviousPage(this);
	}

	public Page getNextPage() {
		return nextPage;
	}

	public Page getPreviousPage() {
		return previousPage;
	}

	protected void addButtonListener() {

		buttonLine.getButtonPrevious().addClickListener(new ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				Object tableValue = spmMain.surveyAssignmentTable.getTable().getValue();
				spmMain.surveyContainer.getItem(tableValue).getItemProperty("PAUSE_PAGE").setValue(previousPage.pageNumber);
				spmMain.mainPanel.setSecondComponent(previousPage);
			}
		});

		buttonLine.getButtonNext().addClickListener(new ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				Object tableValue = spmMain.surveyAssignmentTable.getTable().getValue();
				spmMain.surveyContainer.getItem(tableValue).getItemProperty("PAUSE_PAGE").setValue(nextPage.pageNumber);
				spmMain.mainPanel.setSecondComponent(nextPage);
			}
		});
	}

	public void setSupplierName(String supplierName) {
		this.supplierName.setValue("Supplier Name: " + supplierName);
	}

	public void setItemDataSource(Item textItem, Item numberItem, Item cmtItem) {
		textAnswerFieldGroup.setItemDataSource(textItem);
		numberAnswerFieldGroup.setItemDataSource(numberItem);
		cmtFieldGroup.setItemDataSource(cmtItem);
	}

	@Override
	public void setReadOnly(boolean readonly) {
		textAnswerFieldGroup.setReadOnly(readonly);
		numberAnswerFieldGroup.setReadOnly(readonly);
		cmtFieldGroup.setReadOnly(readonly);
	}

}
