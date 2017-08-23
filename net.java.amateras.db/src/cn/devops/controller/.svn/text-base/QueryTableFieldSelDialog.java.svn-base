package cn.devops.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.java.amateras.db.ui.bean.Node;
import net.java.amateras.db.ui.bean.RootNode;
import net.java.amateras.db.ui.provider.TVContentProvider;
import net.java.amateras.db.ui.provider.TVLabelProvider;
import net.java.amateras.db.util.DatabaseInfo;
import net.java.amateras.db.visual.model.ColumnModel;
import net.java.amateras.db.visual.model.TableModel;

import org.eclipse.jface.viewers.CheckboxTreeViewer;
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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;

import cn.devops.util.db.DbRes;

/**
 * 查询或
 * 
 * @author Administrator
 * 
 */
public class QueryTableFieldSelDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private DatabaseInfo dbinfo;
	private String dbUrl;

	private DbRes res;
	private CheckboxTreeViewer treeViewer;
	private RootNode root;
	private String tableName;
	private List<ColumnModel> columnModels;

	private HashMap resultMap;
	private Object objData;
	int actionType = 0;

	/**
	 * @wbp.parser.constructor
	 */
	public QueryTableFieldSelDialog(Shell shell2, int applicationModal, DbRes res, Object objData, int actionType) {
		super(shell2, applicationModal);
		this.res = res;
		this.objData = objData;
		this.actionType = actionType;

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
		shell.setSize(881, 506);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setBounds(10, 10, 391, 252);
		composite.setLayout(new GridLayout(10, false));

		Group group = new Group(composite, SWT.NONE);
		group.setLayout(new GridLayout(2, false));
		GridData gd_group = new GridData(SWT.FILL, SWT.CENTER, true, false, 10, 1);
		gd_group.heightHint = 45;
		group.setLayoutData(gd_group);

		Button btnAllCheck = new Button(group, SWT.NONE);
		GridData gd_btnAllCheck = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnAllCheck.widthHint = 120;
		btnAllCheck.setLayoutData(gd_btnAllCheck);
		btnAllCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				treeAllCheck();
			}
		});
		btnAllCheck.setText("全选");

		Button btnCheckOK = new Button(group, SWT.NONE);
		GridData gd_btnCheckOK = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_btnCheckOK.widthHint = 120;
		btnCheckOK.setLayoutData(gd_btnCheckOK);
		btnCheckOK.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				procssChose();
			}
		});
		btnCheckOK.setText("确定选择");

		treeViewer = new CheckboxTreeViewer(composite, SWT.BORDER | SWT.CHECK);
		Tree tree = treeViewer.getTree();
		GridData gd_tree = new GridData(SWT.FILL, SWT.FILL, true, true, 7, 1);
		tree.setHeaderVisible(true);

		gd_tree.widthHint = 487;
		gd_tree.heightHint = 180;
		tree.setLayoutData(gd_tree);
		tree.setHeaderVisible(true);
		tree.setLinesVisible(true);

		TreeColumn trclmnNewColumn = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn.setWidth(504);
		trclmnNewColumn.setText("选/字段");
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);
		new Label(composite, SWT.NONE);

		initUI();
	}

	boolean isCheck = false;
	private Text text;
	private String mainTableName;
	private String mainTableAsName;
	private String pkStr;
	private Object[] choseData;

	protected void treeAllCheck() {
		if (!isCheck) {
			treeViewer.setAllChecked(true);
			isCheck = true;
		} else {
			treeViewer.setAllChecked(false);
			isCheck = false;
		}

	}

	protected void procssChose() {

		choseData = treeViewer.getCheckedElements();
		ArrayList<ColumnModel> listCol = new ArrayList<ColumnModel>();
		ArrayList<ColumnModel> listColMain = new ArrayList<ColumnModel>();
		TableModel table = new TableModel();
		table.setTableName(mainTableName);
		table.setTableNameAs(mainTableAsName);
		table.appSqlTableJoin(table.getTableName() + " " + table.getTableNameAs());

		for (int i = 0; i < choseData.length; i++) {
			Node node = (Node) choseData[i];

			if (actionType == 1) {
				node.getColumn().setCanQut(1);
			}
			if (actionType == 2) {
				node.getColumn().setCanTableQut(1);
			}

			if (actionType == 3) {
				node.getColumn().setCanAddOut(1);
			}
			// listCol.add(node.getColumn());
			// listColMain.add(node.getColumn());
		}

		table.setColumnsArr(listCol.toArray());
		table.setColumnsMainArr(listColMain.toArray());
		this.getShell().dispose();

	}

	public Object[] getChoseData() {
		return choseData;
	}

	public static final String Formater_yyyy_MM_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";

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

	private void initUI() {

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

		treeViewer.setInput(objData);
	}

	protected Shell getShell() {
		return shell;
	}

	private void openSubTableDialog(SelectionEvent e) {

	}
}
