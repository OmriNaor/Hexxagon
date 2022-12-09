package hex;


public abstract class Player {
	protected Board board;
	public final int turn;
	
	
	public abstract Move getMove();

	public void setBoard(Board board) {
		this.board = board;		
	}


	public Player(int turn) {
		this.turn = turn;
	}
	
}
