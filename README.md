# KK Backend

### Knowledge Knockout , Best Game
##

![N|Demo](http://aptivist.net/images/logo-white.png)

Ktor is a versatile web framework that can be used to build server-side applications in Kotlin. It supports a range of features, including HTTP and WebSockets, which make it well-suited for building a game server that uses sockets for real-time communication between the server and the clients.

## Messages - HOST

| Message | Detail |
| ------ | ------ |
| Setup the game | This endpoint could be used to set up the game, such as configuring the number of players, time and rounds.|
| Start round |  This endpoint could be used to start a new round of the game. |
| Send point | This endpoint could be used to send a player's score or points to the server.  |
| No answer | This endpoint could be used to indicate that a player did not provide an answer to a question or task in the game. |


## Messages - PLAYER

| Message | Detail |
| ------ | ------ |
| Join room |This endpoint could be used by a player to join a game room or lobby. |
| Send answers |This endpoint could be used by a player to send their answers or responses to the game tasks or questions.|
| Show players | This endpoint could be used to get a list of the players currently in the game.  |
| No answer | This endpoint could be used to indicate that a player did not provide an answer to a question or task in the game. |

> Host: `--Conect as Host` ws://localhost:8080/host.

> Player: `--Conect as Player` ws://localhost:8080/player.

## Messages Details



### Setup the game

This endpoint could be used to set up the game, such as configuring the number of players, time and rounds.

Message


sh
{
    "rules": {
        "maxPlayers": 2,    // Total of players (Int)
        "points": 2,        // Max points (Int)
        "timerSeconds": 10  // Time of session (Int)
    }
}


Message Response

sh
{
  "status": "GAME_ROOM_CREATED",    // Message (String)
  "data": {
    "code": "EOK5BE",               // Code of session (String)
    "host": {
      "code": "EOK5BE"              // Code of session (String)
    },
    "rules": {
        "maxPlayers": 2,            // Total of players (Int)
        "points": 2,                // Max points (Int)
        "timerSeconds": 10          // Time of session (Int)
    },
    "players": [

    ]
  }
}



### Start round

This endpoint could be used to start a new round of the game.

Message


sh
{
    "event":"START_ROUND"    // Message (String)
}


Message Response

sh
{
  "status": "GAME_ROOM_CREATED",    // Message (String)
  "data": {
    "code": "EOK5BE",               // Code of session (String)
    "host": {
      "code": "EOK5BE"              // Code of session (String)
    },
    "rules": {
        "maxPlayers": 2,            // Total of players (Int)
        "points": 2,                // Max points (Int)
        "timerSeconds": 10          // Time of session (Int)
    },
    "players": [

    ]
  }
}



###  Send point

This endpoint could be used to send a player’s score or points to the server.

Message


sh
{
    "event":"ADD_POINT",            // Message (String)
    "playerIdPoint": "B5MXXTAL"     // Player's ID (String)
}


Message Response

sh
{
  "status": "GAME_ROOM_CREATED",    // Message (String)
  "data": {
    "code": "EOK5BE",               // Code of session (String)
    "host": {
      "code": "EOK5BE"              // Code of session (String)
    },
    "rules": {
        "maxPlayers": 2,            // Total of players (Int)
        "points": 2,                // Max points (Int)
        "timerSeconds": 10          // Time of session (Int)
    },
    "players": [

    ]
  }
}



### No answer

This endpoint could be used to indicate that a player did not provide an answer to a question or task in the game.

Message


sh
{
    "event":"NO_POINTS"     // Message (String)
}


Message Response

sh
{
  "status": "GAME_ROOM_CREATED",    // Message (String)
  "data": {
    "code": "EOK5BE",               // Code of session (String)
    "host": {
      "code": "EOK5BE"              // Code of session (String)
    },
    "rules": {
        "maxPlayers": 2,            // Total of players (Int)
        "points": 2,                // Max points (Int)
        "timerSeconds": 10          // Time of session (Int)
    },
    "players": [

    ]
  }
}



### Join room

This endpoint could be used by a player to join a game room or lobby.

Message


sh
{
    "name": "Sergio",   // Name (String)
    "code": "YQ5IHD"    // Room's ID (String)
}


Message Response

sh
{
  "status": "GAME_ROOM_CREATED",    // Message (String)
  "data": {
    "code": "EOK5BE",               // Code of session (String)
    "host": {
      "code": "EOK5BE"              // Code of session (String)
    },
    "rules": {
        "maxPlayers": 2,            // Total of players (Int)
        "points": 2,                // Max points (Int)
        "timerSeconds": 10          // Time of session (Int)
    },
    "players": [

    ]
  }
}



### Send answers

This endpoint could be used by a player to send their answers or responses to the game tasks or questions.

Message


sh
{  
    "event":"SEND_ANSWER",          // Message (String)
    "answer":{
        "answer": "Response text",      // Answer Text (String)
        "timeStamp": 100,               // Time  (Int)
        "gameCode": "0STDB7"            // Room's ID  (String)
    }
}


Message Response

sh
{
  "status": "GAME_ROOM_CREATED",    // Message (String)
  "data": {
    "code": "EOK5BE",               // Code of session (String)
    "host": {
      "code": "EOK5BE"              // Code of session (String)
    },
    "rules": {
        "maxPlayers": 2,            // Total of players (Int)
        "points": 2,                // Max points (Int)
        "timerSeconds": 10          // Time of session (Int)
    },
    "players": [

    ]
  }
}



### Show players

This endpoint could be used to get a list of the players currently in the game.

Message


sh
{
    "event":"SHOW_PLAYERS"  // Message (String)
}


Message Response

sh
{
  "status": "GAME_ROOM_CREATED",    // Message (String)
  "data": {
    "code": "EOK5BE",               // Code of session (String)
    "host": {
      "code": "EOK5BE"              // Code of session (String)
    },
    "rules": {
        "maxPlayers": 2,            // Total of players (Int)
        "points": 2,                // Max points (Int)
        "timerSeconds": 10          // Time of session (Int)
    },
    "players": [

    ]
  }
}




## License

MIT

*Free Software, KK Yeah!*
