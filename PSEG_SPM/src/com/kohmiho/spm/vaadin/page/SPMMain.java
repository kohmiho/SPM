package com.kohmiho.spm.vaadin.page;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.kohmiho.spm.bean.Privilege;
import com.kohmiho.spm.bean.Question;
import com.kohmiho.spm.bean.Section;
import com.kohmiho.spm.bean.Survey;
import com.kohmiho.spm.bean.SurveyAssignment;
import com.kohmiho.spm.bean.SurveyCount;
import com.kohmiho.spm.bean.User;
import com.kohmiho.spm.bean.Survey.AnswerType;
import com.kohmiho.spm.bean.Survey.QuestionType;
import com.kohmiho.spm.config.ConnectDB;
import com.kohmiho.spm.config.DBConfig;
import com.kohmiho.spm.config.DBConfig.ConnectionType;
import com.kohmiho.spm.dao.SurveyCountDAO;
import com.kohmiho.spm.dao.SurveyDAO;
import com.kohmiho.spm.vaadin.APP_UI;
import com.kohmiho.spm.vaadin.page.SurveyAssignmentTable;
import com.kohmiho.spm.vaadin.page.component.CommentOnlyResponse;
import com.kohmiho.spm.vaadin.page.component.OptionOnlyResponse;
import com.kohmiho.spm.vaadin.page.component.OptionResponse;
import com.kohmiho.spm.vaadin.page.component.PercentageResponse;
import com.kohmiho.spm.vaadin.page.component.SectionLine;
import com.kohmiho.spm.vaadin.page.component.WholeNumberResponse;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.filter.Or;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.JDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.connection.SimpleJDBCConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.OrderBy;
import com.vaadin.data.util.sqlcontainer.query.TableQuery;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class SPMMain extends CustomComponent {

	// TODO: specify surveyID here
	String surveyID = "1";

	JDBCConnectionPool pool = createConnectionPool();

	SQLContainer surveyContainer = createContainerByTableQuery("SPM", "VERSION");
	SQLContainer textAnswerContainer = createContainerByTableQuery("SPM_ANS_TEXT", "VERSION");
	SQLContainer numberAnswerContainer = createContainerByTableQuery("SPM_ANS_NUM", "VERSION");
	SQLContainer commentContainer = createContainerByTableQuery("SPM_ANS_CMT", "VERSION");
	SQLContainer usersContainer = createContainerByTableQuery("SPM_USER");

	SQLContainer[] oneToOnecontainers = { textAnswerContainer, numberAnswerContainer, commentContainer };
	// SQLContainer[] oneToManyContainers = {};

	HorizontalSplitPanel mainPanel = new HorizontalSplitPanel();
	VerticalLayout projectSection = new VerticalLayout();

	WelcomePage welcomePage = new WelcomePage();

	SurveyAssignmentTable surveyAssignmentTable = new SurveyAssignmentTable(surveyContainer);

	List<Page> pageList = new ArrayList<>();
	List<PageControl> pageControls = new ArrayList<>();

	VaadinSession vaadinSesssion;
	User loggedUser;
	Privilege privilege;
	boolean isReadOnly;

	List<Survey> surveyList;
	ArrayList<SurveyAssignment> surveyAssignmentList;

	public SPMMain(VaadinSession session) {

		this.vaadinSesssion = session;

		loadSurveys();
		initContainer();
		initLayout();
		initSurveyAssignmentTable();

		for (PageControl control : pageControls) {
			control.setRule();
		}
	}

	private void initContainer() {

		for (SQLContainer sqlContainer : oneToOnecontainers) {
			sqlContainer.addReference(surveyContainer, "SPM_ID", "SPM_ID");
		}

		// for (SQLContainer sqlContainer : oneToManyContainers) {
		// sqlContainer.addReference(surveyContainer, "SPM_ID", "SPM_ID");
		// }

		usersContainer.addOrderBy(new OrderBy("NAME", true));

		surveyContainer.addOrderBy(new OrderBy("SUPPLIER_ID", true));

	}

	private void initLayout() {

		setSizeFull();

		setCompositionRoot(mainPanel);

		mainPanel.setFirstComponent(projectSection);
		mainPanel.setSecondComponent(welcomePage);
		mainPanel.setSplitPosition(30);

		surveyAssignmentTable.setSizeFull();

		// projectSection.addComponent(surveyStatus);
		projectSection.addComponent(surveyAssignmentTable);
		projectSection.setSizeFull();
		projectSection.setExpandRatio(surveyAssignmentTable, 1.0f);

		Survey survey = surveyList.get(0);
		Page prevPage = new StartPage(this);
		addSurveyToPage(survey, prevPage);
		pageList.add(prevPage);

		for (int i = 1; i < surveyList.size(); i++) {
			survey = surveyList.get(i);
			Page newPage = new RegularPage(this);
			addSurveyToPage(survey, newPage);
			pageList.add(newPage);
			prevPage.setNextPage(newPage);
			prevPage = newPage;
		}

		SubmitPage submitPage = new SubmitPage(this);
		pageList.add(submitPage);
		prevPage.setNextPage(submitPage);

		for (int i = 0; i < pageList.size(); i++) {
			pageList.get(i).pageNumber = i;
		}

		pageControls.add(new ButtonControl(this));
	}

	private void addSurveyToPage(Survey survey, Page page) {
		if (QuestionType.Section == survey.getQuestionType()) {
			Section section = (Section) survey;
			SectionLine sectionLine = new SectionLine(section);
			page.surveyLayout.addComponent(sectionLine);

			List<Survey> children = section.getChildren();
			for (Survey child : children) {
				addSurveyToPage(child, page);
			}
		} else if (QuestionType.Question == survey.getQuestionType()) {
			addSurveyToPage((Question) survey, page);
		}
	}

	private void addSurveyToPage(Question question, Page page) {

		if (AnswerType.Percentage == question.getAnswerType()) {

			PercentageResponse response = new PercentageResponse(question);
			page.surveyLayout.addComponent(response);
			page.numberAnswerFieldGroup.bind(response.getValueField(), question.getAnswerField());
			page.cmtFieldGroup.bind(response.getCommentField(), question.getCommentField());

		} else if (AnswerType.Whole_Number == question.getAnswerType()) {

			WholeNumberResponse response = new WholeNumberResponse(question);
			page.surveyLayout.addComponent(response);
			page.numberAnswerFieldGroup.bind(response.getValueField(), question.getAnswerField());
			page.cmtFieldGroup.bind(response.getCommentField(), question.getCommentField());

		} else if (AnswerType.Text_Single_Line == question.getAnswerType()) {

			OptionResponse response = new OptionResponse(question);
			page.surveyLayout.addComponent(response);
			page.textAnswerFieldGroup.bind(response.getValueField(), question.getAnswerField());
			page.cmtFieldGroup.bind(response.getCommentField(), question.getCommentField());

		} else if (AnswerType.Text_Multiple_Line == question.getAnswerType()) {

			CommentOnlyResponse response = new CommentOnlyResponse(question);
			page.surveyLayout.addComponent(response);
			page.cmtFieldGroup.bind(response.getCommentField(), question.getCommentField());

		} else {

			OptionOnlyResponse response = new OptionOnlyResponse(question);
			page.surveyLayout.addComponent(response);
			page.textAnswerFieldGroup.bind(response.getValueField(), question.getAnswerField());
		}
	}

	private void loadSurveys() {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB(APP_UI.DB_CONFIG);
			SurveyDAO surveyDAO = new SurveyDAO(connectDB);
			surveyList = surveyDAO.findAllBySurveyID(surveyID);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != connectDB) {
				try {
					connectDB.close();
				} catch (SQLException e) {
				}
			}
		}
	}

	private void initSurveyAssignmentTable() {

		surveyAssignmentTable.getTable().addValueChangeListener(new ValueChangeListener() {

			@SuppressWarnings("unchecked")
			@Override
			public void valueChange(ValueChangeEvent event) {

				String readOnlySurvey;
				boolean readOnlySurveyAssignment;

				if (null != surveyAssignmentTable.getTable().getValue()) {

					readOnlySurvey = ((Property<String>) surveyContainer.getItem(surveyAssignmentTable.getTable().getValue()).getItemProperty("READ_ONLY"))
							.getValue();

					if (!privilege.isViewAll() && null != surveyAssignmentList && surveyAssignmentList.size() > 0) {
						SurveyAssignment project = surveyAssignmentList.get(surveyAssignmentList.indexOf(new SurveyAssignment(surveyAssignmentTable.getTable()
								.getValue().toString())));
						readOnlySurveyAssignment = project.isReadOnly();
					} else {
						readOnlySurveyAssignment = false;
					}

					isReadOnly = privilege.isReadOnly() | readOnlySurvey.equals("Y") | readOnlySurveyAssignment;
				}

				if (null == surveyAssignmentTable.getTable().getValue()) {
					mainPanel.setSecondComponent(welcomePage);
					return;
				}

				surveyAssignmentTable.getTable().refreshRowCache();

				setSurveyPage();
			}
		});

	}

	private void setSurveyPage() {

		SubmitPage submitPage = (SubmitPage) pageList.get(pageList.size() - 1);
		submitPage.removeSubmitOptionListener();
		submitPage.setItemDataSource(null, null, null);

		Object tableValue = surveyAssignmentTable.getTable().getValue();
		if (null != tableValue) {

			String supplierName = surveyContainer.getItem(tableValue).getItemProperty("SUPPLIER_NAME").getValue().toString();

			Page startPage = pageList.get(0);
			startPage.setItemDataSource(surveyContainer.getItem(tableValue), null, commentContainer.getItem(tableValue));
			startPage.setSupplierName(supplierName);
			startPage.setReadOnly(isReadOnly);

			for (int i = 1; i < pageList.size() - 1; i++) {
				Page page = pageList.get(i);
				page.setItemDataSource(textAnswerContainer.getItem(tableValue), numberAnswerContainer.getItem(tableValue), commentContainer.getItem(tableValue));
				page.setSupplierName(supplierName);
				page.setReadOnly(isReadOnly);
			}

			submitPage.setItemDataSource(surveyContainer.getItem(tableValue), null, null);
			submitPage.setSupplierName(supplierName);
			submitPage.setReadOnly(isReadOnly);
			submitPage.setSubmitOptionListener();

			Integer pausePage = (Integer) surveyContainer.getItem(tableValue).getItemProperty("PAUSE_PAGE").getValue();
			mainPanel.setSecondComponent(pageList.get(pausePage.intValue()));

		} else {
			mainPanel.setSecondComponent(welcomePage);
		}

	}

	private SQLContainer createContainerByTableQuery(String table, String version) {
		try {
			TableQuery tq = new TableQuery(table, pool);
			tq.setVersionColumn(version);
			SQLContainer container = new SQLContainer(tq);
			container.setAutoCommit(true);
			return container;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private SQLContainer createContainerByTableQuery(String table) {
		try {
			TableQuery tq = new TableQuery(table, pool);
			SQLContainer container = new SQLContainer(tq);
			container.setAutoCommit(true);
			return container;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private JDBCConnectionPool createConnectionPool() {
		DBConfig dbConfig = APP_UI.DB_CONFIG;
		try {
			if (ConnectionType.JDBC == dbConfig.getConnectionType()) {
				return new SimpleJDBCConnectionPool(dbConfig.getDBDriver(), dbConfig.getDBURL(), dbConfig.getDBUserName(), dbConfig.getDBPassword(), 2, 5);
			} else if (ConnectionType.DataSource == dbConfig.getConnectionType()) {
				return new J2EEConnectionPool(dbConfig.getDatasourceName().toString());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public SurveyCount getSurveyCount() {
		ConnectDB connectDB = null;
		try {
			connectDB = new ConnectDB(APP_UI.DB_CONFIG);
			return new SurveyCountDAO(connectDB).findByUserId(Integer.toString(privilege.getId()));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (null != connectDB) {
				try {
					connectDB.close();
				} catch (SQLException e) {
				}
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	public void enter() {

		loggedUser = (User) vaadinSesssion.getAttribute(APP_UI.ATTR_USER);
		privilege = (Privilege) vaadinSesssion.getAttribute(APP_UI.ATTR_PRIVILEGE);
		surveyAssignmentList = (ArrayList<SurveyAssignment>) vaadinSesssion.getAttribute(APP_UI.ATTR_USER_PROJECT);

		if (privilege.isAdmin()) {
			// TODO: add admin filter here
		}

		if (!privilege.isViewAll()) {
			ArrayList<Equal> equals = new ArrayList<Equal>();
			if (null != surveyAssignmentList && surveyAssignmentList.size() > 0) {
				for (SurveyAssignment userProject : surveyAssignmentList) {
					equals.add(new Equal("SPM_ID", userProject.getId()));
				}
				surveyContainer.addContainerFilter(new Or(equals.toArray(new Equal[surveyAssignmentList.size()])));
			} else {
				surveyContainer.addContainerFilter(new Equal("SPM_ID", "-1"));
			}

			// surveyContainer.addContainerFilter(new Equal("ARCHIVE", "N"));
		}

		// surveyStatus.setLabelValues(getSurveyCount());
	}

}