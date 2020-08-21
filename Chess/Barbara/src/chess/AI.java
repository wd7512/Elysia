package chess;

import java.util.ArrayList;
import java.util.HashMap;

public class AI {
	
	private int[][] board;
	private HashMap<int[], Integer> takenPieces;
	private ArrayList<int[][]> moveHistory;
	
	/*
	 * 1 = pawn
	 * 2 = rook
	 * 3 = knight
	 * 4 = bishop
	 * 5 = queen
	 * 6 = king
	 */
	
	public void init(int[][] board) {
		this.board = board;
		takenPieces = new HashMap<>();
		moveHistory = new ArrayList<>();
	}
	
	public int[][] bestMove(int[][] board, int depth, int white) {
		float bestScore = -99999 * white;
		int[][] move = new int[2][2];
		
		ArrayList<int[][]> posMoves = getAllMoves(white > 0);
		
		for(int i = 0; i < posMoves.size(); i++) {
			int[][] currentMove = posMoves.get(i);
			
			makeMove(board, currentMove);
			moveHistory.add(currentMove);
			float score = minimax(board, depth, white < 0);
			moveHistory.remove(currentMove);
			reverseMove(board, currentMove);
			
			if(white > 0 && score > bestScore && !similarMoves(moveHistory)) {
				bestScore = score;
				move = currentMove;
			} else if(white < 0 && score < bestScore && !similarMoves(moveHistory)) {
				bestScore = score;
				move = currentMove;
			}
		}
		
		return move;
	}
	
	public float minimax(int[][] state, int depth, boolean maximizing) {
		// take a game state and outputs a move
		
		if(depth == 0 || isOver(state)) {
			return evaluate(state);
		}
		
		if(maximizing) {
			ArrayList<int[][]> moves = getAllMoves(true);
			
			float bestScore = -99999;
			
			for(int i = 0; i < moves.size(); i++) {
				int[][] move = moves.get(i);
				
				makeMove(state, move);
				moveHistory.add(move);
				float score = minimax(state, depth - 1, false);
				moveHistory.remove(move);
				reverseMove(state, move);
				
				if(!similarMoves(moveHistory))
					bestScore = Math.max(score, bestScore);
			}
			
			return bestScore;
		} else {
			ArrayList<int[][]> moves = getAllMoves(false);
			
			float bestScore = 99999;
			
			for(int i = 0; i < moves.size(); i++) {
				int[][] move = moves.get(i);
				
				makeMove(state, move);
				moveHistory.add(move);
				float score = minimax(state, depth - 1, true);
				moveHistory.remove(move);
				reverseMove(state, move);
				
				if(!similarMoves(moveHistory))
					bestScore = Math.min(score, bestScore);
			}
			
			return bestScore;
		}
	}
	
