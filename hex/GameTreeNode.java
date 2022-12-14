package hex;

import java.util.ArrayList;
import java.util.List;


public class GameTreeNode implements Constants
{
	pos p;
	int turn;
	int evaluation;
	Board b;
	List<GameTreeNode> children;
	Move move;
	static int bestValue;
	static Move bestMove;
	
	public GameTreeNode(Board b, pos p, Move move, int turn)
	{
		this.b = b;
		this.move = move;
		this.children = null;
		this.p = p;
		this.turn = turn;
		if(p == pos.minus)
			this.evaluation = Integer.MAX_VALUE;
		else
			this.evaluation = Integer.MIN_VALUE;
	}

	public GameTreeNode(Board b, int turn)
	{
		this.b = b;
		this.p = pos.minus;
		this.children = null;
		this.move = null;
		this.turn = turn;
	}
	public void expand(int depth) 
	{
		if(depth > 0)
		{
			this.children = new ArrayList<GameTreeNode>();
			List<Move> possibleMoves = this.b.getPossibleMoves(this.b.turn);
			
			Board tmp = null;
			
			for(Move m : possibleMoves)
			{
				pos nextState = (this.p == pos.plus ? pos.minus : pos.plus);
				tmp = new Board(this.b);
				tmp.doMove(m);
				int nextTurn = (turn == PLAYER1 ? PLAYER2 : PLAYER1);
				//if(tmp.possibleCompMoves(nextTurn).isEmpty())
					//nextTurn = turn;
				GameTreeNode g = new GameTreeNode(tmp, nextState, m, nextTurn);
				g.expand(depth - 1);
				//this.children.add(new GameTreeNode(tmp, nextState, m, nextTurn));
				this.addSon(g);
			}
		} else{
			this.evaluation = b.evaluate();
		}
	}
	
	private void addSon(GameTreeNode gtn)
	{
		this.children.add(gtn);
		if(gtn.children == null)
			gtn.evaluation = gtn.b.evaluate();
		if(this.p == pos.plus)
			this.evaluation = Math.max(this.evaluation, gtn.evaluation);
		else
			this.evaluation = Math.min(this.evaluation, gtn.evaluation);
	}

	public void generateBestMove(int depth)
	{
		this.expand(depth);
		if(!this.children.isEmpty())
		{
			int eval = this.children.get(0).evaluation;
			GameTreeNode.bestValue = eval;
			GameTreeNode.bestMove = this.children.get(0).move;
			
			for(int i = 1 ; i < this.children.size() ; i++)
			{
				if(this.children.get(i).evaluation >= GameTreeNode.bestValue)
				{
					eval = this.children.get(i).evaluation;
					GameTreeNode.bestValue = eval;
					GameTreeNode.bestMove = this.children.get(i).move;
				}
			}
		}
	}
	
	public int mini_max()
	{
		if(this.children == null)
			return this.b.evaluate();
		
		int bValue, value;
		Move bMove = null;
		if(this.p == pos.plus)
		{
			bValue = Integer.MAX_VALUE;
			for(GameTreeNode tree : this.children)
			{
				value = tree.mini_max();
				if(bValue >= value)
				{
					bValue = value;
					bMove = tree.move;
				}
			}
			GameTreeNode.bestMove = bMove;
			GameTreeNode.bestValue = bValue;
		}
		else
		{ 
			bValue = Integer.MIN_VALUE;
			for(GameTreeNode tree : this.children)
			{
				value = tree.mini_max();
				if(bValue <= value)
				{
					bValue = value;
					bMove = tree.move;
				}
			}
			GameTreeNode.bestMove = bMove;
			GameTreeNode.bestValue = bValue;
		}
		return bValue;
	}
	
	int alphaBetaMax( int alpha, int beta, int depthleft ) {
		   if ( depthleft == 0 )
			   return this.b.evaluate();
		   for (GameTreeNode tree : this.children) {
		      bestValue = alphaBetaMin( alpha, beta, depthleft - 1 );
		      if( bestValue >= beta )
		      {
		    	 bestMove = tree.move;
		         return beta;   // fail hard beta-cutoff
		      }
		      if( bestValue > alpha )
		         alpha = bestValue; // alpha acts like max in MiniMax
		   }
		   return alpha;
		}
		 
		int alphaBetaMin( int alpha, int beta, int depthleft ) {
		   if ( depthleft == 0 )
			   return -this.b.evaluate();
		   for ( GameTreeNode tree : this.children) {
		      bestValue = alphaBetaMax( alpha, beta, depthleft - 1 );
		      if( bestValue <= alpha )
		      {
		    	  bestMove = tree.move;
		         return alpha; // fail hard alpha-cutoff
		      }
		      if( bestValue < beta )
		         beta = bestValue; // beta acts like min in MiniMax
		   }
		   return beta;
		}
		
		public void summonAlphaBeta(int depth)
		{
			this.bestValue  = alphaBetaMax(Integer.MIN_VALUE, Integer.MAX_VALUE, depth);
		}
}
