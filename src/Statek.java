import java.util.concurrent.Semaphore;

public class Statek {
	public int pojemnoscStatku;
	public int aktualnaLiczbaPasazerow;
	public Semaphore wejscie;

	public Statek(int pojemnosc) {
		this.pojemnoscStatku = pojemnosc;
		this.aktualnaLiczbaPasazerow = 0;
		wejscie = new Semaphore(pojemnoscStatku);
	}
}
