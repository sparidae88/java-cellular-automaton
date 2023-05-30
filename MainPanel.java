import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable{
	private final int marginx = 50;
	private final int marginy = 50;
	private final int cellNum = 100;
	private int state[][];
	private int state2[][];
	private final int width = 5;
	private Thread thread;
	public static void main(String args[]){
		JFrame frame = new JFrame("CellAutomaton");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MainPanel mp = new MainPanel();
		frame.add(mp);
		frame.setVisible(true);
		frame.pack();
	}
	MainPanel(){
		setPreferredSize(new Dimension(1024,768));
		state = new int[cellNum][cellNum];
		state2 = new int[cellNum][cellNum];
		for(int i=0;i<state.length;i++){
			for(int j=0;j<state[i].length;j++){
				if(Math.random()<0.5)state[i][j] = 1;
			}
		}
		thread = new Thread(this);
		thread.start();
	}
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0,0,1024,768);
		g.setColor(Color.black);
		for (int i = 0;i<state.length;i++){
			for (int j = 0;j<state[i].length;j++){
				if(state[i][j] == 1)g.fillRect(i*width+marginx,j*width+marginy,width,width);
			}
		}
		
	}
	public int getMoore(int i,int j){
		int moore = 0;
		for(int k = 0;k<3;k++){
			for(int l= 0;l<3;l++){
				moore += state[(i+99+k)%100][(j+99+l)%100];
			}
		}
		moore -= state[i][j];
		return moore;
	}
	@Override
	public void run() {
		while(true){
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
			for(int i= 0;i<state.length;i++){
				for(int j=0;j<state[i].length;j++){
					if(state[i][j] == 0){
						if(getMoore(i,j) == 3){
							state2[i][j] = 1;
						}
						else{
							state2[i][j] = 0;
						}
						
					}
					else{
						if(getMoore(i,j) == 3||getMoore(i,j) == 2){
							state2[i][j] = 1;
						}
						else{
							state2[i][j] = 0;
						}
					}
				}
			}
			for(int i=0;i<cellNum;i++){
			state[i] = state2[i].clone();
		}
		repaint();
	}
	}

}
