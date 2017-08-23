package cn.devops.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.java.amateras.db.DBPlugin;
import net.java.amateras.db.dialect.DialectProvider;
import net.java.amateras.db.dialect.IDialect;
import net.java.amateras.db.dialect.ISchemaLoader;
import net.java.amateras.db.ui.bean.Node;
import net.java.amateras.db.ui.bean.RootNode;
import net.java.amateras.db.ui.provider.TVContentProvider;
import net.java.amateras.db.ui.provider.TVLabelProvider;
import net.java.amateras.db.util.DatabaseInfo;
import net.java.amateras.db.util.JarClassLoader;
import net.java.amateras.db.visual.model.ColumnModel;
import net.java.amateras.db.visual.model.RootModel;
import net.java.amateras.db.visual.model.TableModel;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import cn.devops.util.db.DbRes;

/**
 * 选择连接
 * 
 * @author wing
 * 
 */
public class GenCode4DbSubTableSelDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text txtMainTable;
	private Combo txtRefTable;
	private Text txtMainField;
	private DatabaseInfo dbinfo;
	private String dbUrl;
	private JarClassLoader classLoader;
	private DbRes res;
	private CheckboxTreeViewer treeViewer;
	private RootNode root;
	private String tableName;
	private List<ColumnModel> columnModels;
	private Text txtAsTables;
	private String filedLink;

	HashMap resultMap = new HashMap();
	private Combo comRefField;
	private String schema;

	/**
	 * @wbp.parser.constructor
	 */
	public GenCode4DbSubTableSelDialog(Shell shell2, int applicationModal, DbRes res, JarClassLoader classLoader) {
		super(shell2, applicationModal);
		setText("选择连接");
		this.res = res;
		this.dbUrl = res.getDbUrl();
		this.classLoader = classLoader;
	}

	public GenCode4DbSubTableSelDialog(Shell shell2, int applicationModal, RootModel rootModel, String tableName, Table tblColumns,
			List<ColumnModel> columnModels) {
		super(shell2, applicationModal);
		this.tableName = tableName;
		this.columnModels = columnModels;
	}

	public GenCode4DbSubTableSelDialog(Shell shell2, int applicationModal, Node element, String tableName, String filedLink, DbRes res,
			String schema) {
		super(shell2, applicationModal);
		this.tableName = tableName;
		this.filedLink = filedLink;
		this.res = res;
		this.schema = schema;

	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
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
		shell.setSize(786, 506);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 391, 252);
		composite.setLayout(new GridLayout(3, false));

		Label label = new Label(composite, SWT.NONE);
		label.setText("主表名称");

		txtMainTable = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtMainTable = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtMainTable.widthHint = 200;
		txtMainTable.setLayoutData(gd_txtMainTable);
		new Label(composite, SWT.NONE);

		Label label_2 = new Label(composite, SWT.NONE);
		label_2.setText("主表字段");

		txtMainField = new Text(composite, SWT.BORDER | SWT.READ_ONLY);
		GridData gd_txtMainField = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_txtMainField.widthHint = 200;
		txtMainField.setLayoutData(gd_txtMainField);
		new Label(composite, SWT.NONE);

		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setText("引用表名");

		txtRefTable = new Combo(composite, SWT.BORDER);
		txtRefTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				chaneList();
			}
		});
		GridData gd_txtRefTable = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtRefTable.widthHint = 310;
		txtRefTable.setLayoutData(gd_txtRefTable);

		Label label_1 = new Label(composite, SWT.NONE);
		label_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_1.setText("表别名");

		txtAsTables = new Text(composite, SWT.BORDER);
		GridData gd_txtAsTables = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_txtAsTables.widthHint = 334;
		txtAsTables.setLayoutData(gd_txtAsTables);

		Label label_4 = new Label(composite, SWT.NONE);
		label_4.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		label_4.setText("引用字段");

		comRefField = new Combo(composite, SWT.NONE);
		GridData gd_comRefField = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_comRefField.widthHint = 273;
		comRefField.setLayoutData(gd_comRefField);

		Label label_3 = new Label(composite, SWT.NONE);
		label_3.setText("关聯方式");

		Button btnCheckButton = new Button(composite, SWT.RADIO);
		btnCheckButton.setText("左连接");

		Button button_1 = new Button(composite, SWT.RADIO);
		button_1.setText("右连接");

		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout(7, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 3, 1));

		Button button = new Button(group, SWT.NONE);
		GridData gd_button = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button.widthHint = 70;
		button.setLayoutData(gd_button);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeAllCheck();
			}
		});
		button.setText("全選");

		Button button_2 = new Button(group, SWT.NONE);
		GridData gd_button_2 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_button_2.widthHint = 70;
		button_2.setLayoutData(gd_button_2);
		button_2.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				resultMap.put("CHOSE", "OK");
				resultMap.put("choseNode", treeViewer.getCheckedElements());
				resultMap.put("refField", comRefField.getText());
				resultMap.put("refTablesAs", txtAsTables.getText());
				resultMap.put("refTables", txtRefTable.getText());
				getShell().dispose();

			}
		});
		button_2.setText("确定");

		Button btnLoadTable = new Button(group, SWT.NONE);
		btnLoadTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				loadTableFiled();
			}
		});
		btnLoadTable.setText("载入表字段");
		new Label(group, SWT.NONE);
		new Label(group, SWT.NONE);

		Label lblNewLabel_1 = new Label(group, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("修改字段别名");

		txtFiledAsName = new Text(group, SWT.BORDER);
		txtFiledAsName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		treeViewer = new CheckboxTreeViewer(composite, SWT.BORDER | SWT.CHECK);
		Tree tree = treeViewer.getTree();
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1);
		tree.setHeaderVisible(true);

		gd_tree.widthHint = 522;
		gd_tree.heightHint = 180;
		tree.setLayoutData(gd_tree);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		TreeColumn trclmnNewColumn = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn.setWidth(504);
		trclmnNewColumn.setText("选/输出字段");

		initUI();
	}

	protected void loadTableFiled() {
		chaneList();

	}

	protected Shell getShell() {
		return this.shell;
	}

	boolean isCheck = false;
	private Text txtFiledAsName;

	protected void treeAllCheck() {
		if (!isCheck) {
			treeViewer.setAllChecked(true);
			isCheck = true;
		} else {
			treeViewer.setAllChecked(false);
			isCheck = false;
		}

	}

	protected void chaneList() {
		String tableName = txtRefTable.getText();

		TableModel tmodel;
		Connection connection = null;
		try {
			connection = res.getDatabaseInfo().getConnection();
			String catalog = res.getCatalog();

			String schema = this.schema;
			// if (schema == null || schema.equals(""))
			// schema = "ZJXXT";
			tmodel = res.getDatabaseInfo().getTableInfo(tableName, (IDialect) res.dbStatus.get("MySQL"), connection, catalog, schema,
					false);
			ColumnModel[] columnModel = tmodel.getColumns();
			String[] filedArr = new String[tmodel.getColumns().length];

			RootNode root = new RootNode();
			for (int i = 0; i < columnModel.length; i++) {
				Node node = new Node();
				node.setName(columnModel[i].getColumnName());
				node.setColumnModel(columnModel[i]);
				root.addChild(node);
				filedArr[i] = node.getName();

			}
			this.treeViewer.setInput(root);
			comRefField.setItems(filedArr);
			connection.close();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void initUI() {
		txtMainTable.setText(this.tableName);
		txtMainField.setText(this.filedLink);
		treeViewer.setContentProvider(new TVContentProvider());

		treeViewer.setLabelProvider(new TVLabelProvider());

		treeViewer.addSelectionChangedListener(new ISelectionChangedListener() {

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

						String fieldValue = ((Node) element).getColumn().getColumnNameOutLow();
						if (fieldValue == null) {
							fieldValue = "";
						}
						txtFiledAsName.setText(fieldValue);

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

		Menu menu = new Menu(treeViewer.getTree());

		MenuItem deleteItem = new MenuItem(menu, SWT.PUSH);
		deleteItem.setText("删除");
		deleteItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});

		MenuItem reNameItem = new MenuItem(menu, SWT.PUSH);
		reNameItem.setText("修改名称");
		reNameItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				processRename(e);
			}
		});

		treeViewer.getTree().setMenu(menu);

		root = RootNode.getRootNode2();
		treeViewer.setInput(root);

		Class<?> driverClass = null;
		try {
			driverClass = res.getJarClassLoader().loadClass(res.getJdbcDriver());
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
			txtRefTable.setItems(item);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	protected void processRename(SelectionEvent event) {
		Object ojb = event.widget;
		ISelection select = treeViewer.getSelection();
		IStructuredSelection selection = (IStructuredSelection) select;
		for (Iterator iterator = selection.iterator(); iterator.hasNext();) {
			Node element = (Node) iterator.next();
			String assName = txtFiledAsName.getText();
			boolean isCHeck = MessageDialog.openConfirm(this.getShell(), "提示", "确认要修改属性别名为" + assName + "吗？");
			if (isCHeck) {
				element.getColumn().setColumnNameOutLowSpec(assName);
				element.getColumn().setColumnNameOut(assName);
			}

		}
	}

	private void changeTable(DbRes res, String tables[]) {
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

	public HashMap getResult() {

		return resultMap;
	}
}
