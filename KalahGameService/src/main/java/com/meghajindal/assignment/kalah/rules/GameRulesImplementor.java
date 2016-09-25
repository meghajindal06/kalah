package com.meghajindal.assignment.kalah.rules;

import static com.meghajindal.assignment.kalah.utils.GameUtils.getOpponentPlayer;

import java.util.List;

import org.springframework.stereotype.Component;

import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.House;
import com.meghajindal.assignment.kalah.resources.Pit;
import com.meghajindal.assignment.kalah.resources.Player;
import com.meghajindal.assignment.kalah.resources.Result;

@Component
public class GameRulesImplementor {

	private static int TOTAL_PIT_POSITIONS = 5;
	
	/**
	 * sets current player based on where the last stone lands
	 * @param board
	 * @param lastPit
	 * @param playerId
	 */
	public void selectCurrentPlayer(GameBoard board , Pit lastPit){
		System.out.println(lastPit);
		System.out.println(hasEndedInEmptyHouse(lastPit));
		System.out.println(board.getCurrentPlayerId());
		if(!hasEndedInHouse(lastPit) && !hasEndedInEmptyHouse(lastPit)){
			String currentPlayerId = board.getCurrentPlayerId();
			//give the turn to the opponent player by setting current player id
			board.setCurrentPlayerId(getOpponentPlayer(board, currentPlayerId).getPlayerId());
		}else{
			//dont do anything current player get the next turn as well
		}
		
	}
	
	/**
	 * collect stones from players pit and opponents pit if last stone ends in empty pit
	 * @param board
	 * @param cuurentPlayer
	 * @param lastPit
	 */
	public void collectExtraStonesForPlayer(GameBoard board, Player cuurentPlayer , Pit lastPit){
			
			List<Pit> pits = cuurentPlayer.getPits();
			
			int lastPitPosition = pits.indexOf(lastPit);
			
			int stonesInHouse = cuurentPlayer.getHouse().getNumberOfStones();
			
			stonesInHouse = stonesInHouse+ lastPit.getNumberOfStones();
			lastPit.setNumberOfStones(0);
			
			int opponentPitPosition = TOTAL_PIT_POSITIONS - lastPitPosition;
			
			Player opponentPlayer = getOpponentPlayer(board, cuurentPlayer.getPlayerId());
			
			Pit opponentPit = opponentPlayer.getPits().get(opponentPitPosition);
			
			
			stonesInHouse= stonesInHouse+opponentPit.getNumberOfStones();
					
			cuurentPlayer.getHouse().setNumberOfStones(stonesInHouse);
			opponentPit.setNumberOfStones(0);
		
	}
	
	/**
	 * creates the result of the game
	 * @param board
	 * @return
	 */
	public Result prepareResult(GameBoard board){
		Result result = new Result();
		
		result.setMatchFinished(isMatchFinished(board));
		if(board.getPlayer1().getHouse().getNumberOfStones()>board.getPlayer2().getHouse().getNumberOfStones()){
			result.setWinnerId(board.getPlayer1().getPlayerId());
			result.setTotalStones(board.getPlayer1().getHouse().getNumberOfStones());
		}else if(board.getPlayer2().getHouse().getNumberOfStones()>board.getPlayer1().getHouse().getNumberOfStones()){
			result.setWinnerId(board.getPlayer2().getPlayerId());
			result.setTotalStones(board.getPlayer2().getHouse().getNumberOfStones());
		}else{
			//do not set any winnerId
		}
		
		return result;
	}
	
	/**
	 * match is finished if either player has all empty pits
	 * @param board
	 * @return
	 */
	public boolean isMatchFinished(GameBoard board){
		
		return (allPitsEmpty(board.getPlayer1()) || allPitsEmpty(board.getPlayer2()));
		
	}
	
	/**
	 * chck if all pits are empty for a player
	 * @param player
	 * @return
	 */
	private boolean allPitsEmpty(Player player) {
		
		for(Pit pit: player.getPits()){
			if(pit.getNumberOfStones()>0){
				return false;
			}
		}
		return true;
		
	}
	/**
	 * returns true if the last stone ends in an empty house,
	 * @param lastPit
	 * @return
	 */
	public boolean hasEndedInEmptyHouse(Pit lastPit){
		//lastPit contains only the stone from the last move
		if(!(lastPit instanceof House) && lastPit.getNumberOfStones() == 1){
			return true;
		}
		return false;
	}
	
	/**
	 * returns true if the last stone ends in players house
	 * @param lastPit
	 * @return
	 */
	private boolean hasEndedInHouse(Pit lastPit){
		if(lastPit instanceof House){
			return true;
		}
		return false;
	}
	
	
	
}
