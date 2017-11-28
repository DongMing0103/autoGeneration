package net.java.amateras.db.ui.bean;

public class Sample {

	private String name;
	private Experiment expr1;

	public void setName(String name) {
		this.name = name;
	}

	public void addChild(Experiment expr1) {
		this.expr1 = expr1;

	}

	public String getName() { 
		return name;
	}

}
