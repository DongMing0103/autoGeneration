package kzsrneditor.editors.rnEditor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class RnEditorPage extends FormEditor {

	private Text unitName;

	/**
	 * 是否保存
	 */
	private boolean dirty = false;

	/**
	 * 窗口引用
	 */
	private IManagedForm managedForm;

	/**
	 * input对象，可以获取Input里的属性
	 */
	private RnEditorInput input;

	private String userID;

	public RnEditorPage(FormEditor editor,String id, String title){
//		super(editor, id, title);
		super();
	}

//	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		this.managedForm = managedForm;
		this.input = (RnEditorInput) this.getEditorInput();
		this.userID = input.getId();

		managedForm.getForm().getBody().setLayout(new FillLayout(SWT.HORIZONTAL));
		FormToolkit toolkit = managedForm.getToolkit();
		ScrolledForm form = managedForm.getForm();
		toolkit.decorateFormHeading(form.getForm());
		toolkit.paintBordersFor(managedForm.getForm().getBody());
		managedForm.getForm().setText("单位信息");

		Composite composite = new Composite(managedForm.getForm().getBody(), SWT.NONE);
		managedForm.getToolkit().adapt(composite);
		managedForm.getToolkit().paintBordersFor(composite);
		composite.setLayout(new FormLayout());

		/**
		 * 添加两个控件
		 */
		Label label = new Label(composite, SWT.NONE);
		FormData fd_label = new FormData();
		fd_label.top = new FormAttachment(0, 15);
		fd_label.left = new FormAttachment(0, 45);
		label.setLayoutData(fd_label);
		managedForm.getToolkit().adapt(label, true, true);
		label.setText("测试：");

		FormData fd_text_1 = new FormData();
		fd_text_1.left = new FormAttachment(label, 6);
		fd_text_1.top = new FormAttachment(label, -3, SWT.TOP);
		fd_text_1.right = new FormAttachment(60, 0);
		unitName.setLayoutData(fd_text_1);
		managedForm.getToolkit().adapt(unitName, true, true);
		
		
		//////////////////////// 为控件添加修改事件/////////////////////////////
		
		unitName.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				dirty = true; // 设置为脏状态.好用于保存. 编辑器标签上出现的星号.
				managedForm.dirtyStateChanged();
			}
		});
	}

	@Override
	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}

	@Override
	protected void addPages() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSave(IProgressMonitor arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}
}
