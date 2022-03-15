package model;

import java.time.LocalDateTime;

public class Funtion {
	private String name, cinema;
	private LocalDateTime start, end;
	private boolean[][] audience;
	
	public Funtion(String n, String c, LocalDateTime s, LocalDateTime e) {
		name = n;
		cinema = c;
		start = s;
		end = e;
		if(c.equals("Small Room"))
			audience = new boolean[4][7];
		else
			audience = new boolean[6][7];
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

	public boolean[][] getAudience() {
		return audience;
	}

	public void setAudience(boolean[][] a) {
		audience = a;
	}
}
