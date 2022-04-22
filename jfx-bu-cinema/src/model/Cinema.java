package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Cinema {
	
	private ArrayList<Funtion> funtions;

	public Cinema() {
		funtions = new ArrayList<>();
	}

	public boolean saveFuntion(String n, String c, LocalDateTime s, LocalDateTime e) {
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
	
	public boolean savePerson(String i, String n) {
		return false;
	}
	
	public String[] funtionNames() {
		String[] temp = new String[funtions.size()];
		for(int i=0; i<temp.length; i++) {
			temp[i] = funtions.get(i).getName();
		}
		return temp;
	}
	
	public boolean[][] occupiedChairs(int index) {
		return funtions.get(index).getOccupancy();
	}
	
	public void saveOccupancy(boolean[][] audience, int index) {
		funtions.get(index).setOccupancy(audience);
	}

	public int numberFuntions() {
		return funtions.size();
	}
	
	public int getRows(int index) {
		return funtions.get(index).getRows();
	}
}