package com.meghajindal.assignment.kalah;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meghajindal.assignment.kalah.resources.Game;
import com.meghajindal.assignment.kalah.resources.GameBoard;
import com.meghajindal.assignment.kalah.resources.Result;
import com.meghajindal.assignment.kalah.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {

    
    
    @Autowired
    GameService gameService;
    
    @PostMapping
    public HttpEntity<GameBoard> startGame() throws NoSuchMethodException, SecurityException {
       
    	GameBoard board = gameService.startNewGame();
    	board.add(linkTo(GameController.class.getMethod("getBoard", Long.class), board.getBoardId()).withSelfRel());
    	board.add(linkTo(methodOn(GameController.class).play(board.getBoardId(), board.getCurrentPlayerId(), "startingPitId")).withRel("play"));
     
    	return new ResponseEntity<GameBoard>(board, HttpStatus.OK);
    	
    }
    
    
   
    
    
    @RequestMapping("{boardId}")
    @GetMapping
    public HttpEntity<GameBoard> getBoard(@PathVariable Long boardId) throws NoSuchMethodException, SecurityException {
       
    	GameBoard board = gameService.getBoard(boardId);
    	board.add(linkTo(GameController.class.getMethod("getBoard", Long.class), board.getBoardId()).withSelfRel());
    	board.add(linkTo(methodOn(GameController.class).play(board.getBoardId(), board.getCurrentPlayerId(), "startingPitId")).withRel("play"));
     
    	return new ResponseEntity<GameBoard>(board, HttpStatus.OK);
    	
    }
    
    @RequestMapping("{boardId}/play/{playerId}")
    @PostMapping
    public HttpEntity<Game> play(@PathVariable Long boardId , @PathVariable String playerId , @RequestParam(name="startingPitId") String startingPitId) throws NoSuchMethodException, SecurityException {
       
    	Result result = gameService.play(boardId, playerId, Integer.parseInt(startingPitId));
    	GameBoard board = gameService.getBoard(boardId);
    	if(result == null){
    		if(board.getLink("play") == null) {
    			board.add(linkTo(methodOn(GameController.class).play(board.getBoardId(), board.getCurrentPlayerId(), "startingPitId")).withRel("play"));
    		}
    		if(board.getLink("self") == null) {
    			board.add(linkTo(GameController.class.getMethod("getBoard", Long.class), board.getBoardId()).withSelfRel());
    		}
    		return new ResponseEntity<Game>((Game)board, HttpStatus.OK);
    	}else{
    		board.getLinks().remove(board.getLink("play"));
    		return new ResponseEntity<Game>((Game)result, HttpStatus.OK);
    	}
    	
    	
     
    	
    }
    
}