package com.meghajindal.assignment.kalah.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.House;
import com.meghajindal.assignment.kalah.resources.Pit;
import com.meghajindal.assignment.kalah.resources.Player;

@Component
public class GameSetupUtils {

	public static GameBoard setUp(){
		GameBoard board = new GameBoard();
		
		
		board.setPlayer1(createPlayerSettings("Player1"));
		board.setPlayer2(createPlayerSettings("Player2"));
		board.setCurrentPlayerId("Player1");
		
		return board;
	}
	
	
	private  static Player createPlayerSettings(String playerId){
		Player player = new Player();
		player.setPlayerId(playerId);
		
		List<Pit> pits = new ArrayList<>();
		Pit pit = null;
		for(int i=1;i<=6;i++){
			pit = new Pit();
			pit.setId(i);
			pit.setNumberOfStones(6);
			pits.add(pit);
		}
		
		player.setPits(pits);
		House house = new House();
		house.setNumberOfStones(0);
		player.setHouse(house);
		
		return player;
	}
}
