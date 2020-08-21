package chess;

import java.util.Scanner;

public class Test {
	
	// Tic Tac Toe Minimax
	
	public int[][] board = new int[3][3];
	
	public static void main(String[] args) {
		Test test = new Test();
		
		test.run();
		
		/*int[][] b = {{1, 2, 1}, 
				     {1, 2, 2},
				     {2, 1, 1}};
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(b[i][j]);
			}
			
			System.out.println();
		}
		
		System.out.println(test.eval(b));*/
	}
	
	public void run() {
		printBoard();
		
		while(eval(board) == 0) {
			int[] move = bestMove();
			move(move[0], move[1], 1);
			
			printBoard();
			
			if(isOver() | eval(board) != 0)
				break;
			
			System.out.println("Your go");
			playerMove();
			
			printBoard();
		}
	}
	
	public void playerMove() {
		Scanner sc = new Scanner(System.in);
		
		int x = sc.nextInt();
		int y = sc.nextInt();
		
		move(x, y, -2);
	}
	
	public int[] bestMove() {
		int bestScore = -100;
		int[] move = new int[2];
		
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(board[i][j] == 0) {
					board[i][j] = 1;
					int score = minimax(board, 100, false);
					board[i][j] = 0;
					
					if(score > bestScore) {
						bestScore = score;
						move[0] = i;
						move[1] = j;
					}
				}
			}
		}
		
		return move;
	}
	
	public int minimax(int[][] board, int depth, boolean maximizing) {
		int eval = eval(board);
		if(eval != 0 || depth == 0 || isOver()) {
			return eval(board);
		}
		
		if(maximizing) {
			int bestScore = -100;
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(board[i][j] == 0) { // can move here
						board[i][j] = 1;
						int score = minimax(board, depth - 1, false);
						board[i][j] = 0;
						
						bestScore = Math.max(score, bestScore);
					}
				}
			}
			
			return bestScore;
		} else {
			int bestScore = 100;
			
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					if(board[i][j] == 0) {
						board[i][j] = -2;
						int score = minimax(board, depth - 1, true);
						board[i][j] = 0;
						
						bestScore = Math.min(score, bestScore);
					}
				}
			}
			
			return bestScore;
		}
	}
	
	public void move(int x, int y, int p) {
		board[x][y] = p;
	}
	
	public int eval(int[][] board) {
		// a very simple, hard-coded neural network. uses & and | operators. checks for a win in tic tac toe
		int[] input = new int[9];
		
		for(int i = 0; i < input.length; i++) {
			input[i] = board[i/3][i%3];
		}
		
		int[] layer2 = new int[8];
		int[] layer3 = new int[3];
		
		for(int i = 0; i < 3; i++) {
			// horizontal
			layer2[i] = input[i*3] & input[i*3+1] & input[i*3+2];
			// vertical
			layer2[i+3] = input[i] & input[i+3] & input[i+6];
		}
		
		//diagonal
		layer2[6] = input[0] & input[4] & input[8];
		layer2[7] = input[2] & input[4] & input[6];
		
		layer3[0] = layer2[0] | layer2[1] | layer2[2];
		layer3[1] = layer2[3] | layer2[4] | layer2[5];
		layer3[2] = layer2[6] | layer2[7];
		
		int output = layer3[0] | layer3[1] | layer3[2];
		
		return output;
	}
	
	public boolean isOver() {
		for(int i = 0; i < 9; i++) {
			if(board[i / 3][i % 3] == 0)
				return false;
		}
		
		return true;
	}
	
	public void printBoard() {
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				System.out.print(board[i][j]);
				
				if(j < 2)
					System.out.print("|");
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
}