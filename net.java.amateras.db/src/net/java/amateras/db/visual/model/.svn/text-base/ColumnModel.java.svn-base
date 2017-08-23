package net.java.amateras.db.visual.model;

import java.io.Serializable;

import net.java.amateras.db.dialect.IColumnType;

/**
 * 字段模型
 * 
 * @author Administrator
 * 
 */
public class ColumnModel implements Serializable {

	/**
	 * 字段名
	 */
	private String columnName = "";
	/**
	 * 中文注释
	 */
	private String logicalName = "";
	/**
	 * 数据类型
	 */
	private IColumnType columnType = null;
	/**
	 * 长度
	 */
	private String size = "";
	/**
	 * 是否为空
	 */
	private boolean notNull = false;
	/**
	 * 是否为主键
	 */
	private boolean primaryKey = false;
	/**
	 * 描述
	 */
	private String description = "";
	/**
	 * 是否自增
	 */
	private boolean autoIncrement = false;
	/**
	 * 默认值
	 */
	private String defaultValue = "";
	private DommainModel dommain = null;
	private String columnNameOut;
	private String columnNameOutFriUp;
	private String columnOutDefValue = "\"1\"";

	public String getColumnDef() {
		String name = columnType.getLogicalName();
		if (name.equals("Numeric"))
			return "defaultValue = \"-1\",";

		return " ";
	}

	// public void setColumnOutDefValue(String columnOutDefValue) {
	// this.columnOutDefValue = columnOutDefValue;
	// }

	private String columnNameVoValue;
	/**
	 * 0 主名， 1从名
	 */
	private int mainTableType;
	private String tableAs;
	private String tableName;
	private int canQut;
	private int canTableQut;
	private int canAddOut;
	/**
	 * 特别别名
	 */
	private String columnNameOutLowSpec;

	public void setColumnNameVoValue(String columnNameVoValue) {
		this.columnNameVoValue = columnNameVoValue;
	}

	public String getLogicalName() {
		return this.logicalName;
	}

	public void setLogicalName(String logicalName) {
		this.logicalName = logicalName;
	}

	public String getColumnName() {
	
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
		String out = "";
		String arr[] = columnName.split("_");
		for (int i = 0; i < arr.length; i++) {
			if (i == 0) {
				out += arr[i].substring(0).toLowerCase();
			} else {
				out += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
			}
		}

		this.columnNameOut = out;
		this.columnNameOutFriUp = columnNameOut.substring(0, 1).toUpperCase() + columnNameOut.substring(1);
	}

	public String getColumnNameOut() {
		if(columnNameOutLowSpec!=null){
			return columnNameOutLowSpec;
		}
		if (columnNameOut == null || columnNameOut.equals(""))
			return "";
		return columnNameOut;// columnNameOut.substring(0, 1).toUpperCase() +
								// columnNameOut.substring(1).toLowerCase();

	}

	public void setColumnNameOut(String columnNameOut) {
		this.columnNameOut = columnNameOut;
	}

	public String getColumnNameOutFriUp() {
		return columnNameOutFriUp;// columnNameOut.substring(0, 1).toUpperCase()
									// columnNameOut.substring(1).toLowerCase();
	}

	public String getSqlAsColumn() {
		return this.tableAs + "." + columnName;
	}

	public String getTableAs() {
		return tableAs;
	}

	public void setTableAs(String tableAs) {
		this.tableAs = tableAs;
	}

	public void setColumnNameOutFriUp(String columnNameOutFriUp) {
		this.columnNameOutFriUp = columnNameOutFriUp;
	}

	public String getColumnNameOutLow() {
		if(this.columnNameOutLowSpec!=null)return columnNameOutLowSpec;
		if (columnNameOut == null || columnNameOut.equals(""))
			return "";
		String out = "";
		String arr[] = columnName.split("_");
		for (int i = 0; i < arr.length; i++) {
			if (arr.length == 1) {
				out += arr[i].substring(0).toLowerCase();
			} else if (i == 0) {
				out += arr[i].substring(0, 1).toLowerCase() + arr[i].substring(1).toLowerCase();
			} else {

				out += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
			}
		}

		return out;

	}

	public String getColumnNameOutUp() {
		if(this.columnNameOutLowSpec!=null){
			String firstUp="";
			String arr[] = columnNameOutLowSpec.split("_");
			for (int i = 0; i < arr.length; i++) {

				if (i == 0) {
					firstUp += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
				} else {

					firstUp += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
				}
			}
			return firstUp;

		}
		String out = "";
		String arr[] = columnName.split("_");
		for (int i = 0; i < arr.length; i++) {

			if (i == 0) {
				out += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
			} else {

				out += arr[i].substring(0, 1).toUpperCase() + arr[i].substring(1).toLowerCase();
			}
		}

		return out;

	}

	public IColumnType getColumnType() {
		if (this.dommain != null) {
			return this.dommain.getType();
		}
		return columnType;
	}

