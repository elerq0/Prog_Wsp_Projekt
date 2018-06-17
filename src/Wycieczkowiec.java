import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class Wycieczkowiec extends JPanel implements Runnable {

	int pojemnoscStatku = 130;
	int pojemnoscMostku = 37;
	int oczekujacychNaRejs = 400;
	Port port;
	Statek statek;
	Most most;
	Thread[] watek;
	int width;
	int height;

	int minionPossibleHeight[] = new int[] { 334, 359, 384, 409 };
	int minionPossibleWidth[] = new int[] { 350, 325, 300, 275, 250, 225, 200, 175, 150, 125, 100, 75, 50, 25 };
	int lenght = 56;
	int minionX[] = new int[lenght];
	int minionY[] = new int[lenght];
	int minionS[] = new int[lenght];
	

	private int boatState = 0;
	private int boatX = 400;

	public Wycieczkowiec() {

		width = 1366;
		height = 768;

		port = new Port(oczekujacychNaRejs);
		statek = new Statek(pojemnoscStatku);
		most = new Most(pojemnoscMostku);
		watek = new Thread[oczekujacychNaRejs + 1];
	
		for (int i = 0; i < oczekujacychNaRejs + 1; i++) {
			if (i == 0)
				watek[i] = new Kapitan(port, most, statek, this);
			else
				watek[i] = new Pasazer("" + i, port, most, statek, this);
			watek[i].start();
		}
		
		for(int i = 0; i<lenght; i++) {
			minionX[i] = 0;
			minionY[i] = 0;
			minionS[i] = 0;
		}

		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {

		while (true) {
			if(boatState == 0) boatX = 400;
			else if(boatState == 1) {
				boatX += 5;
				if(boatX > 1366) boatState = 2;
			}
			else if(boatState == 2) {
				boatX -= 5;
				if(boatX < 400) boatState = 3;
			}
			else if(boatState == 3) {
				for(int i = 0; i<lenght; i++) {
					minionX[i] = 0;
					minionY[i] = 0;
					minionS[i] = 0;
				}
				boatState = 0;
			}
			
			for(int i = 0; i<lenght; i++) {
				if(minionS[i] == 0) {
					minionX[i] = 0;
					minionY[i] = 0;
				}
				else if(minionS[i] == 1) {
					minionX[i] += 5;
					if(minionX[i] > minionPossibleWidth[i/4]) {
						minionX[i] = minionPossibleWidth[i/4];
						minionS[i] = 2;
					}
					minionY[i] = minionPossibleHeight[i%4];
				}
				else if(minionS[i] == 2) {;}
				else if(minionS[i] == 3) {
					minionX[i] += 5;
					if(minionX[i] > boatX) {
						minionX[i] = boatX;
						minionS[i] = 0;
					}
				}
			}
			
			for(int i = 0; i<lenght; i++) {
				if(minionS[i] == 2 && i > 3) {
					if(minionS[i-4] == 0) {
						minionS[i-4] = 1;
						minionX[i-4] = minionX[i];
						minionS[i] = 0;
					}
				}
			}
			
			repaint();
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paint(Graphics e) {
		drawBackground(e);
		e.setColor(Color.WHITE);
		e.fillRect(boatX, height / 2 - 50, 200, 100);
		
		if(boatState == 0) {
			e.setColor(Color.RED);
			e.fillOval(380, 309, 10, 10);
			
			e.setColor(Color.GREEN);
			for(int i = 0; i<lenght; i++) {
				if(minionS[i] != 0) {
					e.fillOval(minionX[i], minionY[i], 10, 10);
				}
			}
		}

	}

	public void drawBackground(Graphics e) {
		e.setColor(Color.BLUE);
		e.fillRect(0, 0, width, height);

		e.setColor(Color.GRAY);
		e.fillRect(0, height / 2 - 100, 400, 200);

	}
	
	public void createMinion() {
		boolean end = false;
		int i = 0;
		while(!end) {
			if(minionS[i] == 0) {
				minionS[i] = 1;
				end = true;
			}
			else i+= 1;
			if(i>lenght-1) i = 0;
		}
	}
	
	public void deleteMinion() {
		boolean end = false;
		int i = 0;
		while(!end) {
			if(minionS[i] == 2) {
				minionS[i] = 3;
				end = true;
			}
			else {
				i+= 1;
				if(i>lenght-1) i = 0;
			}
		}
	}
		
	public void boatGo() {
		boatState = 1;
	}
}