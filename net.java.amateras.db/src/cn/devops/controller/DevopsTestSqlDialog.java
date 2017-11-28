package cn.devops.controller;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import cn.devops.util.db.DbRes;

public class DevopsTestSqlDialog extends Dialog {

	protected Object result;
	protected Shell shell;
	private Text txtSql;
	private Text txtRs;
	DbRes dbRes;

	public DevopsTestSqlDialog(Shell parent, int style) {
		super(parent, style);
		setText("SWT Dialog");
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

	public DbRes getDbRes() {
		return dbRes;
	}

	public void setDbRes(DbRes dbRes) {
		this.dbRes = dbRes;
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), getStyle());
		shell.setSize(1075, 720);
		shell.setText(getText());
		shell.setLayout(new GridLayout(2, false));

		txtSql = new Text(shell, SWT.BORDER | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtSql.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

		Button btnRun = new Button(shell, SWT.NONE);
		btnRun.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				runSql();
			}
		});
		btnRun.setText("Run");

		Button btnClean = new Button(shell, SWT.NONE);
		btnClean.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				txtRs.setText("");
			}
		});
		btnClean.setText("clean");

		txtRs = new Text(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | SWT.CANCEL | SWT.MULTI);
		txtRs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));

	}

	protected void runSql() {

		String sql = this.txtSql.getText();
		Connection connection = null;
		try {
			connection = dbRes.getDatabaseInfo().getConnection();

			Statement st = connection.createStatement();
			st.executeQuery(sql);
			this.txtRs.setText("OK");
		} catch (SQLException e) {

			e.printStackTrace();
			this.txtRs.setText(e.getMessage());
		} finally {
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
