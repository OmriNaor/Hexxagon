package hex;
public class GameTree implements Constants
{
	int turn;
	Board b;
	GameTreeNode root;
	int depth;
	
	public GameTree(Board b, int turn, int depth)
	{
		this.b = b;
		this.turn = turn;
		this.depth = depth;
		this.root = new GameTreeNode(b, turn);
		this.root.expand(this.depth);
	}
	
	public Move getBestMove()
	{
		//root.mini_max();
		//root.summonAlphaBeta(this.depth);
		root.generateBestMove(depth);
		return GameTreeNode.bestMove;
	}
	
}
