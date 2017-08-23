package net.java.amateras.db.htmlgen;

import net.java.amateras.db.DBPlugin;

public class ForeignKeyMapping {
	
	private ColumnModelHtml target;
	private ColumnModelHtml refer;
	
	public ColumnModelHtml getRefer() {
		return refer;
	}
	
	public void setRefer(ColumnModelHtml refer) {
		this.refer = refer;
	}
	
	public ColumnModelHtml getTarget() {
		return target;
	}
	
	public void setTarget(ColumnModelHtml target) {
		this.target = target;
	}
	
	public String getDisplayString(boolean logicalMode){
		StringBuffer sb = new StringBuffer();
		if(logicalMode){
			if(getRefer()==null){
				sb.append(DBPlugin.getResourceString("label.undef"));
			} else {
				sb.append(getRefer().getLogicalName());
			}
			sb.append("=");
			sb.append(getTarget().getLogicalName());
		} else {
			if(getRefer()==null){
				sb.append(DBPlugin.getResourceString("label.undef"));
			} else {
				sb.append(getRefer().getColumnName());
			}
			sb.append("=");
			sb.append(getTarget().getColumnName());
		}
		return sb.toString();
	}
}
