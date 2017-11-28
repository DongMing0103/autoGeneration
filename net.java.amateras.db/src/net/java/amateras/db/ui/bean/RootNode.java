package net.java.amateras.db.ui.bean;

public class RootNode extends Node {

	private static RootNode root = null;

	public RootNode() {
	}

	public static synchronized RootNode getRootNode() {

		if (root == null) {

			root = new RootNode();

			root.setParent(null);

			root.initRootNode();

		}

		return root;

	}

	public static synchronized RootNode getRootNode2() {

		if (root == null) {
			root = new RootNode();
		}

		return root;

	}

	private void initRootNode() {

		User user;

		Sample sample1, sample2;

		Experiment expr1, expr2;

		NodeProcess proc1, proc2;

		user = new User();

		user.setName("Zhang");

		root.addChild(user);

		user = new User();

		user.setName("Yang");

		sample1 = new Sample();

		sample1.setName("Sample1");

		sample2 = new Sample();

		sample2.setName("Sample2");

		user.addChild(sample1);

		user.addChild(sample2);

		expr1 = new Experiment();

		expr1.setName("Exp1");

		expr2 = new Experiment();

		expr1.setName("Exp2");

		sample2.addChild(expr1);

		sample2.addChild(expr2);

		proc1 = new NodeProcess();

		proc2 = new NodeProcess();

		proc1.setName("Proc1");

		proc2.setName("Proc2");

		expr2.addChild(proc1);

		expr2.addChild(proc2);

		root.addChild(user);

		user = new User();

		user.setName("Wang");

		root.addChild(user);

	}

	private void addChild(User user) {
		Node node = new Node();
		node.setName(user.getName());
		this.children.add(node);

	}

}