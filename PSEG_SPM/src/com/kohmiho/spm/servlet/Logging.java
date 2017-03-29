package com.kohmiho.spm.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.servlet.http.HttpServlet;

import com.kohmiho.spm.util.DateFormatter;
import com.kohmiho.spm.vaadin.APP_UI;
import com.kohmiho.spm.vaadin.APP_UI.WebLogicServer;

/**
 * <p>
 * No sure why Vaadin Servlet does not read logging.properties when deploying
 * into WebLogic
 * <p>
 * It works for other testing Vaadin web application
 * <p>
 * This is the replacement
 */
public class Logging extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger("com.pseg.spm");

	private boolean toConsole = false;
	private boolean toFile = false;
	private Level logLevel = Level.FINE;
	private Level consoleHandlerLevel = Level.FINE;
	private Level fileHandlerLevel = Level.FINE;
	private boolean useParentHandlers = false;

	private FileHandler fileHandler = null;
	private ConsoleHandler consoleHandler = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Logging() {
		super();

		if (APP_UI.WEBLOGIC_SERVER == WebLogicServer.LOAD_SPRING || APP_UI.WEBLOGIC_SERVER == WebLogicServer.PSEG) {
			toConsole = false;
			toFile = true;
			logLevel = Level.INFO;
			consoleHandlerLevel = Level.INFO;
			fileHandlerLevel = Level.INFO;
			useParentHandlers = false;

		} else if (APP_UI.WEBLOGIC_SERVER == WebLogicServer.LOCAL) {
			toConsole = true;
			toFile = false;
			logLevel = Level.FINE;
			consoleHandlerLevel = Level.FINE;
			fileHandlerLevel = Level.FINE;
			useParentHandlers = false;
		}

		try {

			Handler[] handlers = LOGGER.getHandlers();
			for (Handler handler : handlers) {

				System.out.println("<SPM> Handler found : " + handler);

				if (handler instanceof FileHandler) {
					fileHandler = (FileHandler) handler;
					continue;
				}

				if (handler instanceof ConsoleHandler) {
					consoleHandler = (ConsoleHandler) handler;
					continue;
				}
			}

			if (toFile) {
				if (null == fileHandler) {
					fileHandler = new FileHandler("PSEG_SPM-log.%u.%g.txt", 1024 * 1024, 10, true);
					LOGGER.addHandler(fileHandler);
				}
				fileHandler.setLevel(fileHandlerLevel);
				fileHandler.setFormatter(new SimpleFormatter());

				System.out.println("<SPM> Setup FileHandler : " + fileHandler);
			}

			if (toConsole) {
				if (null == consoleHandler) {
					consoleHandler = new ConsoleHandler();
					LOGGER.addHandler(consoleHandler);
				}
				consoleHandler.setLevel(consoleHandlerLevel);
				consoleHandler.setFormatter(getConsoleFormatter());

				System.out.println("<SPM> Setup ConsoleHandler : " + consoleHandler);
			}

			LOGGER.setLevel(logLevel);

			LOGGER.setUseParentHandlers(useParentHandlers);

			System.out.println("<SPM> Successfully setup Handlers in Logging Servlet");

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Formatter getConsoleFormatter() {

		return new Formatter() {

			private Date date = new Date();

			@Override
			public synchronized String format(LogRecord record) {

				date.setTime(record.getMillis());

				return String.format("<SPM> %s %s %s\n %s: %s \n", DateFormatter.formatter1.format(date), record.getSourceClassName(),
						record.getSourceMethodName(), record.getLevel(), record.getMessage());
			}
		};
	}

}
