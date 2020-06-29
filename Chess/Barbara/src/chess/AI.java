package chess;

import java.util.ArrayList;

public class AI {
	
	int[][] board;
	
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
	}
	
	public float minimax(int[][] state, int depth, int player) {
		// take a game state and outputs a move
		if(depth > 0) depth--;
		
		ArrayList<int[][]> posMoves = new ArrayList<>(); // all the possible moves for the current player
		ArrayList<int[]> positions = new ArrayList<>(); // the positions of every piece owned by the current player
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				int piece = board[j][i];
				
				if(piece * player > 0) {
					int[] pos = {i, j};
					
					positions.add(pos);
					posMoves.addAll(getMoves(pos));
				}
			}
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
	
	public int[][] makeMove(int[][] board, int[][] move) {
		int[][] newBoard = board;
		
		int[] pos1 = move[0];
		int[] pos2 = move[1];
		
		int piece = board[pos1[1]][pos1[0]];
		
		if(isLegal(piece, move)) {
			newBoard[pos1[1]][pos1[0]] = 0;
			newBoard[pos2[1]][pos2[0]] = piece;
		}
		
		return newBoard;
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
			if(pos1[0] == pos2[0] && pos1[1] - pos2[1] == 1 * colour) {
				if(board[pos2[1]][pos2[0]] != 0)
					return false;
				
				return true;
			}
			
			return false;
		case 2: // rook
			return checkOrthogonal(pos1, pos2);
		case 3: // knight
			if(Math.abs(pos1[0] - pos2[0]) == 1) { // up or down
				if(pos1[1] - pos2[1] == 2) { // going up
					if(board[pos2[1]][pos2[0]] != 0)
						return false;
					
					return true;
				} else if(pos1[1] - pos2[1] == -2) { // going down
					if(board[pos2[1]][pos2[0]] != 0)
						return false;
					
					return true;
				}
			} else if(Math.abs(pos1[1] - pos2[1]) == 1) { // left or right
				if(pos1[0] - pos2[0] == 2) { // going left
					if(board[pos2[1]][pos2[0]] != 0)
						return false;
					
					return true;
				} else if(pos1[0] - pos2[0] == -2) { // going right
					if(board[pos2[1]][pos2[0]] != 0)
						return false;
					
					return true;
				}
			}
			
			return false;
		case 4: // bishop
			return checkDiagonal(pos1, pos2);
		case 5: // queen
			return checkOrthogonal(pos1, pos2) ^ checkDiagonal(pos1, pos2);
		case 6: // king
			int dX = pos1[0] - pos2[0];
			int dY = pos1[1] - pos2[1];
			
			if(Math.abs(dX) <= 1 && Math.abs(dY) <= 1) {
				if(board[pos2[1]][pos2[0]] != 0)
					return false;
				
				return true;
			}
			
			return false;
		}
		
		return false;
	}
	
	private boolean checkOrthogonal(int[] pos1, int[] pos2) {
		if(pos1[0] == pos2[0]) {
			if(pos1[1] > pos2[1]) { // going up
				for(int i = pos1[1] - 1; i >= pos2[1]; i--) {
					if(board[i][pos1[0]] != 0)
						return false;
				}
			} else { // going down
				for(int i = pos1[1] + 1; i <= pos2[1]; i++) {
					if(board[i][pos1[0]] != 0)
						return false;
				}
			}
			
			return true;
		} else if(pos1[1] == pos2[1]) {
			if(pos1[0] > pos2[0]) { // going left
				for(int i = pos1[0] - 1; i >= pos2[0]; i--) {
					if(board[pos1[1]][i] != 0)
						return false;
				}
			} else { // going right
				for(int i = pos1[0] + 1; i <= pos2[0]; i++) {
					if(board[pos1[1]][i] != 0)
						return false;
				}
			}
			
			return true;
		}
		
		return false;
	}
	
	private boolean checkDiagonal(int[] pos1, int[] pos2) {
		int dX = pos1[0] - pos2[0];
		int dY = pos1[1] - pos2[1];
		
		if(Math.abs(dX) == Math.abs(dY)) {
			int xDir = dX > 0 ? -1 : 1; // left : right
			
			if(dY > 0) { // up
				int x = pos1[0] + (1 * xDir);
				for(int y = pos1[1] - 1; y >= pos2[1]; y--) {
					if(board[y][x] != 0)
						return false;
						
					x += 1 * xDir;
				}
			} else { // down
				int x = pos1[0] + (1 * xDir);
				for(int y = pos1[1] + 1; y <= pos2[1]; y++) {
					if(board[y][x] != 0)
						return false;
					
					x += 1 * xDir;
				}
			}
			
			return true;
		}
		
		return false;
	}
}