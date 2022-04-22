package model;

public class Person {
	
	private String id, name;
	
	public Person(String i, String n) {
		id = i;
		name = n;
	}

	public String getID() {
		return id;
	}

	public String getName() {
		return name;
	}
}
