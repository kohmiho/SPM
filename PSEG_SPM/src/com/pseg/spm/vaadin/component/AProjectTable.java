package com.pseg.spm.vaadin.component;

import java.util.ArrayList;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.filter.Like;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.ui.AbstractTextField.TextChangeEventMode;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public abstract class AProjectTable extends CustomComponent {

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private TextField searchField;
	@AutoGenerated
	private Table table;
	private ArrayList<Like> likes = new ArrayList<Like>();
	private Or searchFilter;

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("serial")
	public AProjectTable(final SQLContainer projectContainer) {

		buildMainLayout();
		setCompositionRoot(mainLayout);

		mainLayout.setExpandRatio(table, 1);

		table.setSelectable(true);
		table.setImmediate(true);
		table.setColumnReorderingAllowed(true);
		table.setContainerDataSource(projectContainer);
		table.setColumnHeaderMode(ColumnHeaderMode.EXPLICIT);
		table.setVisibleColumns(getVisibleColumns());
		table.setColumnHeaders(getColumnHeaders());

		searchField.setInputPrompt("Search Survey");
		searchField.setTextChangeEventMode(TextChangeEventMode.LAZY);
		searchField.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(final TextChangeEvent event) {

				projectContainer.removeContainerFilter(searchFilter);

				likes.clear();
				for (int i = 1; i < getVisibleColumns().length; i++) {
					likes.add(new Like(getVisibleColumns()[i], "%" + event.getText() + "%", false));
				}

				searchFilter = new Or(likes.toArray(new Like[getVisibleColumns().length - 1]));

				projectContainer.addContainerFilter(searchFilter);

				table.select(projectContainer.firstItemId());
			}
		});
	}

	public abstract Object[] getVisibleColumns();

	public abstract String[] getColumnHeaders();

	public Table getTable() {
		return table;
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setSizeFull();
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);

		// top-level component properties
		setSizeFull();

		// searchField
		searchField = new TextField();
		searchField.setImmediate(true);
		searchField.setWidth("100.0%");
		mainLayout.addComponent(searchField);

		// projectTable
		table = new Table();
		table.setImmediate(true);
		table.setSizeFull();
		mainLayout.addComponent(table);

		return mainLayout;
	}

}
