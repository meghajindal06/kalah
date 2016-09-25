package com.meghajindal.assignment.kalah.resources;

public class Pit {
	
	private int id;

	private int numberOfStones;

	public int getNumberOfStones() {
		return numberOfStones;
	}

	public void setNumberOfStones(int numberOfStones) {
		this.numberOfStones = numberOfStones;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	public String toString(){
		return "id:" + this.id + ", numberOfStones:" + this.numberOfStones;
	}
	
	
}
