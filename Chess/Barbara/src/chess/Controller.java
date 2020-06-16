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
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.init();
	}
}