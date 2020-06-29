package chess;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StateReader {
	// reads a file and outputs a game state (a board)
	
	private File file;
	private Scanner reader;
	private int[][] board;
	
	public void init(String path) {
		try {
			file = new File(path);
			reader = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		board = new int[8][8];
	}
	
	public void read() {
		int x = 0;
		int y = 0;
		
		List<String[]> lines = new ArrayList<String[]>();
		
		while(reader.hasNextLine()) {
			lines.add(reader.nextLine().split(","));
		}
		
		String[][] temp = new String[8][8];
		lines.toArray(temp);
		
		board = Util.to2DIntArray(temp);
	}
	
	public int[][] getBoard() {
		return board;
	}
}