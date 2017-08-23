package kzsrneditor.editors.rnEditor;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * 类说明：文本编辑器类
 * @author dm
 *
 */
public class RnEditorInput implements IEditorInput{
	
	/**
	 * 编辑器名称
	 */
	private String name;
	
	/**
	 * 编辑器id	
	 */
	private String id;
	
	/**
	 * 构造方法可自行修改
	 */
	public RnEditorInput (String name, String id) {
		this.name = name;
		this.id = id;
	}
	
	@Override
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IFile.class)) {
			return this;
		} else {
			return null;
		}
	}
	
	@Override
	public boolean exists() {
		return true;
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public IPersistableElement getPersistable() {
		return null;
	}
	
	@Override
	public String getToolTipText() {
		return name;
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RnEditorInput) {
			RnEditorInput input = (RnEditorInput)obj;
			return ((input).getName()).equals(this.getName()) && input.getId().equals(this.getId());
		}
		return false;
	}
	
	
	
	
	
	
}
