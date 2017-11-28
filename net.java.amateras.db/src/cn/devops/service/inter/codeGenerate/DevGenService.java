package cn.devops.service.inter.codeGenerate;

import java.util.HashMap;

import net.java.amateras.db.visual.model.RootModel;

public interface DevGenService {

	public void generate(String rootDir, RootModel root, HashMap map, String funcPackage) throws Exception;

}
