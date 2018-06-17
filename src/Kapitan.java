import java.util.Random;

public class Kapitan extends Thread{
	private Port port;
	private Most most;
	private Statek statek;
	private Random rand;
	private Wycieczkowiec wyc;
	private boolean go = true;
	
	
	public Kapitan(Port port, Most most, Statek statek, Wycieczkowiec wyc) {
		super();
		this.port = port;
		this.most = most;
		this.statek = statek;
		this.wyc = wyc;
		rand = new Random();
	}
	
	public void run() {
		try {
			while(go) {
		port.K.acquire();
		if (statek.aktualnaLiczbaPasazerow == statek.pojemnoscStatku || (port.liczbaOczekujacychOsob == 0 && most.liczbaOsobOczekujacych == 0)) {
			System.out.println("Statek gotowy, wyp³ywamy!");
			Thread.sleep(rand.nextInt(1000)+200);
			wyc.boatGo();
			for(int i = 0; i < 6; i++) {
				Thread.sleep(rand.nextInt(100)+100);
				System.out.println("P³yniemy!");
			}
			System.out.println("Dop³ynêliœmy, pasa¿erowie wysiadli, wracamy!");
			for(int i = 0; i < 8; i++) {
				Thread.sleep(rand.nextInt(100)+100);
				System.out.println("P³yniemy!");
			}
			for(int i = 0; i < most.pojemnoscMostu; i++) most.wejscie.release();
			for(int i = 0; i < statek.aktualnaLiczbaPasazerow; i++)statek.wejscie.release();
			statek.aktualnaLiczbaPasazerow = 0;
			Thread.sleep(rand.nextInt(1000)+200);
		}
		else if(statek.aktualnaLiczbaPasazerow + most.pojemnoscMostu < statek.pojemnoscStatku) most.wejscie.release();
		else if(most.liczbaOsobOczekujacych == 0 && (most.liczbaOsobOczekujacych + statek.aktualnaLiczbaPasazerow) < statek.pojemnoscStatku) most.wejscie.release();
		else if(most.liczbaOsobOczekujacych == 1 && (most.liczbaOsobOczekujacych + statek.aktualnaLiczbaPasazerow) > statek.pojemnoscStatku - 5) statek.wejscie.release();
		port.P.release();		
		if(port.liczbaOczekujacychOsob == 0 && most.liczbaOsobOczekujacych == 0 && statek.aktualnaLiczbaPasazerow == 0) go = false;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
