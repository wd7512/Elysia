package chess;

public class Controller {
	
	public AI agent;
	public StateReader sr;
	public StateWriter sw;
	
	public static int[][] currentBoard;
	
	public void init() {
		agent = new AI();
		sr = new StateReader();
		sw = new StateWriter();
		
		run();
	}
	
	public void run() {
		sr.init("State.csv");
		sr.read();
		currentBoard = sr.getBoard();
		
		int[][] move = new int[2][2];
		move[0][0] = 3;
		move[0][1] = 3;
		move[1][0] = 4;
		move[1][1] = 2;
		
		Util.printBoard(currentBoard);
		
		agent.init(currentBoard);
		
		System.out.println(agent.isLegal(-6, move));
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.init();
	}
}