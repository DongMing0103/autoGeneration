package cn.devops.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.java.amateras.db.DBPlugin;
import net.java.amateras.db.dialect.DialectProvider;
import net.java.amateras.db.dialect.IDialect;
import net.java.amateras.db.dialect.ISchemaLoader;
import net.java.amateras.db.ui.bean.Node;
import net.java.amateras.db.ui.bean.RootNode;
import net.java.amateras.db.ui.bean.SaveConfig;
import net.java.amateras.db.ui.provider.TVContentProvider;
import net.java.amateras.db.ui.provider.TVLabelProvider;
import net.java.amateras.db.util.DatabaseInfo;
import net.java.amateras.db.util.JarClassLoader;
import net.java.amateras.db.visual.model.ColumnModel;
import net.java.amateras.db.visual.model.RootModel;
import net.java.amateras.db.visual.model.TableModel;

import org.apache.commons.io.FileUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.wb.swt.SWTResourceManager;

import cn.devops.comm.constants.DevopsBasePath;
import cn.devops.comm.constants.DevopsToolConstants;
import cn.devops.service.UiGenCodeService;
import cn.devops.service.impl.codeGenerate.DevopsMavenCodeGenerater;
import cn.devops.util.db.DbRes;

public class DevopsMainTableDialog extends Dialog implements UiGenCodeService {

	public static final String Formater_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
	HashMap resMap = null;
	boolean hadChoseAll = false;
	boolean hadChoseQryAll = false;
	boolean hadChoseAllAu = false;
	boolean hadValiFiledAll = false;
	protected Object result;
	protected Shell shell;
	private Text txtPackage;
	private Combo txtDbTableList;
	private Text txtClassName;
	private Text txtBasePackage;
	private Text txtService;
	private Text txtMainFiled;
	private Text txtEntiry;
	private Text txtFuncPackage;

	private Text txtMapper;
	private Text txtAuth;
	private DatabaseInfo dbinfo;

	private JarClassLoader classLoader;
	private DbRes res;
	private CheckboxTreeViewer mainFieldTreeViewer;
	private RootNode root;
	private String tableName;
	private List<ColumnModel> columnModels;
	private String fieldPK;
	private HashMap<String, Object> resultMap;
	private Text txtMainTableAs;
	private String tableDesc;
	private Object[] qryOutArr;
	private Object[] tableOut;
	private Object[] aduOut;
	private CTabItem tbtmNewItem;
	private Text txtProjTargetDir;
	private Text text_1;
	private Text txtDsw;
	boolean isCheck = false;
	boolean isQryCheck = false;
	boolean isCheckAu = false;
	boolean isVali = false;
	private Text text;
	private Text txtHtmlBasePrevPackage;
	private Text txtDir4business;
	private Text text_3;
	private Text txtServiceDir;
	private Text txtEntityDir;
	private Text txtMapperDir;
	private Combo txtGenCodeProject;
	private Text txtGenCodeComm;

	private Composite composite;
	private Combo txtControlerModel;
	private Combo comHtmlMaven;
	private Combo commServcieName;

	private CTabItem tbFunctionSelItem;
	private Text text_2;
	private Text text_4;
	private Text txtModeName;
	private Composite compChose;
	private CheckboxTreeViewer mainQueryFieldTreeViewer;
	private CheckboxTreeViewer mainAuFieldTreeViewer;
	private RootNode qryRoot;
	private RootNode auRoot;
	private RootNode valiRoot;
	private Button btnCanImage;
	private Button btnCheckButton;
	private Composite btnRich;
	private Button btnCommit;
	private Button btnStopStart;
	private Button btnCanCategory;
	private Button btnAddSql;
	private Button btnDeleteSql;
	private Button btnUpdateSql;
	private Button btnChangeSql;
	private Button btnSplit;
	private Button btnExcel;
	private Button btnTrace;
	private Button btnMap;
	private Button btnTime;
	private Button btnRadio;
	private Combo comboStyle;
	private CheckboxTreeViewer mainValiFieldTreeViewer;

	private Button projectButtion;
	private Text txtProjectPath;
	private Text txtCoreFrameworkPath;

	public DevopsMainTableDialog(Shell shell2) {
		// TODO Auto-generated constructor stub
		super(shell2);
	}

	/**
	 * @wbp.parser.constructor
	 */
	public DevopsMainTableDialog(Shell shell2, int applicationModal, DbRes res, JarClassLoader classLoader) {
		super(shell2, applicationModal);
		setText("表字段选择");
		this.res = res;

		this.classLoader = classLoader;
	}

