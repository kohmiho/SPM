package com.kohmiho.spm.vaadin.page.component;

import com.kohmiho.spm.bean.Question;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.OptionGroup;

@SuppressWarnings("serial")
public class OptionOnlyResponse extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private OptionGroup value;
	@AutoGenerated
	private Label labelQuestion;

	private Question question;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public OptionOnlyResponse(Question question) {

		this.question = question;

		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);

		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");

		// labelQuestion
		labelQuestion = new Label();
		labelQuestion.setImmediate(false);
		labelQuestion.setWidth("300px");
		labelQuestion.setHeight("-1px");
		labelQuestion.setValue(question.getDescription());
		labelQuestion.setCaption(String.format("%s %s", question.getQuestionType(), question.getQuestionNumber()));
		mainLayout.addComponent(labelQuestion);

		// value
		value = new OptionGroup();
		value.setImmediate(false);
		value.setWidth("300px");
		value.setHeight("-1px");
		value.setCaption(question.getAnswerType().toString());
		value.addItems(question.getAcceptedAnswers());
		value.setMultiSelect(false);
		mainLayout.addComponent(value);

		return mainLayout;
	}

	public OptionGroup getValueField() {
		return value;
	}

}