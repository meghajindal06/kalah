package com.meghajindal.assignment.kalah.rules;

import static com.meghajindal.assignment.kalah.utils.GameSetupUtils.setUp;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.House;
import com.meghajindal.assignment.kalah.resources.Pit;
import com.meghajindal.assignment.kalah.resources.Result;
import com.meghajindal.assignment.kalah.rules.GameRulesImplementor;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameRulesImplementorTest {

	@Autowired
	GameRulesImplementor rulesImplementor;
	
	@Test
	public void testSelectCurrentPlayer_LastPitHouse(){
		GameBoard board = setUp();
		Pit lastPit = new House();
		
		rulesImplementor.selectCurrentPlayer(board, lastPit);
		
		//current player set to Player1 in setup
		Assert.isTrue(board.getCurrentPlayerId().equals("Player1"));
		
	}
	
	
	@Test
	public void testSelectCurrentPlayer_LastPitEmptyPit(){
		GameBoard board = setUp();
		//make third pit of player 2 with only 1 stone (last distributed stone)
		board.getPlayer1().getPits().get(2).setNumberOfStones(1);
		Pit lastPit = board.getPlayer1().getPits().get(2);
		
		rulesImplementor.selectCurrentPlayer(board, lastPit);
		//current player set to Player1 in setup
		Assert.isTrue(board.getCurrentPlayerId().equals("Player1"));
	}
	
	@Test
	public void testSelectCurrentPlayer_LastPitNotEmpty(){
		GameBoard board = setUp();
		//last pit contains 6 stones as a result of setup so was not empty before distribution
		Pit lastPit = board.getPlayer1().getPits().get(2);
		
			
		rulesImplementor.selectCurrentPlayer(board, lastPit);
		
		//second player should get the turn
		Assert.isTrue(board.getCurrentPlayerId().equals("Player2"));
	}
	
	@Test
	public void collectExtraStonesForPlayer1(){
			
		GameBoard board = setUp();
		//make second pit of player 2 with only 1 stone (last distributed stone)
		board.getPlayer1().getPits().get(1).setNumberOfStones(1);
		//set 4 stones in th opponent pits opposite to empty pit
		board.getPlayer2().getPits().get(4).setNumberOfStones(4);
		
		
		//Suppose lastPit was Player1's 2nd pit
		Pit lastPit = board.getPlayer1().getPits().get(1);
		
		
		//Total stones in house should be 4 (1+ 4 from opponent)
		rulesImplementor.collectExtraStonesForPlayer(board, board.getPlayer1(), lastPit);	
		Assert.isTrue(board.getPlayer1().getHouse().getNumberOfStones() == 5);
		
	}
	
	@Test
	public void testPrepareResult_Player1MoreStones(){
		GameBoard board = setUp();
		
		board.getPlayer1().getHouse().setNumberOfStones(8);
		
		Result result = rulesImplementor.prepareResult(board);
		
		Assert.notNull(result);
		Assert.isTrue(result.getWinnerId().equals("Player1"));
	}	
	
	@Test
	public void testPrepareResult_BothPlayersSameStones(){
		GameBoard board = setUp();
		
		Result result = rulesImplementor.prepareResult(board);
		
		Assert.notNull(result);
		Assert.isNull(result.getWinnerId());
	}	
	
	
	@Test
	public void testIsMatchFinished_PLayer1EmptyPits(){
		
		GameBoard board = setUp();
		board.getPlayer1().setPits(setUpEmptyPits());
		
		Assert.isTrue(rulesImplementor.isMatchFinished(board));
		
	}
	
	
	private List<Pit> setUpEmptyPits() {
		List<Pit> pits = new ArrayList<>();
		for(int i= 1;i <=6 ; i++){
			Pit pit = new Pit();
			pit.setId(i);
			pit.setNumberOfStones(0);
			pits.add(pit);
		}
		return pits;
	}


	@Test
	public void testIsMatchFinished_PLayer2EmptyPits(){
		
		GameBoard board = setUp();
		board.getPlayer2().setPits(setUpEmptyPits());
		
		Assert.isTrue(rulesImplementor.isMatchFinished(board));
		
	}
	
	@Test
	public void testIsMatchFinished_NoPlayerWithEmptyPits(){
		
		GameBoard board = setUp();
		
		Assert.isTrue(!rulesImplementor.isMatchFinished(board));
		
	}
	
	
	
	
}
