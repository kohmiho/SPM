package com.kohmiho.spm.vaadin;

import com.kohmiho.spm.bean.User;
import com.kohmiho.spm.vaadin.component.AppTitleLine;
import com.kohmiho.spm.vaadin.page.Menu;
import com.kohmiho.spm.vaadin.page.SPMMain;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ViewMain extends CustomComponent implements View {

	public static final String NAME = "";

	private final Label topSpace;
	private final VerticalLayout viewLayout;
	private final AppTitleLine appTitleLine;
	private final Menu menuLine;
	private final VerticalLayout mainArea;

	public ViewMain() {

		setSizeFull();

		viewLayout = new VerticalLayout();
		topSpace = new Label();
		appTitleLine = new AppTitleLine();
		menuLine = new Menu();
		mainArea = new VerticalLayout();

		viewLayout.setSizeFull();

		topSpace.setValue("");
		topSpace.addStyleName("loggeduser");
		topSpace.addStyleName("appBackGround");
		topSpace.setWidth("100%");
		topSpace.setHeight("20px");

		mainArea.setSizeFull();

		viewLayout.addComponent(topSpace);
		viewLayout.addComponent(appTitleLine);
		viewLayout.addComponent(menuLine);
		viewLayout.addComponent(mainArea);

		viewLayout.setExpandRatio(mainArea, 1.0f);

		setCompositionRoot(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {

		User user = (User) getSession().getAttribute(APP_UI.ATTR_USER);

		topSpace.setValue("Welcome, " + user.getName());

		SPMMain mainPage = new SPMMain(getSession());
		mainArea.addComponent(mainPage);

		mainPage.enter();
	}
}
