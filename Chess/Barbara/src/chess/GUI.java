package chess;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import window.Window;

public class GUI extends Window {
	
	public static final int WIDTH = 515;
	public static final int HEIGHT = 600;
	public static final String TITLE = "Barbara";
	
	private int[][] board;
	private float val;
	
	private int sX = 50;
	private int sY = 50;
	private int sW = 50;
	private int sH = 50;
	private int pW = 30;
	private int pH = 30;
	private int xO = (sW - pW) / 2;
	private int yO = (sH - pH) / 2;
	
	public GUI() {
		super.init(WIDTH, HEIGHT, TITLE);
		board = new int[8][8];
	}
	
	public void render(int[][] board, float val) {
		this.board = board;
		this.val = val;
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		for(int i = 0; i < 8; i++) {
			g2d.setColor(Color.BLACK);
			g2d.drawString(Integer.toString(i), sX + i * sW + 25, sY - sH / 4);
			
			for(int j = 0; j < 8; j++) {
				g2d.setColor(Color.BLACK);
				g2d.drawString(Integer.toString(j), sX - sW / 3, sY + j * sH + 25);
				g2d.setColor((i + j) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK);
				g2d.fillRect(sX + i * sW, sY + j * sH, sW, sH);
				
				g2d.setColor(board[j][i] > 0 ? Color.WHITE : new Color(150, 100, 50));
				switch(Math.abs(board[j][i])) {
				case 1:
					g2d.fillOval(getRelX(i) + xO, getRelY(j) + yO, pW, pH);
					break;
				case 2:
					g2d.fillRect(getRelX(i) + xO, getRelY(j) + yO, pW, pH);
					break;
				case 3:
					g2d.fillRect(getRelX(i) + xO * 2, getRelY(j) + yO, pW - xO * 2, pH);
					break;
				case 4:
					int relX = (sX + xO) + i * sW;
					int relY = (sY + yO) + j * sH;
					int[] xPoints = {relX, relX + pW / 2, relX + pW, relX + pW / 2};
					int[] yPoints = {relY + pH / 2, relY, relY + pH / 2, relY + pH};
					int nPoints = 4;
					g2d.fillPolygon(xPoints, yPoints, nPoints);
					break;
				case 5:
					int x = (sX + xO) + i * sW;
					int y = (sY + yO) + j * sH;
					int[] xs = {x, x + 15, x + 30};
					int[] ys = {y + 30, y, y + 30};
					int ns = 3;
					g2d.fillPolygon(xs, ys, ns);
					break;
				case 6:
					int rX = (sX + xO) + i * sW;
					int rY = (sY + yO) + j * sH;
					int[] xPs = {rX, rX + 12, rX + 15, rX + 18, rX + 30, rX + 20, rX + 25, rX + 15, rX + 5, rX + 10};
					int[] yPs = {rY + 12, rY + 12, rY, rY + 12, rY + 12, rY + 17, rY + 30, rY + 20, rY + 30, rY + 17};
					int nPs = 10;
					g2d.fillPolygon(xPs, yPs, nPs);
					break;
				}
			}
		}
		
		g2d.setColor(Color.BLACK);
		g2d.fillRect(50, 500, 15, 30);
		g2d.fillRect(265, 500, 15, 30);
		g2d.fillRect(165, 510, (int) (10 * val), 10);
		g2d.drawString(Float.toString(val), 290, 515);
	}
	
	private int getRelX(int i) {
		return sX + i * sW;
	}
	
	private int getRelY(int i) {
		return sY + i * sH;
	}
}