package hex;

public class Move implements Constants {
	
	public int row;
	public int col;
	public int currentRow;
	public int currentCol;
	public final int turn;

	public Move(int row, int col, int turn, int currentRow, int currentCol) {
		this.row = row;
		this.col = col;
		this.turn = turn;
		this.currentCol = currentCol;
		this.currentRow = currentRow;
	}


	@Override
	public String toString() {
		return "Move [row=" + row + ", col=" + col + ", currentRow="
				+ currentRow + ", currentCol=" + currentCol + ", turn=" + turn
				+ "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Move))
                    return false;
		Move otherMove = (Move) obj;
		return (otherMove.row == this.row && otherMove.col == this.col);
	}

}
