package kzsrneditor.editors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;

import kzsrneditor.editors.keyWord.RnEditorInput;

public class JSXEditor extends TextEditor {

	/**
	 * Editor Id
	 */
	public static String ID = "kzsrneditor.editors.JSXEditor";

	private ColorManager colorManager;

	public JSXEditor() {
		super();
		colorManager = new ColorManager();
		setSourceViewerConfiguration(new XMLConfiguration(colorManager));
		setDocumentProvider(new XMLDocumentProvider());
	}

	/**
	 * 设置字体
	 */
	public void initFont() {
		FontData fontData = new FontData("Consonlas", 11, SWT.NORMAL);
		Font font = new Font(getEditorSite().getShell().getDisplay(), fontData);
		this.getSourceViewer().getTextWidget().setFont(font);
	}

	@Override
	public void dispose() {
		colorManager.dispose();
		super.dispose();
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		initFont();
	}

	/*public void openView() {
		RnEditorInput input = new RnEditorInput("新建查询");
		try {
			page.openEditor(input, "kzsrneditor.editors.keyWord.RnEditorInput");
		} catch (PartInitException e) {
			e.printStackTrace();
		}
	}*/

}
