package cn.devops.comm.constants;

public class DevopsToolConstants {

	public static String expertBase = "expertBase";
	public static String jaDataBase = "split";

	public static String rootDir = "aiStyle";

	public static String PROJECT_NAME = "code";

	public static String getJAIBuss() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME;
	}

	public static String getJADTABuss() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME;
	}

	// xml
	public static String xmlBuss = DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-dao.src.main.resources.mapper.";
	public static String jaiXmlBuss = DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-dao.src.main.resources.mapper.";
	public static String JADTAXmlBuss = DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-dao.src.main.resources.mapper.";

	// entity
	public static String getPoBuss() {
		return DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-dao.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".dao.entity.";
	}

	public static String getJaiPoBuss() {
		return DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-dao.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".dao.entity.";
	}

	public static String getJADTAPoBuss() {
		return DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-dao.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".dao.entity.";
	}

	// 接口
	public static String getInterBuss() {
		return DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".service.serviceInter.";
	}

	public static String getJaiInterBuss() {
		return DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service.serviceInter.";
	}

	public static String getJADTAInterBuss() {
		return DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".service.serviceInter.";
	}

	// 实现类
	public static String getImplBuss() {
		return DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".service.serviceimpl.";
	}

	public static String getJaiImplBuss() {
		return DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service.serviceimpl.";
	}

	public static String getJADTAImplBuss() {
		return DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".service.serviceimpl.";
	}

	// 后台controller
	public static String getControllerBuss() {
		return DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-manage-web.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".manager.controller.";
	}

	public static String getJaiControllerBuss() {
		return DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-manage-web.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".manager.controller.";
	}

	public static String getJADTAControllerBuss() {
		return DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-manage-web.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".manager.controller.";
	}

	// 中台controller
//	public static String getControllerCenterBuss() {
//		return DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-web2.src.main.java." + DevopsBasePath.getBASPACK()
//				+ DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".web.controller.";
//	}
//
//	public static String getJaiControllerCenterBuss() {
//		return DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-web2.src.main.java." + DevopsBasePath.getBASPACK()
//				+ DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".web.controller.";
//	}
//
//	public static String getJADTAControllerCenterBuss() {
//		return DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-web2.src.main.java." + DevopsBasePath.getBASPACK()
//				+ DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".web.controller.";
//
//	}

	// vo param js
	public static String getVoBuss() {
		return DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".service.vo.";
	}

	public static String getParamBuss() {
		return DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".service.param.";
	}

	public static String webBuss = DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-manage-web.src.main.webapp.WEB-INF.tpl.";
	public static String jsBuss = DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-manage-web.src.main.webapp.static.js.tpl.";
//	public static String ztwebBuss = DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-web2.src.main.webapp.WEB-INF.tpl.";
//	public static String ztjsBuss = DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-web-static2.src.main.webapp.static.js.";

	public static String getJaiVoBuss() {
		return DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service.vo.";
	}

	public static String getJaiParamBuss() {
		return DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service.param.";
	}

	// admin src
	public static String jaiWebBuss = DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-manage-web.src.main.webapp.WEB-INF.tpl.";
	public static String jaiJsBuss = DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-manage-web.src.main.webapp.static.js.tpl.";
