package net.java.amateras.db.visual.editor;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import net.java.amateras.db.util.IOUtils;
import net.java.amateras.db.visual.model.RootModel;
import net.java.amateras.xstream.XStreamSerializer;

/**
 * 序列化/反序列化
 * @author  
 *
 */
public class VisualDBSerializer {

	/**
	 * 序列化
	 * @param model
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static InputStream serialize(RootModel model) throws UnsupportedEncodingException {
		return XStreamSerializer.serializeStream(model, VisualDBSerializer.class.getClassLoader());
	}
	
	/**
	 * 反序列化
	 * @param in
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static RootModel deserialize(InputStream in) throws UnsupportedEncodingException {
		String xml = IOUtils.loadStream(in, "UTF-8");
		
		// 1.0.2 -> 1.0.3
		xml = xml.replaceAll(
				"net\\.java\\.amateras\\.db\\.view\\.dialect\\.ColumnType", 
				"net.java.amateras.db.dialect.ColumnType");
		
		return (RootModel)XStreamSerializer.deserialize(
				new ByteArrayInputStream(xml.getBytes("UTF-8")), 
				VisualDBSerializer.class.getClassLoader());
	}
	
}
