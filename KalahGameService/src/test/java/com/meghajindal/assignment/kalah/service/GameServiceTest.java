package com.meghajindal.assignment.kalah.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.meghajindal.assignment.kalah.cache.GameCacheRepository;
import com.meghajindal.assignment.kalah.exceptionhandler.InputValidationException;
import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.Pit;
import com.meghajindal.assignment.kalah.resources.Result;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameServiceTest {

	@Autowired
	GameService gameService;
	
	@Autowired
	GameCacheRepository repo;
	
	@Test
	public void testStartGame_NotNull(){
		GameBoard board = gameService.startNewGame();
		Assert.notNull(board);
		
	}
	
	@Test
	public void testStartGame_StartNewGameIfRunning(){
		GameBoard board1 = gameService.startNewGame();
		GameBoard board2 = gameService.startNewGame();
		Assert.isTrue(board1.getBoardId() != board2.getBoardId());
		
	}
	
	@Test
	public void testGetGameBoard_NotNull(){
		GameBoard board = gameService.startNewGame();
		GameBoard retrieved = gameService.getBoard(board.getBoardId());
		Assert.notNull(retrieved);
		Assert.isTrue(board==retrieved);
	}
	
	@Test
	public void testPlay_InvalidUser(){
		try{
		GameBoard board = gameService.startNewGame();
		 gameService.play(board.getBoardId(), "Player2", 4);
		}catch(Exception e){
		Assert.isInstanceOf(InputValidationException.class, e);
		}
	}
	
	
	@Test
	public void testPlay_InvalidPit(){
		try{
		GameBoard board = gameService.startNewGame();
		board.getPlayer1().getPits().get(2).setNumberOfStones(0);
		repo.saveBoard(board);
		 gameService.play(board.getBoardId(), "Player1", 3);
		}catch(Exception e){
		Assert.isInstanceOf(InputValidationException.class, e);
		}
	}
	
	@Test
	public void testPlay_RunningGame(){
		GameBoard board = gameService.startNewGame();
		Result result =  gameService.play(board.getBoardId(), "Player1", 3);
		Assert.isNull(result);
		
	}
	
	@Test
	public void testPlay_FirstMovePit1(){
		GameBoard board = gameService.startNewGame();
		Result result =  gameService.play(board.getBoardId(), "Player1", 1);
		board = repo.getGameBoard(board.getBoardId());
		Assert.isTrue(board.getPlayer1().getHouse().getNumberOfStones() == 1);
		
	}
	
	@Test
	public void testPlay_CollectStonesEndedInEmptyHouse(){
		GameBoard board = gameService.startNewGame();
		//make pit 6 empty
		board.getPlayer1().getPits().get(5).setNumberOfStones(0);
		//Pit1 should have 5 stones to end in Pit 6
		board.getPlayer1().getPits().get(0).setNumberOfStones(5);
		repo.saveBoard(board);
		Result result =  gameService.play(board.getBoardId(), "Player1", 1);
		board = repo.getGameBoard(board.getBoardId());
		//house should have 7 stones 1 from player1 and 6 from opponent
		Assert.isTrue(board.getPlayer1().getHouse().getNumberOfStones() == 7);
		
	}
	
	public void testPlay_MatchFinished(){
		GameBoard board = gameService.startNewGame();
		//make all pits empty with only pit 6 with 1 stone to end the match
		board.getPlayer1().getPits().get(0).setNumberOfStones(0);
		board.getPlayer1().getPits().get(1).setNumberOfStones(0);
		board.getPlayer1().getPits().get(2).setNumberOfStones(0);
		board.getPlayer1().getPits().get(3).setNumberOfStones(0);
		board.getPlayer1().getPits().get(4).setNumberOfStones(0);
		board.getPlayer1().getPits().get(5).setNumberOfStones(1);
		//Pit1 should have 5 stones to end in Pit 6
		repo.saveBoard(board);
		Result result =  gameService.play(board.getBoardId(), "Player1", 1);
		board = repo.getGameBoard(board.getBoardId());
		//house should have 7 stones 1 from player1 and 6 from opponent
		Assert.notNull(result);
		Assert.isTrue(result.getWinnerId().equals("Player1"));
	}
	
	
	
}
