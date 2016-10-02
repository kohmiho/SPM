package com.pseg.spm.vaadin.component;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.AbsoluteLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class TextField_CurrencySign extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private AbsoluteLayout mainLayout;
	@AutoGenerated
	private TextField textField;
	@AutoGenerated
	private Label label;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public TextField_CurrencySign() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	public TextField getTextField() {
		return textField;
	}

	public Label getLabel() {
		return label;
	}

	@AutoGenerated
	private AbsoluteLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new AbsoluteLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100px");
		mainLayout.setHeight("20px");

		// top-level component properties
		setWidth("100px");
		setHeight("20px");

		// label
		label = new Label();
		label.setImmediate(false);
		label.setWidth("10px");
		label.setHeight("20px");
		label.setValue("$");
		mainLayout.addComponent(label);

		// textField
		textField = new TextField();
		textField.setImmediate(false);
		textField.setWidth("90px");
		textField.setHeight("20px");
		mainLayout.addComponent(textField, "top:0.0px;left:10.0px;");

		return mainLayout;
	}

}
