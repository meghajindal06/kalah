package com.meghajindal.assignment.kalah.validation;

import static com.meghajindal.assignment.kalah.utils.GameUtils.getSelectedPit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.meghajindal.assignment.kalah.cache.GameCacheRepository;
import com.meghajindal.assignment.kalah.exceptionhandler.InputValidationException;
import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.Pit;
import com.meghajindal.assignment.kalah.resources.Player;
import com.meghajindal.assignment.kalah.service.impl.GameServiceImpl;

import static com.meghajindal.assignment.kalah.utils.GameUtils.*;

@Component
public class GameValidator {
	
	private static final Logger logger = Logger.getLogger(GameValidator.class);
	
	@Autowired
	GameCacheRepository cacheRepo;

	
	public void validate(Long boardId ,String playerId, int startingPitId){
		GameBoard board = cacheRepo.getGameBoard(boardId);
		validatePlayer(board, playerId);
		validateMove(getPlayer(board, playerId), startingPitId);
	}
	
	/**
	 * validate if the player playing is the current player
	 * @param boardId
	 * @param playerId
	 */
	public void validatePlayer(GameBoard board ,String playerId){
		
		
		if(!board.getCurrentPlayerId().equals(playerId)){
			 logger.error("Error Message: " + playerId + "is not the valid player.Please check the turn");
			throw new InputValidationException();
		}
	}
	
	
	/**
	 * validate if the player playing is the current player
	 * @param boardId
	 * @param playerId
	 */
	public void validateMove(Player player ,int startingPitId){
		
		Pit selectedPit = getSelectedPit(startingPitId, player.getPits());
		
		if(selectedPit.getNumberOfStones() <= 0){
			 logger.error("Error Message: Pit" + startingPitId + "for player " + player.getPlayerId() + " is not the valid .");
			throw new InputValidationException();
		}
		
	}
}
