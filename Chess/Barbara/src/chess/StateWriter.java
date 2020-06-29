package chess;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StateWriter {
	// takes a game state and writes it to a file
	
	private File file;
	private FileWriter fw;
	private PrintWriter pw;
	
	public void init(String path) {
		try {
			file = new File(path);	
			fw = new FileWriter(file, false);
			pw = new PrintWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(int[][] board) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				pw.print(board[i][j]);
				
				if(j != 7)
					pw.print(",");
			}
			
			if(i != 7) pw.println();
		}
		
		pw.close();
	}
}