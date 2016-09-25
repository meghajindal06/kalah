package com.meghajindal.assignment.kalah.service.impl;

import static com.meghajindal.assignment.kalah.utils.GameSetupUtils.setUp;
import static com.meghajindal.assignment.kalah.utils.GameUtils.getPlayer;
import static com.meghajindal.assignment.kalah.utils.GameUtils.getSelectedPit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meghajindal.assignment.kalah.cache.GameCacheRepository;
import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.Pit;
import com.meghajindal.assignment.kalah.resources.Player;
import com.meghajindal.assignment.kalah.resources.Result;
import com.meghajindal.assignment.kalah.rules.GameRulesImplementor;
import com.meghajindal.assignment.kalah.service.GameService;
import com.meghajindal.assignment.kalah.validation.GameValidator;

@Service
public class GameServiceImpl implements GameService {

	@Autowired
	GameCacheRepository repo;
	
	@Autowired
	GameValidator gameValidator;
	
	
	@Autowired
	GameRulesImplementor ruleImplementor;
	
	private static final Logger logger = Logger.getLogger(GameServiceImpl.class);
	
	@Override
	public GameBoard startNewGame() {
		
		GameBoard board = setUp();
		repo.saveBoard(board);
		return board;
	}
	
	
	@Override
	public GameBoard getBoard(Long boardId) {
		return repo.getGameBoard(boardId);
	}


	@Override
	public Result play(Long boardId , String playerId, int startingPitId) {
		
		
		gameValidator.validate(boardId, playerId , startingPitId);
		
		GameBoard board = repo.getGameBoard(boardId);
		
		//get the player
		Player player = getPlayer(board ,playerId);
		
		Pit lastPit = distributeStones(player , startingPitId);
		
		ruleImplementor.selectCurrentPlayer(board, lastPit);
		if(ruleImplementor.hasEndedInEmptyHouse(lastPit)){
			ruleImplementor.collectExtraStonesForPlayer(board, player, lastPit);
		}
		
		
		repo.saveBoard(board);
		
		Result result = null;
		if(ruleImplementor.isMatchFinished(board)){
			logger.info("Match with gameboard" + boardId + "is finished");
			result = ruleImplementor.prepareResult(board);
		}
			
		return result;
	}


	/**
	 * 
	 * @param player
	 * @param startingPitId
	 * @return
	 */
	private Pit distributeStones(Player player, int startingPitId) {
		
		Pit lastPit = null;
		Pit selectedPit = getSelectedPit(startingPitId, player.getPits());
		int selectedPitPosition = player.getPits().indexOf(selectedPit);
		
		List<Pit> pitsIncludingHouse = new ArrayList<>();
		pitsIncludingHouse.addAll(player.getPits());
		pitsIncludingHouse.add(player.getHouse());
		
		//rotate and distribute stones
		Collections.rotate(pitsIncludingHouse, 6-selectedPitPosition);
		
		
		int stones = selectedPit.getNumberOfStones();
		lastPit = addStonesToPits(pitsIncludingHouse, stones);
		selectedPit.setNumberOfStones(0);
		return lastPit;
	}


	private Pit addStonesToPits(List<Pit> pits , int stones){
		
		Pit lastPit = null;
		for(Pit pit: pits){
			pit.setNumberOfStones(pit.getNumberOfStones()+1);
			stones--;
			if(stones == 0){
				lastPit = pit;
				break;
			}
			
		}
		
		if(stones!=0){
			return addStonesToPits(pits, stones);
		}else{
			return lastPit;
		}
		
		
		
	}


	
	
	
	
	


	

}
