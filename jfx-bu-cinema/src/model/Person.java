package model;

import java.util.ArrayList;

public class Person {
	private String id;
	private String name;
	private ArrayList<String> chairs;
	
	public Person(String i, String n) {
		id = i;
		name = n;
		chairs = new ArrayList<>();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public ArrayList<String> getChairs() {
		return chairs;
	}
}
