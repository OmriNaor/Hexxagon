package hex;

import java.util.List;
import java.util.Random;

public class ComputerPlayer extends Player{

	private int depth;
	private Random rnd;
	
	public ComputerPlayer(int depth, Board b) {
		super(b.turn);
		this.depth = depth;
		setBoard(b);
		rnd = new Random();
	}

	@Override
	public Move getMove() {
		Move moveToMake = null;
		
		if(depth == 0 )
		{
			List<Move> possibleMoves = board.getPossibleMoves(this.turn);
			moveToMake = possibleMoves.get(rnd.nextInt(possibleMoves.size()));
		}
		else
		{
			GameTree myTree = new GameTree(board, turn, depth);
			moveToMake = myTree.getBestMove();
		}
		return moveToMake;
	}
	
}
