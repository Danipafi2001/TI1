package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cinema {
	private ArrayList<Funtion> funtions;

	public Cinema() {
		funtions = new ArrayList<>();
	}

	public boolean addFuntion(String n, String c, LocalDateTime s, LocalDateTime e) {
		boolean added = true;
		for (int i = 0; i<funtions.size(); i++) {
			Funtion temp = funtions.get(i);
			if(temp.getCinema().equals(c) && !temp.getStart().isAfter(e) && !temp.getEnd().isBefore(s))
				added = false;
		}
		if(added)
			funtions.add(new Funtion(n, c, s, e));
		return added;
	}
	
	public boolean addPerson(String i, String n) {
		return false;
	}
	
	public String[] funtionNames() {
		String[] temp = new String[funtions.size()];
		for(int i=0; i<temp.length; i++) {
			temp[i] = funtions.get(i).getName();
		}
		return temp;
	}
	
	public boolean[][] chairBussy(int index) {
		return funtions.get(index).getAudience();
	}
	
	public void save(boolean[][] audience, int index) {
		funtions.get(index).setAudience(audience);
	}

	public int funtionsSize() {
		return funtions.size();
	}

	public ArrayList<Funtion> getFuntions() {
		return funtions;
	}
}