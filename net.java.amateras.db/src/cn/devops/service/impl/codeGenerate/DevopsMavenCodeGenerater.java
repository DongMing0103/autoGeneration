package cn.devops.service.impl.codeGenerate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;

import net.java.amateras.db.util.IOUtils;
import net.java.amateras.db.visual.model.RootModel;
import net.java.amateras.db.visual.model.TableModel;

import org.apache.commons.io.FileUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.NullLogChute;
import org.eclipse.core.resources.IFile;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;

import cn.devops.comm.constants.DevopsBasePath;
import cn.devops.comm.constants.DevopsToolConstants;
import cn.devops.comm.constants.DevopsToolMaps;
import cn.devops.service.impl.codeGenerate.html.HTMLGenerator;
import cn.devops.service.inter.codeGenerate.IGenerator;
import cn.devops.util.VelocityUtils;
import cn.devops.view.ViewClassRootPath;

/**
 * 生成java ,html code page maven版代码
 * 
 * @author wing
 * 
 */
public class DevopsMavenCodeGenerater implements IGenerator {

	/**
	 * 是否只生成在一个包
	 */
	boolean isOnMode = true;
	private static ResourceBundle bundle = ResourceBundle.getBundle(HTMLGenerator.class.getName());
	private static Map<String, String> messages = new HashMap<String, String>();
	static {
		for (Enumeration<String> e = bundle.getKeys(); e.hasMoreElements();) {
			String key = e.nextElement();
			messages.put(key, bundle.getString(key));
		}
	}

