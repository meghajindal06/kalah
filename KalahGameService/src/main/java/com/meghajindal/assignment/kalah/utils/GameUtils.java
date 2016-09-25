package com.meghajindal.assignment.kalah.utils;

import java.util.List;

import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.Pit;
import com.meghajindal.assignment.kalah.resources.Player;

public class GameUtils {

	/**
	 * return the pit matching the starting pit id
	 * @param startingPitId
	 * @param pits
	 * @return
	 */
	public static Pit getSelectedPit(int startingPitId , List<Pit> pits) {
		
		for(Pit pit: pits){
			if(pit.getId() == startingPitId){
				return pit;
			}
		}
		return null;
	}
	
	/**
	 * return current player based on playerId
	 * @param board
	 * @param playerId
	 * @return
	 */
	public static Player getPlayer(GameBoard board , String playerId) {
		switch(playerId){
		case "Player1": return board.getPlayer1();
		case "Player2": return board.getPlayer2();
		default: return null;
		}
	}
	
	/**
	 * return opponent player based on playerId
	 * @param board
	 * @param playerId
	 * @return
	 */
	public static Player getOpponentPlayer(GameBoard board , String playerId) {
		switch(playerId){
		case "Player1": return board.getPlayer2();
		case "Player2": return board.getPlayer1();
		default: return null;
		}
	}
	
}
