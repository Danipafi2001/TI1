package model;

import java.time.LocalDateTime;

public class Funtion {

	private int rows;
	private boolean[][] occupancy;
	private String name, cinema;
	private LocalDateTime start, end;
	
	public Funtion(String n, String c, LocalDateTime s, LocalDateTime e) {
		name = n;
		cinema = c;
		start = s;
		end = e;
		rows = c.equals("Small Room") ? 4 : 6;
		occupancy = new boolean[rows][7];
	}

	public String getName() {
		return name;
	}

	public String getCinema() {
		return cinema;
	}

	public LocalDateTime getStart() {
		return start;
	}

	public LocalDateTime getEnd() {
		return end;
	}
	
	public int getRows() {
		return rows;
	}

	public boolean[][] getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(boolean[][] o) {
		occupancy = o;
	}
}
