# Adapted from Berkeley CS61B Spring 2018 Project 2: https://sp18.datastructur.es/materials/lab/lab6/lab6

This is a small game which challenges how good the player's memory is.

This game is much like the electronic toy Simon, but on a computer and with a keyboard instead of with 4 colored buttons. The goal of the game will be to type in a randomly generated target string of characters after it is briefly displayed on the screen one letter at a time. The target string starts off as a single letter, but for each successful string entered, the game gets harder by making the target string longer.

For this memory game it would looks something like:

1. Create the game window
2. Randomly generate a target string
3. Display target string on screen one character at a time
4. Wait for player input until they type in as many characters are there are in the target
5. Repeat from step 2 if player input matches the target string except with a longer random target string

To be specific, the game follows the flow:
1. Start the game at round 1
2. Display the message "Round: " followed by the round number in the center of the screen
3. Generate a random string of length equal to the current round number
4. Display the random string one letter at a time
5. Wait for the player to type in a string the same length as the target string
6. Check to see if the player got it correct
	If they got it correct, repeat from step 2 after increasing the round by 1
	If they got it wrong, end the game and display the message "Game Over! You made it to round:" followed by the round number they failed in the center of the screen