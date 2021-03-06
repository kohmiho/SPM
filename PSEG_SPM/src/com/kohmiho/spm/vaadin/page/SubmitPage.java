package com.kohmiho.spm.vaadin.page;

import java.util.Date;

import com.kohmiho.spm.vaadin.converter.DateToSqlTimestampConverter;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.DateField;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class SubmitPage extends Page {

	private GridLayout surveyLayout;

	private Label labelToSubmit;
	OptionGroup valueToSubmit;
	private Label labelSubmitDate;
	DateField valueSubmitDate;

	private HorizontalLayout buttonLine;
	private Button preBtn;
	private Button closeBtn;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public SubmitPage(SPMMain spmMain) {
		super(spmMain);

		buildMainLayout();
		setCompositionRoot(mainLayout);

		initFields();
		addButtonListener();
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {

		setSizeUndefined();

		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setMargin(false);

		// supplierName
		supplierName = new Label();
		supplierName.addStyleName("supplierTitle");
		supplierName.setImmediate(false);
		mainLayout.addComponent(supplierName);

		// Q&A section
		surveyLayout = new GridLayout(2, 2);
		surveyLayout.setMargin(true);
		surveyLayout.setSpacing(true);

		mainLayout.addComponent(surveyLayout);
		mainLayout.setComponentAlignment(surveyLayout, Alignment.TOP_CENTER);
		mainLayout.setExpandRatio(surveyLayout, 1.0f);

		labelToSubmit = new Label("Ready to submit");
		labelToSubmit.setWidth("200px");
		valueToSubmit = new OptionGroup();
		valueToSubmit.addItems("Yes", "No");
		valueToSubmit.setMultiSelect(false);
		valueToSubmit.setImmediate(true);

		surveyLayout.addComponent(labelToSubmit, 0, 0);
		surveyLayout.addComponent(valueToSubmit, 1, 0);

		labelSubmitDate = new Label("Submit Date");
		labelSubmitDate.setWidth("200px");
		valueSubmitDate = new DateField();
		valueSubmitDate.setDateFormat("yyyy-MM-dd");
		valueSubmitDate.setConverter(new DateToSqlTimestampConverter());
		valueSubmitDate.setImmediate(true);

		surveyLayout.addComponent(labelSubmitDate, 0, 1);
		surveyLayout.addComponent(valueSubmitDate, 1, 1);

		// Button
		buttonLine = new HorizontalLayout();
		buttonLine.setWidth("400px");
		buttonLine.setHeight("30px");
		mainLayout.addComponent(buttonLine);
		mainLayout.setComponentAlignment(buttonLine, Alignment.MIDDLE_CENTER);

		preBtn = new Button("Previous");
		preBtn.addStyleName(ValoTheme.BUTTON_PRIMARY);
		buttonLine.addComponent(preBtn);
		buttonLine.setComponentAlignment(preBtn, Alignment.MIDDLE_CENTER);

		closeBtn = new Button("Close");
		closeBtn.addStyleName(ValoTheme.BUTTON_DANGER);
		buttonLine.addComponent(closeBtn);
		buttonLine.setComponentAlignment(closeBtn, Alignment.MIDDLE_CENTER);

		return mainLayout;
	}

	protected void initFields() {

		textAnswerFieldGroup.bind(valueToSubmit, "SUBMIT");
		textAnswerFieldGroup.bind(valueSubmitDate, "SUBMIT_DATE");
	}

	@Override
	protected void addButtonListener() {

		preBtn.addClickListener(new ClickListener() {

			@Override
			public void buttonClick(ClickEvent event) {
				spmMain.mainPanel.setSecondComponent(getPreviousPage());
			}
		});

		closeBtn.addClickListener(new ClickListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void buttonClick(ClickEvent event) {

				removeSubmitOptionListener();
				textAnswerFieldGroup.setItemDataSource(null);

				Object tableValue = spmMain.surveyAssignmentTable.getTable().getValue();
				spmMain.surveyContainer.getItem(tableValue).getItemProperty("PAUSE_PAGE").setValue(0);

				spmMain.mainPanel.setSecondComponent(spmMain.welcomePage);

				Table table = spmMain.surveyAssignmentTable.getTable();
				Object nextItem = table.nextItemId(table.getValue());
				table.setValue(nextItem);

				// spmMain.surveyStatus.setLabelValues(spmMain.getSurveyCount());
			}
		});

	}

	private ValueChangeListener valueToSubmitListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			Object value = event.getProperty().getValue();

			if (null == value || "No".equals(value.toString())) {
				valueSubmitDate.setValue(null);
			} else if ("Yes".equals(value.toString())) {
				valueSubmitDate.setValue(new Date());
			}

			spmMain.surveyAssignmentTable.getTable().refreshRowCache();
		}
	};

	public void setSubmitOptionListener() {
		valueToSubmit.addValueChangeListener(valueToSubmitListener);
	}

	public void removeSubmitOptionListener() {
		valueToSubmit.removeValueChangeListener(valueToSubmitListener);
	}

}
