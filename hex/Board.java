/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author omri
 */
public class Board implements Constants {

    private int[][] board;
    private static final Point[] replicateDirections = {new Point(1, 0),
        new Point(1, 1), new Point(0, 1), new Point(-1, 1),
        new Point(-1, 0), new Point(-1, -1), new Point(0, -1),
        new Point(1, -1)};

    private static final Point[] regularDirections = {new Point(2, 0),
        new Point(2, -1), new Point(2, -2), new Point(1, -2),
        new Point(0, -2), new Point(-1, -2), new Point(-2, -2),
        new Point(-2, -1), new Point(-2, 0), new Point(-2, 1), new Point(-2, 2),
        new Point(-1, 2), new Point(0, 2), new Point(1, 2), new Point(2, 2), new Point(2, 1)};

    public int player1Score;
    public int player2Score;
    int turn;
    
    public Board() {
        board = new int[SIZE + WALL_SIZE * 2][SIZE + WALL_SIZE * 2];
        this.turn = PLAYER1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i < WALL_SIZE|| i >= SIZE + WALL_SIZE|| j < WALL_SIZE || j >= SIZE + WALL_SIZE) {
                    board[i][j] = WALL;
                } else {
                    board[i][j] = EMPTY;
                }
            }
        }

        board[2][4] = WALL;
        board[3][4] = WALL;
        board[4][4] = WALL;
        board[5][4] = WALL;
        board[11][4] = WALL;
        board[12][4] = WALL;
        board[13][4] = WALL;
        board[15][4] = WALL;

        board[2][5] = WALL;
        board[3][5] = WALL;
        board[4][5] = WALL;
        board[12][5] = WALL;
        board[13][5] = WALL;
        board[14][5] = WALL;

        board[2][6] = WALL;
        board[3][6] = WALL;
        board[13][6] = WALL;
        board[14][6] = WALL;

        board[2][7] = WALL;
        board[14][7] = WALL;

        board[2][9] = WALL;
        board[14][9] = WALL;

        board[2][10] = WALL;
        board[3][10] = WALL;
        board[13][10] = WALL;
        board[14][10] = WALL;

        board[2][11] = WALL;
        board[3][11] = WALL;
        board[4][11] = WALL;
        board[12][11] = WALL;
        board[13][11] = WALL;
        board[15][11] = WALL;

        board[2][12] = WALL;
        board[3][12] = WALL;
        board[4][12] = WALL;
        board[5][12] = WALL;
        board[11][12] = WALL;
        board[12][12] = WALL;
        board[13][12] = WALL;
        board[14][12] = WALL;

        board[7][SIZE / 2 + 4] = WALL;
        board[8][SIZE / 2 + 3] = WALL;
        board[8][SIZE / 2 + 5] = WALL;
        board[3][7] = EMPTY;
        board[3][8] = EMPTY;
        board[2][8] = PLAYER2;
        board[3][9] = EMPTY;

        board[13][7] = EMPTY;
        board[13][8] = EMPTY;
        board[14][8] = PLAYER1;
        board[13][9] = EMPTY;

        board[WALL_SIZE * 2 + 2][WALL_SIZE] = PLAYER2;
        board[WALL_SIZE * 2 + 2][SIZE + 3] = PLAYER2;
        board[WALL_SIZE  + 2][SIZE + 3] = PLAYER1;
        board[WALL_SIZE  + 2][SIZE + 3] = PLAYER1;
        board[SIZE-3][WALL_SIZE] = PLAYER1;

        player1Score = 3;
        player2Score = 3;
    }
    
    public Board(Board b){
    	this.board = new int[b.board.length][b.board[0].length];
    	this.turn = b.turn;
    	this.player1Score = b.player1Score;
    	this.player2Score = b.player2Score;
    	for(int i=0 ; i<SIZE + WALL_SIZE * 2; i++)
    		for(int j=0; j<SIZE + WALL_SIZE * 2; j++)
    			this.board[i][j] = b.board[i][j];
    }
    
    public int getPos(int row, int col) {
        return this.board[row][col];
    }

    public String toString() {
        String s = "";
        for (int i = 0; i < SIZE + WALL_SIZE * 2; i++) {
            for (int j = 0; j < SIZE + WALL_SIZE * 2; j++) {
                s += board[i][j] + " ";
            }
            s += "\n";
        }

        return s;
    }

    public void setPlayer(int row, int col, int player) {
        this.board[row][col] = player;
    }

    public boolean isValidMoveReplicate(Move m) {
    	if(board[m.row][m.col] == EMPTY)
    	{
        for (Point dir : replicateDirections)
            if (dir.getX() + m.currentCol == m.col && dir.getY() + m.currentRow == m.row)
                return true;
    	}
        return false;
    }

    public void addScore(int score, int player)
    {
        if(player == PLAYER1)
            player1Score += score;
        else
            player2Score += score;
    }
    public boolean isValidMoveRegular(Move m) {
    	if(board[m.row][m.col] == EMPTY)
    	{
        for (Point dir : regularDirections)
            if (dir.getX() + m.currentCol == m.col && dir.getY() + m.currentRow == m.row)
                return true;
    	}
        return false;
    }

    public List<Move> getPossibleMoves(int turn) {
    	List<Move> possibleMoves = new ArrayList<Move>();
    	Move tmpMove;
    	for(int i=WALL_SIZE / 2; i< SIZE + WALL_SIZE * 2; i++)
    		for(int j= WALL_SIZE; j< SIZE + WALL_SIZE * 2; j++)
    		{
    			if(this.board[i][j] == turn)
    			{
    				for (Point dir : regularDirections)
    				{
    					 tmpMove = new Move(dir.y + i,dir.x + j,turn, i, j);
    					 if(isValidMoveRegular(tmpMove))
    						 possibleMoves.add(tmpMove);
    				}
    				for (Point dirR : replicateDirections)
    				{
    					 tmpMove = new Move(dirR.y + i,dirR.x + j,turn, i, j);
    					 if(isValidMoveReplicate(tmpMove))
    						 possibleMoves.add(tmpMove);
    				}
    			}
    		}
    	return possibleMoves;
    }
    
     public int getScore(int player)
    {
         if(player == PLAYER1)
             return player1Score;
         return player2Score;
    }
     
     public void doMove(Move m)
     {
    	 if(isValidMoveReplicate(m))
    		 this.addScore(1, m.turn);
    	 else
    		 this.setPlayer(m.currentRow, m.currentCol, EMPTY);
    	 this.setPlayer(m.row, m.col, m.turn);
    	 this.changeNeighbors(m.turn, m.row, m.col);
     }
     
    public void changeNeighbors(int player, int row, int col)
    {
        if(player == PLAYER2)
        {
         for (Point dir : replicateDirections)
             if (board[(int)dir.getY() + row][(int)dir.getX() + col] == PLAYER1)
             {
                  board[(int)dir.getY() + row][(int)dir.getX() + col] = PLAYER2;
                  player2Score++;
                  player1Score--;
             }
        }
        else
        {
           for (Point dir : replicateDirections)
             if (board[(int)dir.getY() + row][(int)dir.getX() + col] == PLAYER2)
             {
                  board[(int)dir.getY() + row][(int)dir.getX() + col] = PLAYER1;
                  player1Score++;
                  player2Score--;
              }
        }    
    }
    
    public int countPlayersNum(int turn)
    {
    	int count = 0;
    	for(int i=0; i<SIZE + WALL_SIZE * 2; i++)
    		for(int j=0; j<SIZE + WALL_SIZE * 2; j++)
    			if(board[i][j] == turn)
    				count++;
    	return count;
    }
    
    public int checkEnd()
    {
    	if(player2Score == 0 || (player1Score + player2Score ==  FULL_BOARD && player1Score > player2Score))
    		return PLAYER1;
    	if((player1Score + player2Score ==  FULL_BOARD && player1Score < player2Score) || player1Score == 0)
    		return PLAYER2;
    	return -1;
    }
    
    public boolean isNearWall(int turn)
    {
    	for(int i=WALL_SIZE / 2; i< SIZE + WALL_SIZE * 2; i++)
    		for(int j= WALL_SIZE; j< SIZE + WALL_SIZE * 2; j++)
    			if(board[i][j] == turn && (board[i-1][j] == WALL || board[i+1][j] == WALL || board[i][j+1] == WALL || board[i][j-1] == WALL))
    				return true;
    	return false;
    }
    
    public boolean isHole(int surPlayer)
    {
    	int m = 0;
    	for(int i=0; i< SIZE + WALL_SIZE * 2; i++)
    		for(int j= 0; j< SIZE + WALL_SIZE * 2; j++)
    		{
    			if(board[i][j] == EMPTY)
    				for (Point dir : replicateDirections)
    		             if (board[(int)dir.getY() + i][(int)dir.getX() + j] == surPlayer)
    		             {
    		                 m++;
    		             }
    			if(m >= 4)
    			{
    				return true;
    			}
    			m = 0;
    		}
    	
    	return false;
    }
    
    public boolean isSurrounded(int player, int surPlayer)
    {
    	int m = 0;
    	for(int i=0; i< SIZE + WALL_SIZE * 2; i++)
    		for(int j= 0; j< SIZE + WALL_SIZE * 2; j++)
    		{
    			if(board[i][j] == player)
    				for (Point dir : replicateDirections)
    		             if (board[(int)dir.getY() + i][(int)dir.getX() + j] == surPlayer)
    		             {
    		                 m++;
    		             }
    			if(m >= 4)
    			{
    				return true;
    			}
    			m = 0;
    		}
    	
    	return false;
    }
    
    public int evaluate()
    {
    	int opponent = (this.turn == PLAYER1 ? PLAYER2 : PLAYER1); 
    	int value = this.countPlayersNum(turn) - this.countPlayersNum(opponent);
    	if((getScore(opponent)< getScore(turn) && player1Score + player2Score ==  FULL_BOARD) || getScore(opponent) == 0) // ????? ??????
    		value = Integer.MAX_VALUE;
    	if(isHole(this.turn)) // ?????, ?? ????? ????? ?????? ?? ?????? 4 ?????? ?????
    		value = Integer.MIN_VALUE; 
    	return value;
    }
}
