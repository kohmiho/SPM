package com.pseg.spm.vaadin.component;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.server.ErrorEvent;

@SuppressWarnings("serial")
public class ErrorHandler implements com.vaadin.server.ErrorHandler {

	public static final Logger LOGGER = Logger.getLogger(ErrorHandler.class.getName());

	private static final ErrorHandler handler = new ErrorHandler();

	public static ErrorHandler getInstance() {
		return handler;
	}

	@Override
	public void error(ErrorEvent event) {
		LOGGER.log(Level.SEVERE, "Vaadin Error caught:" + event, event.getThrowable());
	}

}
