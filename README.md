# Java Project: Hexxagon

## About

Play against your PC in a typical Hexxagon board game. The objective is to fill the board with more pawns than the enemy.
PC is using the min-max algorithm in order to calculate the best movements and win the game.

## Structure

- `Main` - Main function definition. Creates the window using SwingUtilities.
- `Board` - Creates the the board and sets default values (e.g. player location). Additionally, manages the game rules and functionality.
- `Constants` - An interface defining the game constants.
- `GameTreeNode` - A node in the game tree. Each node is a game status. Here's where the game gets expanded to different directions and the best move (for the PC to win) is generated
- `GameTree` - Holds the game's different outcomes and generates the best move by using a GameTreeNode.
- `HexxagonWindow` - Creates and handles the game's layout including buttons, labels, frame and menu.

## Examples

[YouTube Video](https://www.youtube.com/watch?v=okX18jRizTQ)

![image](https://user-images.githubusercontent.com/106623821/206726778-b9c235d4-8fff-4433-a9cd-0faa687ca25f.png)
