package com.meghajindal.assignment.kalah.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import com.meghajindal.assignment.kalah.cache.GameCacheRepository;
import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.Player;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GameCacheRepositoryTest {

	
	
	@Autowired
	GameCacheRepository repo;
	
	@Test
	public void testSaveBoard_ShouldUpdateId(){
		GameBoard board = new GameBoard();
		repo.saveBoard(board);
		Assert.notNull(board.getBoardId());
	}
	
	@Test
	public void testSaveBoard_ShouldNotUpdateBoardIdIfPresent(){
		
		GameBoard board = createBoard();
		board.setBoardId(123L);
		
		repo.saveBoard(board);
		Long boardId = board.getBoardId();
		
		
		Assert.isTrue(boardId.longValue() == 123);
	}
	
	@Test
	public void testgetGameBoard_RetrievingBoardNotNull(){
		GameBoard board = createBoard();
		repo.saveBoard(board);
		
		GameBoard retrievedBoard = repo.getGameBoard(board.getBoardId());
		
		Assert.notNull(retrievedBoard);
	}
	
	@Test
	public void testgetGameBoard_RetrievingSameBoard(){
		GameBoard board = createBoard();
		repo.saveBoard(board);
		
		GameBoard retrievedBoard = repo.getGameBoard(board.getBoardId());
		
		Assert.isTrue((board.getPlayer1().getPlayerId().equals(retrievedBoard.getPlayer1().getPlayerId())));
	}
	
	
	@Test
	public void testgetGameBoard_RetrievingBoard_NonExisting(){
		
		GameBoard retrievedBoard = repo.getGameBoard(234L);
		
		Assert.isNull(retrievedBoard);
	}
	
	private GameBoard createBoard(){
		GameBoard board = new GameBoard();
		Player player = new Player();
		player.setPlayerId("Player1");
		board.setPlayer1(player);
		return board;
	}
}
