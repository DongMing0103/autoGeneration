package net.java.amateras.db.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.java.amateras.db.dialect.IColumnType;
import net.java.amateras.db.dialect.IDialect;
import net.java.amateras.db.dialect.IndexType;
import net.java.amateras.db.visual.model.ColumnModel;
import net.java.amateras.db.visual.model.IndexModel;
import net.java.amateras.db.visual.model.TableModel;

public class DatabaseInfo {

	private String uri = "";
	private String user = "";
	private String password = "";
	private String catalog = "";
	private String schema = "";
	private Driver driver = null;
	private boolean enableView = false;
	private String productName = null;
	private boolean autoConvert = false;

	final public String POSTGRESQL = "PostgreSQL";
	final public String MYSQL = "MySQL";
	final public String HSQLDB = "HSQL Database Engine";
	final public String DERBY = "Apache Derby";
	final public String SYBASE = "Adaptive Server Enterprise";

	public DatabaseInfo(Class<?> driverClass) throws InstantiationException, IllegalAccessException {
		driver = (Driver) driverClass.newInstance();
	}

	public void setURI(String uri) {
		this.uri = uri;
	}

	public String getURI() {
		return this.uri;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getCatalog() {
		return this.catalog;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return this.password;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getSchema() {
		return this.schema;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getUser() {
		return this.user;
	}

	public void setEnableView(boolean flag) {
		enableView = flag;
	}

	public boolean isEnableView() {
		return this.enableView;
	}

	public boolean isAutoConvert() {
		return autoConvert;
	}

	public void setAutoConvert(boolean autoConvert) {
		this.autoConvert = autoConvert;
	}

	/**
	 * Connect to the database and return the connection.
	 * 
	 * @return the JDBC connection
	 * @throws SQLException
	 *             Connect error
	 */
	public Connection connect() throws SQLException {
		Properties p = new Properties();

		if (isSybase()) {
			p.setProperty("JCONNECT_VERSION", "3");
			p.setProperty("charSet", "eucksc"); // Database encoding
		}

		p.setProperty("user", user);
		p.setProperty("password", password);
		return driver.connect(uri, p);
	}

	public List<String> loadTables() throws SQLException {
		List<String> list = new ArrayList<String>();
		Connection con = null;

		try {
			con = connect();
			DatabaseMetaData meta = con.getMetaData();
			productName = meta.getDatabaseProductName();
			// DBPlugin.logException(new Exception(productName));

			if (isMSSQL()) {
				if (catalog.length() == 0) {
					catalog = "%";
				}
			}

			catalog = (catalog.length() == 0) ? null : catalog;
			schema = (schema.length() == 0) ? null : schema;

			// , "VIEW", "SYNONYM"
			boolean isFilterDouble = false;
			if (catalog != null && "sl".equals(catalog)) {
				isFilterDouble = true;
			} else if (catalog != null && "dsw".equals(catalog)) {
				isFilterDouble = true;
			}
			ResultSet tables = meta.getTables(catalog, schema, "%", isOracle() ? new String[] { "TABLE" } : null);

			boolean isFirst = true;
			while (tables.next()) {
				if (isFirst) {
					ResultSetMetaData resMetaData = tables.getMetaData();
					int count = resMetaData.getColumnCount();
					for (int i = 0; i < count; i++) {
						System.out.println(resMetaData.getColumnName(i + 1));
					}
					isFirst = false;
				}

				String TABLE_CAT = tables.getString("TABLE_CAT");
				System.out.println("TABLE_CAT:" + TABLE_CAT);
				String TABLE_SCHEM = tables.getString("TABLE_SCHEM");

				System.out.println("TABLE_SCHEM:" + TABLE_SCHEM);

				String t = tables.getString("TABLE_TYPE");
				// || ("VIEW".equals(t) && enableView)
				// if (TABLE_SCHEM.startsWith("APEX")) {

				// } else
				if (TABLE_SCHEM != null && TABLE_SCHEM.equals("XDB")) {
					System.out.println("table_name:" + tables.getString("table_name"));
				}

				// TABLE_SCHEM
				String tableName = tables.getString("table_name");
				if (isFilterDouble) {
					if (tableName.startsWith("BILL_SELL")) {
						System.out.println("table:--->" + tableName);
					}
					if (TABLE_SCHEM.equalsIgnoreCase(catalog)) {
						// || (isOracle() && "SYNONYM".equals(t))) {
						if (tableName.startsWith("/")) {
							// filter
						} else {
							if (tableName.indexOf("$") > -1) {

							} else {
								if (tableName.startsWith("goods")) {
									System.out.println("xxx");
								}
								list.add(tableName);
							}
						}
					}
				} else if ("TABLE".equals(t)) {
					// || (isOracle() && "SYNONYM".equals(t))) {
					if (tableName.startsWith("/")) {
						// filter
					} else {
						if (tableName.indexOf("$") > -1) {

						} else {
							if (tableName.startsWith("goods")) {
								System.out.println("xxx");
							}
							list.add(tableName);
						}
					}
				}

			}

			tables.close();

			if (driver.getClass().getName().equals("org.hsqldb.jdbcDriver") && uri.indexOf("jdbc:hsqldb:hsql://") != 0) {
				Statement stmt = null;
				try {
					stmt = con.createStatement();
					// System.out.println("SHUTDOWN");
					// System.out.println(uri);
					stmt.executeUpdate("SHUTDOWN;");
				} finally {
					if (stmt != null) {
						stmt.close();
					}
				}
			}
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return list;

	}

	public TableModel getTableInfo(String tableName, IDialect dialect, Connection conn, String catalog, String schema, boolean autoConvert)
			throws SQLException {

		TableModel table = new TableModel();
		table.setTableName(tableName);

		List<ColumnModel> list = new ArrayList<ColumnModel>();

		DatabaseMetaData dbmd = conn.getMetaData();
		ResultSet resultSet = dbmd.getTables(null, "%", schema.isEmpty()?"%":schema+"%", new String[] { "TABLE" });
		while (resultSet.next()) {
			String tableNameIn = resultSet.getString("TABLE_NAME");
			if (tableName.equals(tableNameIn)) {
				// System.out.println(tableName);
				if (autoConvert) {
					// table.setLogicalName(NameConverter.physical2logical(table.getTableName()));
					table.setLogicalName(table.getTableName());
				} else {
					table.setLogicalName(tableName);
				}
				// ResultSet rs =getConnection.getMetaData().getColumns(null,
				// getXMLConfig.getSchema(),tableName.toUpperCase(),
				// "%");//其他数据库不需要这个方法的，直接传null，这个是oracle和db2这么用
				ResultSet rs = dbmd.getColumns(null, schema.isEmpty()?"%":schema+"%", tableName, "%");
				System.out.println("表名：" + tableName + "\t\n表字段信息：");
				while (rs.next()) {
					System.out.println(rs.getString("COLUMN_NAME") + "----" + rs.getString("REMARKS"));

					IColumnType type = dialect.getColumnType(rs.getString("TYPE_NAME"));
					if (type == null) {
						type = dialect.getColumnType(rs.getInt("DATA_TYPE"));
						if (type == null) {
							type = dialect.getDefaultColumnType();
						}
					}

					ColumnModel column = new ColumnModel();
					column.setColumnName(rs.getString("COLUMN_NAME"));
					if (autoConvert) {
						// column.setLogicalName(NameConverter.physical2logical(column.getColumnName()));
						column.setLogicalName(column.getColumnName());
					} else {
						column.setLogicalName(  rs.getString("REMARKS"));
					}
					column.setColumnType(type);
					column.setSize(rs.getString("COLUMN_SIZE"));
					column.setNotNull(rs.getString("IS_NULLABLE").equals("NO"));

					list.add(column);

				}

			}
		}
		table.setColumns(list.toArray(new ColumnModel[list.size()]));

		List<IndexModel> indices = loadIndexModels(tableName, dialect, conn, catalog, schema, list);
		table.setIndices(indices.toArray(new IndexModel[indices.size()]));

		return table;
	}

	protected List<IndexModel> loadIndexModels(String tableName, IDialect dialect, Connection conn, String catalog, String schema,
			List<ColumnModel> columns) throws SQLException {

		List<IndexModel> result = new ArrayList<IndexModel>();
		if(true)return result;
		// DatabaseMetaData meta = conn.getMetaData();
		// ResultSet rs = meta.getIndexInfo(catalog, schema, tableName, false,
		// true);
		ResultSet rs = getIndexInfo(conn, schema, tableName);
		while (rs.next()) {
			String indexName = rs.getString("INDEX_NAME");
			if (indexName != null) {
				IndexModel indexModel = null;
				for (IndexModel index : result) {
					if (index.getIndexName().equals(indexName)) {
						indexModel = index;
						break;
					}
				}
				if (indexModel == null) {
					indexModel = new IndexModel();
					indexModel.setIndexName(indexName);
					indexModel.setIndexName(rs.getString("INDEX_NAME"));
					if (rs.getBoolean("NON_UNIQUE")) {
						indexModel.setIndexType(new IndexType("INDEX"));
					} else {
						indexModel.setIndexType(new IndexType("UNIQUE"));
					}
					result.add(indexModel);
				}
				indexModel.getColumns().add(rs.getString("COLUMN_NAME"));
			}
		}
		rs.close();

		List<IndexModel> removeIndexModels = new ArrayList<IndexModel>();
		for (IndexModel indexModel : result) {
			List<String> pkColumns = new ArrayList<String>();
			for (ColumnModel columnModel : columns) {
				if (columnModel.isPrimaryKey()) {
					pkColumns.add(columnModel.getColumnName());
				}
			}
			if (indexModel.getColumns().size() == pkColumns.size()) {
				boolean isNotPk = false;
				for (int i = 0; i < indexModel.getColumns().size(); i++) {
					if (!indexModel.getColumns().get(i).equals(pkColumns.get(i))) {
						isNotPk = true;
						break;
					}
				}
				if (!isNotPk) {
					removeIndexModels.add(indexModel);
				}
			}
		}
		result.removeAll(removeIndexModels);

		return result;
	}

	/**
	 * get Table's Comments
	 */
	protected String getTableComment(String tableName, Connection conn, String catalog, String schema) throws SQLException {
		String comment = tableName; // default

		StringBuffer query = new StringBuffer();
		query.append("SELECT COMMENTS FROM ALL_TAB_COMMENTS WHERE TABLE_NAME = ? ");
		if (StringUtils.isNotEmpty(schema))
			query.append("AND OWNER = ?");
		else {
			query.append("AND OWNER = USER");
		}

		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		pstmt.setString(1, tableName.toUpperCase());
		if (StringUtils.isNotEmpty(schema))
			pstmt.setString(2, schema.toUpperCase());

		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			comment = rs.getString(1);
		}
		rs.close();
		pstmt.close();
		return StringUtils.isEmpty(comment) ? tableName : comment;
	}

	/**
	 * get Column's Comments
	 */
	protected String getColumnComment(String tableName, String columnName, Connection conn, String catalog, String schema)
			throws SQLException {
		String comment = columnName; // default

		StringBuffer query = new StringBuffer();
		query.append("SELECT COMMENTS FROM ALL_COL_COMMENTS WHERE TABLE_NAME = ? AND COLUMN_NAME = ? ");
		if (StringUtils.isNotEmpty(schema))
			query.append("AND OWNER = ?");
		else {
			query.append("AND OWNER = USER");
		}

		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		pstmt.setString(1, tableName.toUpperCase());
		pstmt.setString(2, columnName.toUpperCase());
		if (StringUtils.isNotEmpty(schema))
			pstmt.setString(3, schema.toUpperCase());

		ResultSet rs = pstmt.executeQuery();
		if (rs.next()) {
			comment = rs.getString(1);
		}
		// DBPlugin.logException(new Exception("comments = "+ rs.getString(1)));
		rs.close();
		pstmt.close();
		return StringUtils.isEmpty(comment) ? columnName : comment;
	}

	public String getProductName() {
		return productName;
	}

	public boolean isPostgreSQL() {
		return POSTGRESQL.equals(productName);
	}

	public boolean isMySQL() {
		return MYSQL.equals(productName);
	}

	public boolean isHSQLDB() {
		return HSQLDB.equals(productName);
	}

	public boolean isDerby() {
		return DERBY.equals(productName);
	}

	public boolean isMSSQL() {
		if (productName.toLowerCase().indexOf("microsoft") != -1) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSybase() {
		return SYBASE.equals(productName);
	}

	public boolean isOracle() {
		if (productName.toLowerCase().indexOf("oracl") != -1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * get Table's Name
	 * 
	 * @param tabName
	 * @param schema
	 * @return
	 */
	private String getTableName(String tabName, String schema) {
		if (StringUtils.isNotEmpty(schema)) {
			return schema + "." + tabName;
		} else {
			return tabName;
		}
	}

	// public void writeResultColumns(ResultSet cols) throws SQLException {
	// ResultSetMetaData m = cols.getMetaData();
	// int c = m.getColumnCount();
	//
	// while (cols.next()) {
	// for (int k = 1; k < (c + 1); k++) {
	// System.out.println(m.getColumnName(k) + ":" + cols.getString(k));
	// }
	// }
	// }
	/**
	 * Return the index in the <code>ResultSetMetaData</code> for the given
	 * column.
	 */
	protected int getResultSetMetaDataIndex(ResultSetMetaData rm, String columnName) throws SQLException {
		for (int i = 1; i < rm.getColumnCount(); i++) {
			if (rm.getColumnName(i).equals(columnName)) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * get Index Information
	 * 
	 * @param conn
	 * @param schema
	 * @param tableName
	 * @return
	 * @throws SQLException
	 */
	private ResultSet getIndexInfo(Connection conn, String schema, String tableName) throws SQLException {
		StringBuffer query = new StringBuffer();
		query.append("SELECT NULL                                     AS table_cat				");
		query.append("	    ,i.owner                                  AS table_schem            ");
		query.append("	    ,i.table_name                             AS table_name             ");
		query.append("	    ,decode(i.uniqueness ,'UNIQUE' ,0 ,1)     AS non_unique             ");
		query.append("	    ,NULL                                     AS index_qualifier        ");
		query.append("	    ,i.index_name                             AS index_name             ");
		query.append("	    ,1                                        AS type                   ");
		query.append("	    ,c.column_position                        AS ordinal_position       ");
		query.append("	    ,c.column_name                            AS column_name            ");
		query.append("	    ,NULL                                     AS asc_or_desc            ");
		query.append("	    ,i.distinct_keys                          AS cardinality            ");
		query.append("	    ,i.leaf_blocks                            AS pages                  ");
		query.append("	    ,NULL                                     AS filter_condition       ");
		query.append("  FROM all_indexes     i                                                  ");
		query.append("	    ,all_ind_columns c                                                  ");
		query.append(" WHERE i.table_name    = ?                                    			");
		if (StringUtils.isNotEmpty(schema))
			query.append("   AND i.owner         = ?                                          		");
		else {
			query.append("   AND i.owner         = USER                                        		");
		}
		query.append("   AND i.index_name    = c.index_name                                     ");
		query.append("   AND i.table_owner   = c.table_owner                                    ");
		query.append("   AND i.table_name    = c.table_name                                     ");
		query.append("   AND i.owner         = c.index_owner                                    ");
		query.append(" ORDER BY non_unique ,type ,index_name ,ordinal_position                  ");

		// DBPlugin.logException(new Exception(query.toString()));

		PreparedStatement pstmt = conn.prepareStatement(query.toString());
		pstmt.setString(1, tableName.toUpperCase());
		if (StringUtils.isNotEmpty(schema))
			pstmt.setString(2, schema.toUpperCase());

		return pstmt.executeQuery();
	}

	public Connection getConnection() throws SQLException {
		return this.connect();

	}
}
