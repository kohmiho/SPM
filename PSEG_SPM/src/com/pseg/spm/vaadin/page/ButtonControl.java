package com.pseg.spm.vaadin.page;

import com.pseg.spm.vaadin.page.component.OptionResponse;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class ButtonControl implements PageControl {

	private SPMMain spmMain;

	public ButtonControl(SPMMain spmMain) {
		this.spmMain = spmMain;
	}

	@Override
	public void setRule() {

		final StartPage startPage = (StartPage) spmMain.pageList.get(0);
		final SubmitPage submitPage = (SubmitPage) spmMain.pageList.get(spmMain.pageList.size() - 1);

		OptionResponse optionResponse = (OptionResponse) startPage.surveyLayout.getComponent(1);
		OptionGroup optionGroup = optionResponse.getValueField();
		optionGroup.setImmediate(true);

		optionGroup.addValueChangeListener(new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				Object value = event.getProperty().getValue();
				if (null == value) {
					startPage.startBtn.setEnabled(false);
				} else if ("No".equals(value.toString())) {
					startPage.startBtn.setEnabled(false);
					submitPage.valueToSubmit.setValue(null);
					submitPage.valueSubmitDate.setValue(null);
				} else if ("Yes".equals(value.toString())) {
					startPage.startBtn.setEnabled(true);
				}

				spmMain.surveyAssignmentTable.getTable().refreshRowCache();
				// spmMain.surveyStatus.setLabelValues(spmMain.getSurveyCount());
			}
		});

		startPage.startBtn.addClickListener(new ClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {
				Object tableValue = spmMain.surveyAssignmentTable.getTable().getValue();
				spmMain.surveyContainer.getItem(tableValue).getItemProperty("PAUSE_PAGE").setValue(1);
				spmMain.mainPanel.setSecondComponent(startPage.getNextPage());
			}
		});

	}
}
