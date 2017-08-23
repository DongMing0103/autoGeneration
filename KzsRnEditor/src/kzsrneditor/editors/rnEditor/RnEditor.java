package kzsrneditor.editors.rnEditor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.ISaveablePart2;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

//import com.xt.dialog.Message;  

/**
 * 
 * 类说明: Form编辑器
 * 
 * @author dm
 * 
 */
public class RnEditor extends FormEditor implements ISaveablePart2 {

	public static final String ID = "kzsrneditor.editors.RnEditor";

	/** 页面引用 **/
	private RnEditorPage page;

	@Override
	protected void addPages() {
		try {
			page = new RnEditorPage(this, "RnEditor", "属性"); // 此名字为左下的名字
			addPage((IFormPage) page);
		} catch (PartInitException e) {
			e.printStackTrace();
		}
		updateTitle();
	}

	private void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		System.out.println("保存成功");
		// 保存成功.修改属性.
		page.setDirty(false);
//		page.getManagedForm().staleStateChanged();
		editorDirtyStateChanged();
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

	@Override
	public int promptToSaveOnClose() { // 退出时会调用 返回0会调用上面的保存方法.返回1则不会调用.真接退出.
		if (page.isDirty()) { // 修改过.
//			int result = Message.showConfirmMessage("是否保存后再退出?", page.getManagedForm().getForm().getShell());
//			if (result == SWT.YES) { // 保存后退出.
//				return 0;
//			} else {
//				return 1;
//			}
		} else {
			return 1;
		}
		return 0;
	}

	@Override
	protected void pageChange(int newPageIndex) {
		super.pageChange(newPageIndex);
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveOnCloseNeeded() {
		// TODO Auto-generated method stub
		return false;
	}

}