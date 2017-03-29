package com.kohmiho.spm.vaadin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.kohmiho.spm.bean.Privilege;
import com.kohmiho.spm.bean.SurveyAssignment;
import com.kohmiho.spm.bean.User;
import com.kohmiho.spm.config.ConnectDB;
import com.kohmiho.spm.dao.PrivilegeDAO;
import com.kohmiho.spm.dao.UserDAO;
import com.kohmiho.spm.dao.UserSurveyDAO;
import com.kohmiho.spm.vaadin.component.AppTitleLine;
import com.pseg.security.PSEGLDAP;
import com.pseg.security.PSEGLDAP.ReturnCode;
import com.vaadin.event.FieldEvents.FocusEvent;
import com.vaadin.event.FieldEvents.FocusListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ViewLogin extends CustomComponent implements View {

	private static final ResourceBundle bundle = ResourceBundle.getBundle(ViewLogin.class.getName(), new Locale("en", "US", APP_UI.LDAP_SERVER.toString()));

	public static final String NAME = "login";

	private final Label topSpace;
	private final TextField fieldLanID;
	private final PasswordField fieldPassword;
	private final Button loginButton;
	private final Label errorMsg;
	private final AppTitleLine appTitleLine;

	private static final Logger LOGGER = Logger.getLogger(ViewLogin.class.getName());

	public ViewLogin() {

		setSizeFull();
		addStyleName("appBackGround");

		VerticalLayout viewLayout = new VerticalLayout();
		viewLayout.setSizeFull();

		topSpace = new Label();
		topSpace.setValue("");
		topSpace.setWidth("100%");
		topSpace.setHeight("25px");
		viewLayout.addComponent(topSpace);

		appTitleLine = new AppTitleLine();
		viewLayout.addComponent(appTitleLine);
		viewLayout.setComponentAlignment(appTitleLine, Alignment.TOP_CENTER);

		FormLayout form = new FormLayout();
		form.setSpacing(true);
		form.setMargin(new MarginInfo(true, true, true, true));
		form.setSizeUndefined();

		errorMsg = new Label();
		errorMsg.setIcon(FontAwesome.EXCLAMATION_TRIANGLE);
		errorMsg.setWidth("200px");
		errorMsg.setImmediate(true);
		errorMsg.addStyleName("errorMsg");
		errorMsg.setVisible(false);
		form.addComponent(errorMsg);

		fieldLanID = new TextField(bundle.getString("ID"));
		fieldLanID.setWidth("200px");
		fieldLanID.setRequired(true);
		fieldLanID.setMaxLength(20);
		fieldLanID.setInvalidAllowed(false);
		fieldLanID.setNullRepresentation("");
		fieldLanID.addFocusListener(focusListener);
		form.addComponent(fieldLanID);

		fieldPassword = new PasswordField(bundle.getString("Password"));
		fieldPassword.setWidth("200px");
		fieldPassword.setRequired(true);
		fieldPassword.setValue("");
		fieldPassword.setMaxLength(20);
		fieldPassword.setNullRepresentation("");
		fieldPassword.addFocusListener(focusListener);
		form.addComponent(fieldPassword);

		loginButton = new Button("Log In");
		loginButton.addStyleName(ValoTheme.BUTTON_PRIMARY);
		loginButton.setImmediate(true);
		loginButton.setDisableOnClick(true);
		loginButton.addClickListener(loginButtonListener);
		loginButton.setClickShortcut(KeyCode.ENTER);
		form.addComponent(loginButton);

		GridLayout grid = new GridLayout(1, 1);
		grid.addStyleName("loginView");
		grid.addComponent(form);
		grid.setSpacing(true);
		grid.setMargin(new MarginInfo(true, true, true, true));

		viewLayout.addComponent(grid);
		viewLayout.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
		viewLayout.setExpandRatio(grid, 1.0f);

		setCompositionRoot(viewLayout);
	}

	private ClickListener loginButtonListener = new ClickListener() {

		@Override
		public void buttonClick(ClickEvent event) {

			String lanID = fieldLanID.getValue();
			String password = fieldPassword.getValue();

			if (null == lanID || "".equals(lanID.trim())) {
				fieldLanID.setInputPrompt(bundle.getString("ID_InputPrompt"));
				loginButton.setEnabled(true);
				return;
			}
			if (null == password || "".equals(password.trim())) {
				loginButton.setEnabled(true);
				return;
			}

			// ReturnCode returnCode =
			// PSEGLDAP.getInstance(APP_UI.LDAP_SERVER).verifyPassword(lanID,
			// password);

			ReturnCode returnCode = null;

			if ("ADMIN".equals(lanID.toUpperCase()) || "SPM".equals(lanID.toUpperCase())) {
				returnCode = ReturnCode.GOOD;
			} else {
				returnCode = returnCode.NO_USER;
			}

			if (ReturnCode.GOOD == returnCode) {

				lanID = lanID.toUpperCase();

				ConnectDB connectDB = null;
				try {
					connectDB = new ConnectDB(APP_UI.DB_CONFIG);

					User user = new UserDAO(connectDB).findByLanID(lanID);
					if (null != user) {

						Privilege privilege = new PrivilegeDAO(connectDB).findByLanID(lanID);
						ArrayList<SurveyAssignment> userSurveys = new UserSurveyDAO(connectDB).findByPrivilege(privilege);

						getSession().setAttribute(APP_UI.ATTR_USER, user);
						getSession().setAttribute(APP_UI.ATTR_PRIVILEGE, privilege);
						getSession().setAttribute(APP_UI.ATTR_USER_PROJECT, userSurveys);

						getUI().getNavigator().navigateTo(ViewMain.NAME);
					} else {
						errorMsg.setVisible(true);
						errorMsg.setValue("You are not authorized to access Supplier Survey Application. Please contact Help Desk.");
						loginButton.setEnabled(true);
					}

				} catch (Exception e) {
					e.printStackTrace();
					LOGGER.log(Level.SEVERE, String.format("Fail to load user [%s] from database", lanID), e);
					errorMsg.setVisible(true);
					errorMsg.setValue("System error! Fail to look up user privilege. Please contact Help Desk.");
					loginButton.setEnabled(true);
				} finally {
					if (null != connectDB) {
						try {
							connectDB.close();
						} catch (SQLException e) {
						}
					}
				}
			} else {

				loginButton.setEnabled(true);

				switch (returnCode) {
				case NO_USER:
					errorMsg.setVisible(true);
					errorMsg.setValue(bundle.getString("MSG_NO_USER"));
					fieldLanID.setValue(null);
					fieldPassword.setValue(null);
					break;
				case WRONG_PASSWORD:
					errorMsg.setVisible(true);
					errorMsg.setValue(bundle.getString("MSG_WRONG_PASSWORD"));
					fieldPassword.setValue(null);
					break;
				default:
					errorMsg.setVisible(true);
					errorMsg.setValue(bundle.getString("MSG_WRONG_DEFAULT"));
					fieldLanID.setValue(null);
					fieldPassword.setValue(null);
					break;
				}
			}
		}

	};

	private FocusListener focusListener = new FocusListener() {

		@Override
		public void focus(FocusEvent event) {
			errorMsg.setVisible(false);
			errorMsg.setEnabled(false);
		}
	};

	@Override
	public void enter(ViewChangeEvent event) {
		fieldLanID.focus();
	}

}
