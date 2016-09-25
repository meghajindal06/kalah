package com.meghajindal.assignment.kalah.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.meghajindal.assignment.kalah.resources.GameBoard;

@Repository
public class GameCacheRepository {

	private static final Map<Long,GameBoard> cachedGames = new ConcurrentHashMap<>();
	
	private final AtomicLong counter = new AtomicLong();
	
	public void saveBoard(GameBoard board){
		Long boardId = board.getBoardId();
		if(boardId == null){
			boardId = counter.incrementAndGet();
			board.setBoardId(boardId);
		}
		
		
		cachedGames.put(boardId, board);
	}
	
	public GameBoard getGameBoard(Long boardId){
		return cachedGames.get(boardId);
	}
	
	
}
