# kalah
Kalah Game basic implementation using Sprintboot and Java 7

#Implementation details
Service provides 3 apis for playing the game  
** startNewGame -> starts a new game  and returns the link to the game  
POST method  
URL -> http://localhost:8080/game  


** getGame -> Retrieves a running game  
GET method  
URL -> http://localhost:8080/game/{boardId}  

** play -> Used by the player to play his turn  
POST method  
URL -> http://localhost:8080/game/1/play/Player1?startingPitId=1  
