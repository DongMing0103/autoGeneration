package net.java.amateras.db.ui.bean;

public class Experiment {

	private NodeProcess proc1;
	private String name;

	public void setName(String name) {
		this.name = name;

	}

	public String getName() {
		return name;
	}

	public void addChild(NodeProcess proc1) {
		this.proc1 = proc1;

	}

}