	static {
		// kills Velocity logging
		Velocity.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, NullLogChute.class.getName());
	}

	private void processTemplate(String templateName, File output, VelocityContext context) throws Exception {
		StringWriter writer = new StringWriter();
		InputStreamReader reader = new InputStreamReader(ViewClassRootPath.class.getResourceAsStream(templateName), "UTF-8");
		Velocity.evaluate(context, writer, null, reader);

		FileOutputStream out = new FileOutputStream(output);
		out.write(writer.getBuffer().toString().getBytes("UTF-8"));
		IOUtils.close(out);

		IOUtils.close(reader);
		IOUtils.close(writer);
	}

	public void execute(IFile erdFile, RootModel root, GraphicalViewer viewer, String funcPackage, RootModel qryRoot, RootModel auRoot,
			RootModel valiRoot) {
		try {
			DirectoryDialog dialog = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.SAVE);
			String rootDir = dialog.open();

			if (rootDir != null) {
				generate(rootDir, null, root, null, funcPackage, qryRoot, auRoot, valiRoot);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * @param rootDir
	 *            comm dir
	 * @param proCodeDir
	 *            project code dir
	 * @param root
	 * @param map
	 * @param funcPackage
	 * @throws Exception
	 */

	public void generate(String rootDir, String proCodeDir, RootModel root, HashMap<String, Object> map, String funcPackage,
			RootModel qryRoot, RootModel auRoot, RootModel valiRoot) throws Exception {

		Velocity.init();
		VelocityContext context = new VelocityContext();
		map.put("funcPackage", funcPackage);
		Set<Map.Entry<String, Object>> key = map.entrySet();
		Iterator<Entry<String, Object>> it1 = key.iterator();
		while (it1.hasNext()) {
			Map.Entry<String, Object> me = it1.next();
			String mapKey = me.getKey();
			Object value = me.getValue();
			context.put(mapKey, value);
		}
		String mainTableAs = (String) map.get("MainTableAs");
		String htmlModel = (String) map.get("htmlModel");
		String serviceFileName = (String) map.get("serviceFileName");
		// 获取map参数
		Map<String, Object> newMap = DevopsToolMaps.getMap();
		Set<Map.Entry<String, Object>> set = newMap.entrySet();
		Iterator<Entry<String, Object>> it2 = set.iterator();
		while (it2.hasNext()) {
			Map.Entry<String, Object> me = it2.next();
			String mapKey = me.getKey();
			String value = (String) me.getValue();
			context.put(mapKey, value);
		}
		context.put("model", root);
		context.put("qeyModel", qryRoot);
		context.put("auModel", auRoot);
		context.put("valiModel", valiRoot);
		context.put("util", new VelocityUtils());
		context.put("msg", messages);

		// resultMap.put("frameWorkPath", this.txtCoreFrameworkPath.getText() +
		// "");
		File codeRootDir = new File(rootDir, "code");
		if (!codeRootDir.exists())
			if (isOnMode) {
			} else {
				codeRootDir.mkdirs();
			}

		File projectCodeDir = new File(proCodeDir, "code");
		if (!projectCodeDir.exists())
			projectCodeDir.mkdirs();

		String allDir = null;
		query: for (TableModel qryTable : qryRoot.getTables()) {
			cud: for (TableModel auTable : auRoot.getTables()) {
				if (!qryTable.getTableName().equals(auTable.getTableName())) {
					break query;
				}
				for (TableModel table : root.getTables()) {
					if (!table.getTableName().equals(auTable.getTableName())) {
						break cud;
					}
					TableModel valiTable = valiRoot.getTables().get(0);
					context.put("table", table);
					context.put("qryTable", qryTable);
					context.put("auTable", auTable);
					context.put("valiTable", valiTable);
					context.put("columnSize", table.getColumns().length);
					String dirBusiness = (String) map.get("dir4business");
					String commDao = (String) map.get("commDao");
					File mapperDir = codeRootDir;
					if (commDao != null && "true".equals(commDao)) {
					} else {
						mapperDir = projectCodeDir;
					}

					String txtMapperDir = (String) map.get("txtMapperDir");

					txtMapperDir = txtMapperDir.replaceAll("\\.", "/");
					if (isOnMode) {
						if (allDir == null) {
							txtMapperDir = mapperDir.getAbsolutePath() + File.separator + txtMapperDir;
							allDir = projectCodeDir.getAbsolutePath();
							allDir = allDir.replaceAll("\\.", "\\/");
							checkDir(allDir);
						}
					}
					// 1 mapper
					String txtButtonName = (String) map.get("projectButtion");
					String mapperParamXml = null;
					String entryParam = null;
					String infoInterfaceParam = null;
					String infoParam = null;
					String controlerParam = null;
					String controlerParams = null;
					String voDir = null;
					String paramDir = null;
					String webAdminDirStr = null;
					String jsAdminDirStr = null;
//					String ztWebDirStr = null;
//					String ztJsDirStr = null;
					// CRM
					if (txtButtonName != null && DevopsToolConstants.CRM_BUTTON_NAME.equals(txtButtonName)) {

						mapperParamXml = allDir + File.separator + DevopsToolConstants.jaiXmlBuss + map.get("modelName");
						entryParam = allDir + File.separator + DevopsToolConstants.getJaiPoBuss() + map.get("modelName");
						infoInterfaceParam = allDir + File.separator + DevopsToolConstants.getJaiInterBuss() + map.get("modelName");
						infoParam = allDir + File.separator + DevopsToolConstants.getJaiImplBuss() + map.get("modelName");
						controlerParam = allDir + File.separator + DevopsToolConstants.getJaiControllerBuss() + map.get("modelName");
						// controlerParams = allDir + File.separator +
						// DevopsToolConstants.getJaiControllerCenterBuss() +
						// map.get("modelName");
						voDir = allDir + File.separator + DevopsToolConstants.getJaiVoBuss() + map.get("modelName");
						paramDir = allDir + File.separator + DevopsToolConstants.getJaiParamBuss() + map.get("modelName");
						webAdminDirStr = allDir + File.separator + DevopsToolConstants.jaiWebBuss + mainTableAs;
						jsAdminDirStr = allDir + File.separator + DevopsToolConstants.jaiJsBuss + mainTableAs;
						// ztWebDirStr = allDir + File.separator +
						// DevopsToolConstants.jaiZtwebBuss + mainTableAs;
						// ztJsDirStr = allDir + File.separator +
						// DevopsToolConstants.jaiZtjsBuss + mainTableAs;

						// KANKAN
					} else if (txtButtonName != null && DevopsBasePath.KANKAN_BUTTON_NAME.equals(txtButtonName)) {

						mapperParamXml = allDir + File.separator + DevopsToolConstants.xmlBuss + map.get("modelName");
						entryParam = allDir + File.separator + DevopsToolConstants.getPoBuss() + map.get("modelName");
						infoInterfaceParam = allDir + File.separator + DevopsToolConstants.getInterBuss() + map.get("modelName");
						infoParam = allDir + File.separator + DevopsToolConstants.getImplBuss() + map.get("modelName");
						controlerParam = allDir + File.separator + DevopsToolConstants.getControllerBuss() + map.get("modelName");
						// controlerParams = allDir + File.separator +
						// DevopsToolConstants.getControllerCenterBuss() +
						// map.get("modelName");
						voDir = allDir + File.separator + DevopsToolConstants.getVoBuss() + map.get("modelName");
						paramDir = allDir + File.separator + DevopsToolConstants.getParamBuss() + map.get("modelName");
						webAdminDirStr = allDir + File.separator + DevopsToolConstants.webBuss + mainTableAs;
						jsAdminDirStr = allDir + File.separator + DevopsToolConstants.jsBuss + mainTableAs;
						// ztWebDirStr = allDir + File.separator +
						// DevopsToolConstants.ztwebBuss + mainTableAs;
						// ztJsDirStr = allDir + File.separator +
						// DevopsToolConstants.ztjsBuss + mainTableAs;

						// kzs
					} else if (txtButtonName != null && DevopsToolConstants.KZS_BUTTON_NAME.equals(txtButtonName)) {

						mapperParamXml = allDir + File.separator + DevopsToolConstants.JADTAXmlBuss + map.get("modelName");
						entryParam = allDir + File.separator + DevopsToolConstants.getJADTAPoBuss() + map.get("modelName");
						infoInterfaceParam = allDir + File.separator + DevopsToolConstants.getJADTAInterBuss() + map.get("modelName");
						infoParam = allDir + File.separator + DevopsToolConstants.getJADTAImplBuss() + map.get("modelName");
						controlerParam = allDir + File.separator + DevopsToolConstants.getJADTAControllerBuss() + map.get("modelName");
						// controlerParams = allDir + File.separator +
						// DevopsToolConstants.getJADTAControllerCenterBuss()
						// + map.get("modelName");
						voDir = allDir + File.separator + DevopsToolConstants.getJADTAVoBuss() + map.get("modelName");
						paramDir = allDir + File.separator + DevopsToolConstants.getJADTAParamBuss() + map.get("modelName");
						webAdminDirStr = allDir + File.separator + DevopsToolConstants.JADTAWebBuss + mainTableAs;
						jsAdminDirStr = allDir + File.separator + DevopsToolConstants.JADTAJsBuss + mainTableAs;
						// ztWebDirStr = allDir + File.separator +
						// DevopsToolConstants.JADTAZtwebBuss + mainTableAs;
						// ztJsDirStr = allDir + File.separator +
						// DevopsToolConstants.JADTAZtjsBuss + mainTableAs;

					}
					mapperParamXml = mapperParamXml.replaceAll("\\.", "\\/");
					checkDir(mapperParamXml);
					processTemplate(DevopsToolConstants.rootDir + "/dao/mapper/MapperTemplet.xml",
							new File(mapperParamXml, "" + table.getTableNameOut() + "POMapper.xml"), context);

					// 2 entity
					entryParam = entryParam.replaceAll("\\.", "\\/");
					checkDir(entryParam);
					processTemplate(DevopsToolConstants.rootDir + "/dao/entity/JavaEntryTemplet.javatpl",
							new File(entryParam, "" + table.getTableNameOut() + "PO.java"), context);

					// 3 inter
					infoInterfaceParam = infoInterfaceParam.replaceAll("\\.", "\\/");
					checkDir(infoInterfaceParam);
					processTemplate(DevopsToolConstants.rootDir + "/service/inter/serviceInfoTemplet.javatpl",
							new File(infoInterfaceParam, "I" + table.getTableNameOut() + serviceFileName + ".java"), context);

					// 4 impl
					infoParam = infoParam.replaceAll("\\.", "\\/");
					checkDir(infoParam);
					processTemplate(DevopsToolConstants.rootDir + "/service/impl/serviceImplTemplet.javatpl",
							new File(infoParam, "" + table.getTableNameOut() + serviceFileName + "Impl.java"), context);

					// 5 控制器controller
					controlerParam = controlerParam.replaceAll("\\.", "\\/");
					checkDir(controlerParam);
					processTemplate(DevopsToolConstants.rootDir + "/controller/ManageControllerTemplet.javatpl",
							new File(controlerParam, "" + table.getTableNameOut() + "Controller.java"), context);

					// 6 中台controller
					/*
					 * controlerParams = controlerParams.replaceAll("\\.",
					 * "\\/"); checkDir(controlerParams);
					 * processTemplate(DevopsToolConstants.rootDir +
					 * "/controller/CenterControllerTemplet.javatpl", new
					 * File(controlerParams, "Zt" + table.getTableNameOut() +
					 * "Controller.java"), context);
					 */

					// 7 vo
					voDir = voDir.replaceAll("\\.", "\\/");
					checkDir(voDir);
					processTemplate(DevopsToolConstants.rootDir + "/service/vo/JavaVoTemplet.javatpl",
							new File(voDir, "" + table.getTableNameOut() + "VO.java"), context);

					// 8 param
					paramDir = paramDir.replaceAll("\\.", "\\/");
					checkDir(paramDir);
					processTemplate(DevopsToolConstants.rootDir + "/service/param/JavaParamTemplet.javatpl",
							new File(paramDir, "" + table.getTableNameOut() + "Param.java"), context);

					webAdminDirStr = webAdminDirStr.replaceAll("\\.", "\\/");
					jsAdminDirStr = jsAdminDirStr.replaceAll("\\.", "\\/");
//					ztWebDirStr = ztWebDirStr.replaceAll("\\.", "\\/");
//					ztJsDirStr = ztJsDirStr.replaceAll("\\.", "\\/");
					checkDir(webAdminDirStr);
					checkDir(jsAdminDirStr);
//					checkDir(ztWebDirStr);
//					checkDir(ztJsDirStr);

					// 后台 html
					File spmainFile = new File(webAdminDirStr, "main" + table.getTableNameOut() + ".html");
					processTemplate(DevopsToolConstants.rootDir + "/ManageTemplet/ManageMainPageTemplet.htmlTpl", spmainFile, context);
					replaceMainpageHtml(spmainFile);

					// 后台 js
					File spmainJsFile = new File(jsAdminDirStr, table.getTableNameOut() + ".js");
					processTemplate(DevopsToolConstants.rootDir + "/ManageTemplet/ManageMainPageTemplet.jsTpl", spmainJsFile, context);
					replaceMainpageHtml(spmainJsFile);

					// 后台公共 js
					File spPublicJsFile = new File(jsAdminDirStr, "aiPublic.js");
					processTemplate(DevopsToolConstants.rootDir + "/PublicPage.jsTpl", spPublicJsFile, context);
					replaceMainpageHtml(spPublicJsFile);

					// 后台公共 js
					File valiJsFile = new File(jsAdminDirStr, "valiAiPublic.js");
					processTemplate(DevopsToolConstants.rootDir + "/ValiManageMainPageTemplet.jsTpl", valiJsFile, context);
					replaceMainpageHtml(valiJsFile);

					// 后台 add/edit html
					File addMainFile = new File(webAdminDirStr, "mainAdd" + table.getTableNameOut() + ".html");
					processTemplate(DevopsToolConstants.rootDir + "/ManageTemplet/ManageAddPageTemplet.htmlTpl", addMainFile,
							context);
					replaceMainpageHtml(addMainFile);
					
					File EditMainFile = new File(webAdminDirStr, "mainEdit" + table.getTableNameOut() + ".html");
					processTemplate(DevopsToolConstants.rootDir + "/ManageTemplet/ManageEditPageTemplet.htmlTpl", EditMainFile, context);
					replaceMainpageHtml(EditMainFile);

					// 后台 add/edit js
					File addOReditmainJsFile = new File(jsAdminDirStr, "mainAddOrEdit" + table.getTableNameOut() + ".js");
					processTemplate(DevopsToolConstants.rootDir + "/ManageTemplet/ManageAddOrEditPageTemplet.jsTpl", addOReditmainJsFile,
							context);
					replaceMainpageHtml(addOReditmainJsFile);

					// 中台 html ,js
					// getnCenterCode(ztWebDirStr, ztJsDirStr, table, context);

				}
			}
		}
	}

	/**
	 * 生成中台代码
	 * 
	 * @param ztWebDirStr
	 * @param ztJsDirStr
	 * @param table
	 * @param context
	 * @throws Exception
	 */
	
//	  private void getnCenterCode(String ztWebDirStr, String ztJsDirStr,
//	  TableModel table, VelocityContext context) throws Exception { if (true)
//	  return; // 中台 html File frontFile = new File(ztWebDirStr, "main" +
//	  table.getTableNameOut() + ".html");
//	  processTemplate(DevopsToolConstants.rootDir +
//	  "/ztManageTemplet/mouldOne/CenterMainPageTemplet.htmlTpl", frontFile,
//	  context); replaceMainpageHtml(frontFile);
//	  
//	  // 中台 js File frontJsFile = new File(ztJsDirStr, table.getTableNameOut()
//	  + ".js"); processTemplate(DevopsToolConstants.rootDir +
//	  "/ztManageTemplet/mouldOne/CenterMainPageTemplet.jsTpl", frontJsFile,
//	  context); replaceMainpageHtml(frontJsFile);
//	  
//	  // 中台add/edit html File addOReditfrontFile = new File(ztWebDirStr,
//	  "mainAddOrEdit" + table.getTableNameOut() + ".html");
//	  processTemplate(DevopsToolConstants.rootDir +
//	  "/ztManageTemplet/mouldOne/CenterAddOrEditPage.htmlTpl",
//	  addOReditfrontFile, context); replaceMainpageHtml(addOReditfrontFile); //
//	    中台编辑新增的js File addOReditfrontJsFile = new File(ztJsDirStr,
//	  "mainAddOrEdit" + table.getTableNameOut() + ".js");
//	  processTemplate(DevopsToolConstants.rootDir +
//	  "/ztManageTemplet/mouldOne/CenterAddOrEditPage.jsTpl",
//	  addOReditfrontJsFile, context);
//	  replaceMainpageHtml(addOReditfrontJsFile); // 收件箱发件箱的页面 File boxFile =
//	  new File(ztWebDirStr, "mould" + table.getTableNameOut() + ".html");
//	  processTemplate(DevopsToolConstants.rootDir +
//	  "/ztManageTemplet/mouldTwo/CenterMouldPage.htmlTpl", boxFile, context);
//	  replaceMainpageHtml(boxFile);
//	  
//	  // 收件箱发件箱的js模板 File boxJsFile = new File(ztJsDirStr, "mould" +
//	  table.getTableNameOut() + ".js");
//	  processTemplate(DevopsToolConstants.rootDir +
//	  "/ztManageTemplet/mouldTwo/CenterMouldPage.jsTpl", boxJsFile, context);
//	  replaceMainpageHtml(boxJsFile);
//	  
//	  File addOReditboxFile = new File(ztWebDirStr, "mouldAddOrEdit" +
//	  table.getTableNameOut() + ".html");
//	  processTemplate(DevopsToolConstants.rootDir +
//	  "/ztManageTemplet/mouldTwo/CenterMouldPageAddOrEdit.htmlTpl",
//	  addOReditboxFile, context); replaceMainpageHtml(addOReditboxFile);
//	  
//	  }

	private void replaceMainpageHtml(File mainFile) throws IOException {
		String fileData = FileUtils.readFileToString(mainFile, "UTF-8");
		fileData = fileData.replaceAll("gg555", "\\$\\(");
		fileData = fileData.replaceAll("jqueryHadTag", "\\$\\(");
		fileData = fileData.replaceAll("GGGGGG", "");

		fileData = fileData.replaceAll("gg888", "\\$");
		fileData = fileData.replaceAll("jqueryNoSignTag", "\\$");
		fileData = fileData.replaceAll("@@@", "\\$");
		fileData = fileData.replaceAll("#5555", "\\$\\(\"\\#");
		fileData = fileData.replaceAll("#6666", "\\#");

		fileData = fileData.replaceAll("XXXXXX", "\\$");
		FileUtils.writeStringToFile(mainFile, fileData, "UTF-8");
	}

	private void checkDir(String dir) {
		File dirCheck = new File(dir);
		if (!dirCheck.exists())
			dirCheck.mkdirs();

	}

	public String getGeneratorName() {
		return "HTML_JAVA";
	}

}
