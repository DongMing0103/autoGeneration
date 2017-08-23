
package net.java.amateras.db.ui.bean;

import java.util.ArrayList;
import java.util.List;

import net.java.amateras.db.visual.model.ColumnModel;

public class Node {

	String name;

	Node parent;

	List<Node> children = new ArrayList<Node>(10);

	private ColumnModel column;

	private String refTables;

	private String refTablesAs;

	private String refField;

	private String mainLinedField;

	private String mainLinkedtableName;

	private String mainTableNameAs;

	public Node() {

	}

	public Node(Node parent) {

		this.setParent(parent);

	}

	public String getName() {

		return name;

	}

	public void setName(String name) {

		this.name = name;

	}

	public Node getParent() {

		return parent;

	}

	public void setParent(Node parent) {

		this.parent = parent;

	}

	public void addChild(Node child) {

		child.setParent(this);

		children.add(child);

	}

	public void removeChild(Node child) {

		children.remove(child);

	}

	public List<Node> getChildren() {

		return children;

	}

	public void setColumnModel(ColumnModel column) {
		this.column = column;
	}

	public void clearnChildNode() {
		children.clear();

	}

	public void setRefTable(String refTables) {
		this.refTables = refTables;

	}

	public void setRefTablesAs(String refTablesAs) {
		this.refTablesAs = refTablesAs;

	}

	public void setRefField(String refField) {
		this.refField = refField;
	}

	public ColumnModel getColumn() {
		return column;
	}

	public String getMainLinedField() {
		return mainLinedField;
	}

	public void setMainLinedField(String mainLinedField) {
		this.mainLinedField = mainLinedField;
	}

	public String getRefTables() {
		return refTables;
	}

	public String getRefTablesAs() {
		return refTablesAs;
	}

	public String getRefField() {
		return refField;
	}

	public String getMainLinkedtableName() {
		return mainLinkedtableName;
	}

	public void setMainLinkedtableName(String mainLinkedtableName) {
		this.mainLinkedtableName = mainLinkedtableName;
	}

	public String getMainTableNameAs() {
		return mainTableNameAs;
	}

	public void setMainTableNameAs(String mainTableNameAs) {
		this.mainTableNameAs = mainTableNameAs;
	}
}