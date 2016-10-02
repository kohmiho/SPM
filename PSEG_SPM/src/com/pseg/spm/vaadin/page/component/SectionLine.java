package com.pseg.spm.vaadin.page.component;

import com.pseg.spm.bean.Section;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class SectionLine extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private Label sectionTitle;

	private Section section;

	/**
	 * The constructor should first build the main layout, set the composition
	 * root and then do any custom initialization.
	 * 
	 * The constructor will not be automatically regenerated by the visual
	 * editor.
	 */
	public SectionLine(Section section) {
		this.section = section;

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

		sectionTitle = new Label();
		sectionTitle.addStyleName("sectionTitle");
		sectionTitle.setImmediate(false);
		sectionTitle.setValue(String.format("%s %s %s", section.getQuestionType(), section.getQuestionNumber(), section.getDescription()));
		mainLayout.addComponent(sectionTitle);

		return mainLayout;
	}

}
