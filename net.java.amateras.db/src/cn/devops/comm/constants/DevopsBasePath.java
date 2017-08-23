package cn.devops.comm.constants;

public class DevopsBasePath {
	public static String KANKAN_PROJECTSHORTNAME = "kankan";
	public static String KZS_CRMKANKAN_PROJECTSHORTNAME = "kzscrm";
	public static String KZS_KANKAN_PROJECTSHORTNAME = "qzs";
	private static String PROJECT_FULLNAME = KZS_KANKAN_PROJECTSHORTNAME;
	public static String KANKAN_BUTTON_NAME = "KANKAN";
	public static String CRM_BUTTON_NAME = "KZSCRM";
	public static String KZS_BUTTON_NAME = "KZS";
	
	public static String expertBase = "expertBase";
	public static String jaDataBase = "split";
	public static String rootDir = "aiStyle";

	public static String BASECOMPANY_HD = "com.hd.";
	public static String BASECOMPANY_BASE = BASECOMPANY_HD;
	public static String BASECOMPANY_KANKAN = "com.mei.";
	public static String BASECOMPANY_PATH = "com\\hd";
	public static String BASECOMPANY_PATH_BASE = BASECOMPANY_PATH;
	public static String BASECOMPANY_PATH_KANKAN = "com\\mei";

	public static String PROJECT_NAME = "code";

	public static String Buss = getBASPACK() + KANKAN_PROJECTSHORTNAME;
	public static String JAIBuss = getBASPACK() + KZS_CRMKANKAN_PROJECTSHORTNAME;
	public static String JADTABuss = getBASPACK() + KZS_KANKAN_PROJECTSHORTNAME;

	public static String PROJECT_AUTHOR = "hd dev";

	public static String PROJECT_MappeSrcPath = "resource";

	public static String PROJECT_SRC = "src.main.java";

	public static String PROJECT_WebContent = "WebContent";

	public static String getProjectName() {
		return PROJECT_FULLNAME;
	}

	/**
	 * 
	 * @param projectName
	 *            格式如：kzscrm qzs.
	 */
	public static void setProjectName(String projectName) {
		PROJECT_FULLNAME = projectName;
	}

	public static String getBASPACK() {
		return BASECOMPANY_BASE;
	}

	public static String getBasecompanyBase() {
		return BASECOMPANY_BASE;
	}

	/**
	 * 
	 * @param basePack
	 *            格式如：com.hd.
	 */
	public static void setBASPACK(String basePack) {
		BASECOMPANY_BASE = basePack;
	}

	public static String getPath() {
		return BASECOMPANY_PATH_BASE;
	}

	/**
	 * 
	 * @param path
	 *            如：com\\hd
	 */
	public static void setPath(String path) {
		BASECOMPANY_PATH_BASE = path;
	}
	//
}
