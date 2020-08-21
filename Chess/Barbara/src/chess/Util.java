package chess;

public class Util {
	
	public static int[][] to2DIntArray(String[][] array) {
		int[][] temp = new int[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				temp[i][j] = Integer.parseInt(array[i][j]);
			}
		}
		
		return temp;
	}
	
	public static void printBoard(int[][] board) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				System.out.print(board[i][j]);
			}
			
			System.out.println();
		}
		
		System.out.println();
	}
	
	public static void printMove(int[][] move) {
		System.out.println(move[0][0] + " " + move[0][1] + " -> " + move[1][0] + " " + move[1][1]);
	}
}