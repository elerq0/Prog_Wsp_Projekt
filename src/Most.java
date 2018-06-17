import java.util.concurrent.Semaphore;

public class Most {
	public int pojemnoscMostu;
	public int liczbaOsobOczekujacych;
	public Semaphore wejscie;
	
	public Most(int pojemnosc) {
		this.pojemnoscMostu = pojemnosc;
		this.liczbaOsobOczekujacych = 0;
		wejscie = new Semaphore(pojemnoscMostu);
		
	}
}
