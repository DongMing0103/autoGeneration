package kzsrneditor.editors.keyWord;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

public class RnEditorInput implements IStorageEditorInput {
	/**
	 * 显示名称
	 */
	private String name;
	/**
	 * 正文
	 */
	private String content = "";
	/**
	 * 存储器
	 */
	public IStorage storage;

	public RnEditorInput(String name) {
		this.name = name;
		storage = new IStorage() {
			private boolean isReadOnly;

			@Override
			public Object getAdapter(Class adapter) {
				return null;
			}

			@Override
			public boolean isReadOnly() {
				return isReadOnly;
			}

			@Override
			public String getName() {
				return RnEditorInput.this.name;
			}

			@Override
			public IPath getFullPath() {
				return null;
			}

			@Override
			public InputStream getContents() throws CoreException {
				return new ByteArrayInputStream(content.getBytes());
			}
		};
	}

	public RnEditorInput(String name, IStorage is) {
		this.name = name;
		this.storage = is;
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
		return "编辑器";
	}

	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public IStorage getStorage() throws CoreException {
		return storage;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RnEditorInput) {
			RnEditorInput newDbEditorInput = (RnEditorInput) obj;
			if (this.name.equals(newDbEditorInput.getName())) {
				return true;
			}
		}
		return false;
	}
}
