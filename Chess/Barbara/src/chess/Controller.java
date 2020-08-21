package chess;

import java.util.ArrayList;
import java.util.Scanner;

public class Controller {
	
	public GUI gui;
	public AI agent;
	public StateReader sr;
	public StateWriter sw;
	public String path;
	
	public static int[][] currentBoard;
	
	public ArrayList<int[][]> moveHistory;
	
	public void init() {
		gui = new GUI();
		agent = new AI();
		sr = new StateReader();
		sw = new StateWriter();
		path = "State.csv";
		moveHistory = new ArrayList<>();
		
		run();
	}
	
	public void run() {
		sr.init(path);
		sr.read();
		currentBoard = sr.getBoard();
		agent.init(currentBoard);
		gui.render(currentBoard, agent.evaluate(currentBoard));
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while(true) {
			int[][] m1 = agent.bestMove(currentBoard, 2, 1);
			agent.makeMove(currentBoard, m1);
			//playerMove();
			//System.out.println(agent.similarMoves(moveHistory));
			gui.render(currentBoard, agent.evaluate(currentBoard));
			
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			int[][] m2 = agent.bestMove(currentBoard, 2, -1);
			agent.makeMove(currentBoard, m2);
			//playerMove();
			//System.out.println(agent.similarMoves(moveHistory));
			gui.render(currentBoard, agent.evaluate(currentBoard));
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//sw.init(path);
		//sw.write(currentBoard);
	}
	
	public void playerMove() {
		Scanner sc = new Scanner(System.in);
		
		int[][] move = {{sc.nextInt(), sc.nextInt()}, {sc.nextInt(), sc.nextInt()}};
		
		agent.makeMove(currentBoard, move);
		moveHistory.add(move);
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.init();
	}
}