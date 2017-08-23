package cn.devops.comm.constants;

import java.util.HashMap;
import java.util.Map;

public class DevopsToolMaps {
	
	public static Map<String,Object> getMap(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("callAction", "$!callAction");
		map.put("idOut", "${id}");
		map.put("startRow", "{startRow}");
		map.put("startVar", "#{startRow}");
		map.put("pageSizeVal", "#{pageSize}");
		map.put("orderOut", "${key} ${orders[key]}");
		map.put("CreateTimeBegin", "WdatePicker({maxDate:'#F{$dp.$D(\'createTimeEnd\',{d:0});}'})");
		map.put("CreateTimeEnd", "WdatePicker({maxDate:'#F{$dp.$D(\'createTimeBegin\',{d:0});}'})");
		map.put("foreach", "#foreach");
		map.put("end", "#end");
		map.put("garbled", "222");
		map.put("gg777", "#");
		map.put("ggtype", "$!type");
		map.put("gg4", "$");
		return map;
	}
}
