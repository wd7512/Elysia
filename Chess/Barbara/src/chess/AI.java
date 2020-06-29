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
	
	public void minimax(int[][] state, int depth, int player) {
		// take a game state and outputs a move
		
		
	}
	
	public void evaluate() {
		// take a game state and return a value
	}
	
	public ArrayList<Integer[][]> getMoves(int[] pos) {
		int piece = Math.abs(board[pos[0]][pos[1]]);
		
		if(piece == 0) return null;
		
		
		
		return null;
	}
	
	public boolean isLegal(int piece, int[][] move) {
		// move[0][0] = x1, move[0][1] = y1, move[1][0] = x2, move[1][1] = y2
		
		int colour = piece > 0 ? -1 : 1;
		
		int[] pos1 = move[0];
		int[] pos2 = move[1];
		
		switch(Math.abs(piece)) {
		case 1: // pawn
			if(pos1[0] == pos2[0] && pos1[1] - pos2[1] == -1 * colour) 
				return true;
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