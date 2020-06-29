package chess;

public class Controller {
	
	public AI agent;
	public StateReader sr;
	public StateWriter sw;
	public String path;
	
	public static int[][] currentBoard;
	
	public void init() {
		agent = new AI();
		sr = new StateReader();
		sw = new StateWriter();
		path = "State.csv";
		
		run();
	}
	
	public void run() {
		sr.init(path);
		sr.read();
		currentBoard = sr.getBoard();
		agent.init(currentBoard);
		
		System.out.println(agent.evaluate(currentBoard));
		
		sw.init(path);
		sw.write(currentBoard);
	}
	
	public static void main(String[] args) {
		Controller c = new Controller();
		c.init();
	}
}