package cn.devops.util.db;

import java.net.URL;
import java.util.HashMap;

import net.java.amateras.db.util.DatabaseInfo;
import net.java.amateras.db.util.JarClassLoader;
import net.java.amateras.db.visual.model.RootModel;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;

public class DbRes {
	private static HashMap<String, DbRes> res = new HashMap<String, DbRes>();

	private RootModel root;
	private JarClassLoader jarClassLoader;
	public static HashMap dbStatus = new HashMap();
	DatabaseInfo dbinfo;

	private DbRes() {

	}

	public static DbRes getInstance(String appName) {
		if (res.containsKey(appName)) {
			return res.get(appName);
		}
		DbRes bean = new DbRes();
		res.put(appName, bean);
		return bean;

	}

	public String getJdbcDriver() {
		return root.getJdbcDriver();
	}

	public String getDbUrl() {
		return root.getJdbcUrl();
	}

	public String getUser() {
		return root.getJdbcUser();
	}

	public String getPassword() {
		return root.getJdbcPassword();
	}

	public String getCatalog() {

		return root.getJdbcCatalog();
	}

	public String getSchema() {
		return root.getJdbcSchema();
	}

	public String getDatabaseURI() {

		return root.getDialectName();
	}

	public void setRootModel(RootModel root) {
		this.root = root;

	}

	public void setJarClassLoader(JarClassLoader jarClassLoader) {
		this.jarClassLoader = jarClassLoader;

	}

	public JarClassLoader getJarClassLoader() {
		if (jarClassLoader == null) {
			try {
				URL[] classpathes = new URL[0];
				String jarFile = root.getJarFile();
				URL jarURL = null;
				String jarFilePath = jarFile;

				if (jarFilePath.startsWith("workspace:")) {
					IWorkspaceRoot wsroot = ResourcesPlugin.getWorkspace().getRoot();
					jarFilePath = jarFilePath.replaceFirst("^workspace:", "");

					IFile file = wsroot.getFile(new Path(jarFilePath));
					jarFilePath = file.getLocation().makeAbsolute().toString();

					jarURL = new URL("file:///" + jarFilePath);

				} else {
					jarURL = new URL("file:///" + jarFilePath);
				}

				URL[] clspath = new URL[classpathes.length + 1];
				clspath[0] = jarURL;
				for (int i = 0; i < classpathes.length; i++) {
					clspath[i + 1] = classpathes[i];
				}
				jarClassLoader = new JarClassLoader(clspath);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return jarClassLoader;
	}

	public RootModel getRoot() {
		return root;
	}

	public void setRoot(RootModel root) {
		this.root = root;
	}

	public DatabaseInfo getDatabaseInfo() {
		if (dbinfo == null) {
			Class<?> driverClass = null;
			try {
				driverClass = getJarClassLoader().loadClass(getJdbcDriver());
				try {
					dbinfo = new DatabaseInfo(driverClass);
				} catch (InstantiationException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (IllegalAccessException e2) {
					e2.printStackTrace();
				}
			} catch (ClassNotFoundException e3) {
				e3.printStackTrace();
			}

			dbinfo.setURI(getDbUrl());
			dbinfo.setUser(getUser());
			dbinfo.setPassword(getPassword());
			dbinfo.setCatalog(getCatalog());
			dbinfo.setSchema(getSchema());
			dbinfo.setEnableView(false);
			dbinfo.setAutoConvert(false);
		}
		return dbinfo;
	}

	public static String getConvertName(String name) {
		String nameOut = "";
		String arr[] = name.split("_");
		for (int i = 0; i < arr.length; i++) {
			nameOut += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
		}
		if (nameOut == null) {
			nameOut = "";
		}
		return nameOut;
	}

	public static String getFirstLowConvertName(String name) {
		String nameOut = "";
		String arr[] = name.split("_");
		for (int i = 0; i < arr.length; i++) {
			if (i == 0) {
				nameOut += arr[i].substring(0).toUpperCase();
			} else {
				nameOut += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
			}
		}
		if (nameOut == null) {
			nameOut = "";
		}
		return nameOut;
	}

	public static String getLowConvertName(String name) {
		String nameOut = "";
		String arr[] = name.split("_");
		for (int i = 0; i < arr.length; i++) {
			if (i == 0) {
				nameOut += arr[i].substring(0).toLowerCase();
			} else {
				nameOut += arr[i].substring(0, 1).toLowerCase() + arr[i].substring(1).toLowerCase();
			}
		}
		if (nameOut == null) {
			nameOut = "";
		}
		return nameOut;
	}

}