//	public static String jaiZtwebBuss = DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-web2.src.main.webapp.WEB-INF.tpl.";
//	public static String jaiZtjsBuss = DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-web-static2.src.main.webapp.static.js.";

	public static String getJADTAVoBuss() {
		return DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".service.vo.";
	}

	public static String getJADTAParamBuss() {
		return DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-service.src.main.java." + DevopsBasePath.getBASPACK()
				+ DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".service.param.";

	}

	// admin qzs-system-manage-web
	public static String JADTAWebBuss = DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-manage-web.src.main.webapp.WEB-INF.tpl.";
	public static String JADTAJsBuss = DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-manage-web.src.main.webapp.static.js.tpl.";
	// public static String JADTAZtwebBuss =
	// DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME +
	// "-web2.src.main.webapp.WEB-INF.tpl.";
	// public static String JADTAZtjsBuss =
	// DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME +
	// "-web-static2.src.main.webapp.static.js.";

	// public static String PACK_DAO = DevopsBasePath.getPath() + "\\dao";
	public static String Project_modem = "manager";

	public static String getProject_front_path() {
		return DevopsBasePath.getPath() + "\\web-" + DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-front";
	}

	public static String getJai_project_front_path() {
		return DevopsBasePath.getPath() + "\\web-" + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-front";
	}

	public static String getKzs_project_front_path() {
		return DevopsBasePath.getPath() + "\\web-" + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-front";
	}

	public static String getProject_manager_path() {
		return DevopsBasePath.getPath() + "\\web-" + DevopsBasePath.KANKAN_PROJECTSHORTNAME + "-manager";
	}

	public static String getJai_project_manager_path() {
		return DevopsBasePath.getPath() + "\\web-" + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + "-manager";
	}

	public static String getKzs_project_manager_path() {
		return DevopsBasePath.getPath() + "\\web-" + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + "-manager";
	}

	// public static String getProject_entity() {
	// return DevopsBasePath.getBASPACK() +
	// DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".dao.entity";
	// }

	public static String getJai_project_entity() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".dao.entity";
	}

	public static String getKzs_project_entity() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".dao.entity";
	}

	public static String Project_web_path = "tpl";

	public static String getBusiness() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".service";
	}

	public static String getJaiBusiness() {
//		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service";	CRM实现类路径为 *.service.serviceimpl.*
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service.serviceimpl";
	}

	public static String getKzsBusiness() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".service";
	}

	public static String getJai_business_interface() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service.serviceInter";
	}

	public static String getKzs_business_interface() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".service.serviceInter";
	}

	public static String getJai_business_center() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".service.serviceimpl";
	}

	public static String getKzs_business_center() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".service.serviceimpl";
	}

	public static String getJai_project_mapper_path() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_CRMKANKAN_PROJECTSHORTNAME + ".dao.mapper";
	}

	public static String getKzs_project_mapper_path() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KZS_KANKAN_PROJECTSHORTNAME + ".dao.mapper";

	}

	public static String Project_dao = DevopsBasePath.getPath() + "\\dao";
	public static String PROJECT_APP = DevopsBasePath.getPath() + "\\app";

	public static String PROJECT_AUTHOR = " dev";

	public static String PROJECT_MappeSrcPath = "resource";

	public static String PROJECT_SRC = "src.main.java";

	public static String PROJECT_WebContent = "WebContent";
	public static String KANKAN_BUTTON_NAME = "KANKAN";
	public static String CRM_BUTTON_NAME = "KZSCRM";
	public static String KZS_BUTTON_NAME = "KZS";

	//
	public static String getBuss() {

		return DevopsBasePath.getBASPACK() + DevopsBasePath.getProjectName();
	}

	public static String getBusiness_interface() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".service.serviceInter";

	}

	public static String getBusiness_center() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".service.serviceimpl";
	}

	public static String getProject_entity() {

		return DevopsBasePath.getBASPACK() + DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".dao.entity";
	}

	public static String getProject_mapper_path() {
		return DevopsBasePath.getBASPACK() + DevopsBasePath.KANKAN_PROJECTSHORTNAME + ".dao.mapper";
	}

	public static String getPACK_DAO() {
		return DevopsBasePath.getPath() + "\\dao";
	}

	public static String getProject_model() {
		return DevopsBasePath.getPath() + "\\model";
	}

	public static String getPROJECT_WEB_APP() {
		return DevopsBasePath.getPath() + "\\web-app";
	}

	public static String getPROJECT_SERVCIE() {
		return DevopsBasePath.getPath() + "\\service";
	}

}
