package com.meghajindal.assignment.kalah.service;

import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.Result;

public interface GameService {

	/**
	 * starts a new game
	 * @return
	 */
	public GameBoard startNewGame();
	
	/**
	 * retrieves the running game based on id
	 * @param boardId
	 * @return
	 */
	public GameBoard getBoard(Long boardId);
	
	/**
	 * makes a move for the player , returns result if the match is finished else returns null
	 * @param boardId
	 * @param playerId
	 * @param pitId
	 * @return
	 */
	public Result play(Long boardId ,String playerId , int pitId);
}
