package com.meghajindal.assignment.kalah.resources;

import org.springframework.hateoas.ResourceSupport;

public class GameBoard extends Game {

	private Long boardId;
	
	private Player player1;
	
	private Player player2;
	
	private String currentPlayerId;

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public Long getBoardId() {
		return boardId;
	}

	public void setBoardId(Long id) {
		this.boardId = id;
	}

	public String getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(String currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}
	
	
	
}
