package jp.knct.di.c6t.model;


public class User {
	private int id;
	private String name;
	private String area;

	public User(int id, String name, String area) {
		this.setId(id);
		this.setName(name);
		this.setArea(area);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
}
