package com.kohmiho.spm.vaadin.component;

import com.kohmiho.spm.vaadin.APP_UI;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class AppTitleLine extends CustomComponent {

	private HorizontalLayout mainLayout;
	private Image imgPSEGLogo;
	private Label appTitle;

	public AppTitleLine() {
		buildMainLayout();
		setCompositionRoot(mainLayout);
	}

	@AutoGenerated
	private void buildMainLayout() {

		mainLayout = new HorizontalLayout();

		imgPSEGLogo = new Image();
		imgPSEGLogo.setSource(APP_UI.psegLogoThemeRsrc);

		mainLayout.addStyleName("appTitle");
		mainLayout.setHeight("60px");
		mainLayout.setWidth("100%");
		mainLayout.addComponent(imgPSEGLogo);
		mainLayout.setComponentAlignment(imgPSEGLogo, Alignment.MIDDLE_LEFT);

		appTitle = new Label("Supplier Survey Application");
		mainLayout.addComponent(appTitle);
		mainLayout.setComponentAlignment(appTitle, Alignment.MIDDLE_RIGHT);
		mainLayout.setExpandRatio(appTitle, 1.0f);
	}

}
