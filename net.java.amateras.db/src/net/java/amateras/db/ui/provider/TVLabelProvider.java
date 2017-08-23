package net.java.amateras.db.ui.provider;

import java.util.HashMap;
import java.util.Map;

import net.java.amateras.db.DBPlugin;
import net.java.amateras.db.ui.bean.Experiment;
import net.java.amateras.db.ui.bean.Node;
import net.java.amateras.db.ui.bean.NodeProcess;
import net.java.amateras.db.ui.bean.RootNode;
import net.java.amateras.db.ui.bean.Sample;
import net.java.amateras.db.ui.bean.User;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class TVLabelProvider implements ILabelProvider {

	private Map<ImageDescriptor, Image> imageCache = new HashMap<ImageDescriptor, Image>(20);

	public Image getImage(Object element) {

		ImageDescriptor descriptor = null;

		if (element instanceof User) {

			descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(

					DBPlugin.ID, "icons/1.gif");

		} else if (element instanceof Sample) {

			descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(

					DBPlugin.ID, "icons/1.gif");

		} else if (element instanceof Experiment) {

			descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(

					DBPlugin.ID, "icons/1.gif");

		} else if (element instanceof Process) {

			descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(

					DBPlugin.ID, "icons/1.gif");

		} else if (element instanceof Node) {

			descriptor = AbstractUIPlugin.imageDescriptorFromPlugin(

					DBPlugin.ID, "icons/column.gif");

		} else {

			throw unknownElement(element);

		}

		// obtain the cached image corresponding to the descriptor

		Image image = (Image) imageCache.get(descriptor);

		if (image == null) {

			image = descriptor.createImage();

			imageCache.put(descriptor, image);

		}

		return image;

	}

	public String getText(Object element) {

		String text;

		if (element instanceof RootNode)

			text = "root";

		else if (element instanceof User)

			text = ((User) element).getName();

		else if (element instanceof Sample)

			text = ((Sample) element).getName();

		else if (element instanceof Experiment)

			text = ((Experiment) element).getName();

		else if (element instanceof Process)

			text = ((NodeProcess) element).getName();

		else

			text = ((Node) element).getName();

		return text;

	}

	public void addListener(ILabelProviderListener listener) {

		// TODO Auto-generated method stub

	}

	public void dispose() {

		// TODO Auto-generated method stub

	}

	public boolean isLabelProperty(Object element, String property) {

		// TODO Auto-generated method stub

		return false;

	}

	public void removeListener(ILabelProviderListener listener) {

		// TODO Auto-generated method stub

	}

	protected RuntimeException unknownElement(Object element) {

		return new RuntimeException("Unknown type of element in tree of type " + element.getClass().getName());

	}

}