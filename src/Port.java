import java.util.concurrent.Semaphore;

public class Port {
	public int liczbaOczekujacychOsob;
	public Semaphore wolne;
	public Semaphore K;
	public Semaphore P;
	
	public Port(int liczba) {
		this.liczbaOczekujacychOsob = liczba;
		wolne = new Semaphore(1);
		P = new Semaphore(0);
		K = new Semaphore(0);
	}
}
