import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Main extends JFrame {
	
	public static void main(String[] args) {

		JFrame parentWindow = new JFrame("Wycieczkowiec");
		parentWindow.getContentPane().add(new Wycieczkowiec());
		parentWindow.setSize(1366, 768);
		parentWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		parentWindow.setVisible(true);		
	}
}
