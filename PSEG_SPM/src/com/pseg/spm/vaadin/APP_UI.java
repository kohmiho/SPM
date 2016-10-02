package com.pseg.spm.vaadin;

import com.pseg.security.PSEGLDAP.LDAPServer;
import com.pseg.spm.config.DBConfig;
import com.pseg.spm.config.DBConfig.TargetDB;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("PSEG_SPM")
@Title("PSEG SPM")
public class APP_UI extends UI {

	// TODO:
	public static final WebLogicServer WEBLOGIC_SERVER = WebLogicServer.PSEG;
	public static final LDAPServer LDAP_SERVER = LDAPServer.PSEG;
	public static final DBConfig DB_CONFIG = DBConfig.getConfig(TargetDB.HSQL);

	public enum WebLogicServer {
		PSEG, LOAD_SPRING, LOCAL,
	}

	public static final String ATTR_USER = "user";
	public static final String ATTR_PRIVILEGE = "privilege";
	public static final String ATTR_USER_PROJECT = "user_project";

	public static final ThemeResource psegLogoThemeRsrc = new ThemeResource("img/PsegLogo.jpg");

	@Override
	protected void init(VaadinRequest request) {

		new Navigator(this, this);

		getNavigator().addView(ViewLogin.NAME, ViewLogin.class);
		getNavigator().addView(ViewMain.NAME, ViewMain.class);

		getNavigator().addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				boolean isLoggedIn = getSession().getAttribute(ATTR_USER) != null;
				boolean isLoginView = event.getNewView() instanceof ViewLogin;

				if (!isLoggedIn && !isLoginView) {
					getNavigator().navigateTo(ViewLogin.NAME);
					return false;

				} else if (isLoggedIn && isLoginView) {
					return false;
				}

				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}

		});
	}
}