package net.java.amateras.db.ui.provider;

import net.java.amateras.db.ui.bean.Node;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class TVContentProvider implements ITreeContentProvider {

	public Object[] getChildren(Object parentElement) {
		Node node = ((Node) parentElement);
		return node.getChildren().toArray();

	}

	public Object getParent(Object element) {

		return ((Node) element).getParent();

	}

	public boolean hasChildren(Object element) {

		return (((Node) element).getChildren().size() > 0);

	}

	public Object[] getElements(Object inputElement) {

		return ((Node) inputElement).getChildren().toArray();

	}

	public void dispose() {

		// TODO Auto-generated method stub

	}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {

		// TODO Auto-generated method stub

	}

}