package com.meghajindal.assignment.kalah.resources;

public class Result extends Game{

	private boolean matchFinished;
	
	private String winnerId;
	
	private int totalStones;

	public boolean isMatchFinished() {
		return matchFinished;
	}

	public void setMatchFinished(boolean matchFinished) {
		this.matchFinished = matchFinished;
	}

	public String getWinnerId() {
		return winnerId;
	}

	public void setWinnerId(String winnerId) {
		this.winnerId = winnerId;
	}

	public int getTotalStones() {
		return totalStones;
	}

	public void setTotalStones(int totalStones) {
		this.totalStones = totalStones;
	}

	
	
	
	
}