	public DevopsMainTableDialog(Shell shell2, int applicationModal, RootModel rootModel, String tableName, Table tblColumns,
			List<ColumnModel> columnModels, DbRes res, String tableDesc) {
		super(shell2, applicationModal);
		this.tableName = tableName;
		this.columnModels = columnModels;
		for (int i = 0; i < columnModels.size(); i++) {
			ColumnModel column = (ColumnModel) columnModels.get(i);
			if (column.isPrimaryKey()) {
				this.fieldPK = column.getColumnName();
			}
		}
		this.res = res;
		this.tableDesc = tableDesc;
	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		Rectangle parentBounds = shell.getParent().getBounds();
		Rectangle shellBounds = shell.getBounds();
		// shell.setLocation(parentBounds.x + (parentBounds.width -
		// shellBounds.width) / 2, parentBounds.y + (parentBounds.height -
		// shellBounds.height) / 2);
		shell.setLocation(10, 20);
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(1150, 850);
		shell.setText(getText());
		shell.setLayout(new GridLayout(1, false));

		Composite mainComp = new Composite(shell, SWT.NONE);
		mainComp.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		mainComp.setLayout(new GridLayout(1, false));
		CTabFolder tabFolder_1 = new CTabFolder(mainComp, SWT.BORDER);
		tabFolder_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tabFolder_1.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));
		CTabItem baseTab = new CTabItem(tabFolder_1, SWT.NONE);
		baseTab.setText("代码生成");

		btnRich = new Composite(tabFolder_1, SWT.NONE);
		baseTab.setControl(btnRich);
		btnRich.setLayout(new GridLayout(10, false));

		Label lblNewLabel_3 = new Label(btnRich, SWT.NONE);
		lblNewLabel_3.setText("生成项目名称:");

		comboStyle = new Combo(btnRich, SWT.NONE);
		GridData gd_comboStyle = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_comboStyle.widthHint = 50;
		comboStyle.setLayoutData(gd_comboStyle);
		comboStyle.setItems(new String[] { "KANKAN", "KZSCRM", "KZS" });
		comboStyle.addSelectionListener(new SelectionListener() {

			public void widgetSelected(SelectionEvent arg0) {
				changePath();
			}

			public void widgetDefaultSelected(SelectionEvent arg0) {
			}
		});
		new Label(btnRich, SWT.NONE);

		Label label_5 = new Label(btnRich, SWT.NONE);
		label_5.setText("项目正式目录:");

		txtProjTargetDir = new Text(btnRich, SWT.BORDER);
		GridData gd_txtProjTargetDir = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_txtProjTargetDir.widthHint = 150;
		txtProjTargetDir.setLayoutData(gd_txtProjTargetDir);
		// txtProjTargetDir.setText("F:\\mavenEclipseWorkspaceJM09\\JTTCenter");
		txtProjTargetDir.setText("");

		Label label_4 = new Label(btnRich, SWT.NONE);
		label_4.setText("作者:");

		txtAuth = new Text(btnRich, SWT.BORDER);
		GridData gd_txtAuth = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtAuth.widthHint = 150;
		txtAuth.setLayoutData(gd_txtAuth);
		txtAuth.setText(DevopsToolConstants.PROJECT_AUTHOR);
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);

		Label label_6 = new Label(btnRich, SWT.NONE);
		label_6.setText("公共包目录:");

		txtGenCodeComm = new Text(btnRich, SWT.BORDER);
		GridData gd_txtGenCodeComm = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		gd_txtGenCodeComm.widthHint = 200;
		txtGenCodeComm.setLayoutData(gd_txtGenCodeComm);
		txtGenCodeComm.setText("c:\\userx\\codegen\\");

		Button btnNewButton_2 = new Button(btnRich, SWT.NONE);
		btnNewButton_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dir = new DirectoryDialog(getShell(), SWT.APPLICATION_MODAL);
				String directoryName = dir.open();
				if (directoryName != null) {
					txtGenCodeComm.setText(directoryName);
				}
			}
		});
		btnNewButton_2.setText("选择");

		Label label_3 = new Label(btnRich, SWT.NONE);
		label_3.setText("html保存基础路径：");

		txtHtmlBasePrevPackage = new Text(btnRich, SWT.BORDER);
		GridData gd_txtHtmlBasePrevPackage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtHtmlBasePrevPackage.widthHint = 150;
		txtHtmlBasePrevPackage.setLayoutData(gd_txtHtmlBasePrevPackage);
		txtHtmlBasePrevPackage.setText(DevopsToolConstants.Project_modem);
		txtHtmlBasePrevPackage.setToolTipText("例如center  或 center.front");
		Label label_11 = new Label(btnRich, SWT.NONE);
		label_11.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_11.setText("模块:");

		txtModeName = new Text(btnRich, SWT.BORDER);
		txtModeName.setText(DevopsToolConstants.jaDataBase);
		GridData gd_txtModeName = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_txtModeName.widthHint = 150;
		txtModeName.setLayoutData(gd_txtModeName);
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);

		Label label_9 = new Label(btnRich, SWT.NONE);
		label_9.setText("生成目录:");

		txtGenCodeProject = new Combo(btnRich, SWT.BORDER);
		GridData gd_txtGenCodeProject = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtGenCodeProject.widthHint = 200;
		txtGenCodeProject.setLayoutData(gd_txtGenCodeProject);
		txtGenCodeProject.setItems(new String[] { "c:\\userx\\codegen\\" + DevopsToolConstants.PROJECT_NAME });
		txtGenCodeProject.setText("C:\\Users\\PC\\workspace\\qzs0420");

		Button button_1 = new Button(btnRich, SWT.NONE);
		button_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		button_1.setText("选择");

		Label lblNewLabel = new Label(btnRich, SWT.NONE);
		lblNewLabel.setText("分类包:");
		txtPackage = new Text(btnRich, SWT.BORDER);
		GridData gd_txtPackage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtPackage.widthHint = 200;
		txtPackage.setLayoutData(gd_txtPackage);

		Label label_1 = new Label(btnRich, SWT.NONE);
		label_1.setText("类名:");

		txtClassName = new Text(btnRich, SWT.BORDER);
		GridData gd_txtClassName = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtClassName.widthHint = 150;
		txtClassName.setLayoutData(gd_txtClassName);
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);

		Label label_12 = new Label(btnRich, SWT.NONE);
		label_12.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_12.setText("正式代码路径");

		txtProjectPath = new Text(btnRich, SWT.BORDER);
		txtProjectPath.setText("C:\\Users\\PC\\workspace\\qzs0420");
		txtProjectPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(btnRich, SWT.NONE);

		Label label_13 = new Label(btnRich, SWT.NONE);
		label_13.setText("核心框架基础包");

		txtCoreFrameworkPath = new Text(btnRich, SWT.BORDER);
		txtCoreFrameworkPath.setText("com.hd.wolverine");
		txtCoreFrameworkPath.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);

		Label label = new Label(btnRich, SWT.NONE);
		label.setText("主表名称:");

		txtDbTableList = new Combo(btnRich, SWT.BORDER);
		GridData gd_txtDbTableList = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtDbTableList.widthHint = 150;
		txtDbTableList.setLayoutData(gd_txtDbTableList);
		txtDbTableList.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				chaneList();
			}
		});
		new Label(btnRich, SWT.NONE);

		Label label_2 = new Label(btnRich, SWT.NONE);
		label_2.setText("主键:");

		txtMainFiled = new Text(btnRich, SWT.BORDER);
		GridData gd_txtMainFiled = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtMainFiled.widthHint = 150;
		txtMainFiled.setLayoutData(gd_txtMainFiled);
		new Label(btnRich, SWT.NONE);
		Label txtMainAsName = new Label(btnRich, SWT.NONE);
		txtMainAsName.setText("別名:");
		txtMainTableAs = new Text(btnRich, SWT.BORDER);
		GridData gd_txtMainTableAs = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtMainTableAs.widthHint = 150;
		txtMainTableAs.setLayoutData(gd_txtMainTableAs);
		new Label(btnRich, SWT.NONE);
		new Label(btnRich, SWT.NONE);

		btnCommit = new Button(btnRich, SWT.CHECK);
		btnCommit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCommit.setText("上下架代码");

		btnStopStart = new Button(btnRich, SWT.CHECK);
		btnStopStart.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnStopStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnStopStart.setText("启用/停用");

		btnCheckButton = new Button(btnRich, SWT.CHECK);
		btnCheckButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCheckButton.setText("富文本");

		btnCanImage = new Button(btnRich, SWT.CHECK);
		btnCanImage.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCanImage.setText("图片上传");

		btnCanCategory = new Button(btnRich, SWT.CHECK);
		btnCanCategory.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnCanCategory.setText("分类");

		btnAddSql = new Button(btnRich, SWT.CHECK);
		btnAddSql.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnAddSql.setText("新增SQL");
		new Label(btnRich, SWT.NONE);

		btnDeleteSql = new Button(btnRich, SWT.CHECK);
		btnDeleteSql.setText("删除SQL");

		btnUpdateSql = new Button(btnRich, SWT.CHECK);
		btnUpdateSql.setText("更改SQL");

		btnChangeSql = new Button(btnRich, SWT.CHECK);
		btnChangeSql.setText("批量变更");
		
		btnSplit = new Button(btnRich, SWT.CHECK);
		btnSplit.setText("分账明细");
		
		btnExcel = new Button(btnRich, SWT.CHECK);
		btnExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnExcel.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnExcel.setText("导出功能");
		
		btnTrace = new Button(btnRich, SWT.CHECK);
		btnTrace.setText("跟踪记录");
		
		btnMap = new Button(btnRich, SWT.CHECK);
		btnMap.setText("地理信息");
		
		btnTime = new Button(btnRich, SWT.CHECK);
		btnTime.setText("查询时间");
		
		btnRadio = new Button(btnRich, SWT.CHECK);
		btnRadio.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnRadio.setText("单选框");
		
	



		CTabItem adTab = new CTabItem(tabFolder_1, SWT.NONE);
		adTab.setToolTipText("高级设置");
		adTab.setText("高级设置");

		Composite compAds = new Composite(tabFolder_1, SWT.NONE);
		adTab.setControl(compAds);
		compAds.setLayout(new GridLayout(12, false));

		Label lblSchema = new Label(compAds, SWT.NONE);
		lblSchema.setText("schema：");

		txtDsw = new Text(compAds, SWT.BORDER);
		GridData gd_txtDsw = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtDsw.widthHint = 200;
		txtDsw.setLayoutData(gd_txtDsw);
		txtDsw.setText("");
		new Label(compAds, SWT.NONE);

		Label label_7 = new Label(compAds, SWT.LEFT);
		label_7.setText("服务层后缀：");

		commServcieName = new Combo(compAds, SWT.BORDER);
		commServcieName.setItems(new String[] { "Service" });
		commServcieName.setText("Service");
		new Label(compAds, SWT.NONE);

		Label labPackage = new Label(compAds, SWT.NONE);
		labPackage.setText("package：");

		txtFuncPackage = new Text(compAds, SWT.BORDER);
		GridData gd_txtFuncPackage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtFuncPackage.widthHint = 200;
		txtFuncPackage.setLayoutData(gd_txtFuncPackage);
		txtFuncPackage.setText("");
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);

		Label lblDao = new Label(compAds, SWT.NONE);
		lblDao.setText("基础包：");

		txtBasePackage = new Text(compAds, SWT.BORDER);
		GridData gd_txtBasePackage = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtBasePackage.widthHint = 250;
		txtBasePackage.setLayoutData(gd_txtBasePackage);
		// txtBasePackage.setText(DevopsToolConstants.business_interface);
		new Label(compAds, SWT.NONE);

		Label baseService = new Label(compAds, SWT.NONE);
		baseService.setText("DAO基础包：");

		text_1 = new Text(compAds, SWT.BORDER);
		GridData gd_text_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_text_1.widthHint = 200;
		text_1.setLayoutData(gd_text_1);
		text_1.setText("dao");
		new Label(compAds, SWT.NONE);

		Label lblNewLabel_2 = new Label(compAds, SWT.NONE);
		lblNewLabel_2.setText("mapper：");

		txtMapperDir = new Text(compAds, SWT.BORDER);
		GridData gd_txtMapperDir = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtMapperDir.widthHint = 200;
		txtMapperDir.setLayoutData(gd_txtMapperDir);
		// txtMapperDir.setText(DevopsToolConstants.Project_dao);

		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		Label lblService = new Label(compAds, SWT.NONE);
		lblService.setText("Service：");

		txtService = new Text(compAds, SWT.BORDER);
		GridData gd_txtService = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtService.widthHint = 250;
		txtService.setLayoutData(gd_txtService);
		// txtService.setText(DevopsToolConstants.business_center);
		new Label(compAds, SWT.NONE);

		Label lblBunsiness = new Label(compAds, SWT.NONE);
		lblBunsiness.setText("DAO目录：");

		txtDir4business = new Text(compAds, SWT.BORDER);
		GridData gd_txtDir4business = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtDir4business.widthHint = 200;
		txtDir4business.setLayoutData(gd_txtDir4business);
		txtDir4business.setText(DevopsToolConstants.getPACK_DAO());

		Button btnNewButton = new Button(compAds, SWT.NONE);
		btnNewButton.setText("选");

		Label lblNewLabel_1 = new Label(compAds, SWT.NONE);
		lblNewLabel_1.setText("entity：");

		txtEntiry = new Text(compAds, SWT.BORDER);
		GridData gd_txtEntiry = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtEntiry.widthHint = 200;
		txtEntiry.setLayoutData(gd_txtEntiry);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		Label label_8 = new Label(compAds, SWT.NONE);
		label_8.setText("Service目录：");

		txtServiceDir = new Text(compAds, SWT.BORDER);
		GridData gd_txtServiceDir = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtServiceDir.widthHint = 250;
		txtServiceDir.setLayoutData(gd_txtServiceDir);
		txtServiceDir.setText(DevopsToolConstants.getPROJECT_SERVCIE());
		new Label(compAds, SWT.NONE);

		Label lblMapper = new Label(compAds, SWT.NONE);
		lblMapper.setText("mapper目录：");

		txtMapper = new Text(compAds, SWT.BORDER);
		GridData gd_txtMapper = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtMapper.widthHint = 200;
		txtMapper.setLayoutData(gd_txtMapper);
		// txtMapper.setText(DevopsToolConstants.project_mapper_path);
		new Label(compAds, SWT.NONE);

		Label lblEntity = new Label(compAds, SWT.NONE);
		lblEntity.setText("Entity目录：");

		txtEntityDir = new Text(compAds, SWT.BORDER);
		GridData gd_txtEntityDir = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtEntityDir.widthHint = 200;
		txtEntityDir.setLayoutData(gd_txtEntityDir);
		txtEntityDir.setText(DevopsToolConstants.getProject_model());
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);

		Label lblmaven = new Label(compAds, SWT.NONE);
		lblmaven.setText("网页Maven：");

		comHtmlMaven = new Combo(compAds, SWT.NONE);
		GridData gd_comHtmlMaven = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_comHtmlMaven.widthHint = 250;
		comHtmlMaven.setLayoutData(gd_comHtmlMaven);
		comHtmlMaven.setItems(new String[] { DevopsToolConstants.PROJECT_APP });
		comHtmlMaven.setText(DevopsToolConstants.getPROJECT_WEB_APP());
		new Label(compAds, SWT.NONE);

		Label lblmaven_1 = new Label(compAds, SWT.NONE);
		lblmaven_1.setText("控制器Maven：");

		txtControlerModel = new Combo(compAds, SWT.BORDER);
		GridData gd_txtControlerModel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtControlerModel.widthHint = 200;
		txtControlerModel.setLayoutData(gd_txtControlerModel);
		txtControlerModel
				.setItems(new String[] { DevopsToolConstants.getProject_front_path(), DevopsToolConstants.getProject_manager_path(),
						DevopsToolConstants.getJai_project_front_path(), DevopsToolConstants.getJai_project_manager_path() });
		txtControlerModel.setText(DevopsToolConstants.Project_web_path);

		Button button_2 = new Button(compAds, SWT.NONE);
		button_2.setText("选");
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);
		new Label(compAds, SWT.NONE);

		CTabItem 其他 = new CTabItem(tabFolder_1, SWT.NONE);
		其他.setText("其他");

		composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		composite.setBounds(10, 10, 391, 252);
		GridLayout gl_composite = new GridLayout(11, false);
		gl_composite.verticalSpacing = 2;
		gl_composite.marginHeight = 1;
		gl_composite.horizontalSpacing = 1;
		composite.setLayout(gl_composite);

		Composite groupFunction = new Composite(composite, SWT.NONE);
		GridLayout gl_group = new GridLayout(11, false);
		gl_group.verticalSpacing = 1;
		gl_group.marginWidth = 1;
		gl_group.marginHeight = 1;
		gl_group.horizontalSpacing = 1;
		groupFunction.setLayout(gl_group);
		GridData gd_group = new GridData(SWT.FILL, SWT.FILL, true, false, 11, 1);
		gd_group.heightHint = 35;
		groupFunction.setLayoutData(gd_group);

		Button btnAllCheck = new Button(groupFunction, SWT.NONE);
		GridData gd_btnAllCheck = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAllCheck.widthHint = 126;
		btnAllCheck.setLayoutData(gd_btnAllCheck);
		btnAllCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeAllCheck();
			}
		});

		Button btnQryAll = new Button(groupFunction, SWT.NONE);
		btnQryAll.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeQryAllCheck();
			}
		});
		btnQryAll.setText("查询字段全选");

		Button btnAllAu = new Button(groupFunction, SWT.NONE);
		btnAllAu.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeAllAuCheck();
			}
		});
		btnAllAu.setText("增修字段全选");

		Button btnVali = new Button(groupFunction, SWT.NONE);
		btnVali.setText("vali验证字段全选");

		btnVali.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeValiFieldTreeChcek();
			}
		});

		Button btnGenMaven = new Button(groupFunction, SWT.NONE);
		btnGenMaven.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (comboStyle.getText() == null || "".equals(comboStyle.getText())) {
					MessageDialog.openInformation(null, "提示", "请选择要生成的项目名称！");
					return;
				}
				// 生成代码
				genMavenCode();
			}
		});
		btnGenMaven.setText("生成代码");
		btnAllCheck.setText("主表全选");

		Button btnNewButton_3 = new Button(groupFunction, SWT.NONE);
		btnNewButton_3.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {

				copyToProject();
			}
		});
		btnNewButton_3.setText("复制至正式项目");

		Button btnNewButton_1 = new Button(groupFunction, SWT.NONE);
		btnNewButton_1.setVisible(false);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				saveConfig2Object();
			}
		});
		GridData gd_btnNewButton_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnNewButton_1.widthHint = 120;
		btnNewButton_1.setLayoutData(gd_btnNewButton_1);
		btnNewButton_1.setText("保存修改");
		Button btnQryField = new Button(groupFunction, SWT.NONE);
		btnQryField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				choseQryField();
			}
		});
		btnQryField.setText("查询字段选择");
		new Label(groupFunction, SWT.NONE);
		btnQryField.setVisible(false);

		Button btnSelTableField = new Button(groupFunction, SWT.NONE);
		btnSelTableField.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showTableSel();
			}
		});
		btnSelTableField.setText("列表字段选择");
		btnSelTableField.setVisible(false);

		Button btnSelAdFields = new Button(groupFunction, SWT.NONE);
		btnSelAdFields.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				showAduList();
			}
		});
		btnSelAdFields.setText("增删字段选择");
		btnSelAdFields.setVisible(false);

		CTabFolder tabFolder = new CTabFolder(composite, SWT.BORDER);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 10, 1));
		tabFolder.setSelectionBackground(Display.getCurrent().getSystemColor(SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT));

		tbtmNewItem = new CTabItem(tabFolder, SWT.NONE);
		tbtmNewItem.setText("字段选择");

		tbFunctionSelItem = new CTabItem(tabFolder, SWT.NONE);
		tbFunctionSelItem.setText("select function");

		Composite composite_1 = new Composite(tabFolder, SWT.NONE);
		tbFunctionSelItem.setControl(composite_1);
		composite_1.setLayout(new GridLayout(4, false));
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);
		new Label(composite_1, SWT.NONE);

		Label label_10 = new Label(composite_1, SWT.NONE);
		label_10.setText("New Label");
		compChose = new Composite(tabFolder, SWT.BORDER);
		tbtmNewItem.setControl(compChose);
		compChose.setLayout(new GridLayout(3, false));
		mainFieldTreeViewer = new CheckboxTreeViewer(compChose, SWT.BORDER | SWT.CHECK);
		Tree mailFieldTree = mainFieldTreeViewer.getTree();
		GridData gd_mailFieldTree = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_mailFieldTree.widthHint = 297;
		mailFieldTree.setLayoutData(gd_mailFieldTree);
		mailFieldTree.setHeaderVisible(true);
		mailFieldTree.setHeaderVisible(true);
		mailFieldTree.setLinesVisible(true);

		TreeColumn trclmnNewColumn = new TreeColumn(mailFieldTree, SWT.NONE);
		trclmnNewColumn.setWidth(300);
		trclmnNewColumn.setText("主表字段");

		Menu menu = new Menu(mainFieldTreeViewer.getTree());
		MenuItem newItem = new MenuItem(menu, SWT.PUSH);
		newItem.setText("连接子表");
		newItem.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openSubFiledLinkedDialog(e);
			}
		});

		MenuItem deleteItem = new MenuItem(menu, SWT.PUSH);
		deleteItem.setText("删除");
		deleteItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		mainFieldTreeViewer.getTree().setMenu(menu);

		mainQueryFieldTreeViewer = new CheckboxTreeViewer(compChose, SWT.BORDER | SWT.CHECK);
		Tree qryMailFieldTree = mainQueryFieldTreeViewer.getTree();
		GridData qryGgd_mailFieldTree = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		qryGgd_mailFieldTree.widthHint = 255;
		qryMailFieldTree.setLayoutData(qryGgd_mailFieldTree);
		qryMailFieldTree.setHeaderVisible(true);
		qryMailFieldTree.setHeaderVisible(true);
		qryMailFieldTree.setLinesVisible(true);

		TreeColumn qryClmnNewColumn = new TreeColumn(qryMailFieldTree, SWT.NONE);
		qryClmnNewColumn.setWidth(250);
		qryClmnNewColumn.setText("查询条件字段");

		mainAuFieldTreeViewer = new CheckboxTreeViewer(compChose, SWT.BORDER | SWT.CHECK);
		Tree auMailFieldTree = mainAuFieldTreeViewer.getTree();
		auMailFieldTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		qryGgd_mailFieldTree.widthHint = 255;
		auMailFieldTree.setHeaderVisible(true);
		auMailFieldTree.setHeaderVisible(true);
		auMailFieldTree.setLinesVisible(true);

		TreeColumn auClmnNewColumn = new TreeColumn(auMailFieldTree, SWT.NONE);
		auClmnNewColumn.setWidth(250);
		auClmnNewColumn.setText("增修字段");

		// 校验
		mainValiFieldTreeViewer = new CheckboxTreeViewer(compChose, SWT.BORDER | SWT.CHECK);
		Tree valiFiedTree = mainValiFieldTreeViewer.getTree();
		valiFiedTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		qryGgd_mailFieldTree.widthHint = 255;
		valiFiedTree.setHeaderVisible(true);
		valiFiedTree.setHeaderVisible(true);
		valiFiedTree.setLinesVisible(true);

		TreeColumn valiClmnNewColumn = new TreeColumn(valiFiedTree, SWT.NONE);
		valiClmnNewColumn.setWidth(250);
		valiClmnNewColumn.setText("vali验证字段");
		new Label(compChose, SWT.NONE);
		new Label(compChose, SWT.NONE);

		initUI();
		tabFolder.setSelection(tbtmNewItem);
		new Label(composite, SWT.NONE);
	}

	protected void copyToProject() {
		this.copyProj();
	}

	/**
	 * 不同代码风格对应不同分类包路径
	 */
	protected void changePath() {
		String name = comboStyle.getText().trim();
		if (DevopsToolConstants.KANKAN_BUTTON_NAME.equals(name)) {
			DevopsBasePath.setBASPACK(DevopsBasePath.BASECOMPANY_KANKAN);
			DevopsBasePath.setPath(DevopsBasePath.BASECOMPANY_PATH_KANKAN);
			DevopsBasePath.setProjectName(DevopsBasePath.KANKAN_PROJECTSHORTNAME);
			txtPackage.setText(DevopsToolConstants.getBuss());
			txtBasePackage.setText(DevopsToolConstants.getBusiness_interface());
			txtService.setText(DevopsToolConstants.getBusiness_center());
			txtEntiry.setText(DevopsToolConstants.getProject_entity());
			txtMapper.setText(DevopsToolConstants.getProject_mapper_path());

		} else if (DevopsToolConstants.CRM_BUTTON_NAME.equals(name)) {

			DevopsBasePath.setBASPACK(DevopsBasePath.BASECOMPANY_HD);
			DevopsBasePath.setPath(DevopsBasePath.BASECOMPANY_PATH);
			DevopsBasePath.setProjectName(DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME);
			txtPackage.setText(DevopsToolConstants.getBuss());
			txtBasePackage.setText(DevopsToolConstants.getJai_business_interface());
			txtService.setText(DevopsToolConstants.getJaiBusiness());
			txtEntiry.setText(DevopsToolConstants.getJai_project_entity());
			txtMapper.setText(DevopsToolConstants.getJai_project_mapper_path());
		} else if (DevopsToolConstants.KZS_BUTTON_NAME.equals(name)) {
			DevopsBasePath.setBASPACK(DevopsBasePath.BASECOMPANY_HD);
			DevopsBasePath.setPath(DevopsBasePath.BASECOMPANY_PATH);
			DevopsBasePath.setProjectName(DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME);
			txtPackage.setText(DevopsToolConstants.getBuss());
			txtBasePackage.setText(DevopsToolConstants.getKzs_business_interface());
			txtService.setText(DevopsToolConstants.getKzsBusiness());
			txtEntiry.setText(DevopsToolConstants.getKzs_project_entity());
			txtMapper.setText(DevopsToolConstants.getKzs_project_mapper_path());
		}
		txtDir4business.setText(DevopsToolConstants.getPACK_DAO());
		txtServiceDir.setText(DevopsToolConstants.getBuss());
		comHtmlMaven.setText(DevopsToolConstants.getPROJECT_SERVCIE());
		txtEntityDir.setText(DevopsToolConstants.getProject_model());

	}

	/**
	 * 生成代码
	 */
	public HashMap<String, Object> genMavenCode() {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		String mainTableName = txtDbTableList.getText();
		String mainTableAsName = txtMainTableAs.getText();
		resultMap = new HashMap<String, Object>();
		resultMap.put("choseNode", mainFieldTreeViewer.getCheckedElements());
		resultMap.put("dbTableList", mainTableName);
		resultMap.put("mainTableName", mainTableName);
		resultMap.put("MainTableAs", mainTableAsName);
		resultMap.put("MainFiled", txtMainFiled.getText());
		resultMap.put("package", txtPackage.getText());
		resultMap.put("daopackage", txtPackage.getText());
		resultMap.put("basePackage", txtBasePackage.getText());
		resultMap.put("service", txtService.getText());
		resultMap.put("dbClassName", txtClassName.getText());
		resultMap.put("entity", txtEntiry.getText());
		resultMap.put("mapper", txtMapper.getText());
		resultMap.put("auth", txtAuth.getText());
		resultMap.put("commDao", "true");
		resultMap.put("commService", "true");
		resultMap.put("commEntity", "true");
		resultMap.put("commMapper", "true");
		resultMap.put("Dir4business", txtDir4business.getText() + "");
		resultMap.put("txtMapperDir", txtMapperDir.getText() + "");
		resultMap.put("txtEntityDir", txtEntityDir.getText() + "");
		resultMap.put("txtServiceDir", txtServiceDir.getText() + "");
		resultMap.put("txtControlerModel", txtControlerModel.getText() + "");
		resultMap.put("comHtmlMaven", comHtmlMaven.getText() + "");
		resultMap.put("serviceFileName", commServcieName.getText() + "");
		// 模块
		resultMap.put("modelName", txtModeName.getText() + "");
		resultMap.put("projectButtion", comboStyle.getText());

		// 上下架代码

		resultMap.put("canUpDown", this.btnCommit.getSelection() + "");
		resultMap.put("canUploadImage", this.btnCanImage.getSelection() + "");
		resultMap.put("canRichText", this.btnCheckButton.getSelection() + "");
		resultMap.put("canStopStart", this.btnStopStart.getSelection() + "");
		resultMap.put("canCategory", this.btnCanCategory.getSelection() + "");
		// sql按钮增删改 批量
		resultMap.put("canAddSql", this.btnAddSql.getSelection() + "");
		resultMap.put("canDeleteSql", this.btnDeleteSql.getSelection() + "");
		resultMap.put("canUpdateSql", this.btnUpdateSql.getSelection() + "");
		resultMap.put("canChangeSql", this.btnChangeSql.getSelection() + "");
		// 分账明细勾选
		resultMap.put("Split", this.btnSplit.getSelection() + "");
		// 导出功能
		resultMap.put("Excel", this.btnExcel.getSelection() + "");
		// 跟踪记录
		resultMap.put("Trace", this.btnTrace.getSelection() + "");
		// 地理信息
		resultMap.put("Map", this.btnMap.getSelection() + "");
		// 查询时间
		resultMap.put("Time", this.btnTime.getSelection() + "");
		// 单选框
		resultMap.put("Radio",this.btnRadio.getSelection() + "");

		// 核心框架包的路径
		resultMap.put("frameWorkPath", this.txtCoreFrameworkPath.getText() + "");

		String funcPackage = this.txtFuncPackage.getText();
		DevopsMavenCodeGenerater gen = new DevopsMavenCodeGenerater();
		RootModel root = new RootModel();
		TableModel table = getTableRootObject(mainFieldTreeViewer, mainTableName, mainTableAsName, root);

		RootModel qryRoot = new RootModel();
		TableModel qryTable = getTableRootObject(this.mainQueryFieldTreeViewer, mainTableName, mainTableAsName, qryRoot);

		RootModel auRoot = new RootModel();
		TableModel auTable = getTableRootObject(this.mainAuFieldTreeViewer, mainTableName, mainTableAsName, auRoot);

		// 新增校验
		RootModel valiRoot = new RootModel();
		TableModel valiTable = getTableRootObject(this.mainValiFieldTreeViewer, mainTableName, mainTableAsName, valiRoot);

		resultMap.put("logicalName", tableDesc);
		resultMap.put("sysdateOut", getSysDate());

		String lastModel = txtPackage.getText();
		int lastPos = lastModel.lastIndexOf('.');
		if (lastPos > -1) {
			lastModel = lastModel.substring(lastPos + 1);
			resultMap.put("htmlModel", lastModel);
		}
		try {

			// 调用生成代码的服务
			gen.generate(txtGenCodeComm.getText(), txtGenCodeProject.getText() + File.separator + DbRes.getConvertName(tableName), root,
					resultMap, funcPackage, qryRoot, auRoot, valiRoot);
			resMap.put("entityPath", resultMap.get("entityPath"));
			resMap.put("mapAs", resultMap.get("mapAs"));

			MessageDialog.openInformation(this.getShell(), "提示", "代码已生成");
		} catch (Exception e) {
			System.out.println("e:" + e.getMessage());
			e.printStackTrace();
		}

		this.resMap = resMap;
		return resMap;
	}

	private TableModel getTableRootObject(CheckboxTreeViewer treeView, String mainTableName, String mainTableAsName, RootModel root) {
		Object data[] = treeView.getCheckedElements();
		ArrayList<ColumnModel> listCol = new ArrayList<ColumnModel>();
		ArrayList<ColumnModel> listColMain = new ArrayList<ColumnModel>();
		TableModel table = new TableModel();
		table.setTableName(mainTableName);
		table.setTableNameAs(mainTableAsName);
		table.appSqlTableJoin(table.getTableName() + " " + table.getTableNameAs());

		for (int i = 0; i < data.length; i++) {
			Node node = (Node) data[i];
			node.getColumn().setTableAs(mainTableAsName);
			node.getColumn().setTableName(mainTableName);
			node.getColumn().setMainTableType(0);
			listCol.add(node.getColumn());
			listColMain.add(node.getColumn());

			List<Node> linkNodeList = node.getChildren();
			Node firstJoinNode = null;
			for (int j = 0; j < linkNodeList.size(); j++) {
				Node linkNode = linkNodeList.get(j);
				firstJoinNode = linkNode;
				linkNode.getColumn().setMainTableType(1);
				listCol.add(linkNode.getColumn());

			}
			if (firstJoinNode != null) {

				table.appSqlTableJoin(
						" left join " + firstJoinNode.getColumn().getTableName() + " " + firstJoinNode.getColumn().getTableAs() + " " + "");
				table.appSqlTableJoin(" on " + firstJoinNode.getMainTableNameAs() + "." + firstJoinNode.getMainLinedField() + " = "
						+ firstJoinNode.getRefTablesAs() + "." + firstJoinNode.getRefField() + " \n ");

			}
		}

		table.setColumnsArr(listCol.toArray());
		table.setColumnsMainArr(listColMain.toArray());
		root.addChild(table);
		return table;
	}

	/**
	 * 复制代码
	 */
	protected void copyProj() {
		if (resMap == null) {
			MessageDialog.openInformation(this.getShell(), "提示", "先生成代码后再复制");
			return;
		}
		String targetBasePath = txtProjectPath.getText();// txtProjTargetDir.getText();
		String sourceDirPath = txtGenCodeProject.getText() + File.separator + DbRes.getConvertName(tableName) + File.separator + "code";
		File srcDir = new File(sourceDirPath);
		File destDir = new File(targetBasePath);

		try {

			System.out.println("source destDir---->:" + destDir);
			FileUtils.copyDirectory(srcDir, destDir);
			MessageDialog.openInformation(this.getShell(), "提示", "已复制代码至正式项目对应目录");
		} catch (IOException e) {
			System.out.println("复制代码 异常:" + destDir);
		}

	}

	protected void showAduList() {
		QueryTableFieldSelDialog qryDialog = new QueryTableFieldSelDialog(this.getShell(), SWT.APPLICATION_MODAL | SWT.CLOSE, this.res,
				mainFieldTreeViewer.getInput(), 1);
		qryDialog.open();
		this.aduOut = qryDialog.getChoseData();

	}

	protected void showTableSel() {
		QueryTableFieldSelDialog qryDialog = new QueryTableFieldSelDialog(this.getShell(), SWT.APPLICATION_MODAL | SWT.CLOSE, this.res,
				mainFieldTreeViewer.getInput(), 2);
		qryDialog.open();
		this.qryOutArr = qryDialog.getChoseData();

	}

	protected void choseQryField() {
		QueryTableFieldSelDialog qryDialog = new QueryTableFieldSelDialog(this.getShell(), SWT.APPLICATION_MODAL | SWT.CLOSE, this.res,
				mainFieldTreeViewer.getInput(), 1);
		qryDialog.open();
		this.tableOut = qryDialog.getChoseData();

	}

	protected void saveConfig2Object() {
		String key = txtPackage.getText() + txtDbTableList.getText();

		SaveConfig saveConf = new SaveConfig();
		saveConf.setKey(key);
		saveConf.setPackage(txtPackage.getText());
	}

	protected void treeAllCheck() {
		hadChoseAll = true;
		if (!isCheck) {
			mainFieldTreeViewer.setAllChecked(true);
			isCheck = true;
		} else {
			mainFieldTreeViewer.setAllChecked(false);
			isCheck = false;
		}

	}

	protected void treeQryAllCheck() {
		hadChoseQryAll = true;
		if (!isQryCheck) {
			mainQueryFieldTreeViewer.setAllChecked(true);
			isQryCheck = true;
		} else {
			mainQueryFieldTreeViewer.setAllChecked(false);
			isQryCheck = false;
		}

	}

	protected void treeValiFieldTreeChcek() {
		hadValiFiledAll = true;
		if (!isVali) {
			mainValiFieldTreeViewer.setAllChecked(true);
			isVali = true;
		} else {
			mainValiFieldTreeViewer.setAllChecked(false);
			isVali = false;
		}

	}

	protected void treeAllAuCheck() {
		hadChoseAllAu = true;
		if (!isCheckAu) {
			mainAuFieldTreeViewer.setAllChecked(true);
			isCheckAu = true;
		} else {
			mainAuFieldTreeViewer.setAllChecked(false);
			isCheckAu = false;
		}

	}

	protected void procssGenCode() {
		resultMap = new HashMap();
		resultMap.put("choseNode", mainFieldTreeViewer.getCheckedElements());
		String mainTableName = txtDbTableList.getText();
		String mainTableAsName = txtMainTableAs.getText();
		resultMap.put("dbTableList", mainTableName);
		resultMap.put("mainTableName", mainTableName);
		resultMap.put("MainTableAs", mainTableAsName);
		resultMap.put("MainFiled", txtMainFiled.getText());
		resultMap.put("package", txtPackage.getText());
		resultMap.put("daopackage", txtPackage.getText());
		// xyx.dsw.dao.mapper.center

		resultMap.put("basePackage", txtBasePackage.getText());
		resultMap.put("service", txtService.getText());
		resultMap.put("dbClassName", txtClassName.getText());

		resultMap.put("entity", txtEntiry.getText());
		resultMap.put("mapper", txtMapper.getText());
		resultMap.put("auth", txtAuth.getText());

		String funcPackage = this.txtFuncPackage.getText();
		RootModel root = new RootModel();
		Object data[] = mainFieldTreeViewer.getCheckedElements();
		ArrayList<ColumnModel> listCol = new ArrayList<ColumnModel>();
		ArrayList<ColumnModel> listColMain = new ArrayList<ColumnModel>();
		TableModel table = new TableModel();
		table.setTableName(mainTableName);
		table.setTableNameAs(mainTableAsName);
		table.appSqlTableJoin(table.getTableName() + " " + table.getTableNameAs());

		for (int i = 0; i < data.length; i++) {
			Node node = (Node) data[i];
			node.getColumn().setTableAs(mainTableAsName);
			node.getColumn().setTableName(mainTableName);
			node.getColumn().setMainTableType(0);
			listCol.add(node.getColumn());
			listColMain.add(node.getColumn());

			List<Node> linkNodeList = node.getChildren();
			Node firstJoinNode = null;
			for (int j = 0; j < linkNodeList.size(); j++) {
				Node linkNode = linkNodeList.get(j);
				firstJoinNode = linkNode;
				linkNode.getColumn().setMainTableType(1);
				listCol.add(linkNode.getColumn());

			}
			if (firstJoinNode != null) {

				table.appSqlTableJoin(
						" left join " + firstJoinNode.getColumn().getTableName() + " " + firstJoinNode.getColumn().getTableAs() + " " + "");
				table.appSqlTableJoin(" on " + firstJoinNode.getMainTableNameAs() + "." + firstJoinNode.getMainLinedField() + " = "
						+ firstJoinNode.getRefTablesAs() + "." + firstJoinNode.getRefField() + " \n ");

			}
		}

		table.setColumnsArr(listCol.toArray());
		table.setColumnsMainArr(listColMain.toArray());

		resultMap.put("logicalName", tableDesc);
		resultMap.put("sysdateOut", getSysDate());

		root.addChild(table);

		String lastModel = txtPackage.getText();
		int lastPos = lastModel.lastIndexOf('.');
		if (lastPos > -1) {
			lastModel = lastModel.substring(lastPos + 1);
			resultMap.put("htmlModel", lastModel);
		}
		try {
			File dir = new File(txtGenCodeComm.getText());
			if (!dir.exists()) {
				dir.mkdirs();
			}

			MessageDialog.openInformation(this.getShell(), "提示",
					"代码已生成，code目录下的文件请复制到项目根目录下，第二页签下的resultMap配置 请手动copy到configurationDsw.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getSysDate() {
		return dateToString(new Date(), Formater_yyyy_MM_dd_HH_mm_ss);
	}

	public static String dateToString(Date date, String format) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat formater = new SimpleDateFormat(isNull(format) ? "yyyy-MM-dd HH:mm:ss" : format.trim());
		return formater.format(date);
	}

	public static boolean isNull(String str) {
		str = str != null ? str.trim() : str;
		return str == null || "".equals(str) ? true : false;
	}

	protected void chaneList() {

	}

	private void initUI() {

		mainFieldTreeViewer.setContentProvider(new TVContentProvider());
		mainFieldTreeViewer.setLabelProvider(new TVLabelProvider());

		mainAuFieldTreeViewer.setContentProvider(new TVContentProvider());
		mainAuFieldTreeViewer.setLabelProvider(new TVLabelProvider());

		mainQueryFieldTreeViewer.setContentProvider(new TVContentProvider());
		mainQueryFieldTreeViewer.setLabelProvider(new TVLabelProvider());
		// 验证
		mainValiFieldTreeViewer.setContentProvider(new TVContentProvider());
		mainValiFieldTreeViewer.setLabelProvider(new TVLabelProvider());

		mainFieldTreeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

			public void selectionChanged(SelectionChangedEvent event) {

				if (event.getSelection().isEmpty()) {
					System.out.println("Selected Nothing");
					return;

				}

				if (event.getSelection() instanceof IStructuredSelection) {

					IStructuredSelection selection = (IStructuredSelection) event

							.getSelection();

					StringBuffer selected = new StringBuffer();

					for (Iterator iterator = selection.iterator(); iterator

							.hasNext();) {

						Object element = (Node) iterator.next();

						String value = ((Node) element).getName();

						selected.append(value);

						selected.append(", ");

					}

					// remove the trailing comma space pair

					if (selected.length() > 0) {

						selected.setLength(selected.length() - 2);

					}

					System.out.println(selected.toString());
				}
			}
		});
		String pkStr = "";
		if (tableName != null) {
			txtDbTableList.add(tableName);
			root = new RootNode();
			qryRoot = new RootNode();
			auRoot = new RootNode();
			valiRoot = new RootNode();
			for (int i = 0; i < columnModels.size(); i++) {
				ColumnModel column = columnModels.get(i);
				if (column.isPrimaryKey()) {
					pkStr = column.getColumnName();
				}
				Node node = new Node();
				node.setName(column.getColumnName());
				node.setColumnModel(column);
				root.addChild(node);
				qryRoot.addChild(node);
				auRoot.addChild(node);
				valiRoot.addChild(node);
			}
			mainFieldTreeViewer.setInput(root);
			this.mainAuFieldTreeViewer.setInput(auRoot);
			this.mainQueryFieldTreeViewer.setInput(qryRoot);
			this.mainValiFieldTreeViewer.setInput(valiRoot);
		} else {

			root = new RootNode();

			mainFieldTreeViewer.setInput(root);

			Class<?> driverClass;
			try {
				driverClass = classLoader.loadClass(res.getJdbcDriver());
				try {
					dbinfo = new DatabaseInfo(driverClass);
				} catch (InstantiationException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IllegalAccessException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			} catch (ClassNotFoundException e3) {

				e3.printStackTrace();
			}

			dbinfo.setURI(res.getDbUrl());
			dbinfo.setUser(res.getUser());
			dbinfo.setPassword(res.getPassword());
			dbinfo.setCatalog(res.getCatalog());
			dbinfo.setSchema(res.getSchema());
			dbinfo.setEnableView(false);
			dbinfo.setAutoConvert(false);

			ArrayList<String> tableNames = new ArrayList<String>();

			try {
				List<String> tableList = dbinfo.loadTables();
				String[] item = new String[tableList.size()];
				try {
					int i = 0;
					for (String tableName : tableList) {
						item[i] = tableName;
						i++;
						tableNames.add(tableName);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				txtDbTableList.setItems(item);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		txtDbTableList.setText(tableName);
		txtMainTableAs.setText(DbRes.getLowConvertName(tableName));
		txtClassName.setText(DbRes.getConvertName(tableName));
		// this.txtPackage.setText(DbRes.getLowConvertName(tableName));
		this.txtFuncPackage.setText(DbRes.getLowConvertName(tableName));

		if (pkStr == null || pkStr.equals("")) {
			pkStr = "id";
		}
		txtMainFiled.setText(pkStr);
		this.txtGenCodeComm.setText(txtGenCodeComm.getText() + DbRes.getConvertName(tableName));
		// this.txtGenCodeProject.setText(txtGenCodeProject.getText() +
		// DbRes.getConvertName(tableName));

	}

	protected Shell getShell() {
		return shell;
	}

	public void changeTable(DbRes res, String tables[]) {
		// dbTableList
		Connection conn = null;
		RootModel root = res.getRoot();
		IDialect dialect = DialectProvider.getDialect(res.getRoot().getDialectName());
		ISchemaLoader loader = dialect.getSchemaLoader();

		try {
			conn = dbinfo.connect();
			loader.loadSchema(root, DialectProvider.getDialect(root.getDialectName()), conn, tables, dbinfo.getCatalog(),
					dbinfo.getSchema(), dbinfo.isAutoConvert());

		} catch (Exception ex) {
			DBPlugin.logException(ex);

		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void openSubFiledLinkedDialog(SelectionEvent e) {
		Object ojb = e.widget;
		ISelection select = mainFieldTreeViewer.getSelection();
		IStructuredSelection selection = (IStructuredSelection) select;
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {

			Node element = (Node) iterator.next();
			String tableName = txtDbTableList.getText();
			String tableAs = txtMainTableAs.getText();
			element.getColumn().setTableName(tableName);
			element.getColumn().setTableAs(tableAs);
			System.out.println("" + element.getName());

			DevopsLinkSubFiledDialog subDia = new DevopsLinkSubFiledDialog(getShell(), SWT.APPLICATION_MODAL | SWT.CLOSE, element,
					tableName, element.getName(), res, txtDsw.getText());
			subDia.open();
			HashMap resultMap = subDia.getResult();

			if (resultMap.get("CHOSE") == null) {
				return;
			}
			Object choseRef[] = (Object[]) resultMap.get("choseNode");
			String refField = (String) resultMap.get("refField");
			String refTablesAs = (String) resultMap.get("refTablesAs");
			String refTableName = (String) resultMap.get("refTables");

			element.clearnChildNode();
			for (int i = 0; i < choseRef.length; i++) {
				Node no = (Node) choseRef[i];
				no.getColumn().setMainTableType(1);
				no.getColumn().setTableAs(refTablesAs);
				no.getColumn().setTableName(refTableName);

				no.setRefTable(refTableName);
				no.setRefTablesAs(refTablesAs);
				no.setRefField(refField);
				no.setMainLinedField(element.getName());
				no.setMainLinkedtableName(element.getColumn().getTableName());
				no.setMainTableNameAs(element.getColumn().getTableAs());
				element.addChild(no);
			}
			mainFieldTreeViewer.refresh(element);
		}
	}
}
