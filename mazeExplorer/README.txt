This is a small game that requires the player to walk out of a randomly generated maze.

Each time the game is started, a new map is generated from pseudo random numbers. The game is built with the StdDraw library from pixel up; some codes for generating the tiles are borrowed from Berkeley CS61B Project 2 skeleton codes. (TileEngine directory)

User should use "w", "A", "S", "D" to control the moving direction. There is one and only one randomly generated door to the openspace. The user should walk to the door to pass the game.

There are four versions of the game. 
(1) Classic mode: the whole map is shown on the screen. The user simply takes walks to get out of the maze.
(2) Explorer mode: only a part of the map centered at the player position is shown on the screen. the user can select different difficulty levels, where the radius of the visiable map reduces at higher difficulty levels.
(3) Challenge mode: the whole map is shown, but a health evaluation system is introduced. The player's health will drop with time. Besides, there are randomly filled foods and robots. The robots will randomly walk; once it is one-tile away from the player, the player is considered as caught and health will drop by 25%. If the player walks into a food tile, the player will gain health of 10%. Once the player's health drops to 0, the user loose the game. If the user successfully walks out of the exit before health level drops to 0, he wins the game.
(4) Adventure mode: same rule as challenge mode, but similar to explorer mode, only a limited range of vision is shown. The user will be allowed to choose difficulty level which sets the radius of the vision scope.