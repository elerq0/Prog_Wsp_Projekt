import java.util.Random;

public class Pasazer extends Thread {
	private Port port;
	private Most most;
	private Statek statek;
	private Random rand;
	private Wycieczkowiec wyc;
	
	
	public Pasazer(String name, Port port, Most most, Statek statek, Wycieczkowiec wyc) {
		super(name);
		this.port = port;
		this.most = most;
		this.statek = statek;
		this.wyc = wyc;
		rand = new Random();
	}
	
	public void WejdzNaMostek() throws InterruptedException {
		Thread.sleep(rand.nextInt(10)+1);
		port.liczbaOczekujacychOsob -= 1;
		most.liczbaOsobOczekujacych += 1;
		wyc.createMinion();
		System.out.println("Pasa¿er nr: " + Thread.currentThread().getName() + " wszedl na mostek, w porcie: " + port.liczbaOczekujacychOsob + 
				", na mostku: " + most.liczbaOsobOczekujacych  + ", na statku: " + statek.aktualnaLiczbaPasazerow);
	}
	
	public void WejdzNaStatek() throws InterruptedException {
		Thread.sleep(rand.nextInt(10)+1);
		most.liczbaOsobOczekujacych -= 1;
		statek.aktualnaLiczbaPasazerow += 1;
		wyc.deleteMinion();
		System.out.println("Pasa¿er nr: " + Thread.currentThread().getName() + " wszedl na statek, w porcie: " + port.liczbaOczekujacychOsob + 
				", na mostku: " + most.liczbaOsobOczekujacych  + ", na statku: " + statek.aktualnaLiczbaPasazerow);
	}
	
	public void run() {
		try {
			most.wejscie.acquire();
			port.wolne.acquire();
			Thread.sleep(rand.nextInt(300)+100);
			WejdzNaMostek();
			port.wolne.release();
			Thread.sleep(rand.nextInt(300)+100);
			statek.wejscie.acquire();
			port.wolne.acquire();
			WejdzNaStatek();
			port.K.release();
			port.P.acquire();
			port.wolne.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}