	public String getColumnTypeName() {

//		String name = columnType.getLogicalName();
		// if (columnName.equals("id"))
		// return "Long";
		// if (columnName.equals("id"))
		// return "Long";
		// if (columnName.equals("id"))
//		if (name.equals("Numeric"))
//			return "Long";
//		
//		if (name.equals("Datetime"))
//			return "Date";
//		
//		
//		if (name.equals("Real"))
//			return "Double";
		
		
		// if (columnName.equals("id"))
		// return "double";
		// if (name.equals("Numeric"))
		// return "double";
		
		String name = columnType.getName();
		if (name.equals("TINYINT") || name.equals("SMALLINT"))
			return "Short";
		if (name.equals("BIGINT"))
			return "Long";
		if (name.equals("INT"))
			return "Integer";
		if (name.equals("REAL") || name.equals("DOUBLE"))
			return "Double";
		if (name.equals("FLOAT"))
			return "Float";
		if (name.equals("DECIMAL") || name.equals("NUMERIC"))
			return "BigDecimal";
		if (name.equals("CHAR") || name.equals("VARCHAR") || name.equals("TEXT"))
			return "String";
		if (name.equals("DATE") || name.equals("TIME") || name.equals("YEAR") || name.equals("DATETIME") || name.equals("TIMESTAMP"))
			return "java.util.Date";

		return name;// getLogicalName();//tran2String(.getType());
	}

	public String getColumnType4DB() {

		if (columnType == null)
			return "VARCHAR";
//		String name = columnType.getLogicalName();
		String name = columnType.getName();
		/*if (name == null || "".equals(name))
			return "VARCHAR";
		if (name.equals("Numeric"))
			return "NUMERIC";
		if (name.equals("Integer"))
			return "NUMERIC";
		if (name.equals("String"))
			return "VARCHAR";
		if (name.equals("Real"))
			return "NUMERIC";
		if (name.equals("Datetime"))
			return "TIMESTAMP";
		if (name.equals("Real"))
			return "NUMERIC";
		if (name.equals("Datetime"))
			return "TIMESTAMP";
		if (name.equals("Date"))
			return "TIMESTAMP";*/
		if (name.equals("DATETIME"))
			return "TIMESTAMP";
		if (name.equals("INT"))
			return "BIGINT";
		return name;// getLogicalName();//tran2String(.getType());
	}

	public int getShowDatatype() {
		if (columnType == null)
			return 0;
		String name = columnType.getLogicalName();
		if (name == null || "".equals(name))
			return 0;
		if (name.equals("Numeric"))
			return 1;
		if (name.equals("Integer"))
			return 1;
		if (name.equals("String"))
			return 0;
		if (name.equals("Date"))
			return 0;
		return 0;
	}

	public String getColumnNameVoValue() {

		if (getColumnTypeName().equals("double"))
			return "-1";
		if (getColumnTypeName().equals("Long"))
			return "-1L";
		
		if (getColumnTypeName().equals("Integer"))
			return "-1";
		return "null";
	}

	public String getColumnNameType() {

		if ("String".equals(getColumnTypeName()))
			return "VARCHAR";
		if ("Integer".equals(getColumnTypeName()))
			return "INTEGER";
		if ("double".equals(getColumnTypeName()))
			return "NUMBER";
		return "VARCHR";
	}

	private String tran2String(int type) {
		return "String";
	}

	public int getColumnTypeOut() {
		if (this.dommain != null) {
			return this.dommain.getType().getType();
		}
		if (columnType != null) {
			return columnType.getType();
		}
		return 0;
	}

	public void setColumnType(IColumnType columnType) {
		this.columnType = columnType;
	}

	public boolean isNotNull() {
		return notNull;
	}

	public void setNotNull(boolean notNull) {
		this.notNull = notNull;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public int getPKField() {

		if (primaryKey)
			return 1;
		if (this.getColumnName().equalsIgnoreCase("id"))
			return 1;
		return 0;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getSize() {
		if (this.dommain != null) {
			return this.dommain.getSize();
		}
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getDescription() {
		if (this.description == null) {
			this.description = "";
		}
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public String getDefaultValue() {
		if (this.defaultValue == null) {
			this.defaultValue = "";
		}
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public DommainModel getDommain() {
		return dommain;
	}

	public void setDommain(DommainModel dommain) {
		this.dommain = dommain;
	}

	public String toString() {
		return getColumnName();
	}

	public int getMainTableType() {
		return mainTableType;
	}

	public void setMainTableType(int mainTableType) {
		this.mainTableType = mainTableType;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;

	}

	public String getTableName() {
		return tableName;
	}

	public void setCanQut(int canQut) {
		this.canQut = canQut;
	}

	public int getCanQut() {
		return canQut;
	}

	public void setCanTableQut(int canTableQut) {
		this.canTableQut = canTableQut;
	}

	public void setCanAddOut(int canAddOut) {
		this.canAddOut = canAddOut;
	}

	public void setColumnNameOutLowSpec(String columnNameOutLowSpec) {
		this.columnNameOutLowSpec = columnNameOutLowSpec;
	}

	public String getColumnNameOutLowSpec() {
		return columnNameOutLowSpec;
	}

}
