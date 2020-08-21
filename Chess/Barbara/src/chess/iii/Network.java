package chess.iii;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;
import java.util.Scanner;

import window.Window;

public class Network extends Window {
	
	// a network of nodes, mapped by connections
	
	private int networkSize;
	private int[] layerSizes;
	private float[][][] weights;
	private float[][] biases; // potentially replaceable by removing or adding connections
	
	public Network(int[] lS) {
		networkSize = lS.length;
		layerSizes = lS;
		
		weights = new float[networkSize][][];
		biases = new float[networkSize][];
		
		for(int i = 1; i < networkSize; i++) {
			weights[i] = new float[layerSizes[i]][layerSizes[i-1]]; // weights[0][][] doesn't need to be defined as layer 0 is the input layer
			biases[i] = new float[layerSizes[i]]; // biases[0][] also doesn't need to be defined(?)
		}
	}
	
	private static float[] IN;
	private static int[] OUT;
	
	public static void main(String[] args) {
		int[] lS = {64, 20, 15, 5, 6};
		Network net = new Network(lS);
		net.initRandomNetwork();
		net.init(800, 600, "Network");
		
		IN = new float[lS[0]];
		
		for(int i = 0; i < IN.length; i++) {
			IN[i] = Math.random() < 0.5 ? 1 : 0;
		}
		
		OUT = new int[lS[lS.length-1]];
		
		net.run();
	}
	
	// see if you could train the network to replicate the decode function, i.e. if it can encode a unique meaning to a specific state
	// trivia, there are 2^64 possible states, but only 64 unique meanings, so each meaning can be represented by (2^64)/64 different states, which is 2.88*10^17(!!!)
	
	public void run() {
		while(true) {
			Scanner sc = new Scanner(System.in);
			int a = sc.nextInt();
			
			IN[a] = IN[a] == 1 ? 0 : 1;
			float[] out = calculate(IN);
			
			for(int i = 0; i < out.length; i++) {
				OUT[i] = (int) out[i];
			}
			
			OUT = decode(IN);
			
			for(int i = 0; i < OUT.length; i++)
				System.out.print(OUT[5-i]);
			System.out.println();
				
			repaint();
		}
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				switch((int) IN[i*8+j]) {
				case 0:
					g.setColor(Color.DARK_GRAY);
					break;
				case 1:
					g.setColor(Color.LIGHT_GRAY);
					break;
				}
				
				int s = 0;
				
				for(int k = 0; k < 6; k++) {
					if(OUT[k] == 1) s += Math.pow(2, k);
				}
				
				if(i*8+j == s) g.setColor(Color.RED);
				
				g.fillRect(i*50, j*50, 50, 50);
				
				g.setColor(Color.BLACK);
				g.drawRect(i*50, j*50, 50, 50);
			}
		}
	}
	
	public int[] decode(float[] input) {
		int[] output = new int[6];
		
		int parity = 0; // sum of a set of squares mod(2)
		int parity2 = 0;
		for(int i = 1; i < 8; i += 2) {
			for(int j = 0; j < 8; j++) {
				parity += input[i*8+j];
				parity2 += input[j*8+i];
			}
		}
		output[0] = parity % 2;
		output[3] = parity2 % 2;
		
		parity = 0;
		parity2 = 0;
		for(int i = 2; i < 8; i += 4) {
			for(int j = 0; j < 8; j++) {
				parity += input[i*8+j];
				parity += input[(i+1)*8+j];
				parity2 += input[j*8+i];
				parity2 += input[j*8+(i+1)];
			}
		}
		output[1] = parity % 2;
		output[4] = parity2 % 2;
		
		parity = 0;
		parity2 = 0;
		for(int i = 4; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				parity += input[i*8+j];
				parity2 += input[j*8+i];
			}
		}
		output[2] = parity % 2;
		output[5] = parity2 % 2;
		
		return output;
	}
	
	public float[] calculate(float[] input) {
		float[] output = new float[layerSizes[networkSize-1]];
		
		float[][] network = new float[networkSize][];
		for(int i = 0; i < networkSize; i++) {
			network[i] = new float[layerSizes[i]];
		}
		
		network[0] = input;
		
		for(int l = 1; l < networkSize; l++) {
			for(int n = 0; n < layerSizes[l]; n++) {
				float sum = biases[l][n];
				
				for(int pn = 0; pn < layerSizes[l-1]; pn++) {
					sum += network[l-1][pn] * weights[l][n][pn];
					
					network[l][n] = function(sum);
					if(l == networkSize-1)
						network[l][n] = step(sum);
				}
			}
		}
		
		output = network[networkSize-1];
		
		return output;
	}
	
	private float function(float x) {
		return (float) (1 / (1+Math.exp(-4.9*x+2.5)));
	}
	
	private float step(float x) {
		return x < 0 ? 1 : 0;
	}
	
	public void initRandomNetwork() {
		Random r = new Random();
		
		for(int l = 1; l < networkSize; l++) {
			for(int n = 0; n < layerSizes[l]; n++) {
				biases[l][n] = r.nextFloat();
				
				for(int pn = 0; pn < layerSizes[l-1]; pn++) {
					weights[l][n][pn] = r.nextFloat() * 2 - 1;
				}
				
			}
		}
	}
	
	public void initLoadNetwork(int nS, int[] lS, float[][][] w, float[][] b) {
		networkSize = nS;
		layerSizes = lS;
		weights = w;
		biases = b;
	}
}