	public float evaluate(int[][] state) {
		// take a game state and return a value
		
		int[] nKings = new int[2];
		int[] nQueens = new int[2];
		int[] nRooks = new int[2];
		int[] nKnights = new int[2];
		int[] nBishops = new int[2];
		int[] nPawns = new int[2];
		
		int[] nMoves = new int[2];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				int[] pos = {i,j};
				int piece = state[j][i];
				
				switch(piece) {
				case 1:
					nPawns[0]++;
					break;
				case 2:
					nRooks[0]++;
					break;
				case 3:
					nKnights[0]++;
					break;
				case 4:
					nBishops[0]++;
					break;
				case 5:
					nQueens[0]++;
					break;
				case 6:
					nKings[0]++;
					break;
				case -1:
					nPawns[1]++;
					break;
				case -2:
					nRooks[1]++;
					break;
				case -3:
					nKnights[1]++;
					break;
				case -4:
					nBishops[1]++;
					break;
				case -5:
					nQueens[1]++;
					break;
				case -6:
					nKings[1]++;
					break;
				}
				
				if(piece > 0) {
					nMoves[0] += getMoves(pos).size();
				} else if(piece < 0) {
					nMoves[1] += getMoves(pos).size();
				}
			}
		}
		
		float materialScore = 1000 * (nKings[0] - nKings[1]) 
				+ 10 * (nQueens[0] - nQueens[1])
				+ 5.25f * (nRooks[0] - nRooks[1])
				+ 3.5f * (nBishops[0] - nBishops[1])
				+ 3.5f * (nKnights[0] - nKnights[1])
				+ 1 * (nPawns[0] - nPawns[1]);
		
		float mobilityScore = 0.1f * (nMoves[0] - nMoves[1]);
		
		return materialScore + mobilityScore;
	}
	
	public void makeMove(int[][] board, int[][] move) {
		int[] pos1 = move[0];
		int[] pos2 = move[1];
		
		int piece = board[pos1[1]][pos1[0]];
		
		if(isLegal(piece, move)) {
			if(board[pos2[1]][pos2[0]] != 0) {
				takenPieces.put(pos2, board[pos2[1]][pos2[0]]);
			}
			
			board[pos1[1]][pos1[0]] = 0;
			board[pos2[1]][pos2[0]] = piece;
			
			moveHistory.add(move);
		}
	}
	
	public void reverseMove(int[][] board, int[][] move) {
		int[] pos2 = move[0];
		int[] pos1 = move[1];
		
		int piece = board[pos1[1]][pos1[0]];
		
		board[pos1[1]][pos1[0]] = 0;
		board[pos2[1]][pos2[0]] = piece;
		
		if(takenPieces.containsKey(pos1)) {
			board[pos1[1]][pos1[0]] = takenPieces.get(pos1);
		}
		
		if(moveHistory.contains(move))
			moveHistory.remove(move);
	}
	
	public ArrayList<int[][]> getAllMoves(boolean white) {
		ArrayList<int[][]> moves = new ArrayList<>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(white) {
					if(board[j][i] > 0) {
						int[] pos = {i, j};
						moves.addAll(getMoves(pos));
					}
				} else {
					if(board[j][i] < 0) {
						int[] pos = {i, j};
						moves.addAll(getMoves(pos));
					}
				}
			}
		}
		
		return moves;
	}
	
	public ArrayList<int[][]> getMoves(int[] pos) {
		int piece = board[pos[1]][pos[0]];
		
		if(piece == 0) return null;
		
		ArrayList<int[][]> moves = new ArrayList<>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				int[][] move = new int[2][2];
				move[0] = pos;
				move[1][0] = i;
				move[1][1] = j;
				
				if(isLegal(piece, move))
					moves.add(move);
				
				if(move[0][0] == move[1][0] && move[0][1] == move[1][1])
					moves.remove(move);
			}
		}
		
		return moves;
	}
	
	public boolean isLegal(int piece, int[][] move) {
		int colour = piece > 0 ? 1 : -1;
		
		int[] pos1 = move[0];
		int[] pos2 = move[1];
		
		switch(Math.abs(piece)) {
		case 1: // pawn
			if(pos1[0] == pos2[0]) {
				if(pos1[1] - pos2[1] == 1 * colour) {
					if(board[pos2[1]][pos2[0]] != 0)
						return false;
					
					return true;
				} else if(pos1[1] == 3.5 + 2.5 * colour && pos1[1] - pos2[1] == 2 * colour) {
					if(board[pos2[1]][pos2[0]] != 0)
						return false;
					
					return true;
				}
			}
			
			if(Math.abs(pos1[0] - pos2[0]) == 1 && pos1[1] - pos2[1] == 1 * colour && canTake(pos2[0], pos2[1], colour))
				return true;
			
			return false;
		case 2: // rook
			return checkOrthogonal(pos1, pos2, colour);
		case 3: // knight
			if(Math.abs(pos1[0] - pos2[0]) == 1) {
				if(Math.abs(pos1[1] - pos2[1]) == 2) {
					return isEmpty(pos2[0], pos2[1]) | canTake(pos2[0], pos2[1], colour);
				}
			} else if(Math.abs(pos1[1] - pos2[1]) == 1) {
				if(Math.abs(pos1[0] - pos2[0]) == 2) {
					return isEmpty(pos2[0], pos2[1]) | canTake(pos2[0], pos2[1], colour);
				}
			}
			
			return false;
		case 4: // bishop
			return checkDiagonal(pos1, pos2, colour);
		case 5: // queen
			return checkOrthogonal(pos1, pos2, colour) ^ checkDiagonal(pos1, pos2, colour);
		case 6: // king
			int dX = pos1[0] - pos2[0];
			int dY = pos1[1] - pos2[1];
			
			if(Math.abs(dX) <= 1 && Math.abs(dY) <= 1) {
				return isEmpty(pos2[0], pos2[1]) | canTake(pos2[0], pos2[1], colour);
			}
			
			return false;
		}
		
		return false;
	}
	
	private boolean isEmpty(int x, int y) {
		return board[y][x] == 0; // returns true if empty
	}
	
	private boolean canTake(int x, int y, int colour) {
		return board[y][x] * colour < 0; // returns true if different colours
	}
	
	private boolean checkOrthogonal(int[] pos1, int[] pos2, int colour) {
		if(pos1[0] == pos2[0]) {
			if(pos1[1] > pos2[1]) { // going up
				for(int i = pos1[1] - 1; i > pos2[1]; i--) {
					if(!isEmpty(pos2[0], i))
						return false;
				}
				
				if(isEmpty(pos2[0], pos2[1]) || canTake(pos2[0], pos2[1], colour))
					return true;
				else
					return false;
			} else { // going down
				for(int i = pos1[1] + 1; i < pos2[1]; i++) {
					if(!isEmpty(pos2[0], i))
						return false;
				}
				
				if(isEmpty(pos2[0], pos2[1]) || canTake(pos2[0], pos2[1], colour))
					return true;
				else
					return false;
			}
		} else if(pos1[1] == pos2[1]) {
			if(pos1[0] > pos2[0]) { // going left
				for(int i = pos1[0] - 1; i > pos2[0]; i--) {
					if(!isEmpty(i, pos2[1]))
						return false;
				}
				
				if(isEmpty(pos2[0], pos2[1]) || canTake(pos2[0], pos2[1], colour))
					return true;
				else
					return false;
			} else { // going right
				for(int i = pos1[0] + 1; i < pos2[0]; i++) {
					if(!isEmpty(i, pos2[1]))
						return false;
				}
				
				if(isEmpty(pos2[0], pos2[1]) || canTake(pos2[0], pos2[1], colour))
					return true;
				else
					return false;
			}
		}
		
		return false;
	}
	
	private boolean checkDiagonal(int[] pos1, int[] pos2, int colour) {
		int dX = pos1[0] - pos2[0];
		int dY = pos1[1] - pos2[1];
		
		if(Math.abs(dX) == Math.abs(dY)) {
			int xDir = dX > 0 ? -1 : 1; // left : right
			
			if(dY > 0) { // up
				int x = pos1[0] + (1 * xDir);
				for(int y = pos1[1] - 1; y > pos2[1]; y--) {
					if(!isEmpty(x, y))
						return false;
						
					x += 1 * xDir;
				}
				
				if(isEmpty(pos2[0], pos2[1]) || canTake(pos2[0], pos2[1], colour))
					return true;
				else
					return false;
			} else { // down
				int x = pos1[0] + (1 * xDir);
				for(int y = pos1[1] + 1; y < pos2[1]; y++) {
					if(!isEmpty(x, y))
						return false;
					
					x += 1 * xDir;
				}
				
				if(isEmpty(pos2[0], pos2[1]) || canTake(pos2[0], pos2[1], colour))
					return true;
				else
					return false;
			}
		}
		
		return false;
	}
	
	private boolean isOver(int[][] state) {
		int nKings = 0;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(Math.abs(state[i][j]) == 6)
					nKings++;
			}
		}
		
		if(nKings == 2)
			return false;
		else
			return true;
	}
	
	public boolean similarMoves(ArrayList<int[][]> moveHistory) {
		int length = moveHistory.size();
		int inARow = 0;
		
		if(length >= 20) {
			int[][] m = moveHistory.get(length - 1);
			
			for(int i = length - 2; i > length - 20; i--) {
				if(inARow == 3)
					return true;
				
				if(sameMove(m, moveHistory.get(i)))
					inARow++;
			}
		}
		
		return false;
	}
	
	private boolean sameMove(int[][] m1, int[][] m2) {
		return m1[0][0] == m2[0][0] && m1[0][1] == m2[0][1] && m1[1][0] == m2[1][0] && m1[1][1] == m2[1][1];
	}
}